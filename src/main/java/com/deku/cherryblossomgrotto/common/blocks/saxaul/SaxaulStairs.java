package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenStairsBlock;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulStairs extends AbstractWoodenStairsBlock {
    public SaxaulStairs() {
        super(() -> ModBlocks.SAXAUL_PLANKS.defaultBlockState(), Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
