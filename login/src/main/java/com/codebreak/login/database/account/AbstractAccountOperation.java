package com.codebreak.login.database.account;

import com.codebreak.common.persistence.Database;

import com.codebreak.common.persistence.AbstractDatabaseOperation;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public abstract class AbstractAccountOperation extends AbstractDatabaseOperation<AccountRecord> {
	public AbstractAccountOperation(final Database database) {
		super(database);
	}
}
