package com.codebreak.login.database.account.impl.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NotBannedException extends AccountException {
	public NotBannedException(final Optional<AccountRecord> account) {
		super(account, "Account is not banned");
	}
}
