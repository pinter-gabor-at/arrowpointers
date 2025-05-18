package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.main.ClientArrowRegistry;

import net.fabricmc.api.ClientModInitializer;


public final class ModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientArrowRegistry.registerClient();
	}
}
