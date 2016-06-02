package com.codebreak.common.network;

public final class TcpEvent<T> {
	private final T object;
	private final TcpEventType type;
	public TcpEvent(final T object, final TcpEventType type) {
		this.object = object;
		this.type = type;
	}	
	public final T object() {
		return this.object;
	}
	public final TcpEventType type() {
		return this.type;
	}
}
