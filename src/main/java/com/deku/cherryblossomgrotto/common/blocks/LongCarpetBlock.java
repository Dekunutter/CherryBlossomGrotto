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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class LongCarpetBlock extends CarpetBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public LongCarpetBlock(Properties properties) {
        super(properties);
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
        return SHAPE;
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
        Direction facing = blockState.getValue(FACING);

        if (doubleblockhalf == DoubleBlockHalf.LOWER && direction == facing && !otherBlockState.is(this)) {
            return Blocks.AIR.defaultBlockState();
        }
        if (doubleblockhalf == DoubleBlockHalf.UPPER && direction.getOpposite() == facing && !otherBlockState.is(this)) {
            return Blocks.AIR.defaultBlockState();
        }

        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || otherBlockState.is(this) && otherBlockState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, otherBlockState, levelAccessor, position, otherPosition);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    /**
     * Sets the correct state for the block upon its placement.
     * Designates the lower half of the block with a block state so that we can differentiate it from the top in other functions.
     * Ensures that the other half of the block is being placed in an area where it can survive and is a replaceable block
     *
     * @param blockPlaceContext Usage context of the block as it is placed into the level
     * @return Updated state of the block that was placed.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        BlockPos position = blockPlaceContext.getClickedPos();
        BlockPos otherHalfPosition = position.relative(blockPlaceContext.getHorizontalDirection());
        Level level = blockPlaceContext.getLevel();
        if (position.getY() < level.getMaxBuildHeight() && canSurvive(blockPlaceContext.getLevel().getBlockState(otherHalfPosition), blockPlaceContext.getLevel(), otherHalfPosition) && blockPlaceContext.getLevel().getBlockState(otherHalfPosition).canBeReplaced(blockPlaceContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, blockPlaceContext.getHorizontalDirection());
        } else {
            return null;
        }
    }

    /**
     * Places the block into the world if the position one block in the facing direction is also free so that the top block can be generated also.
     *
     * @param level Level the block is being placed in
     * @param position Position the block is being placed on
     * @param blockState State of the block to be placed
     * @param entity The entity placing the block
     * @param itemStack The stack of items that the block may be originating from
     */
    public void setPlacedBy(Level level, BlockPos position, BlockState blockState, LivingEntity entity, ItemStack itemStack) {
        Direction direction = blockState.getValue(FACING);
        BlockPos otherHalfPosition = position.relative(direction);

        level.setBlock(otherHalfPosition, blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    /**
     * Determines whether the block can survive in the current position.
     * Ensures that it is being placed on valid, sturdy ground.
     *
     * @param blockState State of the block being checked
     * @param levelReader Reader for the level the block is in
     * @param position Position of the block being checked
     * @return Whether the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos position) {
        BlockPos blockpos = position.below();
        BlockState blockstate = levelReader.getBlockState(blockpos);

        return blockstate.isFaceSturdy(levelReader, blockpos, Direction.UP);
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
