package eu.pintergabor.arrowpointers.main;

import static eu.pintergabor.arrowpointers.Global.arrowMarkBlockLumi;
import static eu.pintergabor.arrowpointers.Global.glowArrowMarkBlockLumi;

import eu.pintergabor.arrowpointers.Global;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;


public final class ArrowRegistry {
	public static Block arrowMarkBlock;
	public static Block glowArrowMarkBlock;

	public static void init() {
		arrowMarkBlock = Blocks.register(
			ResourceKey.create(Registries.BLOCK, Global.modId("arrow_mark")),
			ArrowMarkBlock::new,
			BlockBehaviour.Properties
				.of()
				.replaceable()
				.noCollision()
				.noOcclusion()
				.sound(SoundType.LADDER)
				.lightLevel((_) -> arrowMarkBlockLumi)
				.emissiveRendering(_ -> true)
				.pushReaction(PushReaction.DESTROY)
		);
		glowArrowMarkBlock = Blocks.register(
			ResourceKey.create(Registries.BLOCK, Global.modId("glow_arrow_mark")),
			ArrowMarkBlock::new,
			BlockBehaviour.Properties
				.of()
				.replaceable()
				.noCollision()
				.noOcclusion()
				.sound(SoundType.LADDER)
				.lightLevel((_) -> glowArrowMarkBlockLumi)
				.emissiveRendering(_ -> true)
				.pushReaction(PushReaction.DESTROY)
		);
	}
}
