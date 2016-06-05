package com.codebreak.game.logic.account;

import java.util.Optional;

import com.codebreak.common.network.ipc.message.impl.AccountInformations;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public interface AccountSource {
	Optional<AccountRecord> byId(final long id);
	Optional<AccountRecord> byNickname(final String nickname);
	AccountRecord createIfNonExistant(final AccountInformations infos) throws Exception;
}
