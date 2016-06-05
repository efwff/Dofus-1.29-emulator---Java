package com.codebreak.game.network.handler.impl;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.network.handler.AbstractGameState;

public class CheckingTicketState extends AbstractGameState<TicketVerificationSource> {

	public CheckingTicketState(final Database db, final TicketVerificationSource ticketVerificationSource) {
		super(db, ticketVerificationSource);
		register(new CheckTicketHandler(this, db, ticketVerificationSource));
	}

	@Override
	public void enter() {
	}

	@Override
	public void exit() {
	}
}
