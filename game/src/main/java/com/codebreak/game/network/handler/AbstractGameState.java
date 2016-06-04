package com.codebreak.game.network.handler;

import com.codebreak.common.network.handler.AbstractNetworkState;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.network.impl.GameClient;

public abstract class AbstractGameState<T> extends AbstractNetworkState<GameClient, T> implements GameState {
	public AbstractGameState(final Database db, final T data) {
		super(db, data);
	}
}
