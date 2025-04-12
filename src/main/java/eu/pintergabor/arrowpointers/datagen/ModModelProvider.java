package eu.pintergabor.arrowpointers.datagen;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;


public class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	/**
	 * Generate block models and block states.
	 */
	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		ModModelGenerator generator = new ModModelGenerator(blockStateModelGenerator);
		generator.registerFlat9Direction(ArrowRegistry.arrowMarkBlock);
		generator.registerFlat9Direction(ArrowRegistry.glowArrowMarkBlock);
	}

	/**
	 * Only vanilla arrow items are used in this mod.
	 */
	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
	}
}
