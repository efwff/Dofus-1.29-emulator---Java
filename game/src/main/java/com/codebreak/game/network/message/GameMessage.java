package com.codebreak.game.network.message;

import java.util.List;

import com.codebreak.common.network.message.AbstractDofusMessage;
import com.codebreak.game.persistence.tables.records.PlayerRecord;
import com.codebreak.game.world.entity.impl.GameCharacter;

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
	
	public static final AbstractDofusMessage CHARACTERS_LIST(final long remainingSubscription, List<GameCharacter> characters) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				  final StringBuilder message = new StringBuilder("ALK" + remainingSubscription);
		            if (characters.size() > 0) {
		                message.append('|').append(characters.size());
		                for (final GameCharacter character : characters) {
		                    message.append('|').append(character.id());
		                    message.append(';').append(character.name());
		                    message.append(';').append(character.level());
		                    message.append(';').append(character.skin());
		                    message.append(';').append(character.hexColors());
		                    message.append(';');
		                    character.serializeAsEquippedItemsInformations(message);
		                    message.append(';').append(character.merchant() ? '1' : '0');
		                    message.append(';').append(0); // Original game server id ?
		                    message.append(';').append(character.dead() ? '1' : '0');
		                    message.append(';').append(character.deathCount());
		                    message.append(';').append(character.maxLevel());
		                }
		            }
		            return message.toString();
			}
		};
	}
}
