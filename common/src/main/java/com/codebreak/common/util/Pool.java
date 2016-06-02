package com.codebreak.common.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Pool<T> {
	public T borrow() throws InterruptedException;
	public void giveBack(T object) throws InterruptedException;
	
	public static <T> Pool<T> Basic(final Supplier<T> factory) {
		return Basic(factory, (object) -> {});
	}
	public static <T> Pool<T> Basic(final Supplier<T> factory, final Consumer<T> cleaner) {
		final BlockingQueue<T> objects = new ArrayBlockingQueue<T>(0, false);		
		return new Pool<T>() {					
			@Override
			public T borrow() throws InterruptedException {
				if(objects.isEmpty())
					return factory.get();
				return objects.take();
			}
			@Override
			public void giveBack(T object) throws InterruptedException {
				cleaner.accept(object);
				objects.put(object);
			}
		};
	}
}
