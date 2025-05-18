package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.Global.arrowMarkBlockLumi;
import static eu.pintergabor.arrowpointers.Global.glowArrowMarkBlockLumi;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;


public final class ArrowRegistry {
	public static final DeferredRegister.Blocks BLOCKS =
		DeferredRegister.createBlocks(Global.MODID);
	public static DeferredBlock<Block> arrowMarkBlock;
	public static DeferredBlock<Block> glowArrowMarkBlock;

	private static boolean always(
		BlockState state, BlockGetter blockView, BlockPos pos
	) {
		return true;
	}

	public static void init(IEventBus modEventBus) {
		arrowMarkBlock = BLOCKS.registerBlock(
			"arrow_mark",
			ArrowMarkBlock::new,
			Block.Properties
				.of()
				.replaceable()
				.noCollission()
				.noOcclusion()
				.sound(SoundType.LADDER)
				.lightLevel((state) -> arrowMarkBlockLumi)
				.hasPostProcess(ArrowRegistry::always)
				.emissiveRendering(ArrowRegistry::always)
				.pushReaction(PushReaction.DESTROY)
		);
		glowArrowMarkBlock = BLOCKS.registerBlock(
			"glow_arrow_mark",
			ArrowMarkBlock::new,
			Block.Properties
				.of()
				.replaceable()
				.noCollission()
				.noOcclusion()
				.sound(SoundType.LADDER)
				.lightLevel((state) -> glowArrowMarkBlockLumi)
				.hasPostProcess(ArrowRegistry::always)
				.emissiveRendering(ArrowRegistry::always)
				.pushReaction(PushReaction.DESTROY)
		);
		BLOCKS.register(modEventBus);
	}
}
