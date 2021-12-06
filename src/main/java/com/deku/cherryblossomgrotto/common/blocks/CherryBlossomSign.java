package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.tileEntities.CherryBlossomSignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class CherryBlossomSign extends StandingSignBlock {
    public CherryBlossomSign() {
        super(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(1.0F).sound(SoundType.WOOD), ModWoodType.CHERRY_BLOSSOM);
        setRegistryName("cherry_blossom_sign");
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
