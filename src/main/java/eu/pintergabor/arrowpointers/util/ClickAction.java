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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;


public class ClickAction {

	/**
	 * @return true if the {@code block} at {@code pos} is an arrow mark block.
	 */
	private static boolean isArrowMarkBlock(Level level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof ArrowMarkBlock;
	}

	/**
	 * @return true if the arrow mark block can be placed.
	 */
	@SuppressWarnings("RedundantIfStatement")
	private static boolean canPlace(Item item, UseOnContext context) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final BlockState clickedBlockState = level.getBlockState(pos);
		final Player player = context.getPlayer();
		final ItemStack stack = context.getItemInHand();
		final Direction clickedFace = context.getClickedFace();
		final BlockPos targetPos = pos.relative(clickedFace);
		if (player == null) {
			// If there is no player.
			return false;
		}
		if (!Block.isFaceFull(clickedBlockState.getCollisionShape(
			level, pos, CollisionContext.of(player)), clickedFace)) {
			// If the clicked block is unsuitable for placing an arrow mark on it.
			return false;
		}
		if ((!level.isEmptyBlock(targetPos) && isArrowMarkBlock(level, targetPos)) ||
			stack.getItem() != item) {
			// If the arrow mark block and the item in hand are different types.
			return false;
		}
		// Success: the arrow mark block can be placed.
		return true;
	}

	@NotNull
	private static InteractionResult placeBlock(UseOnContext context, Block block) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Player player = context.getPlayer();
		final ItemStack stack = context.getItemInHand();
		final Direction clickedFace = context.getClickedFace();
		final BlockPos targetPos = pos.relative(clickedFace);
		// Normally 1 item is needed, but if orientation is center, then 2.
		final int orientation = getClickedRegion(context.getClickLocation(), clickedFace);
		int consume = 1;
		if (orientation == MIDDLECENTER) {
			if (stack.getCount() < 2) {
				return InteractionResult.PASS;
			}
			consume = 2;
		}
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		// The new block.
		BlockState blockState = block.defaultBlockState()
			.setValue(ArrowMarkBlock.FACING, clickedFace)
			.setValue(ArrowMarkBlock.ORIENTATION, orientation);
		// Place it.
		if (level.setBlockAndUpdate(targetPos, blockState)) {
			if (player != null &&
				!player.isCreative()) {
				stack.shrink(consume);
			}
			level.playSound(null, targetPos,
				SoundEvents.LADDER_BREAK, SoundSource.BLOCKS,
				0.5F, RandomSource.create().nextFloat() * 0.2F + 0.8F);
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Called when the player right-clicks on a block with an ArrowItem or a
	 * SpectralArrowItem in hand.
	 *
	 * @param item    an ArrowItem or a SpectralArrowItem
	 * @param context the context of the useOnBlock call
	 * @param block   an ArrowMarkBlock or a GlowArrowMarkBlock
	 * @return the usual InteractionResult values
	 */
	public static InteractionResult useOn(Item item, UseOnContext context, Block block) {
		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Direction clickedFace = context.getClickedFace();
		final BlockPos targetPos = pos.relative(clickedFace);
		if (!level.isEmptyBlock(targetPos) &&
			!isArrowMarkBlock(level, targetPos)) {
			// If the target position is not empty, and it does not contain an arrow mark block.
			return InteractionResult.FAIL;
		}
		if (!canPlace(item, context)) {
			// If the arrow mark cannot be placed for any reason.
			return InteractionResult.PASS;
		}
		return placeBlock(context, block);
	}
}
