package com.codebreak.login.database.account.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class WrongPasswordException extends AccountException {
	public WrongPasswordException(Optional<AccountRecord> account) {
		super(account, "Password mismatch");
	}
}
