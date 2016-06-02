package com.codebreak.login.database.account;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import com.codebreak.common.persistence.Operation;
import com.codebreak.login.database.account.exception.BannedException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NotBanned extends AccountOperationWrap {
	public NotBanned(final Operation<AccountRecord> origin) {
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
