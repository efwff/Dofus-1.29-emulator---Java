package com.codebreak.game.network.handler.authentication;

import java.util.concurrent.ExecutorService;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.logic.LogicSource;
import com.codebreak.game.network.handler.AbstractGameState;

public class CheckingTicketState extends AbstractGameState<LogicSource> {

	public CheckingTicketState(final ExecutorService context, final Database db, final LogicSource logicSource) {
		super(context, db, logicSource);
		register(new CheckTicketHandler(this, db, logicSource));
	}

	@Override
	public void enter() {
	}

	@Override
	public void exit() {
	}
}
