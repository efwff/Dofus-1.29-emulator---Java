package com.codebreak.common.persistence;

import java.util.Optional;

import org.jooq.UpdatableRecord;

import com.codebreak.common.persistence.impl.Database;

public abstract class AbstractSingleObjectOperation<T extends UpdatableRecord<?>> extends AbstractDatabaseOperation<T> {		
	public AbstractSingleObjectOperation(final Database database) {
		super(database);
	}
	public Optional<T> fetch() {
		return fetchInternal(this.context());	
	}
}
