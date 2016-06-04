package com.codebreak.game.network.impl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.AbstractExecutorService;

import com.codebreak.common.network.AbstractDofusClient;
import com.codebreak.common.persistence.impl.Database;

public final class GameClient extends AbstractDofusClient<GameClient> {
	public GameClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel,
			final Database database, final AbstractExecutorService service) {
		super(identity, buffer, channel, service, null);
	}
}
