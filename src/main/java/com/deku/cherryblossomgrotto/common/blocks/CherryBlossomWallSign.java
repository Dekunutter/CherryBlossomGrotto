package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomWallSign extends WallSignBlock {
    public CherryBlossomWallSign() {
        super(Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(() -> ModBlocks.CHERRY_SIGN), ModWoodType.CHERRY_BLOSSOM);
        setRegistryName("cherry_blossom_wall_sign");
    }
}
