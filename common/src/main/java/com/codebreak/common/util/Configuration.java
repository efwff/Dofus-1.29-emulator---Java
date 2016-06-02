package com.codebreak.common.util;

import java.util.NoSuchElementException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Configuration {
	default Integer integer(final String key) throws NoSuchElementException {
		return Integer.parseInt(string(key));
	}	
	String string(final String key) throws NoSuchElementException;	
	default void set(final String key, final Object value) {
		set(key, value.toString());
	}
	default void set(final String key, final String value) {
		throw new NotImplementedException();
	}
}
