package com.codebreak.common.util;

public interface Identities<T> {	
	T give();
	void take(final T identity);
}
