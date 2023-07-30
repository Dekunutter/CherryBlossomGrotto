package com.deku.eastwardjourneys.common.blocks.maple;

import com.deku.eastwardjourneys.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MaplePressurePlate extends PressurePlateBlock {
    public MaplePressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of().noCollission().strength(0.5F).mapColor(MapColor.WOOD).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava(), ModBlockSetType.MAPLE);
    }
}
