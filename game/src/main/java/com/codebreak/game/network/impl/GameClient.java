package com.codebreak.game.network.impl;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

import com.codebreak.common.network.AbstractDofusClient;
import com.codebreak.game.network.handler.GameState;

public final class GameClient extends AbstractDofusClient<GameClient> {
	public GameClient(final int identity, final ByteBuffer buffer, final AsynchronousSocketChannel channel, final GameState initialState) {
		super(identity, buffer, channel, initialState);
	}
}
