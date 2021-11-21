package com.deku.cherryblossomgrotto.common.tileEntities;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CherryBlossomSignTileEntity extends SignTileEntity {
    /**
     * Gets the type for the sign
     *
     * @return Sign type
     */
    @Override
    public TileEntityType<CherryBlossomSignTileEntity> getType() {
        return ModTileEntityData.CHERRY_SIGN_TILE_DATA;
    }
}
