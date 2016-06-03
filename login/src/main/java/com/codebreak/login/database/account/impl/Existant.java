package com.codebreak.login.database.account.impl;

import java.util.Optional;

import com.codebreak.common.persistence.Operation;
import com.codebreak.login.database.account.AbstractAccountOperationWrap;
import com.codebreak.login.database.account.impl.exception.NonExistantException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class Existant extends AbstractAccountOperationWrap {
	public Existant(final Operation<AccountRecord> origin) {
		super(origin);
	}
	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		if(!account.isPresent())
			throw new NonExistantException(account);
		return account;				
	}
}
