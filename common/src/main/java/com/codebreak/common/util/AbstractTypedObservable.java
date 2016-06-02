package com.codebreak.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Consumer;


public abstract class AbstractTypedObservable<TEvent> {
	private final HashSet<TypedObserver<TEvent>> observers;
		
	@SafeVarargs
	public AbstractTypedObservable(final TypedObserver<TEvent>... observers) {
		this.observers = new HashSet<>(Arrays.asList(observers));
	}
	
	public void addObserver(TypedObserver<TEvent> observer) {
		this.observers.add(observer);
	}
	
	public void removeObserver(TypedObserver<TEvent> observer) {
		this.observers.remove(observer);
	}
	
	public void notifyObservers(Consumer<TypedObserver<TEvent>> action) {
		this.observers.forEach(action);
	}
}
