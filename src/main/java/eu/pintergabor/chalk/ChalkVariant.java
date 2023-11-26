package eu.pintergabor.chalk;

import eu.pintergabor.chalk.blocks.ChalkMarkBlock;
import eu.pintergabor.chalk.blocks.GlowChalkMarkBlock;
import eu.pintergabor.chalk.items.ChalkItem;
import eu.pintergabor.chalk.items.GlowChalkItem;
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
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ChalkVariant {
	public Item chalkItem;
	public Block chalkBlock;
	public Item glowChalkItem;
	public Block glowChalkBlock;
	String colorString;
	int color;

	public ChalkVariant(DyeColor dyeColor, int color, String colorString) {
		this.color = color;
		this.colorString = colorString;
		this.chalkItem = new ChalkItem(new Item.Settings().maxCount(1).maxDamage(64), dyeColor);
		this.chalkBlock = new ChalkMarkBlock(AbstractBlock.Settings.create().replaceable().noCollision().nonOpaque().sounds(BlockSoundGroup.GRAVEL).pistonBehavior(PistonBehavior.DESTROY), dyeColor);
		this.glowChalkItem = new GlowChalkItem(new Item.Settings().maxCount(1).maxDamage(64), dyeColor);
		this.glowChalkBlock = new GlowChalkMarkBlock(AbstractBlock.Settings.create().replaceable().noCollision().nonOpaque().sounds(BlockSoundGroup.GRAVEL)
				.luminance((state) -> 1)
				.postProcess(ChalkVariant::always)
				.emissiveLighting(ChalkVariant::always)
				.pistonBehavior(PistonBehavior.DESTROY), dyeColor);
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
		registerBlock(colorString + "chalk_mark", chalkBlock);
		registerItem(colorString + "chalk", chalkItem);
		registerBlock(colorString + "glow_chalk_mark", glowChalkBlock);
		registerItem(colorString + "glow_chalk", glowChalkItem);
	}

	@Environment(EnvType.CLIENT)
	public void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(this.chalkBlock, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(this.glowChalkBlock, RenderLayer.getCutout());
		ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> color, chalkBlock);
		ColorProviderRegistry.BLOCK.register((state, world, pos, index) -> color, glowChalkBlock);
	}
}
