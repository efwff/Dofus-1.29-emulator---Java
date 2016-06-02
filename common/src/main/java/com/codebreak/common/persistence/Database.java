package com.codebreak.common.persistence;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;
/*jdbc() {
    driver('com.mysql.cj.jdbc.Driver')
    url('jdbc:mysql://localhost:3306/codebreak_login?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris')
    user('root')
    password('')
}
generator() {
    database() {
    	name('org.jooq.util.mysql.MySQLDatabase')
    	includes('.*')
    	excludes('')
    	inputSchema('codebreak_login')
    }
    target() {
        packageName('com.codebreak.login.persistence')
        directory('./src/main/java')
    }
}*/

public final class Database {

	public static final String CONFIG_PACKAGE = "db.gen.package";
	public static final String CONFIG_URL = "db.url";
	public static final String CONFIG_USER = "db.user";
	public static final String CONFIG_PASS = "db.pass";
	public static final String CONFIG_SCHEMA = "db.schema";
	
	private final Configuration dbConfig;
	
	public Database(final com.codebreak.common.util.Configuration config) {
		this.dbConfig = new Configuration()
			    .withJdbc(new Jdbc()
				        .withDriver("com.mysql.cj.jdbc.Driver")
				        .withUrl(config.string(CONFIG_URL))
				        .withUser(config.string(CONFIG_USER))
				        .withPassword(config.string(CONFIG_PASS)))
				    .withGenerator(new Generator()
				        .withDatabase(new org.jooq.util.jaxb.Database()
				            .withName("org.jooq.util.mysql.MySQLDatabase")
				            .withIncludes(".*")
				            .withExcludes("")
				            .withInputSchema(config.string(CONFIG_SCHEMA)))
				        .withTarget(new Target()
				            .withPackageName(config.string(CONFIG_PACKAGE))
				            .withDirectory("./src/main/java")));
	}

	public DSLContext context() {
		return DSL.using(this.dbConfig.getJdbc().getUrl(), this.dbConfig.getJdbc().getUser(), this.dbConfig.getJdbc().getPassword());
	}	
	
	public void generate() throws Exception {
		GenerationTool.generate(this.dbConfig);
	}
}
