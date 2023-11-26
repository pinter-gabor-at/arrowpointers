package eu.pintergabor.chalk;

import net.fabricmc.api.ModInitializer;

public class Chalk implements ModInitializer {
	@Override
	public void onInitialize() {
		ChalkRegistry.init();
	}
}