package com.deku.cherryblossomgrotto.common.blocks.maple;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class MapleFenceGate extends FenceGateBlock {
    public MapleFenceGate() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN);
    }
}
