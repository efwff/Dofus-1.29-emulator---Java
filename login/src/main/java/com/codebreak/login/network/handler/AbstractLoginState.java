package com.codebreak.login.network.handler;

import com.codebreak.common.network.handler.AbstractNetworkState;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.LoginClient;

public abstract class AbstractLoginState<T> extends AbstractNetworkState<LoginClient, T> implements LoginState {
	public AbstractLoginState(final Database db, final T data, final AbstractLoginHandler... handlers) {
		super(db, data, handlers);
	}
}
