package com.codebreak.common.network.handler;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

public interface NetworkState<T> {
	ExecutorService context();
	void enter();
	void exit();
	Optional<AbstractMessageHandler<T>> handler(final String message);
}
