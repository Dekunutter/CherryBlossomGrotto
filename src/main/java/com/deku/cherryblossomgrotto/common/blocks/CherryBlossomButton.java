package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class CherryBlossomButton extends WoodButtonBlock {
    public CherryBlossomButton() {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5f).sound(SoundType.WOOD));
        setRegistryName("cherry_blossom_button");
    }
}
