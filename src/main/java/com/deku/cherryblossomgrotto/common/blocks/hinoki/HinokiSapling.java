package com.deku.cherryblossomgrotto.common.blocks.hinoki;

import com.deku.cherryblossomgrotto.common.world.level.block.grower.HinokiTreeGrower;
import com.deku.cherryblossomgrotto.common.world.level.block.grower.MapleTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class HinokiSapling extends SaplingBlock {
    public HinokiSapling() {
        super(new HinokiTreeGrower(), Properties.of().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
