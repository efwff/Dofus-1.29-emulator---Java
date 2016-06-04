package com.codebreak.game.network.ipc.impl;

import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.impl.IPCServiceServer;

public final class GameInformationsEndpoint extends IPCServiceServer implements EventListener {	
	
	private final GameInformationsSource source;
	
	public GameInformationsEndpoint(final String host, final int port, final GameInformationsSource stateSource) {
		super(host, port);
		this.source = stateSource;
		bind(this);
	}

	@Override
	public void onEvent(final Event event) throws Throwable {
		LOGGER.debug("eventbus : name=" + event.getName());
		switch(event.getName()) {
			case IPCServiceServer.EVENT_CLIENT_CONNECT:
				trigger(
					IPCServiceServer.EVENT_GAME_UPDATE_INFORMATIONS,
					source.gameInfos()
				);
				break;
			
			case IPCServiceServer.EVENT_LOGIN_PLAYER_TRANSFERT:
				LOGGER.debug("login tranfert : " + event.getData());
				break;
		}
	}	
}
