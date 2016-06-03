package com.codebreak.common.util;

import java.util.function.Consumer;

public interface TypedObservable<T> {
	public void addObserver(TypedObserver<T> observer);
	
	public void removeObserver(TypedObserver<T> observer);
	
	public void notifyObservers(Consumer<TypedObserver<T>> action);
}
