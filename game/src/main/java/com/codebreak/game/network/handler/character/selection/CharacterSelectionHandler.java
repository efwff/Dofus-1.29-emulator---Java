package com.codebreak.game.network.handler.character.selection;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.codebreak.common.persistence.impl.Database;
import com.codebreak.game.database.player.impl.AccountPlayers;
import com.codebreak.game.network.handler.AbstractGameHandler;
import com.codebreak.game.network.handler.AbstractGameState;
import com.codebreak.game.network.handler.GameState;
import com.codebreak.game.network.impl.GameClient;
import com.codebreak.game.network.message.GameMessage;
import com.codebreak.game.persistence.tables.records.PlayerRecord;
import com.codebreak.game.world.entity.impl.GameCharacter;
import com.google.common.base.Preconditions;

public final class CharacterSelectionHandler extends AbstractGameHandler {

	private final CharacterSelectionArguments data;
	
	public CharacterSelectionHandler(final AbstractGameState<?> parent, final Database db, final CharacterSelectionArguments data) {
		super(parent, db, CHARACTER_SELECTION);
		this.data = data;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<BiFunction<GameClient, String, Optional<GameState>>> getHandler(
			final String message) {
		Preconditions.checkArgument(message.length() >= 2);
		switch (message.charAt(0)) {
            case 'A':
                switch (message.charAt(1)) {
                    case 'V':
                        return Optional.of(this::regionalVersionRequest);
                    case 'L':
                        return Optional.of(this::characterListRequest);
                    case 'A': 
                        return Optional.of(this::characterCreationRequest);
                    case 'S': 
                        return Optional.of(this::characterSelectionRequest);
                    case 'D': 
                        return Optional.of(this::characterDeletionRequest);
                    case 'P': 
                        return Optional.of(this::characterNameGenerationRequest);
                    //case 'R': 
                        // heroic mode, character reborn request
                }
		}
		return Optional.empty();
	}
	
	private Optional<GameState> regionalVersionRequest(final GameClient client, final String message) {
		client.write(GameMessage.ACCOUNT_REGIONAL_VERSION);
		return stay();
	}
	
	private Optional<GameState> characterListRequest(final GameClient client, final String message) {
		final List<GameCharacter> characters = 	
				new AccountPlayers(db(), this.data.account().getId())
						.fetch()
						.get()
						.stream()
						.map(p -> { return new GameCharacter(p); })
						.collect(Collectors.toList());				
		client.write(
			GameMessage.CHARACTERS_LIST(
				1000000,
				characters
			)
		);
		return stay();
	}
	
	private Optional<GameState> characterSelectionRequest(final GameClient client, final String message) {
		return stay();
	}
	
	private Optional<GameState> characterCreationRequest(final GameClient client, final String message) {
		return stay();
	}
	
	private Optional<GameState> characterDeletionRequest(final GameClient client, final String message) {
		return stay();
	}
	
	private Optional<GameState> characterNameGenerationRequest(final GameClient client, final String message) {
		return stay();
	}
}
