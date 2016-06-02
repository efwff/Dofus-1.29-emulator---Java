package com.codebreak.login.database.account.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class ExistantException extends AccountException {	
	public ExistantException(final Optional<AccountRecord> account) {
		super(account, "This account already exists");
	}
}
