package com.deku.eastwardjourneys.common.blocks.maple;

import com.deku.eastwardjourneys.common.blocks.AbstractWoodenBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class MapleLog extends AbstractWoodenBlock {

    public MapleLog() {
        super(BlockBehaviour.Properties.of().mapColor((determineMaterialColour) -> {
            return determineMaterialColour.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_BROWN : MapColor.WOOD;
        }).sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava()
        );
    }
}
