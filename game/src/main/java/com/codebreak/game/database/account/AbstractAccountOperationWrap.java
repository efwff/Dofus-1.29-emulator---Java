package com.codebreak.game.database.account;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.common.persistence.DatabaseOperationWrap;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public abstract class AbstractAccountOperationWrap extends DatabaseOperationWrap<AccountRecord> {
	public AbstractAccountOperationWrap(final DatabaseOperation<AccountRecord> origin) {
		super(origin);
	}	
}
