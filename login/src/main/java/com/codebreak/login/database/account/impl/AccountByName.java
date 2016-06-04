package com.codebreak.login.database.account.impl;

import org.jooq.DSLContext;

import static com.codebreak.login.persistence.Tables.ACCOUNT;

import java.util.Optional;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.database.account.AbstractAccountOperation;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public class AccountByName extends AbstractAccountOperation {
	
	private final String name;
	
	public AccountByName(Database database, final String name) {
		super(database);
		this.name = name;
	}
	
	@Override	
	protected Optional<AccountRecord> fetchInternal(DSLContext context) {
		return context.selectFrom(ACCOUNT)
				   .where(ACCOUNT.NAME.eq(name))
				   .fetchOptional();	
	}
}
