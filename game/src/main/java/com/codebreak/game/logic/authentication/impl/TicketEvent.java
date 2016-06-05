package com.codebreak.game.logic.authentication.impl;

import com.codebreak.common.network.ipc.message.impl.TransfertTicket;

public final class TicketEvent {
	private final TicketEventType type;
	private final TransfertTicket ticket;
	public TicketEvent(final TicketEventType type, final TransfertTicket ticket) {
		this.type = type;
		this.ticket = ticket;
	}
	public TicketEventType type() {
		return this.type;
	}
	public TransfertTicket ticket() {
		return this.ticket;
	}
}
