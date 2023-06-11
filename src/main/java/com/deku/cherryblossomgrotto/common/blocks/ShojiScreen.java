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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


public class ShojiScreen extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    // NOTE: Actual model is only 3px wide but extended to 10px to resolve pathfinding issues with thin double-block objects and entities
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);


    public ShojiScreen() {
        super(BlockBehaviour.Properties.of().noOcclusion().strength(0.1f).mapColor(MapColor.NONE).ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.GRASS));
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
    }

    /**
     * Gets the shape of the block depending on the state associated with this instance.
     * Extracts the shape from the designated AABBs.
     * This determines in what direction the block will be rendered, since it doesnt fill an entire block
     *
     * @param state State of this block
     * @param blockGetter Reader for information from this block
     * @param position Position of this block
     * @param selectionContext The context under which this block was selected
     * @return
     */
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext selectionContext) {
        Direction direction = state.getValue(FACING);
        switch(direction) {
            case EAST:
            default:
                return EAST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case NORTH:
                return NORTH_AABB;
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
     * @param blockPlaceContext Usage context of the block as it is placed into the level
     * @return Updated state of the block that was placed.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos position = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        if (position.getY() < level.getMaxBuildHeight() - 1 && blockPlaceContext.getLevel().getBlockState(position.above()).canBeReplaced(blockPlaceContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, blockPlaceContext.getHorizontalDirection());
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
    public void setPlacedBy(Level level, BlockPos position, BlockState blockState, LivingEntity entity, ItemStack itemStack) {
        level.setBlock(position.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    /**
     * Determines whether the block can survive in the current position.
     * If the current block being checked is the lower half, ensures that it is being placed on valid, sturdy ground.
     * If the current block being checked is the upper half, ensures that the block underneath it is the lower half of the object.
     *
     * @param blockState State of the block being checked
     * @param levelReader Reader for the level the block is in
     * @param position Position of the block being checked
     * @return Whether the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos position) {
        BlockPos blockpos = position.below();
        BlockState blockstate = levelReader.getBlockState(blockpos);
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(levelReader, blockpos, Direction.UP) : blockstate.is(this);
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     * This allows us to create a block that is made up of two halves and takes up two positions in the world. The lower and top halves.
     *
     * @param builder The builder for the state container
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HALF, FACING);
    }

    /**
     * Determines what happens to this block if it is pushed by a piston.
     * In this case, we want the block to be destroyed.
     *
     * @param state State of the block being pushed
     * @return What the reaction to being pushed is
     */
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    /**
     * Sets the flammability of the block. The higher the number the more likely it is to catch fire
     *
     * @param state State of the block
     * @param level Level that we are getting the block from
     * @param pos Position of the block
     * @param face The face of the block being set on fire
     * @return The flammability value of the block given its position in the world and the face being set alight
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face)
    {
        return 6;
    }

    /**
     * Determines how likely the fire is to destroy the block. The higher the number the more likely it is to burn up.
     *
     * @param state State of the block
     * @param level Level that the block exists in
     * @param pos Position of the block
     * @param face The face of the block that's currently aflame
     * @return The likelihood that this burning block will be destroyed by the fire
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction face)
    {
        return 6;
    }

    /**
     * Ensures that path finding entities treat this block as an obstruction even though it has no occlusion and is not a
     * full shape.
     *
     * @param state State of the block we are pathing against
     * @param blockGetter Reader for accessing block information
     * @param position Position of the block we are pathing against
     * @param pathType The type of path being checked (land, water, or air)
     * @return Whether block is pathable given the path type being checked
     */
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos position, PathComputationType pathType) {
        switch(pathType) {
            case LAND:
                return false;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
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
