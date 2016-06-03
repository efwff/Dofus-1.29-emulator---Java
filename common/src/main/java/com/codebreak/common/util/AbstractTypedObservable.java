package com.codebreak.common.util;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;

import gnu.trove.set.hash.THashSet;


public abstract class AbstractTypedObservable<T> implements TypedObservable<T> {
	
	private final Set<TypedObserver<T>> observers;
		
	@SafeVarargs
	public AbstractTypedObservable(final TypedObserver<T>... observers) {
		this.observers = new THashSet<>(Arrays.asList(observers));
	}
	
	public void addObserver(TypedObserver<T> observer) {
		this.observers.add(observer);
	}
	
	public void removeObserver(TypedObserver<T> observer) {
		this.observers.remove(observer);
	}
	
	public void notifyObservers(Consumer<TypedObserver<T>> action) {
		new THashSet<>(observers)
			.forEach(action);
	}
}
