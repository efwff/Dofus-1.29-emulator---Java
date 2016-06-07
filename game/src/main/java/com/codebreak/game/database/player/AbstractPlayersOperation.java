package com.codebreak.game.database.player;

import com.codebreak.common.persistence.AbstractMultiObjectOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.persistence.tables.records.PlayerRecord;

public abstract class AbstractPlayersOperation extends AbstractMultiObjectOperation<PlayerRecord> {
	public AbstractPlayersOperation(final Database database) {
		super(database);
	}
}
