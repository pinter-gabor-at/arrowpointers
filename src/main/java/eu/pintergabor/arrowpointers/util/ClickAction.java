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

import static eu.pintergabor.arrowpointers.util.ClickUtil.getClickedRegion;


public class ClickAction {

	private ClickAction() {
		// Static class
	}

	public static ActionResult useOnBlock(Item item, ItemUsageContext context, Block block) {
		final World world = context.getWorld();
		final BlockPos pos = context.getBlockPos();
		final BlockState clickedBlockState = world.getBlockState(pos);
		final PlayerEntity player = context.getPlayer();
		final ItemStack stack = context.getStack();
		Direction clickedFace = context.getSide();
		BlockPos markPosition = pos.offset(clickedFace);
		if (world.isAir(markPosition) || world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) {
			final Block clickedBlock = clickedBlockState.getBlock();
			if (clickedBlock instanceof ArrowMarkBlock) {
				// Remove previous mark, before placing the new one
				clickedFace = clickedBlockState.get(ArrowMarkBlock.FACING);
				markPosition = pos;
				world.removeBlock(pos, false);
				clickedBlock.afterBreak(world, player, pos, clickedBlockState, null, stack);
			} else if (player != null &&
					!Block.isFaceFullSquare(clickedBlockState.getCollisionShape(world, pos, ShapeContext.of(player)), clickedFace)) {
				return ActionResult.PASS;
			} else if ((!world.isAir(markPosition) && world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) || stack.getItem() != item) {
				return ActionResult.PASS;
			}

			if (world.isClient) {
				return ActionResult.SUCCESS;
			}

			final int orientation = getClickedRegion(context.getHitPos(), clickedFace);

			BlockState blockState = block.getDefaultState()
					.with(ArrowMarkBlock.FACING, clickedFace)
					.with(ArrowMarkBlock.ORIENTATION, orientation);

			if (world.setBlockState(markPosition, blockState, 1 | 2)) {
				if (player != null &&
						!player.isCreative()) {
					stack.decrement(1);
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
