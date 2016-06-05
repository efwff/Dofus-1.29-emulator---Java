package com.codebreak.game.logic.account;

import java.util.Optional;

import com.codebreak.common.network.ipc.message.impl.AccountInformations;
import com.codebreak.common.util.TypedObservable;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public interface AccountSource extends TypedObservable<AccountEvent> {
	Optional<AccountRecord> byId(final long id);
	Optional<AccountRecord> byNickname(final String nickname);
	AccountRecord createIfNonExistant(final AccountInformations infos) throws Exception;
	void fireEvent(final AccountEventType type, final long accountId);
}
