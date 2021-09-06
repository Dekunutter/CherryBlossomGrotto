package com.deku.cherryblossomgrotto.common.tileEntities;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModTileEntityData {
    @ObjectHolder("cherry_leaves_tile_entity")
    public static TileEntityType<CherryLeavesTileEntity> CHERRY_LEAVES_TILE_DATA;
}
