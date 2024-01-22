package eu.pintergabor.arrowpointers.main;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import static eu.pintergabor.arrowpointers.Global.arrowMarkBlockLumi;
import static eu.pintergabor.arrowpointers.Global.glowArrowMarkBlockLumi;

public class ArrowRegistry {
	public static Block arrowMarkBlock;
	public static Block glowArrowMarkBlock;

	public static void init() {
		arrowMarkBlock = new ArrowMarkBlock(AbstractBlock.Settings
			.create()
			.replaceable()
			.noCollision()
			.nonOpaque()
			.sounds(BlockSoundGroup.LADDER)
			.luminance((state) -> arrowMarkBlockLumi)
			.postProcess(ArrowRegistry::always)
			.emissiveLighting(ArrowRegistry::always)
			.pistonBehavior(PistonBehavior.DESTROY)
		);
		glowArrowMarkBlock = new ArrowMarkBlock(AbstractBlock.Settings
				.create()
				.replaceable()
				.noCollision()
				.nonOpaque()
				.sounds(BlockSoundGroup.LADDER)
				.luminance((state) -> glowArrowMarkBlockLumi)
				.postProcess(ArrowRegistry::always)
				.emissiveLighting(ArrowRegistry::always)
				.pistonBehavior(PistonBehavior.DESTROY)
		);
		register();
	}

	private static void registerBlock(String name, Block block) {
		Registry.register(Registries.BLOCK, new Identifier(Global.MODID, name), block);
	}

	private static boolean always(BlockState blockState, BlockView blockView, BlockPos blockPos) {
		return true;
	}

	public static void register() {
		registerBlock("arrow_mark", arrowMarkBlock);
		registerBlock("glow_arrow_mark", glowArrowMarkBlock);
	}
}
