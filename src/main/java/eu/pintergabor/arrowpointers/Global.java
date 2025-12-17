package eu.pintergabor.arrowpointers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import net.minecraft.resources.Identifier;


public final class Global {

	private Global() {
		// Static class.
	}

	// Used for logging and registration.
	public static final String MODID = "arrowpointers";

	// This logger is used to write text to the console and the log file.
	@SuppressWarnings("unused")
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	// Parameters, some of which one day might be player configurable.

	// ArrowMarkBlock hitbox thickness: 0.001 ... 2.
	public static final double thickness = 0.001D;

	// ArrowMarkBlock luminance.
	public static final int arrowMarkBlockLumi = 1;
	// GlowArrowMarkBlock luminance.
	public static final int glowArrowMarkBlockLumi = 12;

	/**
	 * Create a mod specific identifier.
	 *
	 * @param path Name without {@link #MODID}.
	 */
	@Contract("_ -> new")
	@SuppressWarnings("unused")
	public static @NotNull Identifier modId(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}
