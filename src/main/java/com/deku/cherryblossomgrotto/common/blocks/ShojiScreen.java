package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class ShojiScreen extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    // NOTE: Actual model is only 3px wide but extended to 10px to resolve pathfinding issues with thin double-block objects and entities
    protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_AABB = Block.box(6.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);


    public ShojiScreen() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.NONE).noOcclusion().strength(0.1f).sound(SoundType.GRASS));
        setRegistryName("shoji_screen");
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
    }

    /**
     * Gets the shape of the block depending on the state associated with this instance.
     * Extracts the shape from the designated AABBs.
     * This determines in what direction the block will be rendered, since it doesnt fill an entire block
     *
     * @param state State of this block
     * @param blockReader Reader for information from this block
     * @param position Position of this block
     * @param selectionContext The context under which this block was selected
     * @return
     */
    public VoxelShape getShape(BlockState state, IBlockReader blockReader, BlockPos position, ISelectionContext selectionContext) {
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
     * @param world World the block is being updated in
     * @param position Position of the block having its shape updated
     * @param otherPosition Position of the other block in the check
     * @return Updated block state of the current block
     */
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState otherBlockState, IWorld world, BlockPos position, BlockPos otherPosition) {
        DoubleBlockHalf doubleblockhalf = blockState.getValue(HALF);
        if (direction.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || otherBlockState.is(this) && otherBlockState.getValue(HALF) != doubleblockhalf) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(world, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, otherBlockState, world, position, otherPosition);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    /**
     * Sets the correct state for the block upon its placement.
     * Designates the lower half of the block with a block state so that we can differentiate it from the top in other functions.
     *
     * @param itemUseContext Usage context of the item interfacing with the block
     * @return Updated state of the block that was placed.
     */
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        BlockPos position = itemUseContext.getClickedPos();
        if (position.getY() < 255 && itemUseContext.getLevel().getBlockState(position.above()).canBeReplaced(itemUseContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, itemUseContext.getHorizontalDirection());
        } else {
            return null;
        }
    }

    /**
     * Places the block into the world if the position above is also free so that the top block can be generated also.
     *
     * @param world World the block is being placed in
     * @param position Position the block is being placed on
     * @param blockState State of the block to be placed
     * @param entity The entity placing the block
     * @param itemStack The stack of items that the block may be originating from
     */
    public void setPlacedBy(World world, BlockPos position, BlockState blockState, LivingEntity entity, ItemStack itemStack) {
        world.setBlock(position.above(), blockState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    /**
     * Determines whether the block can survive in the current position.
     * If the current block being checked is the lower half, ensures that it is being placed on valid, sturdy ground.
     * If the current block being checked is the upper half, ensures that the block underneath it is the lower half of the object.
     *
     * @param blockState State of the block being checked
     * @param worldReader Reader for the world the block is in
     * @param position Position of the block being checked
     * @return Whether the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos position) {
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
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
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
     * @param world World that the block exists in
     * @param pos Position of the block
     * @param face The face of the block being set on fire
     * @return The flammability value of the block given its position in the world and the face being set alight
     */
    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 6;
    }

    /**
     * Determines how likely the fire is to destroy the block. The higher the number the more likely it is to burn up.
     *
     * @param state State of the block
     * @param world World that the block exists in
     * @param pos Position of the block
     * @param face The face of the block that's currently aflame
     * @return The likelihood that this burning block will be destroyed by the fire
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face)
    {
        return 6;
    }

    /**
     * Ensures that path finding entities treat this block as an obstruction even though it has no occlusion and is not a
     * full shape.
     *
     * @param state State of the block we are pathing against
     * @param blockReader Reader for accessing block information
     * @param position Position of the block we are pathing against
     * @param pathType The type of path being checked (land, water, or air)
     * @return Whether block is pathable given the path type being checked
     */
    @Override
    public boolean isPathfindable(BlockState state, IBlockReader blockReader, BlockPos position, PathType pathType) {
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
