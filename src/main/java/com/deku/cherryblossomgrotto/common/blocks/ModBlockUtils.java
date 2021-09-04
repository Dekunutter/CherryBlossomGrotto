package com.deku.cherryblossomgrotto.common.blocks;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.List;

public class ModBlockUtils {
    private static final List<Block> VALID_GROUND_BLOCKS = (
            new ImmutableList.Builder<Block>().add(Blocks.GRASS_BLOCK).add(Blocks.DIRT).add(Blocks.COARSE_DIRT).add(Blocks.PODZOL).add(Blocks.FARMLAND).add(ModBlocks.GRASS).build()
    );

    /**
     * Designed as a replacement to the is mayPlaceOn function in the base game's BushBlock class.
     * This class would check a list of valid ground blocks to see if the bush can grow.
     * It cannot be appended to when we add new ground blocks so I am designing it as a utility class.
     * Checks to see if the given block is in the list of valid ground blocks.
     *
     * @param state the state of the block we are checking
     * @return Whether the block is listed as valid, bush-friendly ground
     */
    public static boolean mayPlaceOn(BlockState state) {
        return VALID_GROUND_BLOCKS.contains(state.getBlock());
    }
}
