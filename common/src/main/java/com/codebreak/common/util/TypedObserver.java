package com.codebreak.common.util;

public interface TypedObserver<TEvent> {
	void onEvent(final TEvent event);
}
