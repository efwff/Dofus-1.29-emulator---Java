package com.codebreak.common.network.ipc.message.impl;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public final class RegisterAccountTicket extends AccountInformations {
	private final String ticket;
	public RegisterAccountTicket(final long id, final Timestamp remainingSubscription, final int power, final String ticket) {
		super(id, remainingSubscription, power);
		this.ticket = ticket;
	}
	public String ticket() {
		return this.ticket;
	}
	@Override
	public String toString() {
		return super.toString() + String.format(" ticket=%s", this.ticket);
	}
}
