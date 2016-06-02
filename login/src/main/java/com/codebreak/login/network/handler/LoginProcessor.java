package com.codebreak.login.network.handler;

import java.util.Optional;
import java.util.function.BiConsumer;

import com.codebreak.common.network.handler.AbstractMessageProcessor;
import com.codebreak.common.network.message.AbstractDofusMessage;
import com.codebreak.common.persistence.Database;
import com.codebreak.login.database.account.*;
import com.codebreak.login.database.account.exception.AlreadyConnectedException;
import com.codebreak.login.database.account.exception.BannedException;
import com.codebreak.login.database.account.exception.ExistantException;
import com.codebreak.login.database.account.exception.NonExistantException;
import com.codebreak.login.database.account.exception.WrongPasswordException;
import com.codebreak.login.network.LoginClient;
import com.codebreak.login.network.message.LoginMessage;
import com.codebreak.login.network.structure.HostInformations;
import com.codebreak.login.persistence.tables.records.AccountRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public final class LoginProcessor extends AbstractMessageProcessor<LoginClient> {
	
	public static final int PROTOCOL = 0;
	public static final int AUTHENTICATION = 1;
	public static final int SERVER_SELECTION = 2;

	private final Database db;
	
	public LoginProcessor(final Database db) {			
		this.db = db;
		
		super.register(new LoginHandler(PROTOCOL) {			
			private static final String PROTOCOL_VERSION = "1.29.1";
			
			@Override
			protected Optional<BiConsumer<LoginClient, String>> getHandler(final String message) {
				return Optional.of(this::protocolCheck);
			}
			
			private void protocolCheck(final LoginClient client, final String message) {
				prohibit(PROTOCOL);
				if(!message.equals(PROTOCOL_VERSION)) {
					client.write(LoginMessage.LOGIN_FAILURE_PROTOCOL);
				}
				else {
					enterProtocolCheckedState();
				}
				prohibit(PROTOCOL);
			}
		});
	}
	
	private final void enterProtocolCheckedState() {
		super.register(new LoginHandler(AUTHENTICATION) {
			
			@Override
			protected Optional<BiConsumer<LoginClient, String>> getHandler(final String message) {
				return Optional.of(this::authenticationRequest);
			}
			
			private final Optional<AccountRecord> fetchAccount(final String encryptKey, final String message) throws Exception {
				final String[] accountData = message.split("#1");
				String name = accountData[0];
				final String password = accountData[1];		
				final String HEADER_NEW = "new";
				if(name.startsWith(HEADER_NEW)) {
					name = name.substring(HEADER_NEW.length());
					new Create(
						new NonExistant(
							new AccountByName(db, name)
						),
						db,
						name, 
						name, 
						"test", 
						"test@test.test", 
						"test", 
						"test"
					)
					.fetch();
				}
				return 	new NotConnected(
							new NotBanned(
									new PasswordMatch(
											new Existant(
													new AccountByName(db, name)
											), 
											encryptKey,
											password
									)
							)
						).fetch();
			}
			
			private final void authenticationRequest(final LoginClient client, final String message) {		
				prohibit(AUTHENTICATION);			
				try {
					final AccountRecord account = fetchAccount(client.encryptKey(), message).get();
					client.write(
						AbstractDofusMessage.batch(
							LoginMessage.LOGIN_SUCCESS(account.getPower()),
							LoginMessage.ACCOUNT_NICKNAME(account.getNickname()),
							LoginMessage.ACCOUNT_SECRET_QUESTION(account.getSecretquestion()),
							LoginMessage.ACCOUNT_HOSTS(Lists.newArrayList(HostInformations.FAKE_LOCAL))
						)
					);
					enterAuthenticatedState(account);
				} 
				catch (NonExistantException | ExistantException | WrongPasswordException e) {
					client.write(LoginMessage.LOGIN_FAILURE_CREDENTIALS);
				}				
				catch (BannedException e) {
					client.write(LoginMessage.LOGIN_FAILURE_BANNED);
				}
				catch(AlreadyConnectedException e) {
					client.write(LoginMessage.LOGIN_FAILURE_CONNECTED);				
				} catch (Exception e) {
					client.closeChannel();
					e.printStackTrace();
				}
			}
		});
	}
				
	private final void enterAuthenticatedState(final AccountRecord account) {
		super.register(new LoginHandler(SERVER_SELECTION) {
			@Override
			protected Optional<BiConsumer<LoginClient, String>> getHandler(String message) {
				Preconditions.checkArgument(message.length() >= 2);
				switch(message.charAt(0)) {
					case 'A':
						switch(message.charAt(1)) {
							case 'x':
								return Optional.of(this::serverListRequest);
							case 'X':
								return Optional.of(this::serverSelectionRequest);
						}
						break;
				}
				return Optional.empty();
			}
			
			private void serverListRequest(final LoginClient client, final String message) {
				client.write(
					LoginMessage.ACCOUNT_CHARACTERS(
						Lists.newArrayList(
							HostInformations.FAKE_LOCAL
						), 
						account.getId(), 
						31536000000L
					)
				);
			}
			
			private void serverSelectionRequest(final LoginClient client, final String message) {	
				LOGGER.debug("server selection request id=" + message.substring(2));
			}
		});
	}	
}
