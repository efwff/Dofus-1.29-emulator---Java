package com.codebreak.login.network.handler.impl;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.ipc.GameServerSource;

public final class ProtocolCheckingState extends AbstractLoginState<Object> {
	public ProtocolCheckingState(final Database db, final GameServerSource gameServiceSource) {
		super(db, new Object());
		register(new ProtocolHandler(this, db, gameServiceSource));
	}

	@Override
	public void enter() {	
	}

	@Override
	public void exit() {
	}
}
