package com.codebreak.login.network.ipc;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.impl.IPCServiceClient;
import com.codebreak.login.network.structure.HostInformations;

public final class GameService implements HostInformations  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
	
	private static final int POOL_SIZE = 1;
	private static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(POOL_SIZE);
	
	private static final int DELAY_BETWEEN_RETRY = 10;
	private static final TimeUnit RETRY_UNIT = TimeUnit.SECONDS;
	
	private final int id;
	private final String name;
	private final String host;
	private final int port;
	private int state;
	private int completionState;
	private boolean selectable;
	
	private IPCServiceClient client;
	
	public GameService(final int id, final String name, final String host, final int port) {
		this.host = host;
		this.port = port;
		this.id = id;
		this.name = name;
		this.state = IPCServiceClient.GAME_OFFLINE;
		this.completionState = 0;
		this.selectable = false;
		this.initialState();
	}
	
	private void initialState() {
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
						break;
						
					case IPCServiceClient.EVENT_GAME_COMPLETION_UPDATE:
						completionState = event.getData(Integer.class);
						break;
						
					case IPCServiceClient.EVENT_GAME_STATE_UPDATE:
						state = event.getData(Integer.class);
						break;
				}
			}
		});
	}

	@Override
	public int gameServerId() {
		return this.id;
	}

	@Override
	public int characterCount(long accountId) {
		return 1;
	}

	@Override
	public int gameServerState() {
		return this.state;
	}

	@Override
	public int completionState() {
		return this.completionState;
	}

	@Override
	public boolean selectable() {
		return this.selectable && this.state == IPCServiceClient.GAME_ONLINE;
	}	
}
