package com.codebreak.game.logic.statistic;

import java.util.Set;

import com.codebreak.common.util.TypedObserver;
import com.codebreak.game.logic.account.AccountEvent;

public interface OnlinePlayersSource extends TypedObserver<AccountEvent> {
	Set<Long> onlineAccounts();
	Set<Long> onlineCharacters();
}
