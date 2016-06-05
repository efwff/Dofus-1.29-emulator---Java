package com.codebreak.game.network.ipc.impl;

import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.impl.IPCServiceServer;
import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.game.logic.account.AccountEvent;
import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.logic.authentication.impl.TicketEvent;

public final class GameInformationsEndpoint 
	extends IPCServiceServer 
	implements EventListener, TypedObserver<TicketEvent> {	
	
	private final GameInformationsSource stateSource;
	private final TicketVerificationSource tickets;
	
	public GameInformationsEndpoint(final String host, 
			final int port,
			final GameInformationsSource stateSource,
			final TicketVerificationSource tickets,
			final AccountSource accounts) {
		super(host, port);
		this.stateSource = stateSource;
		this.tickets = tickets;
		this.tickets.addObserver(this);
		bind(this);
		accounts.addObserver(new TypedObserver<AccountEvent>() {			
			@SuppressWarnings("incomplete-switch")
			@Override
			public void onEvent(final AccountEvent event) {
				switch(event.type()) {
					case DISCONNECTED:
						firePlayerDisconnection(event.accountId());
						break;
				}
			}
		});
	}

	@Override
	public void onEvent(final Event event) throws Throwable {
		LOGGER.debug("eventbus : name=" + event.getName());
		switch(event.getName()) {
			case IPCServiceServer.EVENT_CLIENT_CONNECT:
				trigger(
					IPCServiceServer.EVENT_GAME_UPDATE_INFORMATIONS,
					stateSource.gameInfos()
				);
				break;
			
			case IPCServiceServer.EVENT_LOGIN_PLAYER_TRANSFERT:
				registerTicket(event.getData(TransfertTicket.class));
				break;
		}
	}	

	@Override
	public void onEvent(final TicketEvent event) {
		LOGGER.debug("ticket event : " + event.type());
		switch(event.type()) {
			case EVICTED:
				firePlayerDisconnection(event.ticket().id());
				break;
			case ADD:
				break;
			case USED:
				break;
		}
	}
	
	private void firePlayerDisconnection(final long accountId) {
		trigger(
			IPCServiceServer.EVENT_GAME_PLAYER_DISCONNECTED,
			accountId
		);
	}
	
	private void registerTicket(final TransfertTicket ticket) {
		this.tickets.register(ticket);
	}
}
