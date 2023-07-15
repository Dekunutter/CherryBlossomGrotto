package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.blocks.ModWoodType;
import com.deku.cherryblossomgrotto.common.entity.sign.ModHangingSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class WaterFirWallHangingSign extends WallHangingSignBlock {
    public WaterFirWallHangingSign() {
        super(Properties.of().mapColor(MapColor.TERRACOTTA_RED).ignitedByLava().sound(SoundType.HANGING_SIGN).instrument(NoteBlockInstrument.BASS).lootFrom(() -> ModBlocks.WATER_FIR_HANGING_SIGN), ModWoodType.WATER_FIR);
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
        return new ModHangingSignBlockEntity(position, blockState);
    }
}
