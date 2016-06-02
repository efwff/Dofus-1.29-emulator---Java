package com.codebreak.common.network.handler;

import java.util.List;

import com.codebreak.common.network.AbstractDofusClient;

public interface HandlerSource<T extends AbstractMessageHandler<C>, C extends AbstractDofusClient<C>> {
	List<T> handlers();
}
