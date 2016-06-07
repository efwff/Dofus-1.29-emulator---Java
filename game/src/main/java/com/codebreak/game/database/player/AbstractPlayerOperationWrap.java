package com.codebreak.game.database.player;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.common.persistence.DatabaseOperationWrap;
import com.codebreak.game.persistence.tables.records.PlayerRecord;

public abstract class AbstractPlayerOperationWrap extends DatabaseOperationWrap<PlayerRecord> {
	public AbstractPlayerOperationWrap(final DatabaseOperation<PlayerRecord> origin) {
		super(origin);
	}	
}