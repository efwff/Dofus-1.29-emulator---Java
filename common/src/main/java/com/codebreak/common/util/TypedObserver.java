package com.codebreak.common.util;

public interface TypedObserver<T> {
	void onEvent(final T event);
}
