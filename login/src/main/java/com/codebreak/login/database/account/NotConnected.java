package com.codebreak.login.database.account;

import java.util.Optional;

import com.codebreak.common.persistence.Operation;
import com.codebreak.login.database.account.exception.AlreadyConnectedException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class NotConnected extends AccountOperationWrap {	
	public NotConnected(final Operation<AccountRecord> origin) {
		super(origin);
	}
	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		// TODO: check if connected and throw 
		final boolean alreadyConnected = false;
		if(alreadyConnected)
			throw new AlreadyConnectedException(account);
		return account;				
	}
}
