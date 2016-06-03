package com.codebreak.login.database.account.impl;

import java.util.Optional;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.login.database.account.AbstractAccountOperationWrap;
import com.codebreak.login.database.account.impl.exception.ExistantException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NonExistant extends AbstractAccountOperationWrap {
	public NonExistant(final DatabaseOperation<AccountRecord> origin) {
		super(origin);
	}
	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		if(account.isPresent())
			throw new ExistantException(account);
		return account;				
	}
}