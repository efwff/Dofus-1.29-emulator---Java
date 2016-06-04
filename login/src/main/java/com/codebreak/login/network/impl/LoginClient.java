package com.codebreak.login.network.impl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import com.codebreak.common.network.AbstractDofusClient;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.impl.GeneratedString;
import com.codebreak.login.network.handler.impl.ProtocolCheckingState;

public class LoginClient extends AbstractDofusClient<LoginClient> {
	
	private static final int KEY_LENGTH = 32;
	
	private final String encryptKey;
	
	public LoginClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final Database database, final LoginService service) {
		super(identity, buffer, channel, service, new ProtocolCheckingState(database, service));
		this.encryptKey = new GeneratedString(KEY_LENGTH).value();
	}	
	
	public String encryptKey() {
		return this.encryptKey;
	}
}
