package eu.pintergabor.arrowpointers.util;

import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

import static eu.pintergabor.arrowpointers.util.BlockRegion.MIDDLECENTER;
import static eu.pintergabor.arrowpointers.util.BlockRegion.getClickedRegion;


public class ClickAction {
	private ClickAction() {
		// Static class
	}

	/**
	 * Called when the player right-clicks on a block with an ArrowItem or a
	 * SpectralArrowItem in hand.
	 * @param item an ArrowItem or a SpectralArrowItem
	 * @param context the context of the useOnBlock call
	 * @param block an ArrowMarkBlock or a GlowArrowMarkBlock
	 * @return the usual ActionResult values
	 */
	public static ActionResult useOnBlock(Item item, ItemUsageContext context, Block block) {
		final World world = context.getWorld();
		final BlockPos pos = context.getBlockPos();
		final BlockState clickedBlockState = world.getBlockState(pos);
		final PlayerEntity player = context.getPlayer();
		final ItemStack stack = context.getStack();
		final Direction clickedFace = context.getSide();
		final BlockPos markPosition = pos.offset(clickedFace);
		if (world.isAir(markPosition) || world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) {
			if (player != null &&
					!Block.isFaceFullSquare(clickedBlockState.getCollisionShape(world, pos, ShapeContext.of(player)), clickedFace)) {
				return ActionResult.PASS;
			} else if ((!world.isAir(markPosition) && world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) || stack.getItem() != item) {
				return ActionResult.PASS;
			}

			// Normally we need 1 item, but if orientation is center, then 2
			final int orientation = getClickedRegion(context.getHitPos(), clickedFace);
			int consume = 1;
			if (orientation == MIDDLECENTER) {
				if (stack.getCount() < 2) {
					return ActionResult.PASS;
				}
				consume = 2;
			}

			if (world.isClient) {
				return ActionResult.SUCCESS;
			}

			// The new block
			BlockState blockState = block.getDefaultState()
					.with(ArrowMarkBlock.FACING, clickedFace)
					.with(ArrowMarkBlock.ORIENTATION, orientation);

			// Place it
			if (world.setBlockState(markPosition, blockState, 1 | 2)) {
				if (player != null &&
						!player.isCreative()) {
					stack.decrement(consume);
				}
				world.playSound(null, markPosition,
						SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.BLOCKS,
						0.5f, new Random().nextFloat() * 0.2f + 0.8f);
				return ActionResult.CONSUME;
			}
		}
		return ActionResult.FAIL;
	}

}
