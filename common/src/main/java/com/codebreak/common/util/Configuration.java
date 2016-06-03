package com.codebreak.common.util;

import java.util.NoSuchElementException;


public interface Configuration {
	default Integer integer(final String key) throws NoSuchElementException {
		return Integer.parseInt(string(key));
	}	
	String string(final String key) throws NoSuchElementException;	
	default void set(final String key, final Object value) throws Exception {
		set(key, value.toString());
	}
	default void set(final String key, final String value) throws Exception {
		throw new Exception("not implemented");
	}
}
