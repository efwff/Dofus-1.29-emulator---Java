package com.codebreak.common.persistence;

import java.util.Optional;

import org.jooq.DSLContext;

public abstract class AbstractDatabaseOperation<T> implements Operation<T> {	
	private final Database database;	
	public AbstractDatabaseOperation(final Database database) {
		this.database = database;
	}
	public Optional<T> fetch() {
		try(final DSLContext context = this.context()) {
			return fetchInternal(context);
		}
	}
	protected DSLContext context() {
		return this.database.context();
	}
	protected abstract Optional<T> fetchInternal(final DSLContext context);
}
