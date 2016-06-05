package com.codebreak.game;

import java.util.NoSuchElementException;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.Configuration;
import com.codebreak.common.util.impl.Log;
import com.codebreak.game.network.impl.GameServer;
import com.codebreak.game.network.impl.GameService;

public final class Main {
	public static final void main(String[] args) throws Exception {
		Log.configure();
		final Configuration config = new Configuration() {			
			@Override
			public String string(String key) throws NoSuchElementException {
				switch(key) {
					case GameServer.CONFIG_HOST:
						return "127.0.0.1";
					case GameServer.CONFIG_PORT:
						return "6666";
					case GameServer.CONFIG_MAX_CLIENT:
						return "500";
					case GameServer.CONFIG_BUFF_SIZE:
						return "1024";
					case Database.CONFIG_PACKAGE:
						return "com.codebreak.game.persistence";
					case Database.CONFIG_SCHEMA:
						return "codebreak_game";
					case Database.CONFIG_URL:
						return "jdbc:mysql://localhost:3306/codebreak_game?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris";
					case Database.CONFIG_USER:
						return "root";
					case Database.CONFIG_PASS:
						return "";
					case GameService.CONFIG_INFOS_ENDPOINT_IP:
						return "127.0.0.1";
					case GameService.CONFIG_INFOS_ENDPOINT_PORT:
						return "5556";
				}
				throw new NoSuchElementException("unknow key: " + key);
			}			
		};
		final Database db = new Database(config);
		final GameServer server = new GameServer(db, config);
		server.start();
		while(true)
			Thread.sleep(10);
	}
}
