package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.features.ModFeatures;
import com.deku.cherryblossomgrotto.common.features.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnokiMushroom extends MushroomBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public EnokiMushroom() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(EnokiMushroom::always).pushReaction(PushReaction.DESTROY), ModTreeFeatures.HUGE_ENOKI_MUSHROOM);
    }

    private static Boolean always(BlockState state, BlockGetter getter, BlockPos position) {
        return true;
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos position, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos position) {
        BlockPos blockpos = position.below();
        BlockState blockstate = levelReader.getBlockState(blockpos);
        if (blockstate.is(ModBlockTags.MUSHROOM_GROW_BLOCK_WOOD)) {
            return true;
        } else {
            return levelReader.getRawBrightness(position, 0) < 13 && blockstate.canSustainPlant(levelReader, blockpos, net.minecraft.core.Direction.UP, this);
        }
    }
}
