package com.deku.eastwardjourneys.common.blocks.water_fir;

import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.extensions.IForgeBlock;

public class WaterFirSlab extends SlabBlock implements IForgeBlock {
    public WaterFirSlab() {
        super(Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_RED).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS));
    }
}
