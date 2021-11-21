package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;

public class CherryBlossomButton extends WoodButtonBlock {
    public CherryBlossomButton() {
        super(AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5f).sound(SoundType.WOOD));
        setRegistryName("cherry_blossom_button");
    }
}
