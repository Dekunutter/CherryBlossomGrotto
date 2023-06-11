package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HANGING;
import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.WATERLOGGED;

// NOTE: Essentially a copy of the lantern block from vanilla with the voxel shape changed
public class PaperLantern extends Block {
    private static final VoxelShape SITTING_AABB = Shapes.or(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D), Block.box(7.0D, 7.0D, 7.0D, 9.0D, 9.0D, 9.0D));
    private static final VoxelShape HANGING_AABB = Shapes.or(Block.box(4.0D, 1.0D, 4.0D, 12.0D, 7.0D, 12.0D), Block.box(7.0D, 7.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    public PaperLantern() {
        super(BlockBehaviour.Properties.of().strength(0.5F).mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).lightLevel((level) -> {
            return 12;
        }).noOcclusion());
        registerDefaultState(defaultBlockState().setValue(HANGING, false).setValue(WATERLOGGED, false));
    }

    /**
     * Sets the correct state for the block upon its placement.
     * If the block was placed on the upwards Y axis, it will be set with the hanging state set to true. Otherwise it will be grounded.
     * If the block it is placed in is water, set the waterlogged state to true.
     *
     * @param placeContext Placement context of the block being placed
     * @return Updated state of the block that was placed
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        FluidState fluidstate = placeContext.getLevel().getFluidState(placeContext.getClickedPos());

        for(Direction direction : placeContext.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = defaultBlockState().setValue(HANGING, direction == Direction.UP);
                if (blockstate.canSurvive(placeContext.getLevel(), placeContext.getClickedPos())) {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    /**
     * Gets the current shape of the block by extracting information from the designated AABB.
     * Uses the block state to determine which shape the object should render via.
     *
     * @param blockState State of the block
     * @param getter Getter for the block
     * @param position Position of the block
     * @param collisionContext Context for the block's selection
     * @return A voxelShape representing the current shape of the block
     */
    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos position, CollisionContext collisionContext) {
        return blockState.getValue(HANGING) ? HANGING_AABB : SITTING_AABB;
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     *
     * @param builder The builder for the state container
     */
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HANGING, WATERLOGGED);
    }

    /**
     * Determines whether the block can survive in the current position.
     * If the block cannot be supported in hanging state from the block above it, then it will not be placed.
     *
     * @param blockState State of the block being placed
     * @param levelReader Reader for the level that the block is being placed in
     * @param position Position of the block being placed
     * @return Whether the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos position) {
        Direction direction = getConnectedDirection(blockState).getOpposite();
        return Block.canSupportCenter(levelReader, position.relative(direction), direction.getOpposite());
    }

    /**
     * Gets the direction in which this block has been connected
     *
     * @param blockState State of the block being checked
     * @return The direction that the block has been connected to the world. Down if hanging, up if placed.
     */
    protected static Direction getConnectedDirection(BlockState blockState) {
        return blockState.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    /**
     * Sets the reaction of this block when pushed by a piston.
     * These blocks are destroyed when pushed.
     *
     * @param blockState State of the block being pushed.
     * @return
     */
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.DESTROY;
    }

    /**
     * Updates the shape of the block.
     * Destroys the block if its in a position where it can no longer survive
     *
     * @param blockState State of the block being updated
     * @param direction Direction we are going to update the shape in
     * @param otherBlockState State of the other block in the check
     * @param levelAccessor Accessor for the level that the block is being updated in
     * @param position Position of the other block in the check
     * @param otherPosition Position of the other block in the check
     * @return Updated block state of the current block
     */
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState otherBlockState, LevelAccessor levelAccessor, BlockPos position, BlockPos otherPosition) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(position, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return getConnectedDirection(blockState).getOpposite() == direction && !blockState.canSurvive(levelAccessor, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, otherBlockState, levelAccessor, position, otherPosition);
    }

    /**
     * Gets the fluid state of the block to see if it is waterlogged.
     *
     * @param blockState State of the block being checked
     * @return The fluid state of the block
     */
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    /**
     * Removes the block from pathfinding
     *
     * @param blockState State of the block
     * @param blockGetter Getter for the block
     * @param position Position of the block
     * @param pathComputationType Path finding algorithm being used
     * @return
     */
    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos position, PathComputationType pathComputationType) {
        return false;
    }
}
