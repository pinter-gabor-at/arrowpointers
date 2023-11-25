package de.dafuqs.chalk.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Constants {
	private Constants() {
		// Static class
	}

	// Used for logging and registration
	public static final String MODID = "chalk";

	// This logger is used to write text to the console and the log file.
	public static final Logger LOGGER = LogManager.getLogger(MODID);
}
