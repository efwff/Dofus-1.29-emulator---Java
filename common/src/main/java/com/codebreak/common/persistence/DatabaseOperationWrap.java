package com.codebreak.common.persistence;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseOperationWrap<T> implements DatabaseOperation<T> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(DatabaseOperationWrap.class);
	
	private final DatabaseOperation<T> origin;
	
	public DatabaseOperationWrap(final DatabaseOperation<T> origin) {
		this.origin = origin;
	}
	
	@Override
	public Optional<T> fetch() throws Exception {
		return this.origin.fetch();
	}
}
