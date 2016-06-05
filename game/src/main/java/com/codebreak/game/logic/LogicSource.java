package com.codebreak.game.logic;

import com.codebreak.game.logic.account.AccountSource;
import com.codebreak.game.logic.authentication.TicketVerificationSource;
import com.codebreak.game.logic.statistic.OnlinePlayersSource;

public interface LogicSource {
	OnlinePlayersSource onlinePlayers();
	AccountSource accounts();
	TicketVerificationSource tickets();
}
