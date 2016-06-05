package com.codebreak.game.network.handler.character.selection;

import java.util.concurrent.ExecutorService;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.network.handler.AbstractGameState;

public class CharacterSelectionState extends AbstractGameState<CharacterSelectionArguments> {

	public CharacterSelectionState(final ExecutorService context, final Database db, final CharacterSelectionArguments data) {
		super(context, db, data);
		register(new CharacterSelectionHandler(this, db, data));
	}

	@Override
	public void enter() {
		
	}

	@Override
	public void exit() {
		
	}
}
