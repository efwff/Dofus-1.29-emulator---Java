package com.codebreak.common.persistence;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;

public abstract class AbstractMultiObjectOperation <T extends UpdatableRecord<?>> extends AbstractDatabaseOperation<List<T>>  {	
	public AbstractMultiObjectOperation(final Database database) {
		super(database);
	}
	public Optional<List<T>> fetch() {
		try(final DSLContext context = this.context()) {
			final Optional<List<T>> objects = fetchInternal(context);
			if(objects.isPresent())
				objects.get().forEach(object -> object.attach(context.configuration()));
			return objects;
		}
	}
}
