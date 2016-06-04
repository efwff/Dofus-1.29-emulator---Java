package com.codebreak.game.network.ipc.impl;

import org.terracotta.ipceventbus.event.Event;
import org.terracotta.ipceventbus.event.EventListener;

import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.impl.IPCServiceServer;
import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.game.logic.TicketVerificationSource;
import com.codebreak.game.logic.impl.TicketEvent;

public final class GameInformationsEndpoint 
	extends IPCServiceServer 
	implements EventListener, TypedObserver<TicketEvent> {	
	
	private final GameInformationsSource stateSource;
	private final TicketVerificationSource ticketSource;
	
	public GameInformationsEndpoint(final String host, 
			final int port,
			final GameInformationsSource stateSource,
			final TicketVerificationSource ticketSource) {
		super(host, port);
		this.stateSource = stateSource;
		this.ticketSource = ticketSource;
		this.ticketSource.addObserver(this);
		bind(this);
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
	
	private void registerTicket(TransfertTicket message) {
		this.ticketSource.register(message);
	}
}
