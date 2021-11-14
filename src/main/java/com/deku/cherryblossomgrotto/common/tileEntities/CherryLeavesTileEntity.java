package com.deku.cherryblossomgrotto.common.tileEntities;

import com.deku.cherryblossomgrotto.CherryBlossomGrotto;
import com.deku.cherryblossomgrotto.common.blocks.CherryBlossomPetals;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CherryLeavesTileEntity extends TileEntity implements ITickableTileEntity {
    public CherryLeavesTileEntity() {
        super(ModTileEntityData.CHERRY_LEAVES_TILE_DATA);
    }

    /**
     * Tick function that runs 20 times a second to update all tile entities.
     * This entity is attached to an instance of a cherry blossom leaves block and traces downwards to see if piles of
     * petals should spawn.
     */
    @Override
    public void tick() {
        if (!isValidTick()) {
            return;
        }

        Random random = new Random();
        if (random.nextInt(500) == 0) {
            World world = this.level;

            BlockPos position = this.getBlockPos();
            BlockPos belowPosition = position.below();

            //TODO: Maybe put a height max on this rather than just tracing to 0. Plus more recent Minecraft versions go deeper, beyond 0 so need to adjust for that
            while (belowPosition.getY() >= 0) {
                BlockState belowState = world.getBlockState(belowPosition);
                if (!world.isEmptyBlock(belowPosition)) {
                    BlockPos spawningPosition = belowPosition.above();
                    if (ModBlocks.CHERRY_PETALS.defaultBlockState().canSurvive(world, spawningPosition)) {
                        if (belowState.getBlock().is(ModBlocks.CHERRY_PETALS)) {
                            CherryBlossomPetals petals = (CherryBlossomPetals) belowState.getBlock();
                            petals.updateLayerState(belowPosition, world);
                        } else if (CherryBlossomPetals.isFree(world.getBlockState(spawningPosition), world, spawningPosition)) {
                            System.out.println("DETERMINED AS FREE AT " + spawningPosition);
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
    private boolean isValidTick() {
        if (!this.hasLevel()) {
            return false;
        }

        World world = this.getLevel();

        if (world.isClientSide) {
            return false;
        }
        return true;
    }
}