package eu.pintergabor.arrowpointers.datagen;

import java.util.Set;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import eu.pintergabor.arrowpointers.main.ArrowRegistry;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;


public class ModBlockLootTableGenerator extends BlockLootSubProvider {

	public ModBlockLootTableGenerator(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
	}

	/**
	 * NeoForge requires it.
	 */
	@Override
	@NotNull
	protected Iterable<Block> getKnownBlocks() {
		return ArrowRegistry.BLOCKS.getEntries()
			.stream()
			.map(e -> (Block) e.get())
			.toList();
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
		dropOther(ArrowRegistry.arrowMarkBlock.get(), Items.ARROW);
		dropOther(ArrowRegistry.glowArrowMarkBlock.get(), Items.SPECTRAL_ARROW);
	}
}
