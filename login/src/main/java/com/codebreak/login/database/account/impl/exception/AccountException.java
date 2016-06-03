package com.codebreak.login.database.account.impl.exception;

import java.util.Optional;

import com.codebreak.login.persistence.tables.records.AccountRecord;

public abstract class AccountException extends Exception {
	protected final Optional<AccountRecord> account;
	public AccountException(final Optional<AccountRecord> account) {
		this(account, "");
	}
	public AccountException(final Optional<AccountRecord> account, final String message) {
		super(message);
		this.account = account;
	}
	public Optional<AccountRecord> account() {
		return this.account;
	}
}
