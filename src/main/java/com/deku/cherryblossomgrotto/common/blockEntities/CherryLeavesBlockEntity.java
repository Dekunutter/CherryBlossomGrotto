package com.deku.cherryblossomgrotto.common.blockEntities;

import com.deku.cherryblossomgrotto.common.blocks.CherryBlossomPetals;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionDefaults;

import java.util.Random;

public class CherryLeavesBlockEntity extends BlockEntity {
    public CherryLeavesBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntityType.CHERRY_LEAVES_TILE_DATA, position, state);
    }

    /**
     * Tick function that runs 20 times a second to update all tile entities.
     * This entity is attached to an instance of a cherry blossom leaves block and traces downwards to see if piles of
     * petals should spawn.
     */
    public static void tick(Level world, BlockPos position, BlockState state, CherryLeavesBlockEntity entity) {
        if (!isValidTick(world)) {
            return;
        }

        Random random = new Random();
        if (random.nextInt(500) == 0) {
            BlockPos belowPosition = position.below();

            while (belowPosition.getY() >= DimensionDefaults.OVERWORLD_MIN_Y) {
                BlockState belowState = world.getBlockState(belowPosition);
                if (!world.isEmptyBlock(belowPosition)) {
                    BlockPos spawningPosition = belowPosition.above();
                    if (belowState.getBlock() == ModBlocks.CHERRY_PETALS) {
                        CherryBlossomPetals petals = (CherryBlossomPetals) belowState.getBlock();
                        petals.updateLayerState(belowPosition, world);
                    } else if (CherryBlossomPetals.isFree(world.getBlockState(spawningPosition), world, spawningPosition)) {
                        if (ModBlocks.CHERRY_PETALS.defaultBlockState().canSurvive(world, spawningPosition)) {
                            world.setBlockAndUpdate(spawningPosition, ModBlocks.CHERRY_PETALS.defaultBlockState());
                        }
                    }
                    break;
                }
                belowPosition = belowPosition.below();
            }
        }
    }

    /**
     * Check if the tick is valid.
     * A valid tick in this entity's case is one that is running against a legitimate world on the server-side
     *
     * @return Whether the current tick is valid
     */
    private static boolean isValidTick(Level world) {
        if (world == null) {
            return false;
        }

        if (world.isClientSide) {
            return false;
        }
        return true;
    }
}