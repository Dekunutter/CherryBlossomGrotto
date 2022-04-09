package com.deku.cherryblossomgrotto.common.blockEntities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;

public class CherryBlossomSignTileEntity extends SignBlockEntity {
    /**
     * Gets the type for the sign
     *
     * @return Sign type
     */
    @Override
    public BlockEntityType<CherryBlossomSignTileEntity> getType() {
        return ModBlockEntityType.CHERRY_SIGN_TILE_DATA;
    }
}
