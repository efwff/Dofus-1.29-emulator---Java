package com.codebreak.login.network.message;

import java.util.List;
import java.util.stream.Collectors;

import com.codebreak.common.network.message.AbstractDofusMessage;
import com.codebreak.login.network.ipc.GameServer;

public final class LoginMessage {	
	
	public enum Community {
		FR(0);
		private final int code;
		Community(int code) {
			this.code = code;
		}
		public int code() {
			return this.code;
		}
	}
	
	public enum LoginFailureReason {
		PROTOCOL_REQUIRED('v'),
		WRONG_CREDENTIALS('f'),
		BANNED('b'),
		ACCOUNT_ALREADY_CONNECTED('c'),
		SERVER_BUSY('w'),
		SERVER_MAINTENANCE('m');	
		
		private final char code;
		LoginFailureReason(char code) {
			this.code = code;
		}
		public final char code() {
			return this.code;
		}
	}
	
	public enum WorldSelectionFailureReason {
		
		SERVER_DOWN('d'),
		SERVER_FULL_BUT_SOME_AVAILABLE('f'),
		SERVER_FULL('F'),
		GENERIC('r');
		
		
		private final char code;
		WorldSelectionFailureReason(char code) {
			this.code = code;
		}
		public final char code() {
			return this.code;
		}
	}
	
	public static final AbstractDofusMessage WORLD_SELECT_FAILURE_DOWN = WORLD_SELECTION_FAILURE(WorldSelectionFailureReason.SERVER_DOWN);
	public static final AbstractDofusMessage WORLD_SELECT_FAILURE_FULL = WORLD_SELECTION_FAILURE(WorldSelectionFailureReason.SERVER_FULL);
	public static final AbstractDofusMessage WORLD_SELECT_FAILURE_UNKNOW = WORLD_SELECTION_FAILURE(WorldSelectionFailureReason.GENERIC);
	
	public static final AbstractDofusMessage LOGIN_FAILURE_PROTOCOL = LOGIN_FAILURE(LoginFailureReason.PROTOCOL_REQUIRED);
	public static final AbstractDofusMessage LOGIN_FAILURE_CREDENTIALS = LOGIN_FAILURE(LoginFailureReason.WRONG_CREDENTIALS);
	public static final AbstractDofusMessage LOGIN_FAILURE_BANNED = LOGIN_FAILURE(LoginFailureReason.BANNED);
	public static final AbstractDofusMessage LOGIN_FAILURE_BUSY = LOGIN_FAILURE(LoginFailureReason.SERVER_BUSY);
	public static final AbstractDofusMessage LOGIN_FAILURE_MAINTENANCE = LOGIN_FAILURE(LoginFailureReason.SERVER_MAINTENANCE);
	public static final AbstractDofusMessage LOGIN_FAILURE_CONNECTED = LOGIN_FAILURE(LoginFailureReason.ACCOUNT_ALREADY_CONNECTED);
	
	public static final AbstractDofusMessage HELLO_CONNECT(final String key) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return "HC" + key;
			}
		};
	}		
	
	public static final AbstractDofusMessage LOGIN_FAILURE(final LoginFailureReason reason) {
		return new AbstractDofusMessage() {					
			@Override
			protected String internalSerialize() {
				return "AlE" + reason.code();
			}
		};
	}
	
	public static final AbstractDofusMessage LOGIN_SUCCESS(final int level) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return "AlK" + level;
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_NICKNAME(final String nickname) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return "Ad" + nickname;
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_COMMUNITY(final Community community) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return "Ac" + community.code();
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_SECRET_QUESTION(final String question) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return "AQ" + question;
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_HOSTS(final List<GameServer> hosts) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return "AH" + hosts.stream().map(server -> 
						String.format(
							"|%d;%d;%d;%d",
							server.id(),
							server.gameInfos().state(),
							server.gameInfos().completion(),
							server.gameInfos().selectable() ? 1 : 0
						)
					).collect(Collectors.joining());
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_CHARACTERS(final List<GameServer> serverInformations, final long accountId, final long remainingSubsription) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return "AxK" + remainingSubsription + serverInformations.stream().map(server -> 
						String.format(
							"|%d,%d",
							server.id(),
							server.characterCount(accountId)
						)
					).collect(Collectors.joining());
			}
		};
	}
	
	public static final AbstractDofusMessage WORLD_SELECTION_FAILURE(final WorldSelectionFailureReason reason) {
		return new AbstractDofusMessage() {
			@Override
			protected String internalSerialize() {
				return "AXE" + reason.code();
			}
		};
	}
	
	public static final AbstractDofusMessage WORLD_SELECTION_SUCCESS(final String ip, final int port, final String ticket) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return String.format(
							"AYK%s:%d:%s",
							ip,
							port,
							ticket
					   );
			}
		};
	}
}
