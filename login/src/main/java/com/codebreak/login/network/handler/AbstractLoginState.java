package com.codebreak.login.network.handler;

import java.util.concurrent.ExecutorService;

import com.codebreak.common.network.handler.AbstractNetworkState;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.network.impl.LoginClient;

public abstract class AbstractLoginState<T> extends AbstractNetworkState<LoginClient, T> implements LoginState {
	public AbstractLoginState(final ExecutorService context, final Database db, final T data) {
		super(context, db, data);
	}
}
