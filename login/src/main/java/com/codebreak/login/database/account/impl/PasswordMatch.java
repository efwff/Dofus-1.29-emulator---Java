package com.codebreak.login.database.account.impl;

import java.util.Optional;

import com.codebreak.common.persistence.DatabaseOperation;
import com.codebreak.login.database.account.AbstractAccountOperationWrap;
import com.codebreak.login.database.account.impl.exception.WrongPasswordException;
import com.codebreak.login.persistence.tables.records.AccountRecord;

public final class PasswordMatch extends AbstractAccountOperationWrap {
	
	private static final char[] HASH = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
               't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
               'V', 'W', 'X', 'Y', 'Z' , '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
	
	private final String encryptKey;
	private final String givenPassword;
	
	public PasswordMatch(final DatabaseOperation<AccountRecord> origin, final String encryptKey, final String givenPassword) {
		super(origin);
		this.encryptKey = encryptKey;
		this.givenPassword = givenPassword;
	}
	
	@Override
	public Optional<AccountRecord> fetch() throws Exception {
		final Optional<AccountRecord> account = super.fetch();
		final String originalPasswordEncrypted = cryptPassword(account.get().getPassword());
		LOGGER.debug("original encrypted=" + originalPasswordEncrypted + " given=" + this.givenPassword);
		final boolean passwordMatch = this.givenPassword.equals(originalPasswordEncrypted);
		if(!passwordMatch)
			throw new WrongPasswordException(account);
		return account;				
	}
	
	private String cryptPassword(final String originalPassword)
    {
        final StringBuilder crypted = new StringBuilder();
        for (int i = 0; i < originalPassword.length(); i++)
        {
            int pPass = originalPassword.charAt(i);
            int pKey = this.encryptKey.charAt(i);

            int aPass = pPass / 16;
            int aKey = pPass % 16;

            int aNB = (aPass + pKey) % HASH.length;
            int aNB2 = (aKey + pKey) % HASH.length;

            crypted.append(HASH[aNB]);
            crypted.append(HASH[aNB2]);
        }
        return crypted.toString();
    }
}
