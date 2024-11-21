package eu.pintergabor.arrowpointers.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import java.util.List;
import java.util.Random;

import static eu.pintergabor.arrowpointers.Global.margin;
import static eu.pintergabor.arrowpointers.Global.thick;
import static eu.pintergabor.arrowpointers.util.BlockRegion.MIDDLECENTER;

public class ArrowMarkBlock extends Block {
    public static final EnumProperty<Direction> FACING = Properties.FACING;
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

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ORIENTATION);
        super.appendProperties(builder);
    }

    @Override
    protected List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
        List<ItemStack> dropStacks = super.getDroppedStacks(state, builder);
        // If orientation is center, then drop 2 arrows
        if (state.get(ORIENTATION) == MIDDLECENTER) {
            dropStacks.get(0).setCount(2);
        }
        return dropStacks;
    }

    @Override
    protected void spawnBreakParticles(World world, PlayerEntity player, BlockPos pos, BlockState state) {
        if (!world.isClient) {
            world.playSound(null, pos,
                    SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.BLOCKS,
                    0.5f, new Random().nextFloat() * 0.2f + 0.8f);
        }
    }

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

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return true;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING);
        return Block.isFaceFullSquare(world.getBlockState(pos.offset(facing.getOpposite()))
                .getCollisionShape(world, pos.offset(facing)), facing);
    }

    /**
     * Break, if neighboring full face block is broken
     */
    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, WorldView world, ScheduledTickView tickView,
            BlockPos pos, Direction direction, BlockPos neighborPos,
            BlockState neighborState, net.minecraft.util.math.random.Random random) {
        boolean support = neighborPos.equals(pos.offset(state.get(FACING).getOpposite()));
        if (support) {
            if (!this.canPlaceAt(state, world, pos)) {
                return Blocks.AIR.getDefaultState();
            }
        }
        return state;
    }
}
