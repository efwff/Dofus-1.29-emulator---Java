package com.codebreak.game.database.player.impl;

import java.util.List;
import java.util.Optional;

import static com.codebreak.game.persistence.Tables.PLAYER;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.database.player.AbstractPlayersOperation;
import com.codebreak.game.persistence.tables.records.PlayerRecord;

public final class AccountPlayers extends AbstractPlayersOperation {

	private final long accountId;
	
	public AccountPlayers(final Database database, final long accountId) {
		super(database);
		this.accountId = accountId;
	}

	@Override
	protected Optional<List<PlayerRecord>> fetchInternal(final DSLContext context) {
		return Optional.of(context.selectFrom(PLAYER)
				.where(PLAYER.ACCOUNTID.eq(this.accountId))
				.fetch());
	}
}
