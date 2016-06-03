package com.codebreak.login.database.account.impl.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class AlreadyConnectedException extends AccountException {
	public AlreadyConnectedException(Optional<AccountRecord> account) {
		super(account, "This account is already connected");
	}
}
