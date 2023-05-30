package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.FACING;
import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HALF;

public class TerracottaWarriorStatue extends Block {
    protected static final VoxelShape SOUTH_UPPER_HALF_AABB = Shapes.or(
            Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.box(0.0D, 0.0D, 5.0D, 16.0D, 8.0D, 11.0D)
    );
    protected static final VoxelShape SOUTH_LOWER_HALF_AABB = Shapes.or(
            Block.box(4.0D, 0.0D, 5.0D, 12.0D, 16.0D, 11.0D)
    );

    protected static final VoxelShape NORTH_UPPER_HALF_AABB = Shapes.or(
            Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.box(0.0D, 0.0D, 5.0D, 16.0D, 8.0D, 11.0D)
    );
    protected static final VoxelShape NORTH_LOWER_HALF_AABB = Shapes.or(
            Block.box(4.0D, 0.0D, 5.0D, 12.0D, 16.0D, 11.0D)
    );

    protected static final VoxelShape WEST_UPPER_HALF_AABB = Shapes.or(
            Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.box(5.0D, 0.0D, 0.0D, 11.0D, 8.0D, 16.0D)
    );
    protected static final VoxelShape WEST_LOWER_HALF_AABB = Shapes.or(
            Block.box(5.0D, 0.0D, 4.0D, 11.0D, 16.0D, 12.0D)
    );

    protected static final VoxelShape EAST_UPPER_HALF_AABB = Shapes.or(
            Block.box(3.0D, 8.0D, 3.0D, 13.0D, 16.0D, 13.0D),
            Block.box(5.0D, 0.0D, 0.0D, 11.0D, 8.0D, 16.0D)
    );
    protected static final VoxelShape EAST_LOWER_HALF_AABB = Shapes.or(
            Block.box(5.0D, 0.0D, 4.0D, 11.0D, 16.0D, 12.0D)
    );

    public TerracottaWarriorStatue() {
        super(BlockBehaviour.Properties.of(Material.DECORATION).strength(2.0F, 6.0F).sound(SoundType.STONE));
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
    }

    /**
     * Gets the shape of the block depending on the state associated with this instance.
     * Extracts the shape from the designated AABBs.
     * This determines in what direction the block's collision AABB will be rendered, since it doesn't fill an entire block
     *
     * @param blockState State of this block
     * @param blockReader Reader for information from this block
     * @param position Position of this block
     * @param collisionContext The context under which this block was selected
     * @return
     */
    public VoxelShape getShape(BlockState blockState, BlockGetter blockReader, BlockPos position, CollisionContext collisionContext) {
        Direction direction = blockState.getValue(FACING);
        switch(direction) {
            case EAST:
            default:
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    return EAST_UPPER_HALF_AABB;
                } else {
                    return EAST_LOWER_HALF_AABB;
                }
            case SOUTH:
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    return SOUTH_UPPER_HALF_AABB;
                } else {
                    return SOUTH_LOWER_HALF_AABB;
                }
            case WEST:
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    return WEST_UPPER_HALF_AABB;
                } else {
                    return WEST_LOWER_HALF_AABB;
                }
            case NORTH:
                if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    return NORTH_UPPER_HALF_AABB;
                } else {
                    return NORTH_LOWER_HALF_AABB;
                }
        }
    }

    /**
     * Updates the shape of this block.
     * If the block doesn't survive it is replaced with an air block, otherwise its shape is updated.
     * Ensures that if either block is destroyed, then the other half cannot survive in the world either since they make up a single object.
     *
     * @param blockState State of the block having its shape updated
     * @param direction Direction we are going to update the shape in
     * @param otherBlockState State of the other block in the check
     * @param levelAccessor Accessor for the level that the block is being updated in
     * @param position Position of the block having its shape updated
     * @param otherPosition Position of the other block in the check
     * @return Updated block state of the current block
     */
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState otherBlockState, LevelAccessor levelAccessor, BlockPos position, BlockPos otherPosition) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || otherBlockState.is(this) && otherBlockState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, otherBlockState, levelAccessor, position, otherPosition);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    /**
     * Sets the correct state for the block upon its placement.
     * Designates the lower half of the block with a block state so that we can differentiate it from the top in other functions.
     *
     * @param itemUseContext Usage context of the block as it is placed into the level
     * @return Updated state of the block that was placed.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext itemUseContext) {
        BlockPos position = itemUseContext.getClickedPos();
        Level level = itemUseContext.getLevel();
        if (position.getY() < level.getMaxBuildHeight() - 1 && itemUseContext.getLevel().getBlockState(position.above()).canBeReplaced(itemUseContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, itemUseContext.getHorizontalDirection());
        } else {
            return null;
        }
    }

    /**
     * Places the block into the world if the position above is also free so that the top block can be generated also.
     *
     * @param level Level the block is being placed in
     * @param position Position the block is being placed on
     * @param blockState State of the block to be placed
     * @param entity The entity placing the block
     * @param itemStack The stack of items that the block may be originating from
     */
    public void setPlacedBy(Level world, BlockPos position, BlockState blockState, LivingEntity entity, ItemStack itemStack) {
        world.setBlock(position.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    /**
     * Determines whether the block can survive in the current position.
     * If the current block being checked is the lower half, ensures that it is being placed on valid, sturdy ground.
     * If the current block being checked is the upper half, ensures that the block underneath it is the lower half of the object.
     *
     * @param blockState State of the block being checked
     * @param worldReader Reader for the level the block is in
     * @param position Position of the block being checked
     * @return Whether the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, LevelReader worldReader, BlockPos position) {
        BlockPos blockpos = position.below();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldReader, blockpos, Direction.UP) : blockstate.is(this);
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     * This allows us to create a block that is made up of two halves and takes up two positions in the world. The lower and top halves.
     *
     * @param builder The builder for the state container
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF, FACING);
    }

    /**
     * Rotates the block based on the direction the player is facing when it was placed.
     *
     * @param state State of the block being placed
     * @param rotation Rotation the block is being placed at
     * @return Sets the rotation for the facing value into block state for this block
     */
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
}
