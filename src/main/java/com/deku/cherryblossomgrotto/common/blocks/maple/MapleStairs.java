package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.AbstractWoodenStairsBlock;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MapleStairs extends AbstractWoodenStairsBlock {
    public MapleStairs() {
        super(() -> ModBlocks.MAPLE_PLANKS.defaultBlockState(), Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.WOOD).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
