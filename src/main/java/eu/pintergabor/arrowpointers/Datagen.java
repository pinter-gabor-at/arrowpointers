package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.datagen.ModBlockLootTableGenerator;
import eu.pintergabor.arrowpointers.datagen.ModModelProvider;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public final class Datagen implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockLootTableGenerator::new);
	}
}
