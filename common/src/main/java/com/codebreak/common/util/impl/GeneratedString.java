package com.codebreak.common.util.impl;

import java.security.SecureRandom;
import java.util.Random;


public final class GeneratedString {
	
	private static final Random random = new SecureRandom();
	private static final char[] CHARSET_aZ_09 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	private final String generated;
	
	public GeneratedString(final int length) {
		this(CHARSET_aZ_09, length);
	}
	
	public GeneratedString(final char[] charset, final int length) {
	    final char[] result = new char[length];
	    for (int i = 0; i < result.length; i++) {
	        result[i] = charset[random.nextInt(charset.length)];
	    }
	    this.generated = new String(result);
	}
	
	public String value() {
		return this.generated;
	}
	
	@Override
	public boolean equals(final Object other) {
		return other.toString().equals(this.value());
	}
}
