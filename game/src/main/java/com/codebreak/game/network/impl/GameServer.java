package com.codebreak.game.network.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.NoSuchElementException;
import java.util.concurrent.Executors;

import com.codebreak.common.network.AbstractTcpServer;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.Configuration;
import com.codebreak.game.network.handler.authentication.CheckingTicketState;

public final class GameServer extends AbstractTcpServer<GameClient, GameService> {

	public GameServer(final Database database, final Configuration config)
			throws NoSuchElementException, IOException {
		super(database, config, new GameService(Executors.newSingleThreadExecutor(), database, config));
	}

	@Override
	public GameClient createClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final Database database, final GameService service) {
		return new GameClient(
					identity, 
					buffer,
					channel, 
					new CheckingTicketState(
						service,
						database, 
						service
					)
				);
	}
}
