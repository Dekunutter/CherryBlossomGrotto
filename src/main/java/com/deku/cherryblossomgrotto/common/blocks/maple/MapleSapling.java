package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.world.level.block.grower.MapleTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class MapleSapling extends SaplingBlock {
    public MapleSapling() {
        super(new MapleTreeGrower(), Properties.of().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
