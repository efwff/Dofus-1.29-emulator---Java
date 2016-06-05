package com.codebreak.game.database.account.impl;

import static com.codebreak.game.persistence.Tables.ACCOUNT;

import java.util.Optional;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.database.account.AbstractAccountOperationWrap;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public final class CreateIfNonExistant extends AbstractAccountOperationWrap {
	
	private final Database database;
	private final long id;
	private final String nickname;
	
	public CreateIfNonExistant(final DatabaseOperation<AccountRecord> origin,
			final Database database, 
			final long id,
			final String nickname) {
		super(origin);
		this.database = database;
		this.id = id;
		this.nickname = nickname;
	}

	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		if(account.isPresent())
			return account;
		final DSLContext context = this.database.context();
		final AccountRecord newAccount = context.newRecord(ACCOUNT);
		newAccount.setId(this.id);
		newAccount.setNickname(this.nickname);
		newAccount.insert();
		return Optional.of(newAccount);
	}
}

