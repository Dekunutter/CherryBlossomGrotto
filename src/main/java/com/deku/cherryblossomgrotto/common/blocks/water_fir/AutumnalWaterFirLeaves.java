package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.AbstractLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class AutumnalWaterFirLeaves extends AbstractLeavesBlock {
    public AutumnalWaterFirLeaves() {
        super(Properties.of().strength(0.2f).sound(SoundType.GRASS).noOcclusion().mapColor(MapColor.COLOR_ORANGE).pushReaction(PushReaction.DESTROY).ignitedByLava().isValidSpawn(AutumnalWaterFirLeaves::validSpawns).isSuffocating(AutumnalWaterFirLeaves.never()).isViewBlocking(AutumnalWaterFirLeaves.never()).randomTicks());
    }
}
