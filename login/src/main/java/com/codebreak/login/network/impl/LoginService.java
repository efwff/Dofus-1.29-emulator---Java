package com.codebreak.login.network.impl;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.common.util.concurrent.AbstractService;
import com.codebreak.login.database.account.impl.DisconnectAccount;
import com.codebreak.login.database.account.impl.DisconnectAccounts;
import com.codebreak.login.database.gameservice.impl.AllGameservice;
import com.codebreak.login.network.ipc.GameEvent;
import com.codebreak.login.network.ipc.GameServer;
import com.codebreak.login.network.ipc.GameServerSource;
import com.codebreak.login.network.ipc.impl.GameServerProxy;
import com.codebreak.login.network.message.LoginMessage;

public class LoginService 
		extends AbstractService<LoginClient>
		implements GameServerSource {
		
	private final List<GameServer> gameServices;
	
	public LoginService(final Database database) {
		super(Executors.newSingleThreadExecutor(), database);
		this.gameServices = new AllGameservice(database)
				.fetch()
				.get()
				.stream()
				.map(record -> {
						final GameServerProxy proxy = new GameServerProxy(
							record.getId(), 
							record.getName(),
							record.getIp(), 
							record.getPort()
						);
						proxy.addObserver(new TypedObserver<GameEvent>() {						
							@Override
							public void onEvent(final GameEvent event) {
								onGameEvent(event);
							}
						});
						return proxy;
					}
				)
				.collect(Collectors.toList());
		new DisconnectAccounts(database).fetch();
	}
	
	@Override
	public void onEvent(final TcpEvent<LoginClient> event) {
		switch(event.type()) {
			case CONNECTED:
				event.object().write(LoginMessage.HELLO_CONNECT(event.object().encryptKey()));
				break;
				
			case DISCONNECTED:
				break;
				
			case RECEIVED:
				break;
				
			case SENT:
				break;
		}
	}
	
	private void onGameEvent(final GameEvent event) {
		switch(event.type()) {
			case PLAYER_DISCONNECTED:
				new DisconnectAccount(db(), event.data()).fetch();
				break;
			case UPDATE_INFOS:
				break;
		}
	}

	@Override
	public List<GameServer> gameServers() {
		return this.gameServices;
	}
}
