package com.codebreak.common.util.concurrent;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.util.TypedObserver;
import com.google.common.eventbus.EventBus;

public abstract class AbstractService<T>
	extends AbstractExecutorService
	implements TypedObserver<TcpEvent<T>> {
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractService.class);
	private final ExecutorService executor;
	private final EventBus eventBus;
	
	public AbstractService() {
		this.executor = Executors.newCachedThreadPool();
		this.eventBus = new EventBus();
	}
	public final EventBus eventBus() {
		return this.eventBus;
	}	

	@Override
	public void shutdown() {		
		this.executor.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return this.executor.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return this.executor.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return this.executor.isTerminated();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return this.executor.awaitTermination(timeout, unit);
	}

	@Override
	public void execute(Runnable command) {
		this.executor.execute(command);
	}
}