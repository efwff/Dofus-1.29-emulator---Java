package com.codebreak.common.network;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.codebreak.common.network.handler.AbstractMessageProcessor;

public abstract class AbstractDofusClient<T extends AbstractDofusClient<T>> extends AbstractTcpClient<T> {
	
	private final AbstractExecutorService service;
	protected final AbstractMessageProcessor<T> processor;
	private final StringBuilder messageBuilder;
	private Future<?> currentTask;
	
	public AbstractDofusClient(
		final int identity, 
		final ByteBuffer buffer, 
		final AsynchronousSocketChannel channel, 
		final AbstractExecutorService service,
		final AbstractMessageProcessor<T> processor
	) {
		super(identity, buffer, channel);
		this.messageBuilder = new StringBuilder();
		this.service = service;
		this.processor = processor;
	}
	
	public final AbstractMessageProcessor<T> processor() {
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
	
	@SuppressWarnings("unchecked")
	private void processMessage(final String message) {
		if(currentTask != null) {
			try {
				// synchronize until current task end
				currentTask.get(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				LOGGER.error("**task timeout**");
				e.printStackTrace();
			}
		}
		LOGGER.debug("recv << " + message);
		currentTask = this.service.submit(() -> this.processor.process((T)this, message));
	}
}
