package eu.pintergabor.arrowpointers;

import net.fabricmc.api.ModInitializer;

public class ArrowPointers implements ModInitializer {
	@Override
	public void onInitialize() {
		ArrowRegistry.init();
	}
}