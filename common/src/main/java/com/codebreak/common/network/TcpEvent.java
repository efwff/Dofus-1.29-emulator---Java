package com.codebreak.common.network;

public final class TcpEvent<T> {
	private final T client;
	private final TcpEventType type;
	public TcpEvent(final T client, final TcpEventType type) {
		this.client = client;
		this.type = type;
	}	
	public final T client() {
		return this.client;
	}
	public final TcpEventType type() {
		return this.type;
	}
}
