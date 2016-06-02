package com.codebreak.common.network.handler;

import java.util.Optional;
import java.util.function.BiConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.AbstractDofusClient;

public abstract class AbstractMessageHandler<T extends AbstractDofusClient<T>> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);
	
	private final int id;
	
	public AbstractMessageHandler(final int id) {
		this.id = id;
	}
	
	public final int id() {
		return this.id;
	}
	
	protected abstract Optional<BiConsumer<T, String>> getHandler(final String message);
	public boolean canHandle(final String message) {
		return getHandler(message).isPresent();
	}
	public boolean handle(final T client, final String message) {
		final Optional<BiConsumer<T, String>> handler = getHandler(message);
		if(handler.isPresent()) {
			handler.get().accept(client, message);
		}
		return handler.isPresent();
	}
}
