package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.CherryBlossomGrotto;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class NewGrassBlock extends net.minecraft.block.GrassBlock {
    public NewGrassBlock() {
        super(AbstractBlock.Properties.of(Material.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS));
        setRegistryName("cherry_blossom_grass");
        this.registerDefaultState(this.defaultBlockState().setValue(ModBlockStateProperties.CHERRY_BLOSSOM_PETAL_COVERED, false));
    }

    /**
     * Overrides the SnowyDirtBlocks state container so that we can include our new block state.
     * This allows us to cover grass blocks in cherry blossom petals.
     *
     * @param builder The builder for the state container
     */
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ModBlockStateProperties.CHERRY_BLOSSOM_PETAL_COVERED, SNOWY);
    }

    /**
     * Overrides the canSustainPlant function of the base game's Block class.
     * This function checks to see if the given block can sustain the given plant during placement.
     * We override this since the BushBlock's list of compatible ground blocks does not contain our new grass block.
     *
     * @param state State of the block we want to place a plant on
     * @param world World containing the block
     * @param position Position of the block we want to place a plant on
     * @param facing The Direction the plant is facing
     * @param plantable The plant being placed on the block
     * @return Whether the block can sustain the given plant
     */
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos position, Direction facing, IPlantable plantable) {
        boolean canSustainOnBaseBlocks = super.canSustainPlant(state, world, position, facing, plantable);

        if (plantable instanceof BushBlock && ModBlockUtils.mayPlaceOn(state)) {
            return true;
        }

        return canSustainOnBaseBlocks;
    }

    /**
     * Sets the cherry blossom petal covering state of this block to true.
     *
     * @param world The world that the block is updating in
     * @param position The position in the world of the block being updated
     * @param state The state of the block we want to manipulate
     */
    public void coverWithCherryBlossomPetals(ClientWorld world, BlockPos position, BlockState state) {
        world.setBlock(position, state.setValue(ModBlockStateProperties.CHERRY_BLOSSOM_PETAL_COVERED, true), 2);
        CherryBlossomGrotto.LOGGER.info("Cherry state is " + state.getValue(ModBlockStateProperties.CHERRY_BLOSSOM_PETAL_COVERED));
    }

    /**
     * Replaces a block with an instance of this overridden grass block if that block is a vanilla
     * grass block.
     *
     * @param position The position in the world of the block being replaced
     * @param world The world that the block is updating in
     */
    public final void replaceVanillaGrassBlock(BlockPos position, World world) {
        BlockState state = world.getBlockState(position);
        if (state.is(Blocks.GRASS_BLOCK)) {
            BlockState replacementState = ModBlocks.GRASS.defaultBlockState();
            replacementState.setValue(SNOWY, world.getBlockState(position.above()).is(Blocks.SNOW));
            replacementState.setValue(ModBlockStateProperties.CHERRY_BLOSSOM_PETAL_COVERED, false);
            world.setBlockAndUpdate(position, replacementState);
        }
    }
}
