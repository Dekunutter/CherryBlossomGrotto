package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomSign extends StandingSignBlock {
    public CherryBlossomSign() {
        super(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(1.0F).sound(SoundType.WOOD), ModWoodType.CHERRY_BLOSSOM);
        setRegistryName("cherry_blossom_sign");
    }
}
