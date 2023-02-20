package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomWood extends AbstractWoodenBlock {
    public CherryBlossomWood() {
        super(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(2.0f).sound(SoundType.WOOD));
    }
}
