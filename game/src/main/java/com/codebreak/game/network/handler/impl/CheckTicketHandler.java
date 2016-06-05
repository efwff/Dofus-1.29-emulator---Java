package com.codebreak.game.network.handler.impl;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.network.handler.AbstractGameHandler;
import com.codebreak.game.network.handler.AbstractGameState;
import com.codebreak.game.network.handler.GameState;
import com.codebreak.game.network.impl.GameClient;
import com.codebreak.game.network.message.GameMessage;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public final class CheckTicketHandler extends AbstractGameHandler {

	private final TicketVerificationSource ticketVerifSource;
	
	public CheckTicketHandler(final AbstractGameState<?> parent, final Database db, final TicketVerificationSource ticketVerificationSource) {
		super(parent, db, TICKET);
		this.ticketVerifSource = ticketVerificationSource;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<BiFunction<GameClient, String, Optional<GameState>>> getHandler(
			String message) {
		return Optional.of(this::checkTicket);
	}
	
	private Optional<GameState> checkTicket(final GameClient client, final String message) {
		final String hash = message.substring(2);
		try {
			final AccountRecord account = this.ticketVerifSource.get(hash);
			account.setConnected(true);
			account.update();
			client.write(GameMessage.ACCOUNT_TICKET_SUCCESS);
			return next(new CharacterSelectionState(db(), account));
		}
		catch(final Exception e) {
			LOGGER.debug("failed to auth client with hash : " + hash);
			client.write(GameMessage.ACCOUNT_TICKET_ERROR);
			return fail();
		}
	}

}
