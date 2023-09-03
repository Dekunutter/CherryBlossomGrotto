package com.deku.eastwardjourneys.common.blocks.water_fir;

import com.deku.eastwardjourneys.common.AbstractLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class WaterFirLeaves extends AbstractLeavesBlock {
    public WaterFirLeaves() {
        super(Properties.of().strength(0.2f).sound(SoundType.GRASS).noOcclusion().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).ignitedByLava().isValidSpawn(WaterFirLeaves::validSpawns).isSuffocating(WaterFirLeaves.never()).isViewBlocking(WaterFirLeaves.never()).randomTicks());
    }
}
