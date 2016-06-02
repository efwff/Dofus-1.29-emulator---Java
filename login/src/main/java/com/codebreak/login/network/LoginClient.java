package com.codebreak.login.network;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.SecureRandom;
import java.util.concurrent.AbstractExecutorService;

import com.codebreak.common.network.AbstractDofusClient;
import com.codebreak.common.persistence.Database;
import com.codebreak.login.network.handler.LoginProcessor;

public class LoginClient extends AbstractDofusClient<LoginClient> {
	
	private static final int KEY_LENGTH = 32;
	private static final SecureRandom Random = new SecureRandom();
	
	private final String encryptKey;
	
	public LoginClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final AbstractExecutorService service, final Database database) {
		super(identity, buffer, channel, service, new LoginProcessor(database));
		this.encryptKey = new BigInteger(130, Random)
								.toString(10)
								.substring(0, KEY_LENGTH);
	}	
	
	public String encryptKey() {
		return this.encryptKey;
	}
}
