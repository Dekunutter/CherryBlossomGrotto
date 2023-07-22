package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenSlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulSlab extends AbstractWoodenSlabBlock {
    public SaxaulSlab() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
