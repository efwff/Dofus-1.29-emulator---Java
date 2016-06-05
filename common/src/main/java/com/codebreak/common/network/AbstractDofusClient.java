package com.codebreak.common.network;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.network.handler.exception.EmptyStateException;
import com.codebreak.common.network.handler.impl.MessageProcessor;

public abstract class AbstractDofusClient<T> extends AbstractTcpClient<T> {
	
	protected final MessageProcessor<T> processor;
	private final StringBuilder messageBuilder;
	
	public AbstractDofusClient(
		final int identity, 
		final ByteBuffer buffer, 
		final AsynchronousSocketChannel channel,
		final NetworkState<T> initialNetworkState
	) {
		super(identity, buffer, channel);
		this.messageBuilder = new StringBuilder();
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
	
	@SuppressWarnings("unchecked")
	private void processMessage(final String message) {
		LOGGER.debug("recv << " + message);
		try {
			this.processor.processInStateContext((T)this, message);
		} 
		catch(final TimeoutException e) {
			LOGGER.info("message execution timeout", e);
		}
		catch (final EmptyStateException e) {
			LOGGER.info("message received empty state : " + e.getMessage());
		}
		catch(final InterruptedException e) {
			LOGGER.info("interruption on message execution : " + e.getMessage());
		}
		catch(final ExecutionException e) {		
			e.printStackTrace();
		}
	}
}
