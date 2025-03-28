package eu.pintergabor.arrowpointers.datagen;

import java.util.concurrent.CompletableFuture;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import eu.pintergabor.arrowpointers.main.ArrowRegistry;

import net.minecraft.item.ArrowItem;
import net.minecraft.item.Items;
import net.minecraft.item.SpectralArrowItem;
import net.minecraft.registry.RegistryWrapper;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;


public class ModBlockLootTableGenerator extends FabricBlockLootTableProvider {
	public ModBlockLootTableGenerator(
		FabricDataOutput dataOutput,
		CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
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
		addDrop(ArrowRegistry.arrowMarkBlock, Items.ARROW);
		addDrop(ArrowRegistry.glowArrowMarkBlock, Items.SPECTRAL_ARROW);
	}
}
