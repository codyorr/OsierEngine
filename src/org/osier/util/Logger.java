package org.osier.util;

import java.util.logging.Level;

public class Logger {
	
	private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("GlobalLogger");
	
	public static void log(String message) {
		logger.log(Level.ALL, message);
	}
}
