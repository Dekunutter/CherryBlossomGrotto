package com.deku.eastwardjourneys.common.blocks.black_pine;

import com.deku.eastwardjourneys.common.blocks.ModWoodType;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BlackPineFenceGate extends FenceGateBlock {
    public BlackPineFenceGate() {
        super(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).mapColor(MapColor.WOOD).sound(SoundType.WOOD).ignitedByLava().instrument(NoteBlockInstrument.BASS), ModWoodType.BLACK_PINE);
    }
}
