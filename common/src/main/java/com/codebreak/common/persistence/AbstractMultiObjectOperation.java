package com.codebreak.common.persistence;

import java.util.List;
import java.util.Optional;

import org.jooq.UpdatableRecord;

import com.codebreak.common.persistence.impl.Database;

public abstract class AbstractMultiObjectOperation <T extends UpdatableRecord<?>> extends AbstractDatabaseOperation<List<T>>  {	
	public AbstractMultiObjectOperation(final Database database) {
		super(database);
	}
	public Optional<List<T>> fetch() {
		return this.fetchInternal(this.context());
	}
}
