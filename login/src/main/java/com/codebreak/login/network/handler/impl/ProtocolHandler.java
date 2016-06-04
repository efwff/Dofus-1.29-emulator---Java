package com.codebreak.login.network.handler.impl;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.network.handler.NetworkState;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.handler.AbstractLoginHandler;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.impl.LoginClient;
import com.codebreak.login.network.ipc.GameServerSource;
import com.codebreak.login.network.message.LoginMessage;

public final class ProtocolHandler extends AbstractLoginHandler {

	private static final String PROTOCOL_VERSION = "1.29.1";
	
	private final GameServerSource gameServiceSource;

	public ProtocolHandler(final AbstractLoginState<?> parent, final Database db, final GameServerSource gameServiceSource) {
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
