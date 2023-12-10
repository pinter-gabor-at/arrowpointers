package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mod implements ModInitializer {
    public static final Logger LOGGER =
			LoggerFactory.getLogger("arrowpointers");

	@Override
	public void onInitialize() {
		ArrowRegistry.init();
	}
}