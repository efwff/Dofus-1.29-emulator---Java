package com.codebreak.game.network.handler.impl;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.logic.TicketVerificationSource;
import com.codebreak.game.network.handler.AbstractGameHandler;
import com.codebreak.game.network.handler.AbstractGameState;
import com.codebreak.game.network.handler.GameState;
import com.codebreak.game.network.impl.GameClient;

public final class AuthenticationHandler extends AbstractGameHandler {

	public AuthenticationHandler(final AbstractGameState<?> parent, final Database db, final TicketVerificationSource ticketVerificationSource) {
		super(parent, db, TICKET);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<BiFunction<GameClient, String, Optional<GameState>>> getHandler(
			String message) {
		return Optional.of(this::checkTicket);
	}
	
	private Optional<GameState> checkTicket(final GameClient client, final String message) {
		return stay();
	}

}
