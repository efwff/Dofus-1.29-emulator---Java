package com.codebreak.login.database.account.impl;

import static com.codebreak.login.persistence.Tables.ACCOUNT;

import java.util.Optional;

import org.jooq.DSLContext;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.common.persistence.impl.Database;
import com.codebreak.login.database.account.AbstractAccountOperationWrap;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class Create extends AbstractAccountOperationWrap {
	
	private final Database database;
	private final String name;
	private final String nickname;
	private final String password;
	private final String email;
	private final String question;
	private final String answer;
	
	public Create(final DatabaseOperation<AccountRecord> origin,
			final Database database, 
			final String name,
			final String nickname,
			final String password,
			final String email, 
			final String question, 
			final String answer) {
		super(origin);
		this.database = database;
		this.name = name;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
	}

	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		super.fetch();
		final DSLContext context = this.database.context();
		final AccountRecord newAccount = context.newRecord(ACCOUNT);
		newAccount.setName(this.name);
		newAccount.setNickname(this.nickname);
		newAccount.setPassword(this.password);
		newAccount.setEmail(this.email);
		newAccount.setSecretquestion(this.question);
		newAccount.setSecretanswer(this.answer);
		newAccount.insert();
		return Optional.of(newAccount);
	}
}
