package com.codebreak.game.network.handler.impl;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.network.handler.AbstractGameState;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public class CharacterSelectionState extends AbstractGameState<AccountRecord>{

	public CharacterSelectionState(Database db, AccountRecord data) {
		super(db, data);
	}

	@Override
	public void enter() {
		
	}

	@Override
	public void exit() {
		
	}
}
