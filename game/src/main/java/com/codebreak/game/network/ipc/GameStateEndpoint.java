package com.codebreak.game.network.ipc;

import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.impl.IPCServiceServer;

public final class GameStateEndpoint extends IPCServiceServer {		
	public GameStateEndpoint(final String host, final int port, final GameStateSource stateSource) {
		super(host, port);
		bind(new EventListener() {			
			@Override
			public void onEvent(final Event event) throws Throwable {
				LOGGER.debug("eventbus : name=" + event.getName());
				switch(event.getName()) {
					case IPCServiceServer.EVENT_CLIENT_CONNECT:
						trigger(IPCServiceServer.EVENT_GAME_COMPLETION_UPDATE, stateSource.completionState());
						trigger(IPCServiceServer.EVENT_GAME_STATE_UPDATE, stateSource.gameState());
						break;
				}
			}
		});
	}	
}
