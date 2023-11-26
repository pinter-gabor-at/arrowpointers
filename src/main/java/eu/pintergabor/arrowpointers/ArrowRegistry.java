package eu.pintergabor.arrowpointers;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ArrowRegistry {

	private ArrowRegistry() {
		// static class
	}

	public static Block arrowMarkBlock;
	public static Block glowArrowMarkBlock;

	public static void init() {
		arrowMarkBlock = new ArrowMarkBlock(AbstractBlock.Settings
				.create()
				.replaceable()
				.noCollision()
				.nonOpaque()
				.sounds(BlockSoundGroup.LADDER)
				.pistonBehavior(PistonBehavior.DESTROY));
		glowArrowMarkBlock = new ArrowMarkBlock(AbstractBlock.Settings
				.create()
				.replaceable()
				.noCollision()
				.nonOpaque()
				.sounds(BlockSoundGroup.LADDER)
				.luminance((state) -> 1)
				.postProcess(ArrowRegistry::always)
				.emissiveLighting(ArrowRegistry::always)
				.pistonBehavior(PistonBehavior.DESTROY));
		register();
		registerClient();
	}

	private static void registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(Constants.MODID, name), block);
	}

	private static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
		return true;
	}

	public static void register() {
		registerBlock("arrow_mark", arrowMarkBlock);
		registerBlock("glow_arrow_mark", glowArrowMarkBlock);
	}

	@Environment(EnvType.CLIENT)
	public static void registerClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(arrowMarkBlock, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(glowArrowMarkBlock, RenderLayer.getCutout());
	}

}