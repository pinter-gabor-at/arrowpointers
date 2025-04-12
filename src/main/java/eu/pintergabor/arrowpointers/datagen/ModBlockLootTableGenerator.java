package eu.pintergabor.arrowpointers.datagen;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import net.minecraft.world.item.Items;


public class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {

	public ModBlockLootTableGenerator(
		FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	/**
	 * Breaking {@link ArrowMarkBlock} drops an {@link ArrowItem}, Breaking Glow{@link ArrowMarkBlock} drops a
	 * {@link SpectralArrowItem}
	 * <p>
	 * normally 1, but if orientation is center, then 2.
	 */
	@Override
	public void generate() {
		dropOther(ArrowRegistry.arrowMarkBlock, Items.ARROW);
		dropOther(ArrowRegistry.glowArrowMarkBlock, Items.SPECTRAL_ARROW);
	}
}
