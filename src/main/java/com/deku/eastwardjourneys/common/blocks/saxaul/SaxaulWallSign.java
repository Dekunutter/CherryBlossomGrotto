package com.deku.eastwardjourneys.common.blocks.saxaul;

import com.deku.eastwardjourneys.common.blocks.ModBlocks;
import com.deku.eastwardjourneys.common.blocks.ModWoodType;
import com.deku.eastwardjourneys.common.entity.sign.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulWallSign extends WallSignBlock {
    public SaxaulWallSign() {
        super(Properties.of().noCollission().strength(1.0F).mapColor(MapColor.COLOR_LIGHT_GRAY).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS).lootFrom(() -> ModBlocks.SAXAUL_SIGN), ModWoodType.SAXAUL);
    }

    /**
     * Gets the sign block entity for this sign
     *
     * @param position Position of the sign in the level
     * @param blockState State of the sign
     * @return The block entity for this sign
     */
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState blockState) {
        return new ModSignBlockEntity(position, blockState);
    }
}