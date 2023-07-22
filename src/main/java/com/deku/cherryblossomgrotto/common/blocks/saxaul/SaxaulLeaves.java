package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.AbstractLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SaxaulLeaves extends AbstractLeavesBlock {
    public SaxaulLeaves() {
        super(Properties.of().strength(0.2f).sound(SoundType.GRASS).noOcclusion().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).ignitedByLava().isValidSpawn(AbstractLeavesBlock::validSpawns).isSuffocating(AbstractLeavesBlock.never()).isViewBlocking(AbstractLeavesBlock.never()).randomTicks());
    }
}
