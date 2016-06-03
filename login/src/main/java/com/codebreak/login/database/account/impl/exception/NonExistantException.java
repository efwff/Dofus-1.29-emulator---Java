package com.codebreak.login.database.account.impl.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NonExistantException extends AccountException {	
	public NonExistantException(final Optional<AccountRecord> account) {
		super(account, "This account doest not exists");
	}
}
