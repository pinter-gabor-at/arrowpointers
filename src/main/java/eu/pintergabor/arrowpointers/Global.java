package eu.pintergabor.arrowpointers;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Global {
    private Global() {
        // Static class
    }

    // Used for logging and registration
    public static final String MODID = "arrowpointers";

    // This logger is used to write text to the console and the log file.
    @SuppressWarnings("unused")
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    // Parameters, some of which one day might be player configurable

    // ArrowMarkBlock hitbox margin: 0 … 2
    public static final double margin = 0D;
    // ArrowMarkBlock hitbox thickness: 0.001 … 2
    public static final double thick = 0.001D;

    // ArrowMarkBlock luminance
    public static final int arrowMarkBlockLumi = 1;
    // GlowArrowMarkBlock luminance
    public static final int glowArrowMarkBlockLumi = 12;

    /**
     * Create a mod specific identifier
     *
     * @param path Name, as in lang/*.json files without "*.modid." prefix
     */
    public static Identifier ModIdentifier(String path) {
        return Identifier.of(MODID, path);
    }
}
