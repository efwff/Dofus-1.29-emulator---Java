package com.codebreak.game.logic;

import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.TypedObservable;
import com.codebreak.game.logic.impl.TicketEvent;

public interface TicketVerificationSource extends TypedObservable<TicketEvent> {
	void register(TransfertTicket message);	
}
