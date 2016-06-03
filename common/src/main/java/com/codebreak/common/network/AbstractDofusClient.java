package com.codebreak.common.network;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.network.handler.exception.EmptyStateException;
import com.codebreak.common.network.handler.exception.UnhandledMessageException;
import com.codebreak.common.network.handler.impl.MessageProcessor;

public abstract class AbstractDofusClient<T> extends AbstractTcpClient<T> {
	
	private final AbstractExecutorService service;
	protected final MessageProcessor<T> processor;
	private final StringBuilder messageBuilder;
	private Future<?> currentTask;
	
	public AbstractDofusClient(
		final int identity, 
		final ByteBuffer buffer, 
		final AsynchronousSocketChannel channel, 
		final AbstractExecutorService service,
		final NetworkState<T> initialNetworkState
	) {
		super(identity, buffer, channel);
		this.messageBuilder = new StringBuilder();
		this.service = service;
		this.processor = new MessageProcessor<T>(initialNetworkState);
	}
	
	public final MessageProcessor<T> processor() {
		return this.processor;
	}

	@Override
	public void handleIncomming(final int count, final ByteBuffer buffer) {
		char current = 0x00;
		for(int i = 0; i < count; i++) {
			current = (char)buffer.get();
			if(current == 0x00) {
				this.processMessage(this.messageBuilder.toString());
				this.messageBuilder.setLength(0);
			}
			else {
				if(current != '\n') {
					this.messageBuilder.append(current);
				}
			}
		}
	}
	
	private void processMessage(final String message) {
		if(currentTask != null) {
			try {
				// synchronize until current task end
				currentTask.get(5, TimeUnit.SECONDS);
			} 
			catch (final InterruptedException e) {
				e.printStackTrace();
			} 
			catch (final ExecutionException e) {
				e.printStackTrace();
			} 
			catch (final TimeoutException e) {
				LOGGER.error("**task timeout**");
				e.printStackTrace();
			}
		}
		LOGGER.debug("recv << " + message);
		currentTask = this.service.submit(() -> this.executeMessage(message));
	}
	
	@SuppressWarnings("unchecked")
	private void executeMessage(final String message) {
		try {
			this.processor.process((T)this, message);
		} 
		catch (final EmptyStateException e) {
			LOGGER.info("empty state for client while trying to parse message : " + e.getMessage());
		}
		catch(final UnhandledMessageException e) {
			LOGGER.info("unknow message received in wrong state : " + e.getMessage());
		}
		catch(final ExecutionException e) {		
			e.printStackTrace();
		}
	}
}
