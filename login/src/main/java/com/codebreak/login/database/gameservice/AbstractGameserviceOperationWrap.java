package com.codebreak.login.database.gameservice;

import java.util.List;

import com.codebreak.common.persistence.Operation;
import com.codebreak.common.persistence.OperationWrap;
import com.codebreak.login.persistence.tables.records.GameserviceRecord;

public class AbstractGameserviceOperationWrap extends OperationWrap<List<GameserviceRecord>> {
	public AbstractGameserviceOperationWrap(final Operation<List<GameserviceRecord>> origin) {
		super(origin);
	}	
}
