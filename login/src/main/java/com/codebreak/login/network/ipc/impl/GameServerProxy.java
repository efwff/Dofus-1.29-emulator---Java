package com.codebreak.login.network.ipc.impl;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.impl.IPCServiceClient;
import com.codebreak.common.network.ipc.message.impl.GameInformations;
import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.AbstractObservable;
import com.codebreak.login.network.ipc.GameEvent;
import com.codebreak.login.network.ipc.GameEventType;
import com.codebreak.login.network.ipc.GameServer;

public final class GameServerProxy 
	extends AbstractObservable<GameEvent> implements GameServer {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameServerProxy.class);
	
	private static final int POOL_SIZE = 1;
	private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(POOL_SIZE);
	
	private static final int DELAY_BETWEEN_RETRY = 10;
	private static final TimeUnit RETRY_UNIT = TimeUnit.SECONDS;
	
	private final int id;
	private final String name;
	private final String host;
	private final int port;
	private GameInformations gameInfos;
	
	private IPCServiceClient client;
	
	public GameServerProxy(final int id, final String name, final String host, final int port) {
		this.host = host;
		this.port = port;
		this.id = id;
		this.name = name;
		this.initialState();
	}
	
	private void initialState() {
		this.resetState();
		EXECUTOR.schedule(this::connectState, DELAY_BETWEEN_RETRY, RETRY_UNIT);
	}
	
	private void connectState() {
		try {
			client = new IPCServiceClient(host, port);
		}
		catch(final Exception e) {
			LOGGER.info("game service unreacheable : name=" + name);
			this.initialState();
			return;
		}
		eventListeningState();
	}
	
	private void eventListeningState() {
		client.bind(new EventListener() {
			@Override
			public void onEvent(final Event event) throws Throwable {
				LOGGER.debug("eventbus : " + event.getName() + " data=" + event.getData());
				switch(event.getName()) {
					case IPCServiceClient.EVENT_SERV_CLOSE:
					case IPCServiceClient.EVENT_CLIENT_DISCONNECT:
						try {
							client.close();
						}
						catch(final IOException ex) {										
						}
						initialState();			
						fireInformationsChanged();			
						break;
						
					case IPCServiceClient.EVENT_GAME_UPDATE_INFORMATIONS:
						updateInformations(event.getData(GameInformations.class));
						break;
						
					case IPCServiceClient.EVENT_GAME_PLAYER_DISCONNECTED:
						fireEvent(GameEventType.PLAYER_DISCONNECTED, event.getData(Long.class));
						break;
				}
			}
		});
	}
	
	private void resetState() {
		this.gameInfos = new GameInformations(
					"", 
					-1, 
					-1, 
					IPCServiceClient.GAME_OFFLINE,
					false
				);
	}

	private void updateInformations(final GameInformations infos) {
		this.gameInfos = infos;
		this.fireInformationsChanged();
	}

	private void fireInformationsChanged() {
		this.fireEvent(GameEventType.UPDATE_INFOS);
	}
	
	private void fireEvent(final GameEventType type) {
		this.fireEvent(type, new Object());
	}
	
	private void fireEvent(final GameEventType type, final Object data) {
		this.notifyObservers(new GameEvent(type, this, data));
	}
	
	@Override
	public int id() {
		return this.id;
	}
	
	@Override
	public GameInformations gameInfos() {
		return this.gameInfos;
	}

	@Override
	public void transfertPlayer(final TransfertTicket message) {
		client.trigger(IPCServiceClient.EVENT_LOGIN_PLAYER_TRANSFERT, message);
	}

	@Override
	public int characterCount(long accountId) {
		return 1;
	}
}
