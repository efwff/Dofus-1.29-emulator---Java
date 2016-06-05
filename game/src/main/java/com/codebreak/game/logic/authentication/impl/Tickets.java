package com.codebreak.game.logic.authentication.impl;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.AbstractObservable;
import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.logic.authentication.impl.exception.UnknowHashException;
import com.codebreak.game.persistence.tables.records.AccountRecord;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public final class Tickets
	extends AbstractObservable<TicketEvent>
	implements TicketVerificationSource, RemovalListener<String, TransfertTicket>, Runnable {
	
	private static final int TICKET_TIMEOUT = 3;
	private static final TimeUnit TIMEOUT_UNIT = TimeUnit.SECONDS;
	
	private final AccountSource accountSource;
	private final ScheduledExecutorService executor;
	private final Cache<String, TransfertTicket> tickets;
	
	public Tickets(final AccountSource accountSource) {
		this.accountSource = accountSource;
		this.tickets = CacheBuilder.newBuilder()
				.expireAfterAccess(TICKET_TIMEOUT, TIMEOUT_UNIT)
				.removalListener(this)
				.build();
		this.executor = Executors.newSingleThreadScheduledExecutor();
		this.executor.scheduleWithFixedDelay(
			this, 
			TICKET_TIMEOUT, 
			TICKET_TIMEOUT,
			TIMEOUT_UNIT
		);
	}

	@Override
	public AccountRecord get(final String hash) throws Exception {
		final Optional<TransfertTicket> ticket = Optional.ofNullable(this.tickets.asMap().remove(hash));
		if(!ticket.isPresent())
			throw new UnknowHashException(hash);
		return accountSource.createIfNonExistant(ticket.get());
	}
	
	@Override
	public void register(final TransfertTicket ticket) {
		this.tickets.put(ticket.hash(), ticket);
		this.fireTicketEvent(new TicketEvent(TicketEventType.ADD, ticket));
	}

	@Override
	public void onRemoval(RemovalNotification<String, TransfertTicket> notification) {
		if(notification.wasEvicted()) {
			this.fireTicketEvent(new TicketEvent(TicketEventType.EVICTED, notification.getValue()));
		}
		else {
			this.fireTicketEvent(new TicketEvent(TicketEventType.USED, notification.getValue()));
		}
	}
	
	private void fireTicketEvent(final TicketEvent event) {
		this.notifyObservers(event);
	}

	@Override
	public void run() {
		this.tickets.cleanUp();
	}
}
