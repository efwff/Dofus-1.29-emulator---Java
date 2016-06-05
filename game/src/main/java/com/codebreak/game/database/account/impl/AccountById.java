package com.codebreak.game.database.account.impl;

import static com.codebreak.game.persistence.Tables.ACCOUNT;

import java.util.Optional;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.database.account.AbstractAccountOperation;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public class AccountById extends AbstractAccountOperation {
	
	private final long id;
	
	public AccountById(final Database database, final long id) {
		super(database);
		this.id = id;
	}
	
	@Override	
	protected Optional<AccountRecord> fetchInternal(DSLContext context) {
		return context.selectFrom(ACCOUNT)
				   .where(ACCOUNT.ID.eq(this.id))
				   .fetchOptional();	
	}
}
