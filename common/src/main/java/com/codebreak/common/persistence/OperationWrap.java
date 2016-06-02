package com.codebreak.common.persistence;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationWrap<T> implements Operation<T> {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(OperationWrap.class);
	
	private final Operation<T> origin;
	
	public OperationWrap(final Operation<T> origin) {
		this.origin = origin;
	}
	
	@Override
	public Optional<T> fetch() throws Exception {
		return this.origin.fetch();
	}
}
