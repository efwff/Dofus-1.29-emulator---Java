package com.codebreak.common.persistence;

import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;

public abstract class AbstractSingleObjectOperation<T extends UpdatableRecord<?>> extends AbstractDatabaseOperation<T> {		
	public AbstractSingleObjectOperation(final Database database) {
		super(database);
	}
	public Optional<T> fetch() {
		try(final DSLContext context = this.context()) {
			final Optional<T> obj = fetchInternal(context);
			if(obj.isPresent()) {
				obj.get().attach(context.configuration());
			}
			return obj;
		}
	}
}
