package com.deku.eastwardjourneys.common.blocks.hinoki;

import com.deku.eastwardjourneys.common.blocks.ModWoodType;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class HinokiFenceGate extends FenceGateBlock {
    public HinokiFenceGate() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.WOOD).sound(SoundType.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS), ModWoodType.HINOKI);
    }
}
