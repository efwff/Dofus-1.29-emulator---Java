package com.codebreak.game.logic.account.impl;

import java.util.Optional;

import com.codebreak.common.network.ipc.message.impl.AccountInformations;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.common.util.AbstractObservable;
import com.codebreak.game.database.account.impl.AccountById;
import com.codebreak.game.database.account.impl.CreateIfNonExistant;
import com.codebreak.game.logic.account.AccountEvent;
import com.codebreak.game.logic.account.AccountEventType;
import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public final class Accounts extends AbstractObservable<AccountEvent> implements AccountSource {

	private final Database db;
	
	public Accounts(final Database db) {
		this.db = db;
	}
	
	@Override
	public Optional<AccountRecord> byId(long id) {
		return new AccountById(db, id).fetch();
	}

	@Override
	public Optional<AccountRecord> byNickname(String nickname) {
		return Optional.empty();
	}

	@Override
	public AccountRecord createIfNonExistant(AccountInformations infos) throws Exception {
		return new CreateIfNonExistant(
					new AccountById(db, infos.id()),
					db,
					infos.id(),
					infos.nickname()
				).fetch().get();
	}

	@Override
	public void fireEvent(AccountEventType type, long accountId) {
		this.notifyObservers(new AccountEvent(type, accountId));
	}
}
