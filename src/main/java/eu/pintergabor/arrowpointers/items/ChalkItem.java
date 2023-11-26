package eu.pintergabor.arrowpointers.items;

import eu.pintergabor.arrowpointers.ArrowRegistry;
import eu.pintergabor.arrowpointers.blocks.ArrowMarkBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ChalkItem extends Item {

	public ChalkItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		final World world = context.getWorld();
		final BlockPos pos = context.getBlockPos();
		final BlockState clickedBlockState = world.getBlockState(pos);
		final PlayerEntity player = context.getPlayer();
		final ItemStack stack = context.getStack();
		Direction clickedFace = context.getSide();
		BlockPos markPosition = pos.offset(clickedFace);
		if (world.isAir(markPosition) || world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) {
			if (clickedBlockState.getBlock() instanceof ArrowMarkBlock) { // replace mark
				clickedFace = clickedBlockState.get(ArrowMarkBlock.FACING);
				markPosition = pos;
				world.removeBlock(pos, false);
			} else if (player != null &&
					!Block.isFaceFullSquare(clickedBlockState.getCollisionShape(world, pos, ShapeContext.of(player)), clickedFace)) {
				return ActionResult.PASS;
			} else if ((!world.isAir(markPosition) && world.getBlockState(markPosition).getBlock() instanceof ArrowMarkBlock) || stack.getItem() != this) {
				return ActionResult.PASS;
			}

			if (world.isClient) {
				Random r = new Random();
				world.addParticle(ParticleTypes.CLOUD,
						markPosition.getX() + (0.5 * (r.nextFloat() + 0.4)), markPosition.getY() + 0.65, markPosition.getZ() + (0.5 * (r.nextFloat() + 0.4)),
						0.0D, 0.005D, 0.0D);
				return ActionResult.SUCCESS;
			}

			final int orientation = getClickedRegion(context.getHitPos(), clickedFace);

			BlockState blockState = getChalkMarkBlock().getDefaultState()
					.with(ArrowMarkBlock.FACING, clickedFace)
					.with(ArrowMarkBlock.ORIENTATION, orientation);

			if (world.setBlockState(markPosition, blockState, 1 | 2)) {
				if (player != null &&
						!player.isCreative()) {
					if (stack.getDamage() >= stack.getMaxDamage()) {
						world.playSound(null, markPosition,
								SoundEvents.BLOCK_GRAVEL_BREAK, SoundCategory.BLOCKS, 0.5f, 1f);
					}
					stack.damage(1, player, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
				}

				world.playSound(null, markPosition,
						SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 0.6f, world.random.nextFloat() * 0.2f + 0.8f);
				return ActionResult.CONSUME;
			}
		}
		return ActionResult.FAIL;
	}

	public Block getChalkMarkBlock() {
		return ArrowRegistry.arrowVariant.arrowMarkBlock;
	}

	/**
	 * Calculate the fractional part of v
	 * @return Fractional part of v (always non-negative and less than 1)
	 */
	private static double frac(double v) {
		return v - Math.floor(v);
	}

	/**
	 * Calculates which region of the block was clicked
	 * @param rx [0, 1, 2] = [left, center, right]
	 * @param ry [0, 1, 2] = [top, center, bottom]
	 * @return region number (top-left = 0 … bottom-right = 8)
	 */
	private static int blockreg(int rx, int ry) {
		return 3 * rx + ry;
	}

	private int getClickedRegion(@NotNull Vec3d clickLocation, Direction face) {
		final double dx = frac(clickLocation.x);
		final double dy = frac(clickLocation.y);
		final double dz = frac(clickLocation.z);

		return switch (face) {
			default ->
					blockreg(Math.min(2, (int) (3 * dz)), Math.min(2, (int) (3 * dx)));
			case NORTH, SOUTH ->
					blockreg(Math.min(2, (int) (3 * (1 - dy))), Math.min(2, (int) (3 * dx)));
			case WEST, EAST ->
					blockreg(Math.min(2, (int) (3 * (1 - dy))), Math.min(2, (int) (3 * dz)));
		};
	}
}