package com.codebreak.game;

import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.impl.IPCServiceServer;
import com.codebreak.common.network.ipc.message.impl.GameInformations;
import com.codebreak.common.util.impl.Log;
import com.codebreak.game.network.ipc.impl.GameInformationsEndpoint;

public final class Main {
	public static final void main(String[] args) throws InterruptedException {
		Log.configure();
		new GameInformationsEndpoint("127.0.0.1", 5556, new GameInformationsSource() {
			
			@Override
			public GameInformations gameInfos() {
				return new GameInformations(
							"127.0.0.1", 
							6666, 
							0,
							IPCServiceServer.GAME_ONLINE, 
							true
						);
			}
		});
		while(true)
			Thread.sleep(10);
	}
}
