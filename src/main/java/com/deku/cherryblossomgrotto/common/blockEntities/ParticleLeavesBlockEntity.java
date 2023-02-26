package com.deku.cherryblossomgrotto.common.blockEntities;

import com.deku.cherryblossomgrotto.common.blocks.AbstractLeafPileBlock;
import com.deku.cherryblossomgrotto.utils.ModConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionDefaults;

import java.util.Random;

public abstract class ParticleLeavesBlockEntity extends BlockEntity {
    private final Block leafPile;

    public ParticleLeavesBlockEntity(BlockEntityType<?> blockEntityType, BlockPos position, BlockState state, Block leafPile) {
        super(blockEntityType, position, state);
        this.leafPile = leafPile;
    }

    public Block getLeafPile() {
        return leafPile;
    }

    /**
     * Tick function that runs 20 times a second to update all tile entities.
     * This entity is attached to an instance of a leaves block and traces downwards to see if piles of
     * petals should spawn.
     */
    public static void tick(Level world, BlockPos position, BlockState state, ParticleLeavesBlockEntity entity) {
        if (!isValidTick(world)) {
            return;
        }

        Random random = new Random();
        if (random.nextInt(ModConfiguration.leafPileSpawnChance.get()) == 0) {
            BlockPos belowPosition = position.below();

            while (belowPosition.getY() >= DimensionDefaults.OVERWORLD_MIN_Y) {
                BlockState belowState = world.getBlockState(belowPosition);
                if (!world.isEmptyBlock(belowPosition)) {
                    BlockPos spawningPosition = belowPosition.above();
                    if (belowState.getBlock() == entity.getLeafPile()) {
                        AbstractLeafPileBlock leafPile = (AbstractLeafPileBlock) belowState.getBlock();
                        leafPile.updateLayerState(belowPosition, world);
                    } else if (AbstractLeafPileBlock.isFree(world.getBlockState(spawningPosition), world, spawningPosition)) {
                        if (entity.getLeafPile().defaultBlockState().canSurvive(world, spawningPosition)) {
                            world.setBlockAndUpdate(spawningPosition, entity.getLeafPile().defaultBlockState());
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
