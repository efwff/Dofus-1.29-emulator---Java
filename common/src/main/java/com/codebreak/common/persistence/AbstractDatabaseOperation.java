package com.codebreak.common.persistence;

import java.util.Optional;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDatabaseOperation<T> implements Operation<T>  {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabaseOperation.class);
	
	protected final Database database;
	
	public AbstractDatabaseOperation(final Database database) {
		this.database = database;
	}

	protected DSLContext context() {
		return this.database.context();
	}
	
	public abstract Optional<T> fetch();
	protected abstract Optional<T> fetchInternal(final DSLContext context);
}
