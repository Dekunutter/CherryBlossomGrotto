package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import com.deku.cherryblossomgrotto.common.blocks.ModWoodType;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class CherryBlossomFenceGate extends FenceGateBlock {
    public CherryBlossomFenceGate() {
        super(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(2.0f, 3.0f).sound(SoundType.WOOD), ModWoodType.CHERRY_BLOSSOM);
    }
}
