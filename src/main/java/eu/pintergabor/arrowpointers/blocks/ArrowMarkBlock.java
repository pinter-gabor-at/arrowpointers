package eu.pintergabor.arrowpointers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static eu.pintergabor.arrowpointers.Constants.*;
import static eu.pintergabor.arrowpointers.util.BlockRegion.MIDDLECENTER;


public class ArrowMarkBlock extends Block {

	public static final DirectionProperty FACING = Properties.FACING;
	public static final IntProperty ORIENTATION = IntProperty.of("orientation", 0, 8);

	private static final VoxelShape DOWN_AABB = Block.createCuboidShape(
			margin, 16D - thick, margin, 16D - margin, 16D, 16D - margin);
	private static final VoxelShape UP_AABB = Block.createCuboidShape(
			margin, 0D, margin, 16D - margin, thick, 16D - margin);
	private static final VoxelShape SOUTH_AABB = Block.createCuboidShape(
			margin, margin, 0D, 16D - margin, 16D - margin, thick);
	private static final VoxelShape EAST_AABB = Block.createCuboidShape(
			0D, margin, margin, thick, 16D - margin, 16D - margin);
	private static final VoxelShape WEST_AABB = Block.createCuboidShape(
			16D - thick, margin, margin, 16D, 16D - margin, 16D - margin);
	private static final VoxelShape NORTH_AABB = Block.createCuboidShape(
			margin, margin, 16D - thick, 16D - margin, 16D - margin, 16D);

	public ArrowMarkBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(ORIENTATION, MIDDLECENTER));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return true;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, ORIENTATION);
		super.appendProperties(builder);
	}

	@Override
	public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state,
						   @Nullable BlockEntity blockEntity, ItemStack tool) {
		if (!world.isClient) {
			// Breaking ArrowMarkBlock drops an ArrowItem,
			// breaking GlowArrowMarkBlock drops a SpectralArrowItem
			// normally 1, but if orientation is center, then 2.
			final ItemStack stack = new ItemStack(
					state.getLuminance() <= arrowMarkBlockLumi ? Items.ARROW : Items.SPECTRAL_ARROW,
					state.get(ORIENTATION) == MIDDLECENTER ? 2 : 1);
			dropStack(world, pos, stack);
		}
	}

	@Override
	protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
		if (!world.isClient) {
			world.playSound(null, pos,
					SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.BLOCKS,
					0.5f, new Random().nextFloat() * 0.2f + 0.8f);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch (state.get(FACING)) {
			case UP -> UP_AABB;
			case NORTH -> NORTH_AABB;
			case WEST -> WEST_AABB;
			case EAST -> EAST_AABB;
			case SOUTH -> SOUTH_AABB;
			default -> DOWN_AABB;
		};
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction facing = state.get(FACING);
		return Block.isFaceFullSquare(world.getBlockState(pos.offset(facing.getOpposite()))
				.getCollisionShape(world, pos.offset(facing)), facing);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
												WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		boolean support = neighborPos.equals(pos.offset(state.get(FACING).getOpposite()));
		if (support) {
			if (!this.canPlaceAt(state, world, pos)) {
				return Blocks.AIR.getDefaultState();
			}
		}
		return state;
	}
}