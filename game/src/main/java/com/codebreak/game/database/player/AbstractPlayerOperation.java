package com.codebreak.game.database.player;

import com.codebreak.common.persistence.AbstractSingleObjectOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.persistence.tables.records.PlayerRecord;

public abstract class AbstractPlayerOperation extends AbstractSingleObjectOperation<PlayerRecord> {
	public AbstractPlayerOperation(final Database database) {
		super(database);
	}
}