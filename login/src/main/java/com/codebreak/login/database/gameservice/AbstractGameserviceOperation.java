package com.codebreak.login.database.gameservice;

import java.util.List;

import com.codebreak.common.persistence.AbstractDatabaseOperation;
import com.codebreak.common.persistence.Database;
import com.codebreak.login.persistence.tables.records.GameserviceRecord;

public abstract class AbstractGameserviceOperation extends AbstractDatabaseOperation<List<GameserviceRecord>> {
	public AbstractGameserviceOperation(final Database database) {
		super(database);
	}
}
