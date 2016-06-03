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

	@SuppressWarnings("unused")
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
		
		final Optional<AbstractMessageHandler<T>> handler = 
				this.currentState.get().handler(message);		
		if(!handler.isPresent()) {
			throw new UnhandledMessageException(message);
		}
		try {
			final Optional<NetworkState<T>> nextState = 
					handler
					.get()
					.handle(client, message);
			
			// going empty state or staying in the same one
			if(!nextState.isPresent() || !nextState.get().equals(this.currentState.get())) {
				this.currentState.get().exit();
			}
			
			this.currentState = nextState;		
			
			// new state is empty ?
			if(!this.currentState.isPresent()) {
				throw new EmptyStateException();
			}
			
			this.currentState.get().enter();
		} 
		catch(final EmptyStateException e) {
			throw e;
		}
		catch(final Exception e) {
			throw new ExecutionException("failed to handle the message : " + message, e); 
		}
	}
}
