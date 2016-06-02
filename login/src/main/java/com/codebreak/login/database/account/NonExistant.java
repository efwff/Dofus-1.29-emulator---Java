package com.codebreak.login.database.account;

import java.util.Optional;

import com.codebreak.common.persistence.Operation;
import com.codebreak.login.database.account.exception.ExistantException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NonExistant extends AccountOperationWrap {
	public NonExistant(final Operation<AccountRecord> origin) {
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