package com.codebreak.login.network.handler.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.network.TcpEventType;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.login.network.LoginClient;
import com.codebreak.login.network.handler.AbstractLoginHandler;
import com.codebreak.login.network.handler.AbstractLoginState;
import com.codebreak.login.network.handler.LoginState;
import com.codebreak.login.network.ipc.GameServer;
import com.codebreak.login.network.ipc.GameServerSource;
import com.codebreak.login.network.message.LoginMessage;
import com.codebreak.login.persistence.tables.records.AccountRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class ServerSelectionHandler extends AbstractLoginHandler {
	
	private final AccountRecord account;
	private final GameServerSource gameServiceSource;
	
	public ServerSelectionHandler(final AbstractLoginState<?> parent, final Database db, final AccountRecord account, final GameServerSource gameServiceSource) {
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
		this.sendHosts(client, this.gameServiceSource.gameServers());
		this.sendCharacters(client, this.gameServiceSource.gameServers());
		final TypedObserver<GameServer> trigger = new TypedObserver<GameServer>() {
			@Override
			public void onEvent(final GameServer event) {
				sendHosts(client, Lists.newArrayList(event));
			}			
		};
		client.addObserver(new TypedObserver<TcpEvent<LoginClient>>() {	
			@Override
			public void onEvent(TcpEvent<LoginClient> event) {
				if(event.type() == TcpEventType.DISCONNECTED) {
					gameServiceSource
						.gameServers()
						.forEach(server -> server.removeObserver(trigger));
				}
			}
		});
		this.gameServiceSource
				.gameServers()
				.forEach(server -> 
					server.addObserver(trigger)
				);
		return stay();
	}
	
	private void sendHosts(final LoginClient client, final List<GameServer> hosts) {
		client.write(LoginMessage.ACCOUNT_HOSTS(hosts));
	}
	
	private void sendCharacters(final LoginClient client, final List<GameServer> serverInformations) {
		client.write(
				LoginMessage.ACCOUNT_CHARACTERS(
					serverInformations, 
					this.account.getId(), 
					31536000000L
				)
			);
	}
	
	private Optional<LoginState> serverSelectionRequest(final LoginClient client, final String message) {	
		LOGGER.debug("server selection request id=" + message.substring(2));
		return stay();
	}
}
