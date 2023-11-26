package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import eu.pintergabor.arrowpointers.blocks.GlowArrowMarkBlock;
import eu.pintergabor.arrowpointers.items.ChalkItem;
import eu.pintergabor.arrowpointers.items.GlowChalkItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ArrowVariant {
	public Item chalkItem;
	public Item glowChalkItem;
	public Block arrowMarkBlock;
	public Block glowArrowMarkBlock;

	public ArrowVariant() {
		this.chalkItem = new ChalkItem(new Item.Settings()
				.maxCount(1)
				.maxDamage(64));
		this.glowChalkItem = new GlowChalkItem(new Item.Settings()
				.maxCount(1)
				.maxDamage(64));
		this.arrowMarkBlock = new ArrowMarkBlock(AbstractBlock.Settings
				.create()
				.replaceable()
				.noCollision()
				.nonOpaque()
				.sounds(BlockSoundGroup.GRAVEL)
				.pistonBehavior(PistonBehavior.DESTROY));
		this.glowArrowMarkBlock = new GlowArrowMarkBlock(AbstractBlock.Settings
				.create()
				.replaceable()
				.noCollision()
				.nonOpaque()
				.sounds(BlockSoundGroup.GRAVEL)
				.luminance((state) -> 1)
				.postProcess(ArrowVariant::always)
				.emissiveLighting(ArrowVariant::always)
				.pistonBehavior(PistonBehavior.DESTROY));
		this.ItemGroups();
	}

	public void ItemGroups() {
		// Chalk ItemGroups: Functional Blocks, Tools and Utilities
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(this.chalkItem));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(this.chalkItem));
		// Glow Chalk ItemGroups: Functional Blocks, Tools and Utilities
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(this.glowChalkItem));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(this.glowChalkItem));
	}

	private static void registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(Constants.MODID, name), block);
	}

	private static void registerItem(String name, Item item) {
		Registry.register(Registries.ITEM, new Identifier(Constants.MODID, name), item);
	}

	private static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
		return true;
	}

	public void register() {
		registerItem("chalk", chalkItem);
		registerItem("glow_chalk", glowChalkItem);
		registerBlock("arrow_mark", arrowMarkBlock);
		registerBlock("glow_arrow_mark", glowArrowMarkBlock);
	}

	@Environment(EnvType.CLIENT)
	public void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(this.arrowMarkBlock, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(this.glowArrowMarkBlock, RenderLayer.getCutout());
		ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> 0xFFFFFF, arrowMarkBlock);
		ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> 0xFFFFFF, glowArrowMarkBlock);
	}
}
