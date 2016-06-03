package com.codebreak.login.database.account.impl;

import java.util.Optional;

import org.jooq.DSLContext;

import static com.codebreak.login.persistence.Tables.*;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.database.account.AbstractAccountOperation;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class ResetConnectedAccounts extends AbstractAccountOperation {

	public ResetConnectedAccounts(final Database database) {
		super(database);
	}

	@Override
	protected Optional<AccountRecord> fetchInternal(DSLContext context) {
		context.update(ACCOUNT)
			   .set(ACCOUNT.CONNECTED, false)
			   .execute();
		return Optional.empty();
	}

}
