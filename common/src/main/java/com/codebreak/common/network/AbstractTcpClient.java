package com.codebreak.common.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codebreak.common.network.message.AbstractDofusMessage;
import com.codebreak.common.util.AbstractTypedObservable;

public abstract class AbstractTcpClient<T> 
	extends AbstractTypedObservable<TcpEvent<T>> 
	implements CompletionHandler<Integer, Integer> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTcpClient.class);
		
	private static final int OUTPUT_CAPACITY = 1024;
	private static final int OPERATION_WRITE = 0;
	private static final int OPERATION_READ = 1;
	
	private final ByteBuffer output;
	private final AtomicBoolean writing;
	private final ConcurrentLinkedQueue<byte[]> messagesQueue;
	private final int identity;
	private final ByteBuffer buffer;
	private final AsynchronousSocketChannel channel;
	private String ip;
	
	public AbstractTcpClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel) {
		this.output = ByteBuffer.allocate(OUTPUT_CAPACITY);
		this.messagesQueue = new ConcurrentLinkedQueue<>();
		this.writing = new AtomicBoolean(false);
		this.identity = identity;
		this.channel = channel;
		this.buffer = buffer;
		try {
			this.ip = this.channel.getRemoteAddress().toString();
		} catch (IOException e) {
			this.ip = "?.?.?.?";
		}
	}
	
	public final int identity() {
		return this.identity;
	}
	
	public final String ip() {
		return this.ip;
	}
	
	public final ByteBuffer buffer() {
		return this.buffer;
	}
	
	public final AsynchronousSocketChannel socket() {
		return this.channel;
	}
	
	public void read() {
		this.buffer.position(0);
		this.channel.read(this.buffer, OPERATION_READ, this);
	}
		
	public void write(AbstractDofusMessage message) {
		this.write(
			message.serialize().getBytes()
		);
	}
	
	public void write(byte[] message) {
		this.messagesQueue.offer(message);
		this.flushIfNecessary();
	}
	
	private void flushIfNecessary() {
		if(this.messagesQueue.peek() == null)
			return;		
		
		// if we can write (mean that there is no writing operation pending and some messages are waiting)
		if(this.writing.compareAndSet(false, true)) {
			
			// reset the buffer
			output.clear();
			output.limit(OUTPUT_CAPACITY);
			
			// check out if we dont overflow
			int bytesToSend = 0;
			while(this.messagesQueue.peek() != null) {
				final byte[] message = this.messagesQueue.peek();
				final int messageLength = message.length;
				
				// we cannot send more than 1024 bytes in one burst
				if(bytesToSend + messageLength > OUTPUT_CAPACITY) {
					// we need to chunk the message
					if(bytesToSend == 0) {
						final byte[] bigMessage = this.messagesQueue.poll();
						final int bigMessageLength = bigMessage.length;
						final int chunkMaxSize = OUTPUT_CAPACITY;
						final int numberOfChunk = bigMessageLength / chunkMaxSize;
						final int finalChunkSize = bigMessageLength - (chunkMaxSize * numberOfChunk);
						// create sub chunks
						for(int i = 0; i < numberOfChunk; i++) {
							final int beginOffset = i * chunkMaxSize;
							final int endOffset = beginOffset + chunkMaxSize;
							this.messagesQueue.offer(Arrays.copyOfRange(bigMessage, beginOffset, endOffset));
						}
						// final chunk
						if(finalChunkSize > 0) {
							final int lastOffset = numberOfChunk * chunkMaxSize;
							this.messagesQueue.offer(Arrays.copyOfRange(bigMessage, lastOffset, bigMessageLength));
						}
						LOGGER.debug(
								"chunked message: originalLength={}, nbOfChunk={}, finalChunkSize={}", 
								bigMessageLength,
								numberOfChunk,
								finalChunkSize
						);
					}
					else {
						break;
					}
				}
				else  {
					bytesToSend += messageLength;
					output.put(this.messagesQueue.poll());
				}
			}
			
			// reset the buffer and set the limit to the chunk that we wrote
			output.clear();
			output.limit(bytesToSend);
			LOGGER.debug("flush >> " + new String(output.array(), 0, output.remaining()));
			this.channel.write(output, OPERATION_WRITE, this);
		}
	}
	
	@Override
	public void completed(final Integer bytesTransferred, final Integer operation) {
		switch(operation) {
			case OPERATION_WRITE:
				this.writing.set(false);
				this.flushIfNecessary();
				this.notifyObservers(TcpEventType.SENT);
				break;
			
			case OPERATION_READ:
				if(bytesTransferred > 0) {
					this.buffer.position(0);
					this.handleIncomming(bytesTransferred, this.buffer);
					this.notifyObservers(TcpEventType.RECEIVED);
					this.read();
				}
				else {
					this.disconnected();
				}
				break;
		}
	}
	
	@Override
	public void failed(final Throwable exc, final Integer operation) {
		this.disconnected();
	}  
		
	public void closeChannel() {
		try {
			this.channel.shutdownInput();
			this.channel.shutdownOutput();
			this.channel.close();
		} catch (IOException e) {		
		}		
	}
	
	private void disconnected() {
		this.closeChannel();
		this.notifyObservers(TcpEventType.DISCONNECTED);
	}
	
	@SuppressWarnings("unchecked")
	private void notifyObservers(final TcpEventType type) {
		this.notifyObservers(observer -> observer.onEvent(new TcpEvent<T>((T)this, type)));
	}
	
	protected abstract void handleIncomming(final int count, final ByteBuffer buffer);
}
