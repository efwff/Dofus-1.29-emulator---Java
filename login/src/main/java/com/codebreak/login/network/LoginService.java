package com.codebreak.login.network;

import java.util.List;
import java.util.stream.Collectors;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.persistence.Database;
import com.codebreak.common.util.concurrent.AbstractService;
import com.codebreak.login.database.gameservice.impl.AllGameservice;
import com.codebreak.login.network.ipc.GameService;
import com.codebreak.login.network.message.LoginMessage;
import com.codebreak.login.network.structure.GameServiceSource;
import com.codebreak.login.network.structure.HostInformations;

public class LoginService extends AbstractService<LoginClient> implements GameServiceSource {
		
	private final List<HostInformations> gameServices;
	
	public LoginService(final Database database) {
		super(database);
		this.gameServices = new AllGameservice(database)
				.fetch()
				.get()
				.stream()
				.map(record -> new GameService(record.getId(), record.getName(), record.getIp(), record.getPort()))
				.collect(Collectors.toList());
	}
	
	@Override
	public void onEvent(TcpEvent<LoginClient> event) {
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

	@Override
	public List<HostInformations> gameServices() {
		return this.gameServices;
	}
}
