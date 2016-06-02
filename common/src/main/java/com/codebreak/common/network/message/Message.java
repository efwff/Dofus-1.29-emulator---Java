package com.codebreak.common.network.message;

public interface Message<T> {
	T serialize();
}
