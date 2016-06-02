package com.codebreak.common.network.handler;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.AbstractDofusClient;

public abstract class AbstractMessageProcessor<T extends AbstractDofusClient<T>> {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageProcessor.class);
	
	private final Set<AbstractMessageHandler<T>> handlers;
	private final BitSet bits;
	
	public AbstractMessageProcessor() {
		this.bits = new BitSet();
		this.handlers = new HashSet<AbstractMessageHandler<T>>();
	}
	
	protected boolean allowed(final int id) {
		return this.bits.get(id);
	}
	
	protected void allow(final int id) {
		this.bits.set(id);
	}
	
	protected void prohibit(final int id) {
		this.bits.clear(id);
	}
	
	protected void register(final AbstractMessageHandler<T> handler) {
		this.register(handler, true);
	}
	
	protected void register(final AbstractMessageHandler<T> handler, final boolean allowed) {
		this.handlers.add(handler);
		if(allowed)
			this.allow(handler.id());
	}	
	
	public boolean process(final T client, final String message) {
		final Optional<AbstractMessageHandler<T>> handler = this.handlers
			.stream()
			.filter(h -> this.allowed(h.id()) && h.canHandle(message))
			.findAny();
		if(handler.isPresent())
			handler.get().handle(client, message);
		return handler.isPresent();
	}
}
