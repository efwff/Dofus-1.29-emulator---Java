package com.codebreak.common.util;

import java.util.List;
import java.util.Stack;

public abstract class AbstractIdentityStack<T> implements Identities<T> {
	
	private final Stack<T> identities;

	public AbstractIdentityStack(final List<T> identities) {
		this.identities = new Stack<>();
		this.identities.addAll(identities);
	}
	
	@Override
	public T give() {
		return this.identities.pop();
	}

	@Override
	public void take(final T identity) {
		this.identities.push(identity);
	}
	
}
