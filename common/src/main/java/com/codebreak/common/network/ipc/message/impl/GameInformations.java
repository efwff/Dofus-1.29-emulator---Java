package com.codebreak.common.network.ipc.message.impl;

import java.io.Serializable;

@SuppressWarnings("serial")
public final class GameInformations implements Serializable {
	private final String host;
	private final int port;
	private final int completion;
	private final int state;
	private final boolean selectable;
	public GameInformations(final String host, final int port, final int completion, final int state, final boolean selectable) {
		this.host = host;
		this.port = port;
		this.completion = completion;
		this.state = state;
		this.selectable = selectable;
	}
	public String host() { return this.host; }
	public int port() { return this.port; }
	public int completion() { return this.completion; }
	public int state() { return this.state; }
	public boolean selectable() { return this.selectable; }
	public boolean full() { return this.completion == 1; }
	
	@Override
	public String toString() {
		return String.format(
					"GameInfos [host=%s, port=%d, completion=%d, state=%d, selectable=%b]",
					this.host,
					this.port,
					this.completion,
					this.state,
					this.selectable
				);
	}
}
