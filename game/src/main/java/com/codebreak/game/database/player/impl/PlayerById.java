package com.codebreak.game.database.player.impl;

import java.util.Optional;

import static com.codebreak.game.persistence.Tables.PLAYER;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.database.player.AbstractPlayerOperation;
import com.codebreak.game.persistence.tables.records.PlayerRecord;

public class PlayerById extends AbstractPlayerOperation {

	private final long playerId;
	
	public PlayerById(final Database database, final long playerId) {
		super(database);
		this.playerId = playerId;
	}

	@Override
	protected Optional<PlayerRecord> fetchInternal(DSLContext context) {
		return context.selectFrom(PLAYER)
				.where(PLAYER.ID.eq(this.playerId))
				.fetchOptional();
	}
}
