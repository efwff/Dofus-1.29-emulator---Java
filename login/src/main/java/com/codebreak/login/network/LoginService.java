package com.codebreak.login.network;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.util.concurrent.AbstractService;
import com.codebreak.login.network.message.LoginMessage;

public class LoginService extends AbstractService<LoginClient>{
	@Override
	public void onEvent(TcpEvent<LoginClient> event) {
		switch(event.type()) {
			case CONNECTED:
				event.object().write(LoginMessage.HELLO_CONNECT(event.object().encryptKey()));
				break;
				
			case DISCONNECTED:
				break;
				
			case RECEIVED:
				break;
				
			case SENT:
				break;
		}
	}

}
