package com.codebreak.login.database.gameservice;

import com.codebreak.common.persistence.AbstractMultiObjectOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.persistence.tables.records.GameserviceRecord;

public abstract class AbstractGameserviceOperation extends AbstractMultiObjectOperation<GameserviceRecord> {
	public AbstractGameserviceOperation(final Database database) {
		super(database);
	}
}
