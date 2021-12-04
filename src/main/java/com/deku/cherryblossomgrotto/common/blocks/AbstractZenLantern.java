package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HALF;

public abstract class AbstractZenLantern extends Block {
    protected static final VoxelShape UPPER_HALF_AABB = VoxelShapes.or(
            Block.box(5.0D, 8.0D, 5.0D, 11.0D, 12.0D, 11.0D),
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D)
    );
    protected static final VoxelShape LOWER_HALF_AABB = VoxelShapes.or(
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D)
    );
    protected final IParticleData flameParticle;

    public AbstractZenLantern(int lightLevel, IParticleData flameParticle) {
        super(AbstractBlock.Properties.of(Material.DECORATION).lightLevel((level) -> {
            return lightLevel;
        }).strength(2.0F, 6.0F).sound(SoundType.STONE));
        this.flameParticle = flameParticle;
        this.registerDefaultState(this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    /**
     * Gets the current shape of the block by extracting information from the designated AABB.
     * Uses the block state to determine which half of the object this particular block is, which affects its shape
     *
     * @param blockState State of the block
     * @param blockReader Reader interface for the block
     * @param position Position of the block
     * @param selectionContext Context for the block's selection
     * @return A VoxelShape representing the current shape of the block
     */
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos position, ISelectionContext selectionContext) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return UPPER_HALF_AABB;
        } else {
            return LOWER_HALF_AABB;
        }
    }

    /**
     * Updates the shape of the block.
     * If the block survive it is replaced with an air block, otherwise its shape is updated.
     * Ensures that if either block is destroyed, then the other cannot survive in the world either since they make up one object.
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
     * Designates the lower half of the block with a block state so that we can differntiate it from the top in other functions.
     *
     * @param itemUseContext Usage context of the item interfacing with the block
     * @return Updated state of the block that was placed
     */
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        BlockPos position = itemUseContext.getClickedPos();
        if (position.getY() < 255 && itemUseContext.getLevel().getBlockState(position.above()).canBeReplaced(itemUseContext)) {
            return this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER);
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
     * @return Wheather the block can survive in its current position
     */
    public boolean canSurvive(BlockState blockState, IWorldReader worldReader, BlockPos position) {
        BlockPos blockpos = position.below();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        return blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldReader, blockpos, Direction.UP) : blockstate.is(this);
    }

    /**
     * Overrides the base block's state container so that we can include our new block state.
     * This allows us to create a block that is made up of two halves and takes up too positions in the world. The lower and the upper halves.
     *
     * @param builder The builder for the state container
     */
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    /**
     * Overrides the animateTick function for this block.
     * Adds a flickering flame to the block, same as a torch has.
     *
     * @param blockState The state of the block
     * @param world The world instance containing the block instance
     * @param position The position of the block
     * @param random A random number generator
     */
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState blockState, World world, BlockPos position, Random random) {
        if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            double positionX = (double) position.getX() + 0.5D;
            double positionY = (double) position.getY() + 0.3D;
            double positionZ = (double) position.getZ() + 0.5D;
            world.addParticle(this.flameParticle, positionX, positionY, positionZ, 0.0D, 0.0D, 0.0D);
        }
    }
}
