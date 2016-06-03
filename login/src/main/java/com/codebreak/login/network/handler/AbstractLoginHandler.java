package com.codebreak.login.network.handler;

import com.codebreak.common.network.handler.AbstractMessageHandler;
import com.codebreak.common.persistence.Database;
import com.codebreak.login.network.LoginClient;

public abstract class AbstractLoginHandler extends AbstractMessageHandler<LoginClient> {
	
	public static final int PROTOCOL = 0;
	public static final int AUTHENTICATION = 1;
	public static final int SERVER_SELECTION = 2;
	
	public AbstractLoginHandler(final AbstractLoginState<?> parent, final Database db, final int id) {
		super(parent, db, id);
	}
}
