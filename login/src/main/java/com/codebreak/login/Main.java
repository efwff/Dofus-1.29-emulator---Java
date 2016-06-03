package com.codebreak.login;

import java.util.NoSuchElementException;

import com.codebreak.common.network.AbstractTcpServer;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.Configuration;
import com.codebreak.common.util.impl.Log;
import com.codebreak.login.network.LoginServer;

public final class Main {
	public static final void main(String[] args) throws Exception {
		Log.configure();
		final Configuration config = new Configuration() {			
			@Override
			public String string(String key) throws NoSuchElementException {
				switch(key) {
					case AbstractTcpServer.CONFIG_HOST:
						return "127.0.0.1";
					case AbstractTcpServer.CONFIG_PORT:
						return "5555";
					case AbstractTcpServer.CONFIG_MAX_CLIENT:
						return "500";
					case AbstractTcpServer.CONFIG_BUFF_SIZE:
						return "1024";
					case Database.CONFIG_PACKAGE:
						return "com.codebreak.login.persistence";
					case Database.CONFIG_SCHEMA:
						return "codebreak_login";
					case Database.CONFIG_URL:
						return "jdbc:mysql://localhost:3306/codebreak_login?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris";
					case Database.CONFIG_USER:
						return "root";
					case Database.CONFIG_PASS:
						return "";
				}
				throw new NoSuchElementException("unknow key: " + key);
			}			
		};
		final Database database = new Database(config);
		final LoginServer server = new LoginServer(config, database);
		server.start();
		while(true) 
			Thread.sleep(5);
	}
}
