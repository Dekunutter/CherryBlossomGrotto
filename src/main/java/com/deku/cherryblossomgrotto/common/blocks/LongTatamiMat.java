package com.deku.cherryblossomgrotto.common.blocks;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class LongTatamiMat extends LongCarpetBlock {
    public LongTatamiMat() {
        super(Properties.of().strength(0.2F).mapColor(MapColor.COLOR_BROWN).ignitedByLava().pushReaction(PushReaction.DESTROY).sound(SoundType.BAMBOO));
    }
}
