package com.codebreak.game.network.impl;

import java.util.concurrent.ExecutorService;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.impl.IPCServiceServer;
import com.codebreak.common.network.ipc.message.impl.GameInformations;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.Configuration;
import com.codebreak.common.util.concurrent.AbstractService;
import com.codebreak.game.logic.LogicSource;
import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.logic.account.impl.Accounts;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.logic.authentication.impl.Tickets;
import com.codebreak.game.logic.statistic.OnlinePlayersSource;
import com.codebreak.game.logic.statistic.impl.OnlinePlayers;
import com.codebreak.game.network.ipc.impl.GameInformationsEndpoint;
import com.codebreak.game.network.message.GameMessage;

public final class GameService 
	extends AbstractService<GameClient> 
	implements GameInformationsSource, LogicSource {
	
	public static final String CONFIG_INFOS_ENDPOINT_IP = "infos.endpoint.ip";
	public static final String CONFIG_INFOS_ENDPOINT_PORT = "infos.endpoint.port";

	private final GameInformationsEndpoint infosEndpoint;
	private final Configuration config;
	private final TicketVerificationSource tickets;
	private final AccountSource accounts;
	private final OnlinePlayersSource onlinePlayers;
	
	public GameService(final ExecutorService executor, final Database database, final Configuration config) {
		super(executor, database);
		this.config = config;
		this.accounts = new Accounts(database);
		this.tickets = new Tickets(this.accounts);
		this.onlinePlayers = new OnlinePlayers();
		this.accounts.addObserver(this.onlinePlayers);
		this.infosEndpoint = new GameInformationsEndpoint(
			config.string(CONFIG_INFOS_ENDPOINT_IP), 
			config.integer(CONFIG_INFOS_ENDPOINT_PORT), 
			this, 
			this.tickets,
			this.accounts
		);
	}
		
	public TicketVerificationSource tickets() {
		return this.tickets;
	}

	@Override
	public void onEvent(final TcpEvent<GameClient> event) {
		switch(event.type()) {
			case CONNECTED:
				event.client().write(GameMessage.HELLO_GAME);
				break;
			case DISCONNECTED:
				LOGGER.info("client disconnected");
				break;
			case RECEIVED:
				break;
			case SENT:
				break;
			default:
				break;
		}
	}
	
	@Override
	public GameInformations gameInfos() {
		return new GameInformations(
					this.config.string(GameServer.CONFIG_HOST), 
					this.config.integer(GameServer.CONFIG_PORT), 
					0,
					IPCServiceServer.GAME_ONLINE, 
					true
				);
	}

	@Override
	public AccountSource accounts() {
		return this.accounts;
	}

	@Override
	public OnlinePlayersSource onlinePlayers() {
		return this.onlinePlayers;
	}
}
