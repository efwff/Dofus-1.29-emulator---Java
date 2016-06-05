package com.codebreak.game.logic.authentication.impl.exception;

public final class UnknowHashException extends Exception {
	public UnknowHashException(final String hash) {
		super("ticket hash not found : " + hash);
	}
}
