package eu.pintergabor.arrowpointers.datagen;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;


public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		ModModelGenerator generator = new ModModelGenerator(blockStateModelGenerator);
		generator.registerFlat9Direction(ArrowRegistry.arrowMarkBlock);
		generator.registerFlat9Direction(ArrowRegistry.glowArrowMarkBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
	}
}
