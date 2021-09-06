package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HALF_LAYERS;

public class CherryBlossomPetals extends Block {
    private static final int MAX_LAYERS = 4;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
            VoxelShapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
    };

    public CherryBlossomPetals() {
        super(AbstractBlock.Properties.of(Material.LEAVES).strength(0.1f).sound(SoundType.GRASS).noOcclusion().requiresCorrectToolForDrops());
        setRegistryName("cherry_blossom_petals");
        this.registerDefaultState(this.defaultBlockState().setValue(HALF_LAYERS, 1));
    }

    /**
     * Gets the current shape of the block by extracting information from the shape layers array
     *
     * @param state State of the block
     * @param reader Reader interface for the block
     * @param position Position of the block
     * @param selectionContext Context for the block's selection
     * @return A VoxelShape representing the current shape of the block
     */
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos position, ISelectionContext selectionContext) {
        return SHAPE_BY_LAYER[state.getValue(HALF_LAYERS)];
    }

    /**
     * Gets the current shape of the block's collision
     *
     * @param state State of the block
     * @param reader Reader interface for the block
     * @param position Position of the block
     * @param selectionContext Context for the block's selection
     * @return A VoxelShape representing the current collision shape of the block
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos position, ISelectionContext selectionContext) {
        return SHAPE_BY_LAYER[state.getValue(HALF_LAYERS) - 1];
    }

    /**
     * Gets the current supporting shape of the block
     *
     * @param state State of the block
     * @param reader Reader interface for the block
     * @param position Position of the block
     * @return A VoxelShape representing the current supporting chape of the block
     */
    @Override
    public VoxelShape getBlockSupportShape(BlockState state, IBlockReader reader, BlockPos position) {
        return SHAPE_BY_LAYER[state.getValue(HALF_LAYERS)];
    }

    /**
     * Gets the current shape of the block visually
     *
     * @param state State of the block
     * @param reader Reader interface for the block
     * @param position Position of the block
     * @param selectionContext Context for the block's selection
     * @return A VoxelShape representing the current visual shape of the block
     */
    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader reader, BlockPos position, ISelectionContext selectionContext) {
        return SHAPE_BY_LAYER[state.getValue(HALF_LAYERS) -1];
    }

    /**
     * Determines whether the current shape of the block will block out light
     *
     * @param state State of the block
     * @return Whether the current shape blocks light
     */
    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    /**
     * Determines whether the block can survive in the current position
     *
     * @param state State of the block
     * @param world World the block is being added to
     * @param position Position of the block
     * @return Whether the block can survive in its current position
     */
    @Override
    public boolean canSurvive(BlockState state, IWorldReader world, BlockPos position) {
        BlockState belowState = world.getBlockState(position.below());
        return Block.isFaceFull(belowState.getCollisionShape(world, position.below()), Direction.UP) || belowState.getBlock() == this && belowState.getValue(HALF_LAYERS) == MAX_LAYERS;
    }

    /**
     * Updates the shape of the block.
     * If the block cannot survive it is replaced with an air block, otherwise its shape is updated.
     *
     * @param state State of the block having its shape updated
     * @param direction Direction we are going to update the shape in
     * @param otherState State of the other block in the check
     * @param world World the block is being updated in
     * @param position Position of the block having its shape updated
     * @param otherPosition Position of the other block in the check
     * @return Updated block state of the current block
     */
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, IWorld world, BlockPos position, BlockPos otherPosition) {
        return !state.canSurvive(world, position) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, otherState, world, position, otherPosition);
    }

    /**
     * Checks to see if the current block can be replaced by the item being used
     *
     * @param state State of the block potentially being replaced
     * @param itemContext Usage context of the item interacting with the block
     * @return Whether the block can be replaced by the item being used
     */
    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext itemContext) {
        int layers = state.getValue(HALF_LAYERS);
        if (itemContext.getItemInHand().getItem() == this.asItem() && layers < MAX_LAYERS) {
            if (itemContext.replacingClickedOnBlock()) {
                return itemContext.getClickedFace() == Direction.UP;
            }  else {
                return true;
            }
        } else {
            return layers == 1;
        }
    }

    /**
     * Sets the correct state for the block upon its placement.
     * Allows for layers to be incremented upon further placement of the block from the item menu.
     *
     * @param itemContext Usage context of the item interfacting with the block
     * @return Updated state of the block that was placed
     */
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext itemContext) {
        BlockState state = itemContext.getLevel().getBlockState(itemContext.getClickedPos());
        if (state.is(this)) {
            int layers = state.getValue(HALF_LAYERS);
            return state.setValue(HALF_LAYERS, Math.min(MAX_LAYERS, layers + 1));
        } else {
            return super.getStateForPlacement(itemContext);
        }
    }

    /**
     * Updates the current layers stored in state for the current block.
     *
     * @param position Position of the block
     * @param world The world containing the block
     */
    public void updateLayerState(BlockPos position, World world) {
        BlockState state = world.getBlockState(position);
        if (state.is(this)) {
            int layers = state.getValue(HALF_LAYERS);
            world.setBlock(position, state.setValue(HALF_LAYERS, Math.min(MAX_LAYERS, layers + 1)), 3);
        }
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     * This allows us to create a block that can stack in small layers but not all the way up to the size of a full block.
     *
     * @param builder The builder for the state container
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF_LAYERS);
    }

    /**
     * Checks if the current block is a "free" block.
     * One that can be overwritten in place of a pile of cherry blossom petals
     * Currently checks if the given block is an air block, a type of fire, liquid or is marked replaceable
     *
     * @param state State of the block being checked
     * @return Whether the block is "free" for replacement by cherry blossom petals or not
     */
    public static boolean isFree(BlockState state) {
        Material material = state.getMaterial();
        return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
    }
}
