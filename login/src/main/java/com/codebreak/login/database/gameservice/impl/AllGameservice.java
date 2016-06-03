package com.codebreak.login.database.gameservice.impl;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;

import static com.codebreak.login.persistence.Tables.GAMESERVICE;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.database.gameservice.AbstractGameserviceOperation;
import com.codebreak.login.persistence.tables.records.GameserviceRecord;

public final class AllGameservice extends AbstractGameserviceOperation {
	public AllGameservice(final Database database) {
		super(database);
	}

	@Override
	protected Optional<List<GameserviceRecord>> fetchInternal(final DSLContext context) {
		return Optional.of(context.selectFrom(GAMESERVICE)
				.fetch()
				.map(obj -> obj));
	}
}
