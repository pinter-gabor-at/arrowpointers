package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.fabricmc.api.ModInitializer;


public class Mod implements ModInitializer {

	@Override
	public void onInitialize() {
		ArrowRegistry.init();
	}
}
