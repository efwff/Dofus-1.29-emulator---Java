package com.codebreak.login.database.gameservice;

import java.util.List;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.common.persistence.DatabaseOperationWrap;
import com.codebreak.login.persistence.tables.records.GameserviceRecord;

public class AbstractGameserviceOperationWrap extends DatabaseOperationWrap<List<GameserviceRecord>> {
	public AbstractGameserviceOperationWrap(final DatabaseOperation<List<GameserviceRecord>> origin) {
		super(origin);
	}	
}
