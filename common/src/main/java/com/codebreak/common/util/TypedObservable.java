package com.codebreak.common.util;

public interface TypedObservable<T> {
	public void addObserver(TypedObserver<T> observer);
	
	public void removeObserver(TypedObserver<T> observer);
	
	public void notifyObservers(final T event);
}
