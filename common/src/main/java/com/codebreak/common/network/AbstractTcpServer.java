package com.codebreak.common.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.AbstractObservable;
import com.codebreak.common.util.Configuration;
import com.codebreak.common.util.Identities;
import com.codebreak.common.util.TypedObserver;
import com.codebreak.common.util.concurrent.AbstractService;
import com.codebreak.common.util.impl.IntRangeIdentities;
import com.codebreak.common.util.impl.LinearBufferStack;

public abstract class AbstractTcpServer<C extends AbstractTcpClient<C>, S extends AbstractService<C>> 
	extends AbstractObservable<TcpEvent<C>>
	implements TypedObserver<TcpEvent<C>>, CompletionHandler<AsynchronousSocketChannel, Void> {	

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTcpServer.class);
	
	public static final String CONFIG_HOST = "tcp.host";
	public static final String CONFIG_PORT = "tcp.port";
	public static final String CONFIG_MAX_CLIENT = "tcp.maxClient";
	public static final String CONFIG_BUFF_SIZE = "tcp.buffSize";
		
	private final String host;
	private final int port;	
	private final int maxClient;
	private final int bufferSize;
	private final LinearBufferStack bufferStack;
	private final Identities<Integer> clientIdentities;
	private final AsynchronousServerSocketChannel serverChannel;
	private final Set<C> clients;
	private final S service;
	private final Database database;
	
	public AbstractTcpServer(final Database database, final Configuration config, final S service) throws NoSuchElementException, IOException {
		super(service);
		this.service = service;
		this.database = database;
		this.host = config.string(CONFIG_HOST);
		this.port = config.integer(CONFIG_PORT);
		this.maxClient = config.integer(CONFIG_MAX_CLIENT);
		this.bufferSize = config.integer(CONFIG_BUFF_SIZE);
		this.serverChannel = AsynchronousServerSocketChannel.open();
		this.bufferStack = new LinearBufferStack(bufferSize, maxClient);
		this.clientIdentities = new IntRangeIdentities(0, maxClient);
		this.clients = Collections.newSetFromMap(new ConcurrentHashMap<C, Boolean>());
	}
	
	protected final int acquireClientIdentity() {
		return this.clientIdentities.give();
	}
	
	protected final void releaseClientIdentity(final int identity) {
		this.clientIdentities.take(identity);
	}
		
	private ByteBuffer acquireClientBuffer(final int identity) {
		return ByteBuffer.wrap(
					this.bufferStack.buffer(), 
					this.bufferSize * identity, 
					this.bufferSize
				);
	}
		
	private void acceptNext() {
		this.serverChannel.accept(null, this);
	}
			
	public void start() throws Exception {	
        if(!this.serverChannel.isOpen())
        	throw new Exception("TcpServer failed to open socket channel : " + host + ":" + port);        	
		this.serverChannel.bind(new InetSocketAddress(host, port));
		this.acceptNext();
		LOGGER.debug("TcpServer listening");
	}
	
	@Override
	public void completed(final AsynchronousSocketChannel channel, final Void attachment) {
		this.acceptNext();
		final int identity = this.acquireClientIdentity();
		final ByteBuffer buffer = this.acquireClientBuffer(identity);
		final C client = this.createClient(identity, buffer, channel, this.database, this.service);
		this.clients.add(client);
		client.addObserver(this);
		client.read();
		this.onEvent(new TcpEvent<C>(client, TcpEventType.CONNECTED));
	}	
	
	@Override
	public void failed(final Throwable exception, final Void attachment) {
	}
	
	@Override
	public void onEvent(final TcpEvent<C> event) {
		switch(event.type()) {
			case CONNECTED:
				LOGGER.info("client connected");
				break;
				
			case DISCONNECTED: 
				event.object().removeObserver(this);
				this.clients.remove(event.object());
				this.releaseClientIdentity(event.object().identity());
				LOGGER.info("client disconnected");
				break;
				
			default:
				break;
		}		
		fireEvent(event.object(), event.type());
	}
		
	protected void fireEvent(final C client, final TcpEventType type) {
		this.notifyObservers(observer -> observer.onEvent(new TcpEvent<C>(client, type)));
	}
	
	public abstract C createClient(
		final int identity, 
		final ByteBuffer buffer,
		final AsynchronousSocketChannel channel,
		final Database database,
		final S service
	);
}
