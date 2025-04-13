package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.Global.arrowMarkBlockLumi;
import static eu.pintergabor.arrowpointers.Global.glowArrowMarkBlockLumi;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;


public class ArrowRegistry {
	public static Block arrowMarkBlock;
	public static Block glowArrowMarkBlock;

	private static boolean always(
		BlockState state, BlockGetter blockView, BlockPos pos) {
		return true;
	}

	public static void init() {
		arrowMarkBlock = Blocks.register(
			ResourceKey.create(Registries.BLOCK, Global.modId("arrow_mark")),
			ArrowMarkBlock::new,
			BlockBehaviour.Properties
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
		glowArrowMarkBlock = Blocks.register(
			ResourceKey.create(Registries.BLOCK, Global.modId("glow_arrow_mark")),
			ArrowMarkBlock::new,
			BlockBehaviour.Properties
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
	}
}
