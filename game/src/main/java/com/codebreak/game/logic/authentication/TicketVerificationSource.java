package com.codebreak.game.logic.authentication;

import com.codebreak.common.network.ipc.message.impl.TransfertTicket;
import com.codebreak.common.util.TypedObservable;
import com.codebreak.game.logic.authentication.impl.TicketEvent;
import com.codebreak.game.persistence.tables.records.AccountRecord;

public interface TicketVerificationSource extends TypedObservable<TicketEvent> {
	void register(final TransfertTicket message);	
	AccountRecord get(final String hash) throws Exception;
}
