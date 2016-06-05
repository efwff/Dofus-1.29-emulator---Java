package com.codebreak.game.network.handler.authentication;

import java.util.Optional;
import java.util.function.BiFunction;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.network.TcpEventType;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.game.logic.LogicSource;
import com.codebreak.game.logic.account.AccountEventType;
import com.codebreak.game.network.handler.AbstractGameHandler;
import com.codebreak.game.network.handler.AbstractGameState;
import com.codebreak.game.network.handler.GameState;
import com.codebreak.game.network.handler.character.selection.CharacterSelectionArguments;
import com.codebreak.game.network.handler.character.selection.CharacterSelectionState;
import com.codebreak.game.network.impl.GameClient;
import com.codebreak.game.network.message.GameMessage;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public final class CheckTicketHandler extends AbstractGameHandler {

	private final LogicSource logicSource;
	
	public CheckTicketHandler(final AbstractGameState<?> parent, final Database db, final LogicSource logicSource) {
		super(parent, db, TICKET);
		this.logicSource = logicSource;
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
			final AccountRecord account = this.logicSource.tickets().get(hash);
			account.setConnected(true);
			account.update();
			logicSource.accounts().fireEvent(AccountEventType.CONNECTED, account.getId());
			client.addObserver(new TypedObserver<TcpEvent<GameClient>>() {				
				@Override
				public void onEvent(TcpEvent<GameClient> event) {
					if(event.type() == TcpEventType.DISCONNECTED) {
						account.setConnected(false);
						account.update();
						logicSource.accounts().fireEvent(AccountEventType.DISCONNECTED, account.getId());
					}
				}
			});
			client.write(GameMessage.ACCOUNT_TICKET_SUCCESS);
			return next(new CharacterSelectionState(parent().context(), db(), new CharacterSelectionArguments(account, this.logicSource)));
		}
		catch(final Exception e) {
			LOGGER.debug("failed to auth client with hash : " + hash);
			client.write(GameMessage.ACCOUNT_TICKET_ERROR);
			return fail();
		}
	}

}
