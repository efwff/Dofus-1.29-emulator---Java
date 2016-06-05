package com.codebreak.game.network.handler;

import com.codebreak.common.network.handler.AbstractMessageHandler;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.network.impl.GameClient;

public abstract class AbstractGameHandler extends AbstractMessageHandler<GameClient> {
	
	public static final int TICKET = 0;
	public static final int CHARACTER_SELECTION = 1;
	
	public AbstractGameHandler(final AbstractGameState<?> parent, final Database db, final int id) {
		super(parent, db, id);
	}
}
