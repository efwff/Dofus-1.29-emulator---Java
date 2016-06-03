package com.codebreak.game;

import com.codebreak.common.network.ipc.impl.IPCServiceServer;
import com.codebreak.common.util.impl.Log;
import com.codebreak.game.network.ipc.GameStateSource;
import com.codebreak.game.network.ipc.GameStateEndpoint;

public final class Main {
	public static final void main(String[] args) throws InterruptedException {
		Log.configure();
		new GameStateEndpoint("127.0.0.1", 5556, new GameStateSource() {
			@Override
			public int gameState() {
				return IPCServiceServer.GAME_ONLINE;
			}
			@Override
			public int completionState() {
				return 0;
			}
		});
		while(true)
			Thread.sleep(10);
	}
}
