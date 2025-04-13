package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.main.ClientArrowRegistry;

import net.fabricmc.api.ClientModInitializer;


public class ModClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientArrowRegistry.registerClient();
	}
}
