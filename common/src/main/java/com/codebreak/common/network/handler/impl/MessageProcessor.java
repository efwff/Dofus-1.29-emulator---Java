package com.codebreak.common.network.handler.impl;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.handler.AbstractMessageHandler;
import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.network.handler.exception.EmptyStateException;
import com.codebreak.common.network.handler.exception.UnhandledMessageException;

public final class MessageProcessor<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);
	
	private static final int TASK_TIMEOUT = 5;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;

	private Future<?> currentTask;
	private Optional<NetworkState<T>> currentState;
	
	public MessageProcessor(final NetworkState<T> initialState) {
		this.currentState = Optional.of(initialState);
		this.currentState.get().enter();
	}
	
	public void processInStateContext(final T client, final String message) throws InterruptedException, ExecutionException, EmptyStateException, TimeoutException {

		if(!this.currentState.isPresent()) {
			throw new EmptyStateException();
		}
		
		final NetworkState<T> current = this.currentState.get();
		
		if(this.currentTask != null) {
			LOGGER.info("waiting last message to be processed");
			this.currentTask.get(TASK_TIMEOUT, TIMEOUT_UNIT);
		}
		
		this.currentTask = current.context().submit(() -> {
			LOGGER.info("processing message");
			try {
				this.processInternal(client, message);
			} 
			catch(final UnhandledMessageException e) {
				LOGGER.debug("unhandled message : " + e.getMessage());
			}
			catch (final ExecutionException e) {
				LOGGER.debug("failed to process message", e);
			}
		});
	}
	
	private void processInternal(final T client, final String message) throws UnhandledMessageException, ExecutionException {	
		
		final NetworkState<T> current = this.currentState.get();
		
		final Optional<AbstractMessageHandler<T>> handler = this.currentState.get().handler(message);		
		if(!handler.isPresent()) {
			throw new UnhandledMessageException(message);
		}	
		
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
