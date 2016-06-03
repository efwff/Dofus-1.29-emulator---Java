package com.codebreak.login.network.handler.impl;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.persistence.Database;
import com.codebreak.login.network.LoginClient;
import com.codebreak.login.network.handler.AbstractLoginHandler;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.message.LoginMessage;
import com.codebreak.login.network.structure.GameServiceSource;

public final class ProtocolHandler extends AbstractLoginHandler {

	private static final String PROTOCOL_VERSION = "1.29.1";
	
	private final GameServiceSource gameServiceSource;

	public ProtocolHandler(final AbstractLoginState<?> parent, final Database db, final GameServiceSource gameServiceSource) {
		super(parent, db, PROTOCOL);
		this.gameServiceSource = gameServiceSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<BiFunction<LoginClient, String, Optional<NetworkState<LoginClient>>>> getHandler(
			String message) {
		return Optional.of(this::protocolCheck);
	}
	
	private Optional<NetworkState<LoginClient>> protocolCheck(final LoginClient client, final String message) {
		if(!message.equals(PROTOCOL_VERSION)) {
			client.write(LoginMessage.LOGIN_FAILURE_PROTOCOL);
			return fail();
		}
		return next(new AuthenticationState(this.db(), gameServiceSource));
	}
}
