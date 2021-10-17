package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.features.CherryBlossomTree;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CherryBlossomSapling extends SaplingBlock {
    public CherryBlossomSapling() {
        super(new CherryBlossomTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
        setRegistryName("cherry_blossom_sapling");
    }
}
