package com.deku.eastwardjourneys.common.blocks.hinoki;

import com.deku.eastwardjourneys.common.AbstractLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class HinokiLeaves extends AbstractLeavesBlock {
    public HinokiLeaves() {
        super(Properties.of().strength(0.2f).sound(SoundType.GRASS).noOcclusion().mapColor(MapColor.COLOR_RED).pushReaction(PushReaction.DESTROY).ignitedByLava().isValidSpawn(HinokiLeaves::validSpawns).isSuffocating(HinokiLeaves.never()).isViewBlocking(HinokiLeaves.never()).randomTicks());
    }
}
