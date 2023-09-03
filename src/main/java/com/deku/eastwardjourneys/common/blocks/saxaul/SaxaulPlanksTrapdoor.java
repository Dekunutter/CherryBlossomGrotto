package com.deku.eastwardjourneys.common.blocks.saxaul;

import com.deku.eastwardjourneys.common.blocks.ModBlockSetType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulPlanksTrapdoor extends TrapDoorBlock {
    public SaxaulPlanksTrapdoor() {
        super(Properties.of().strength( 3.0f).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().instrument(NoteBlockInstrument.BASS).noOcclusion().ignitedByLava().isValidSpawn(SaxaulPlanksTrapdoor::never), ModBlockSetType.SAXAUL);
    }

    /**
     * A false predicate
     * Note: Should be able to just use an accesstransformer here instead of needing to re-code it locally but doesn't seem to be picking up my access modifier change when refreshing the project
     *
     * @param state State of the block
     * @param getter Getter for the block
     * @param position Position of the block
     * @param entityType Type of the entity
     * @return Boolean set to false
     */
    private static Boolean never(BlockState state, BlockGetter getter, BlockPos position, EntityType<?> entityType) {
        return false;
    }
}