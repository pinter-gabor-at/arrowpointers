package eu.pintergabor.arrowpointers.util;

import static eu.pintergabor.arrowpointers.util.BlockRegion.MIDDLECENTER;
import static eu.pintergabor.arrowpointers.util.BlockRegion.getClickedRegion;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;


public final class ClickAction {

	private ClickAction() {
		// Static class.
	}

	/**
	 * Place an {@link ArrowMarkBlock} next to the clicked block on the server.
	 *
	 * @param context     the {@code context} of the {@code useOnBlock} call.
	 * @param block       an {@link ArrowMarkBlock}.
	 * @param orientation of the new {@link ArrowMarkBlock}.
	 * @param consume     the number of items needed (1 or 2).
	 * @return the usual InteractionResult values.
	 */
	@NotNull
	private static InteractionResult placeBlock(
		UseOnContext context, Block block, BlockRegion orientation, int consume
	) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Player player = context.getPlayer();
		final Direction clickedFace = context.getClickedFace();
		final BlockPos targetPos = pos.relative(clickedFace);
		// The new block.
		BlockState blockState = block.defaultBlockState()
			.setValue(ArrowMarkBlock.FACING, clickedFace)
			.setValue(ArrowMarkBlock.ORIENTATION, orientation);
		// Place it.
		if (level.setBlockAndUpdate(targetPos, blockState)) {
			if (player != null && !player.isCreative()) {
				// Use 1 or 2 items.
				context.getItemInHand().shrink(consume);
			}
			level.playSound(null, targetPos,
				SoundEvents.LADDER_BREAK, SoundSource.BLOCKS,
				0.5F, RandomSource.create().nextFloat() * 0.2F + 0.8F);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Place an {@link ArrowMarkBlock} next to the clicked block, if possible.
	 *
	 * @param context the {@code context} of the {@code useOnBlock} call.
	 * @param block   an {@link ArrowMarkBlock}.
	 * @return the usual InteractionResult values.
	 */
	@NotNull
	private static InteractionResult placeBlock(
		UseOnContext context, Block block
	) {
		// Normally 1 item is needed, but if orientation is center, then 2.
		final BlockRegion orientation = getClickedRegion(
			context.getClickLocation(), context.getClickedFace());
		int consume = 1;
		if (orientation == MIDDLECENTER) {
			// If it is at the center.
			if (context.getItemInHand().getCount() < 2) {
				// If 2 items are needed, but there is only 1 available.
				return InteractionResult.PASS;
			}
			consume = 2;
		}
		if (context.getLevel().isClientSide) {
			return InteractionResult.SUCCESS;
		}
		// Place block on the server.
		return placeBlock(context, block, orientation, consume);
	}

	/**
	 * @return true if the arrow mark block can be placed.
	 */
	@SuppressWarnings("RedundantIfStatement")
	private static boolean canPlace(UseOnContext context) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final BlockState clickedBlockState = level.getBlockState(pos);
		final Player player = context.getPlayer();
		final Direction clickedFace = context.getClickedFace();
		if (player == null) {
			// If there is no player.
			return false;
		}
		if (!Block.isFaceFull(clickedBlockState.getCollisionShape(
			level, pos, CollisionContext.of(player)), clickedFace)) {
			// If the clicked block is unsuitable for placing an arrow mark on it.
			return false;
		}
		// Success: the arrow mark block can be placed.
		return true;
	}

	/**
	 * Called when the player right-clicks on a block with an {@link Items#ARROW}
	 * or a {@link Items#SPECTRAL_ARROW} in hand.
	 *
	 * @param context the {@code context} of the {@code useOnBlock} call.
	 * @param block   an {@link ArrowMarkBlock} corresponding to the item in hand.
	 * @return the usual InteractionResult values.
	 */
	public static InteractionResult useOn(UseOnContext context, Block block) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Direction clickedFace = context.getClickedFace();
		final BlockPos targetPos = pos.relative(clickedFace);
		if (!level.isEmptyBlock(targetPos)) {
			// If the target position is not empty.
			return InteractionResult.FAIL;
		}
		if (!canPlace(context)) {
			// If the arrow mark cannot be placed for any reason.
			return InteractionResult.PASS;
		}
		return placeBlock(context, block);
	}
}
