package com.codebreak.game.network.impl;

import java.util.concurrent.ExecutorService;

import com.codebreak.common.network.TcpEvent;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.concurrent.AbstractService;

public final class GameService extends AbstractService<GameClient> {

	public GameService(final ExecutorService executor, final Database database) {
		super(executor, database);
	}

	@Override
	public void onEvent(final TcpEvent<GameClient> event) {
		switch(event.type()) {
			case CONNECTED:
				LOGGER.info("client connected");
				break;
			case DISCONNECTED:
				LOGGER.info("client disconnected");
				break;
			case RECEIVED:
				break;
			case SENT:
				break;
			default:
				break;
		}
	}
}
