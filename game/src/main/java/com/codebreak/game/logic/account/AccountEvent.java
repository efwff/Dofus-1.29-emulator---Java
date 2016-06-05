package com.codebreak.game.logic.account;

public final class AccountEvent {
	private final AccountEventType type;
	private final long accountId;
	public AccountEvent(final AccountEventType type, final long accountId) {
		this.type = type;
		this.accountId = accountId;
	}
	public AccountEventType type() {
		return this.type;
	}
	public long accountId() {
		return this.accountId;
	}
}
