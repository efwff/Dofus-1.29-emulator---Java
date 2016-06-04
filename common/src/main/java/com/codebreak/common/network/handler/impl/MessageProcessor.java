package com.codebreak.common.network.handler.impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.handler.AbstractMessageHandler;
import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.network.handler.exception.EmptyStateException;
import com.codebreak.common.network.handler.exception.UnhandledMessageException;

public final class MessageProcessor<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

	private Optional<NetworkState<T>> currentState;
	
	public MessageProcessor(final NetworkState<T> initialState) {
		this.currentState = Optional.of(initialState);
		this.currentState.get().enter();
	}
	
	public void process(final T client, final String message) throws EmptyStateException, UnhandledMessageException, ExecutionException {	
		
		if(!this.currentState.isPresent()) {
			throw new EmptyStateException();
		}
		
		final Optional<AbstractMessageHandler<T>> handler = this.currentState.get().handler(message);		
		if(!handler.isPresent()) {
			throw new UnhandledMessageException(message);
		}

		final NetworkState<T> current = this.currentState.get();
		
		try {
			final Optional<NetworkState<T>> nextState = 
					handler
					.get()
					.handle(client, message);
						
			if(nextState.isPresent()) {
				final NetworkState<T> next = nextState.get();
				if(!next.equals(current)) {
					LOGGER.debug(
						"netstate changed, current={}, next={}",
						current.getClass().getName(),
						next.getClass().getName()
					);
					current.exit();
					next.enter();
				}	
			}
			
			this.currentState = nextState;
		} 
		catch(final Exception e) {
			throw new ExecutionException("failed to handle the message : " + message, e); 
		}
	}
}
