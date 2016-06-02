package com.codebreak.login.database.account.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class BannedException extends AccountException {
	public BannedException(final Optional<AccountRecord> account) {
		super(account, "Account is banned");
	}
}
