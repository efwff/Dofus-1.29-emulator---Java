package com.codebreak.login.network.ipc;

import com.codebreak.common.network.ipc.GameInformationsSource;
import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.TypedObservable;

public interface GameServer extends GameInformationsSource, TypedObservable<GameEvent> {
	int id();
	int characterCount(final long accountId);
	void transfertPlayer(final TransfertTicket message);
}
