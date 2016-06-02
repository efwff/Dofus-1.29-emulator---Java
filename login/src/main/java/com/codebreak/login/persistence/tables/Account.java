/**
 * This class is generated by jOOQ
 */
package com.codebreak.login.persistence.tables;


import com.codebreak.login.persistence.CodebreakLogin;
import com.codebreak.login.persistence.Keys;
import com.codebreak.login.persistence.tables.records.AccountRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Account extends TableImpl<AccountRecord> {

    private static final long serialVersionUID = 1122913469;

    /**
     * The reference instance of <code>codebreak_login.account</code>
     */
    public static final Account ACCOUNT = new Account();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountRecord> getRecordType() {
        return AccountRecord.class;
    }

    /**
     * The column <code>codebreak_login.account.Id</code>.
     */
    public final TableField<AccountRecord, Long> ID = createField("Id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Name</code>.
     */
    public final TableField<AccountRecord, String> NAME = createField("Name", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Password</code>.
     */
    public final TableField<AccountRecord, String> PASSWORD = createField("Password", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Nickname</code>.
     */
    public final TableField<AccountRecord, String> NICKNAME = createField("Nickname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Power</code>.
     */
    public final TableField<AccountRecord, Integer> POWER = createField("Power", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>codebreak_login.account.CreationDate</code>.
     */
    public final TableField<AccountRecord, Timestamp> CREATIONDATE = createField("CreationDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>codebreak_login.account.LastConnectionDate</code>.
     */
    public final TableField<AccountRecord, Timestamp> LASTCONNECTIONDATE = createField("LastConnectionDate", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>codebreak_login.account.LastConnectionIP</code>.
     */
    public final TableField<AccountRecord, String> LASTCONNECTIONIP = createField("LastConnectionIP", org.jooq.impl.SQLDataType.VARCHAR.length(16).nullable(false).defaultValue(org.jooq.impl.DSL.inline("?.?.?.?", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>codebreak_login.account.RemainingSubscription</code>.
     */
    public final TableField<AccountRecord, Timestamp> REMAININGSUBSCRIPTION = createField("RemainingSubscription", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>codebreak_login.account.BannedUntil</code>.
     */
    public final TableField<AccountRecord, Timestamp> BANNEDUNTIL = createField("BannedUntil", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>codebreak_login.account.Banned</code>.
     */
    public final TableField<AccountRecord, Boolean> BANNED = createField("Banned", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>codebreak_login.account.SecretQuestion</code>.
     */
    public final TableField<AccountRecord, String> SECRETQUESTION = createField("SecretQuestion", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Answer</code>.
     */
    public final TableField<AccountRecord, String> ANSWER = createField("Answer", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * The column <code>codebreak_login.account.Email</code>.
     */
    public final TableField<AccountRecord, String> EMAIL = createField("Email", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "");

    /**
     * Create a <code>codebreak_login.account</code> table reference
     */
    public Account() {
        this("account", null);
    }

    /**
     * Create an aliased <code>codebreak_login.account</code> table reference
     */
    public Account(String alias) {
        this(alias, ACCOUNT);
    }

    private Account(String alias, Table<AccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private Account(String alias, Table<AccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return CodebreakLogin.CODEBREAK_LOGIN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<AccountRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AccountRecord> getPrimaryKey() {
        return Keys.KEY_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AccountRecord>> getKeys() {
        return Arrays.<UniqueKey<AccountRecord>>asList(Keys.KEY_ACCOUNT_PRIMARY, Keys.KEY_ACCOUNT_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Account as(String alias) {
        return new Account(alias, this);
    }

    /**
     * Rename this table
     */
    public Account rename(String name) {
        return new Account(name, null);
    }
}
