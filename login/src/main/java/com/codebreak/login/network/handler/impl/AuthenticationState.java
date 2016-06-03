package com.codebreak.login.network.handler.impl;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.ipc.GameServerSource;

public final class AuthenticationState extends AbstractLoginState<Object> {
	public AuthenticationState(final Database db, final GameServerSource gameServiceSource) {
		super(db, new Object());
		register(new AuthenticationHandler(this, db, gameServiceSource));
	}

	@Override
	public void enter() {		
	}

	@Override
	public void exit() {		
	}
}
