package com.codebreak.login.database.account;

import com.codebreak.common.persistence.Operation;
import com.codebreak.common.persistence.OperationWrap;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public class AccountOperationWrap extends OperationWrap<AccountRecord> {
	public AccountOperationWrap(final Operation<AccountRecord> origin) {
		super(origin);
	}	
}
