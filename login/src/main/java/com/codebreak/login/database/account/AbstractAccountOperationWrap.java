package com.codebreak.login.database.account;

import com.codebreak.common.persistence.Operation;
import com.codebreak.common.persistence.OperationWrap;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public abstract class AbstractAccountOperationWrap extends OperationWrap<AccountRecord> {
	public AbstractAccountOperationWrap(final Operation<AccountRecord> origin) {
		super(origin);
	}	
}
