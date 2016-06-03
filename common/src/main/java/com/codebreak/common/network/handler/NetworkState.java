package com.codebreak.common.network.handler;

import java.util.Optional;

public interface NetworkState<T> {
	void enter();
	void exit();
	Optional<AbstractMessageHandler<T>> handler(final String message);
}
