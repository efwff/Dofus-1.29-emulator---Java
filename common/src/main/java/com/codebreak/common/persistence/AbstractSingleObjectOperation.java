package com.codebreak.common.persistence;

import java.util.Optional;

import org.jooq.TableRecord;

import com.codebreak.common.persistence.impl.Database;

public abstract class AbstractSingleObjectOperation<T extends TableRecord<?>> extends AbstractDatabaseOperation<T> {		
	public AbstractSingleObjectOperation(final Database database) {
		super(database);
	}
	public Optional<T> fetch() {
		return fetchInternal(this.context());	
	}
}
