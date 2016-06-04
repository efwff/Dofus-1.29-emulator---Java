package com.codebreak.common.network.ipc.message.impl;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public abstract class AccountInformations implements Serializable {
	private final long id;
	private final Timestamp remainingSubscription;
	private final int power;
	public AccountInformations(final long id, final Timestamp remainingSubscription, final int power) {
		this.id = id;
		this.remainingSubscription = remainingSubscription;
		this.power = power;
	}
	public long id() {
		return this.id;
	}
	public Timestamp remainingSubscription() {
		return this.remainingSubscription;
	}
	public int power() {
		return this.power;
	}
	@Override
	public String toString() {
		return String.format("id=%d, subscription=%s, power=%d", this.id, this.remainingSubscription.toString(), this.power);
	}
}
