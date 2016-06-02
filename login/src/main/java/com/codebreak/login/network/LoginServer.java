package com.codebreak.login.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.NoSuchElementException;

import com.codebreak.common.network.AbstractTcpServer;
import com.codebreak.common.persistence.Database;
import com.codebreak.common.util.Configuration;

public final class LoginServer extends AbstractTcpServer<LoginClient> {
	
	public LoginServer(final Configuration config) throws NoSuchElementException, IOException {		
		super(
			config, 
			new LoginService(),
			new Database(config)
		);						
	}
	
	@Override
	public LoginClient createClient(int identity, ByteBuffer buffer, AsynchronousSocketChannel channel) {
		return new LoginClient(identity, buffer, channel, service, database);
	}	
}