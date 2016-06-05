package com.codebreak.login.network.impl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import com.codebreak.common.network.AbstractDofusClient;
import com.codebreak.common.util.impl.GeneratedString;
import com.codebreak.login.network.handler.LoginState;

public class LoginClient extends AbstractDofusClient<LoginClient> {
	
	private static final int KEY_LENGTH = 32;
	
	private final String encryptKey;
	
	public LoginClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final LoginState initialState) {
		super(identity, buffer, channel, initialState);
		this.encryptKey = new GeneratedString(KEY_LENGTH).value();
	}	
	
	public String encryptKey() {
		return this.encryptKey;
	}
}
