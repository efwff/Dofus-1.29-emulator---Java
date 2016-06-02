package com.codebreak.login.network.message;

import java.util.List;
import java.util.stream.Collectors;

import com.codebreak.common.network.message.AbstractDofusMessage;
import com.codebreak.login.network.structure.HostCharactersInformations;
import com.codebreak.login.network.structure.HostInformations;

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
		LOGIN_ERROR_PROTOCOL('v'),
		LOGIN_ERROR_CREDENTIALS('f'),
		LOGIN_ERROR_BANNED('b'),
		LOGIN_ERROR_ALREADY_CONNECTED('c'),
		LOGIN_ERROR_SERVER_BUSY('w'),
		LOGIN_ERROR_SERVER_MAINTENANCE('m');	
		
		private final char code;
		LoginFailureReason(char code) {
			this.code = code;
		}
		public final char code() {
			return this.code;
		}
	}
	
	public static final AbstractDofusMessage LOGIN_FAILURE_PROTOCOL = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_PROTOCOL);
	public static final AbstractDofusMessage LOGIN_FAILURE_CREDENTIALS = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_CREDENTIALS);
	public static final AbstractDofusMessage LOGIN_FAILURE_BANNED = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_BANNED);
	public static final AbstractDofusMessage LOGIN_FAILURE_BUSY = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_SERVER_BUSY);
	public static final AbstractDofusMessage LOGIN_FAILURE_MAINTENANCE = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_SERVER_MAINTENANCE);
	public static final AbstractDofusMessage LOGIN_FAILURE_CONNECTED = LOGIN_FAILURE(LoginFailureReason.LOGIN_ERROR_ALREADY_CONNECTED);
	
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
	
	public static final AbstractDofusMessage ACCOUNT_HOSTS(final List<HostInformations> hosts) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return "AH" + hosts.stream().map(host -> 
						String.format(
							"|%d;%d;%d;%d",
							host.gameServerId(),
							host.gameServerState(),
							host.completionState(),
							host.selectable() ? 1 : 0
						)
					).collect(Collectors.joining());
			}
		};
	}
	
	public static final AbstractDofusMessage ACCOUNT_CHARACTERS(final List<HostCharactersInformations> hosts, final long accountId, final long remainingSubsription) {
		return new AbstractDofusMessage() {			
			@Override
			protected String internalSerialize() {
				return "AxK" + remainingSubsription + hosts.stream().map(host -> 
						String.format(
							"|%d,%d",
							host.gameServerId(),
							host.characterCount(accountId)
						)
					).collect(Collectors.joining());
			}
		};
	}
}
