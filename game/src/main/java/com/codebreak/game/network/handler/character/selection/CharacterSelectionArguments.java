package com.codebreak.game.network.handler.character.selection;

import com.codebreak.game.logic.LogicSource;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public final class CharacterSelectionArguments {
	private final AccountRecord account;
	private final LogicSource logicSource;
	public CharacterSelectionArguments(final AccountRecord account, final LogicSource logicSource) {
		this.account = account;
		this.logicSource = logicSource;
	}
	public AccountRecord account() {
		return this.account;
	}
	public LogicSource logicSource() {
		return this.logicSource;
	}
}
