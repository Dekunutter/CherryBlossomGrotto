package com.deku.cherryblossomgrotto.common.blocks.saxaul;

import com.deku.cherryblossomgrotto.common.world.level.block.grower.SaxaulTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class SaxaulSapling extends SaplingBlock {
    public SaxaulSapling() {
        super(new SaxaulTreeGrower(), Properties.of().mapColor(MapColor.PLANT).noCollission().pushReaction(PushReaction.DESTROY).randomTicks().instabreak().sound(SoundType.GRASS));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter getter, BlockPos position) {
        return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(BlockTags.SAND) || state.is(Blocks.TERRACOTTA);
    }
}
