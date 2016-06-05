package com.codebreak.login.network.ipc;

public final class GameEvent {
	private final GameEventType type;
	private final GameServer server;
	private final Object data;
	public GameEvent(final GameEventType type, final GameServer server) {
		this(type, server, new Object());
	}
	public GameEvent(final GameEventType type, final GameServer server, final Object data) {
		this.type = type;
		this.server = server;
		this.data = data;
	}
	public GameEventType type() {
		return this.type;
	}
	public GameServer server() {
		return this.server;
	}
	@SuppressWarnings("unchecked")
	public <T> T data() {
		return (T)this.data;
	}
}
