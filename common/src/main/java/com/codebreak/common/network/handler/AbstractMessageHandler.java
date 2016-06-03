package com.codebreak.common.network.handler;

import java.util.Optional;
import java.util.function.BiFunction;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.persistence.Database;

public abstract class AbstractMessageHandler<T> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageHandler.class);
	
	private final NetworkState<T> parent;
	private final Database db;
	private final int id;
	
	public AbstractMessageHandler(final NetworkState<T> parent, final Database db, final int id) {
		this.parent = parent;
		this.id = id;
		this.db = db;
	}
	
	public final Database db() {
		return this.db;
	}
	
	public final NetworkState<T> parent() {
		return this.parent;
	}
	
	public final DSLContext dbContext() {
		return this.db.context();
	}
	
	public final int id() {
		return this.id;
	}
	
	public boolean canHandle(final String message) {
		return getHandler(message).isPresent();
	}
	
	public Optional<NetworkState<T>> handle(final T client, final String message) {
		final Optional<BiFunction<T, String, Optional<NetworkState<T>>>> handler = getHandler(message);
		if(handler.isPresent()) {
			return handler.get().apply(client, message);
		}
		return Optional.empty();
	}

	protected <U extends NetworkState<T>> Optional<U> next(final U nextState) {
		return Optional.of(nextState);
	}
	
	@SuppressWarnings("unchecked")
	protected <U extends NetworkState<T>> Optional<U> stay() {
		return next((U)this.parent);
	}
	
	protected <U extends NetworkState<T>> Optional<U> fail() {
		return Optional.empty();
	}

	protected abstract <U extends NetworkState<T>> Optional<BiFunction<T, String, Optional<U>>> getHandler(final String message);
}
