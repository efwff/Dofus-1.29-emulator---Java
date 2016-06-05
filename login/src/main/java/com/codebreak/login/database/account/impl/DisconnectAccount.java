package com.codebreak.login.database.account.impl;

import static com.codebreak.login.persistence.Tables.ACCOUNT;

import java.util.Optional;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.database.account.AbstractAccountOperation;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public class DisconnectAccount extends AbstractAccountOperation {

	private final long accountId;
	
	public DisconnectAccount(final Database database, final long accountId) {
		super(database);
		this.accountId = accountId;
	}

	@Override
	protected Optional<AccountRecord> fetchInternal(DSLContext context) {
		context.update(ACCOUNT)
			   .set(ACCOUNT.CONNECTED, false)
			   .where(ACCOUNT.ID.eq(accountId))
			   .execute();
		return Optional.empty();
	}

}

