package com.codebreak.login.network.handler.impl;

import com.codebreak.common.persistence.Database;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.structure.GameServiceSource;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class ServerSelectionState extends AbstractLoginState<AccountRecord> {
	public ServerSelectionState(final Database db, final AccountRecord data, final GameServiceSource gameServiceSource) {
		super(db, data);
		register(new ServerSelectionHandler(this, db, data, gameServiceSource));
	}

	@Override
	public void enter() {		
	}

	@Override
	public void exit() {			
	}	
}