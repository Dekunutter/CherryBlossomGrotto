package com.deku.eastwardjourneys.common.blocks.saxaul;

import com.deku.eastwardjourneys.common.blocks.ModWoodType;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulFenceGate extends FenceGateBlock {
    public SaxaulFenceGate() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.COLOR_LIGHT_GRAY).sound(SoundType.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS), ModWoodType.SAXAUL);
    }
}
