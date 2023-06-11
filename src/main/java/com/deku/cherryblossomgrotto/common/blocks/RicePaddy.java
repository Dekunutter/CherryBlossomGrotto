package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;

public class RicePaddy extends CropBlock implements LiquidBlockContainer {
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
    };

    public RicePaddy() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.CROP));
    }

    /**
     * Determines what item that will seed this crop
     *
     * @return The item that can be used to plant this crop
     */
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.RICE;
    }

    /**
     * Gets the shape of the voxel that this plant will render as, determined by the age of the crop
     *
     * @param state State of the crop block
     * @param blockGetter Reader for the block from the current level
     * @param position Position of the block
     * @param selectionContext The context upon which the block is being selected
     * @return The shape that the block is currently going to render with
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos position, CollisionContext selectionContext) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    /**
     * Whether or not this plant can be sustained under the current conditions.
     * Based directly on how sustainment of crops are done in vanilla but removes the check for tilled farmland.
     * Checks if the block that this crop is being planted on is a form of dirt.
     *
     * @param state State of the block the crop is being planted on
     * @param blockGetter Reader for the block from the current level
     * @param position The position of the block that the crop is being planted on
     * @param facing The direction the placed crop block is facing
     * @param plantable The plantable crop being placed
     * @return Whether the plant can be sustained on this block
     */
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter blockGetter, BlockPos position, Direction facing, IPlantable plantable) {
        return state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.PODZOL);
    }

    /**
     * Determines whether this crop can survive given the current conditions.
     * Is heavily based on the survival steps of a vanilla crop with some logic re-coded and removed to simplify the checks.
     * Makes sure the block has full access to light or is exposed to the sky without obstruction and then performs follow on checks for placement or state updating as needed.
     *
     * @param state State of the block being checked
     * @param levelReader Reader for the level
     * @param position Position of the block being checked
     * @return Whether this block can survive in the world given the current conditions
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos position) {
        if (levelReader.getRawBrightness(position, 0) >= 8 || levelReader.canSeeSky(position)) {
            BlockPos groundPosition = position.below();
            if (state.getBlock() == this) {
                return levelReader.getBlockState(groundPosition).canSustainPlant(levelReader, groundPosition, Direction.UP, this);
            } else {
                return super.mayPlaceOn(levelReader.getBlockState(groundPosition), levelReader, groundPosition);
            }
        }
        return false;
    }

    /**
     * Determines whether this crop can be placed on the current block.
     * This is heavily based on the placement checks of a vanilla crop with the logic much simplified to suit the differences in our rice paddy crop.
     * Performs the following checks:
     *     - Ensures that the rice is being planted in a still body of water
     *     - Ensures that the water is only one block deep
     *     - Ensures that the rice is being planted on a valid dirt block
     *
     * @param state State of the ground block
     * @param blockGetter Reader for the block from the current level
     * @param groundPosition Position of the ground that this crop is being placed on
     * @return Whether the crop can be placed on the given block
     */
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos groundPosition) {
        BlockPos abovePosition = groundPosition.above();
        FluidState fluidstate = blockGetter.getFluidState(abovePosition);
        if (fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8) {
            BlockPos aboveWaterPosition = abovePosition.above();
            FluidState aboveFluidState = blockGetter.getFluidState(aboveWaterPosition);
            if (aboveFluidState.isEmpty()) {
                BlockState belowState = blockGetter.getBlockState(groundPosition);
                return belowState.is(Blocks.GRASS_BLOCK) || belowState.is(Blocks.DIRT) || belowState.is(Blocks.COARSE_DIRT) || belowState.is(Blocks.PODZOL);
            }
        }
        return false;
    }

    /**
     * The state of the fluid on this block
     *
     * @param state State of the block
     * @return Whether the fluid state of this block can be considered a source block
     */
    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    /**
     * Determines whether liquid can be placed on this block
     *
     * @param blockGetter Reader for the block from the current level
     * @param position Position of the block
     * @param state State of the block
     * @param fluid Fluid that is being placed
     * @return Whether the give fluid can be placed on this block
     */
    @Override
    public boolean canPlaceLiquid(BlockGetter blockGetter, BlockPos position, BlockState state, Fluid fluid) {
        return false;
    }

    /**
     * Performs any fluid interactions that should happen when a fluid is placed on this block.
     * This block has no fluid placement interactions so we will just return false.
     *
     * @param level The level this fluid is being placed in
     * @param position Position of the block
     * @param state State of the block
     * @param fluidState State of the fluid being placed
     * @return Whether the fluid was placed into the world at the given position successfully.
     */
    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos position, BlockState state, FluidState fluidState) {
        return false;
    }
}
