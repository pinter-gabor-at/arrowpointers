package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.datagen.ModBlockLootTableGenerator;
import eu.pintergabor.arrowpointers.datagen.ModModelProvider;
import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


public final class ModDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NonNull FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockLootTableGenerator::new);
	}
}
