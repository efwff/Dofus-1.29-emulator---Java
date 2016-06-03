package com.codebreak.common.network.handler;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.codebreak.common.persistence.Database;

public abstract class AbstractNetworkState<C, T> implements NetworkState<C> {
	
	private final T data;
	private final Set<AbstractMessageHandler<C>> handlers;
	private final BitSet bits;
	
	@SafeVarargs
	public AbstractNetworkState(final Database db, final T data, final AbstractMessageHandler<C>... handlers) {
		this.handlers = new HashSet<AbstractMessageHandler<C>>(Arrays.asList(handlers));
		this.bits = new BitSet();
		this.data = data;
		this.handlers.forEach(handler -> this.allow(handler.id()));
	}
	
	public T data() {
		return this.data;
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
	
	protected void register(final AbstractMessageHandler<C> handler) {
		this.register(handler, true);
	}
	
	protected void register(final AbstractMessageHandler<C> handler, final boolean allowed) {
		this.handlers.add(handler);
		if(allowed)
			this.allow(handler.id());
	}

	@Override
	public Optional<AbstractMessageHandler<C>> handler(String message) {
		return this.handlers
					.stream()
					.filter(h -> allowed(h.id()) && h.canHandle(message))
					.findFirst();
	}	
	
	public abstract void enter();
	public abstract void exit();
}
