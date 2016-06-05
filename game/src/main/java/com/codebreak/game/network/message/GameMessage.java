package com.codebreak.game.network.message;

import com.codebreak.common.network.message.AbstractDofusMessage;

public final class GameMessage {
	public static final AbstractDofusMessage STATIC_MESSAGE(final String message) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return message;
			}
		};
	}
	public static final AbstractDofusMessage HELLO_GAME = STATIC_MESSAGE("HG");
	public static final AbstractDofusMessage ACCOUNT_TICKET_SUCCESS = STATIC_MESSAGE("ATK0");
	public static final AbstractDofusMessage ACCOUNT_TICKET_ERROR = STATIC_MESSAGE("ATE");
	public static final AbstractDofusMessage ACCOUNT_REGIONAL_VERSION = STATIC_MESSAGE("AVfr");
}
