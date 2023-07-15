package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenStairsBlock;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class WaterFirStairs extends AbstractWoodenStairsBlock {
    public WaterFirStairs() {
        super(() -> ModBlocks.WATER_FIR_PLANKS.defaultBlockState(), Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_RED).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
