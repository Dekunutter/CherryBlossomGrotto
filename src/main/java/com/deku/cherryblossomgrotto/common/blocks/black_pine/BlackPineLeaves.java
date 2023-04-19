package com.deku.cherryblossomgrotto.common.blocks.black_pine;

import com.deku.cherryblossomgrotto.common.AbstractLeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class BlackPineLeaves extends AbstractLeavesBlock {
    public BlackPineLeaves() {
        super(Properties.of(Material.LEAVES, MaterialColor.COLOR_GREEN).strength(0.2f).sound(SoundType.GRASS).noOcclusion().isValidSpawn(AbstractLeavesBlock::validSpawns).isSuffocating(AbstractLeavesBlock.never()).isViewBlocking(AbstractLeavesBlock.never()).randomTicks());
    }
}
