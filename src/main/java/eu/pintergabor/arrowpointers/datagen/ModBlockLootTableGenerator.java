package eu.pintergabor.arrowpointers.datagen;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;


public final class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {

	public ModBlockLootTableGenerator(
		FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup
	) {
		super(dataOutput, registryLookup);
	}

	/**
	 * Breaking an {@link ArrowMarkBlock} drops one or two {@link Items#ARROW},
	 * Breaking a Glow{@link ArrowMarkBlock} drops one or two {@link Items#SPECTRAL_ARROW}.
	 * <p>
	 * Normally 1, but if orientation is center, then 2.
	 * See {@link ArrowMarkBlock#getDrops(BlockState, LootParams.Builder)} how it is implemented.
	 */
	@Override
	public void generate() {
		dropOther(ArrowRegistry.arrowMarkBlock, Items.ARROW);
		dropOther(ArrowRegistry.glowArrowMarkBlock, Items.SPECTRAL_ARROW);
	}
}
