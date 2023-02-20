package com.deku.cherryblossomgrotto.common.blocks.cherry_blossom;

import com.deku.cherryblossomgrotto.common.world.level.block.grower.CherryBlossomTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class CherryBlossomSapling extends SaplingBlock {
    public CherryBlossomSapling() {
        super(new CherryBlossomTreeGrower(), BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
