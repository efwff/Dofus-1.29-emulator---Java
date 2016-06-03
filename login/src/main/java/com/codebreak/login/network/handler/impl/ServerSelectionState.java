package com.codebreak.login.network.handler.impl;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.ipc.GameServerSource;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class ServerSelectionState extends AbstractLoginState<AccountRecord> {
	public ServerSelectionState(final Database db, final AccountRecord data, final GameServerSource gameServiceSource) {
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
