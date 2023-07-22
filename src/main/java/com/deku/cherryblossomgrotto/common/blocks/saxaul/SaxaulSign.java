package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.blocks.ModWoodType;
import com.deku.cherryblossomgrotto.common.entity.sign.ModSignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class SaxaulSign extends StandingSignBlock {
    public SaxaulSign() {
        super(Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).noCollission().strength(1.0F).ignitedByLava().sound(SoundType.WOOD).instrument(NoteBlockInstrument.BASS), ModWoodType.SAXAUL);
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
