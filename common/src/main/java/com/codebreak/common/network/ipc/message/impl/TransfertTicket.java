package com.codebreak.common.network.ipc.message.impl;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public final class TransfertTicket extends AccountInformations {
	private final String hash;
	public TransfertTicket(final long id, final String name, final String nickname, final Timestamp remainingSubscription, final int power, final String hash) {
		super(id, name, nickname, remainingSubscription, power);
		this.hash = hash;
	}
	public String hash() {
		return this.hash;
	}
	@Override
	public String toString() {
		return super.toString() + String.format(" hash=%s", this.hash);
	}
}
