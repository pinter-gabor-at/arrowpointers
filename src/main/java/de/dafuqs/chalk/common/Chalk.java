package de.dafuqs.chalk.common;

import net.fabricmc.api.ModInitializer;

public class Chalk implements ModInitializer {
	@Override
	public void onInitialize() {
		ChalkRegistry.init();
	}
}