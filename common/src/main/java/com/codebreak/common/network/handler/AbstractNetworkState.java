package com.codebreak.common.network.handler;

import java.util.BitSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import com.codebreak.common.persistence.impl.Database;

import gnu.trove.set.hash.THashSet;

public abstract class AbstractNetworkState<C, T> implements NetworkState<C> {
	
	private final ExecutorService context;
	private final T data;
	private final Set<AbstractMessageHandler<C>> handlers;
	private final BitSet bits;
	
	public AbstractNetworkState(final ExecutorService context, final Database db, final T data) {
		this.handlers = new THashSet<AbstractMessageHandler<C>>();
		this.bits = new BitSet();
		this.data = data;
		this.context = context;
	}
	
	public ExecutorService context() {
		return this.context;
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
