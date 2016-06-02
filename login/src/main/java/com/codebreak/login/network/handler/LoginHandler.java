package com.codebreak.login.network.handler;

import com.codebreak.common.network.handler.AbstractMessageHandler;
import com.codebreak.login.network.LoginClient;

public abstract class LoginHandler extends AbstractMessageHandler<LoginClient> {	
	public LoginHandler(int id) {
		super(id);
	}
}
