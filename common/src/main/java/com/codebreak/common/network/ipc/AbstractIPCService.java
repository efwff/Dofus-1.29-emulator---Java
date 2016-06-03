package com.codebreak.common.network.ipc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.ipceventbus.event.EventListener;
import org.terracotta.ipceventbus.event.RemoteEventBus;

public abstract class AbstractIPCService {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractIPCService.class);
	
	public final static String EVENT_SERV_CLOSE = "eventbus.server.close";
	public final static String EVENT_CLIENT_CONNECT = "eventbus.client.connect";
	public final static String EVENT_CLIENT_DISCONNECT = "eventbus.client.disconnect";
	
	public final static String EVENT_GAME_STATE_UPDATE = "game.state.update";
	public final static String EVENT_GAME_COMPLETION_UPDATE = "game.completion.update";
	public final static String EVENT_GAME_PLAYER_DISCONNECTED = "game.player.disconnected";
	public final static String EVENT_GAME_PLAYER_CONNECTED = "game.player.connected";
	
    public final static int GAME_OFFLINE = 0;
    public final static int GAME_ONLINE = 1;
    public final static int GAME_STARTING = 2;
    
	private final RemoteEventBus eventBus;
	
	public AbstractIPCService(final RemoteEventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	public void bind(final EventListener listener) {
		this.eventBus.on(listener);
	}
	
	public void bind(final String event, final EventListener listener) {
		this.eventBus.on(event, listener);
	}
	
	public void unbind(final String event) {
		this.eventBus.unbind(event);
	}
	
	public boolean isClosed() {
		return this.eventBus.isClosed();
	}
	
	public void close() throws IOException {
		this.eventBus.close();
	}
	
	public void trigger(final String eventKey, final Object message) {
		this.eventBus.trigger(eventKey, message);
	}
}
