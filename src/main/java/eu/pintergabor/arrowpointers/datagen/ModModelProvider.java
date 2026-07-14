package eu.pintergabor.arrowpointers.datagen;

import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import org.jspecify.annotations.NonNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.world.item.Items;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;


public final class ModModelProvider extends FabricModelProvider {

	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	/**
	 * Generate block models and block states.
	 */
	@Override
	public void generateBlockStateModels(@NonNull BlockModelGenerators generators) {
		final ModModelGenerator generator = new ModModelGenerator(generators);
		generator.registerFlat9Direction(ArrowRegistry.arrowMarkBlock);
		generator.registerFlat9Direction(ArrowRegistry.glowArrowMarkBlock);
	}

	/**
	 * Only vanilla {@link Items#ARROW} items are used in this mod.
	 */
	@Override
	public void generateItemModels(@NonNull ItemModelGenerators generators) {
	}
}
