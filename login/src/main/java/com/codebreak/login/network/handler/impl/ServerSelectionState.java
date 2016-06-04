package com.codebreak.login.network.handler.impl;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.impl.LoginClient;
import com.codebreak.login.network.ipc.GameServerSource;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class ServerSelectionState extends AbstractLoginState<AccountRecord> {
	public ServerSelectionState(final Database db, final AccountRecord account, final GameServerSource gameServiceSource, final TypedObserver<TcpEvent<LoginClient>> disconnectAccountTrigger) {
		super(db, account);
		register(new ServerSelectionHandler(this, db, account, gameServiceSource, disconnectAccountTrigger));
	}

	@Override
	public void enter() {			
	}

	@Override
	public void exit() {			
	}	
}
