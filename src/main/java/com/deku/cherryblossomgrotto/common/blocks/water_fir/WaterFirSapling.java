package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.world.level.block.grower.MapleTreeGrower;
import com.deku.cherryblossomgrotto.common.world.level.block.grower.WaterFirTreeGrower;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class WaterFirSapling extends SaplingBlock {
    public WaterFirSapling() {
        super(new WaterFirTreeGrower(), Properties.of().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().instabreak().sound(SoundType.GRASS));
    }
}
