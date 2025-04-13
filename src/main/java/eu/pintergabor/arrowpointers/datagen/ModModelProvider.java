package eu.pintergabor.arrowpointers.datagen;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;


public class ModModelProvider extends ModelProvider {

	public ModModelProvider(PackOutput output) {
		super(output, Global.MODID);
	}

	/**
	 * Generate block models and block states.
	 */
	private static void generateBlockStateModel(@NotNull BlockModelGenerators blockModels) {
		ModModelGenerator generator = new ModModelGenerator(blockModels);
		generator.registerFlat9Direction(ArrowRegistry.arrowMarkBlock.get());
		generator.registerFlat9Direction(ArrowRegistry.glowArrowMarkBlock.get());
	}

	/**
	 * Generate blockstates, block and item models.
	 */
	@Override
	protected void registerModels(
		@NotNull BlockModelGenerators blockModels,
		@NotNull ItemModelGenerators itemModels) {
		// No items, because only vanilla items are used in this mod.
		// Block models and block states.
		generateBlockStateModel(blockModels);
	}
}
