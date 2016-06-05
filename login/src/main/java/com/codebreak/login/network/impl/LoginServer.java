package com.codebreak.login.network.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.NoSuchElementException;

import com.codebreak.common.network.AbstractTcpServer;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.Configuration;
import com.codebreak.login.network.handler.impl.ProtocolCheckingState;

public final class LoginServer extends AbstractTcpServer<LoginClient, LoginService> {
	
	public LoginServer(final Configuration config, final Database database) throws NoSuchElementException, IOException {		
		super(
			database,
			config, 
			new LoginService(database)
		);
	}
	
	@Override
	public LoginClient createClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final Database database, final LoginService service) {
		return new LoginClient(
					identity, 
					buffer, 
					channel, 
					service,
					new ProtocolCheckingState(
						database, 
						service
					)
				);
	}	
}
