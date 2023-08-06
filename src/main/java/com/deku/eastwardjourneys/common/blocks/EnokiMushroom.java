package com.deku.eastwardjourneys.common.blocks;

import com.deku.eastwardjourneys.common.features.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class EnokiMushroom extends MushroomBlock {
    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    // TODO: Just make this protected with an accesstransformer. Just adding here as a quick work around for the time being
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public EnokiMushroom() {
        super(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).hasPostProcess(EnokiMushroom::always).pushReaction(PushReaction.DESTROY), ModTreeFeatures.HUGE_ENOKI_MUSHROOM);

        this.feature = ModTreeFeatures.HUGE_ENOKI_MUSHROOM;
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

    @Override
    public boolean growMushroom(ServerLevel level, BlockPos position, BlockState state, RandomSource random) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optional = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
        if (optional.isEmpty()) {
            return false;
        } else {
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(level, random, position, optional.get());
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) return false;
            //level.removeBlock(position, false);
            if (event.getFeature().value().place(level, level.getChunkSource().getGenerator(), random, position)) {
                return true;
            } else {
                // Remove the mushroom from the world AFTER placement instead of BEFORE so that I can grab its state during generation
                level.setBlock(position, state, 3);
                level.removeBlock(position, false);
                return false;
            }
        }
    }
}
