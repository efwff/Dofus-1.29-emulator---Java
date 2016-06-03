package com.codebreak.login.network.handler.impl;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.persistence.Database;
import com.codebreak.login.network.LoginClient;
import com.codebreak.login.network.handler.AbstractLoginHandler;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.handler.LoginState;
import com.codebreak.login.network.message.LoginMessage;
import com.codebreak.login.network.structure.GameServiceSource;
import com.codebreak.login.persistence.tables.records.AccountRecord;
import com.google.common.base.Preconditions;

public final class ServerSelectionHandler extends AbstractLoginHandler {
	
	private final AccountRecord account;
	private final GameServiceSource gameServiceSource;
	
	public ServerSelectionHandler(final AbstractLoginState<?> parent, final Database db, final AccountRecord account, final GameServiceSource gameServiceSource) {
		super(parent, db, SERVER_SELECTION);
		this.account = account;
		this.gameServiceSource = gameServiceSource;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Optional<BiFunction<LoginClient, String, Optional<LoginState>>> getHandler(
			String message) {
		Preconditions.checkArgument(message.length() >= 2);
		switch(message.charAt(0)) {
			case 'A':
				switch(message.charAt(1)) {
					case 'x':
						return Optional.of(this::serverListRequest);
					case 'X':
						return Optional.of(this::serverSelectionRequest);
				}
				break;
		}
		return Optional.empty();
	}
	
	private Optional<LoginState> serverListRequest(final LoginClient client, final String message) {
		client.write(
			LoginMessage.ACCOUNT_CHARACTERS(
				this.gameServiceSource.gameServices(), 
				this.account.getId(), 
				31536000000L
			)
		);
		return stay();
	}
	
	private Optional<LoginState> serverSelectionRequest(final LoginClient client, final String message) {	
		LOGGER.debug("server selection request id=" + message.substring(2));
		return stay();
	}
}
