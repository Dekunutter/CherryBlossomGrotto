package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.ModBlockSetType;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulPressurePlate extends PressurePlateBlock {
    public SaxaulPressurePlate() {
        super(Sensitivity.EVERYTHING, Properties.of().noCollission().strength(0.5F).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava(), ModBlockSetType.SAXAUL);
    }
}
