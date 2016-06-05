package com.codebreak.game.database.account;

import com.codebreak.common.persistence.AbstractSingleObjectOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public abstract class AbstractAccountOperation extends AbstractSingleObjectOperation<AccountRecord> {
	public AbstractAccountOperation(final Database database) {
		super(database);
	}
}
