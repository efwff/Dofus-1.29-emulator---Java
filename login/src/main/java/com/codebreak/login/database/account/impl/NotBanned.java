package com.codebreak.login.database.account.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.login.database.account.AbstractAccountOperationWrap;
import com.codebreak.login.database.account.impl.exception.BannedException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NotBanned extends AbstractAccountOperationWrap {
	public NotBanned(final DatabaseOperation<AccountRecord> origin) {
		super(origin);
	}
	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		if(account.get().getBanned() || account.get().getBanneduntil().after(Timestamp.from(Instant.now())))
			throw new BannedException(account);
		return account;				
	}
}
