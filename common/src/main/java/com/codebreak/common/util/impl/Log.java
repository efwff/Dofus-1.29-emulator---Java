package com.codebreak.common.util.impl;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public final class Log {
	public static void configure() {
		final ConsoleAppender console = new ConsoleAppender();
		final String PATTERN = "%d [%p|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN));
		console.setThreshold(Level.DEBUG);
		console.activateOptions();
		Logger.getRootLogger().addAppender(console);
	}
}
