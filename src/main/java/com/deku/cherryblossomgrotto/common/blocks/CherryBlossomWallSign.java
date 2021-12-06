package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.tileEntities.CherryBlossomSignTileEntity;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CherryBlossomWallSign extends WallSignBlock {
    public CherryBlossomWallSign() {
        super(Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> ModBlocks.CHERRY_SIGN), ModWoodType.CHERRY_BLOSSOM);
        setRegistryName("cherry_blossom_wall_sign");
    }

    /**
     * Assigns the corresponding tile entity to this sign
     *
     * @param reader Reader interface for the block
     * @return The tile entity associated with this sign block
     */
    @Override
    public TileEntity newBlockEntity(IBlockReader reader) {
        return new CherryBlossomSignTileEntity();
    }
}
