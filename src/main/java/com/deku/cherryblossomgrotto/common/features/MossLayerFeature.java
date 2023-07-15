package com.deku.cherryblossomgrotto.common.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class MossLayerFeature extends Feature<NoneFeatureConfiguration> {
    public MossLayerFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    // This is being only called SOMETIMES on biome generation and I have no idea why. When it does, it only gets applied to the first chunk... May get called a second/third time but those times will not generate any moss. Why does freeze layer and maple leaf cover work on entire biome but this just on one chunk in a biome!?
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos origin = context.origin();
        BlockPos.MutableBlockPos mutablePosition = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                int k = origin.getX() + i;
                int l = origin.getZ() + j;

                int i1 = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, k, l);
                mutablePosition.set(k, i1, l);

                BlockState state = worldGenLevel.getBlockState(mutablePosition);
                if (state.isAir() || state.canBeReplaced()) {
                    // NOTE: Seems this is only spawning on grass on slopes??? Is that something to do with light level
                    //if (mutablePosition.getY() < worldGenLevel.getMaxBuildHeight() && worldGenLevel.getBrightness(LightLayer.BLOCK, mutablePosition) < 10) {
                    if (mutablePosition.getY() < worldGenLevel.getMaxBuildHeight() && worldGenLevel.canSeeSky(mutablePosition)) {
                        BlockPos.MutableBlockPos beneathPosition = new BlockPos.MutableBlockPos();
                        beneathPosition.set(mutablePosition.below());
                        BlockState beneathState = worldGenLevel.getBlockState(beneathPosition);
                        if (beneathState.isSolid() && beneathState.isFaceSturdy(worldGenLevel, beneathPosition, Direction.UP)) {
                            Random random = new Random();
                            if (random.nextInt(10) > 7) {
                                worldGenLevel.setBlock(mutablePosition, Blocks.MOSS_CARPET.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
        }
        return true;

        // TODO: Even copy pasting in freeze top layer it seems to just not get properly called. So something to do with the instantiation of this feature is what is missing entirely. No idea what though. Nothing in the logs either
        /*WorldGenLevel worldgenlevel = context.level();
        BlockPos blockpos = context.origin();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
                int k = blockpos.getX() + i;
                int l = blockpos.getZ() + j;
                int i1 = worldgenlevel.getHeight(Heightmap.Types.MOTION_BLOCKING, k, l);
                blockpos$mutableblockpos.set(k, i1, l);
                blockpos$mutableblockpos1.set(blockpos$mutableblockpos).move(Direction.DOWN, 1);
                Biome biome = worldgenlevel.getBiome(blockpos$mutableblockpos).value();
                worldgenlevel.setBlock(blockpos$mutableblockpos1, Blocks.ICE.defaultBlockState(), 2);

                worldgenlevel.setBlock(blockpos$mutableblockpos, Blocks.SNOW.defaultBlockState(), 2);
                BlockState blockstate = worldgenlevel.getBlockState(blockpos$mutableblockpos1);
                if (blockstate.hasProperty(SnowyDirtBlock.SNOWY)) {
                    worldgenlevel.setBlock(blockpos$mutableblockpos1, blockstate.setValue(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
                }
            }
        }

        return true;*/
    }
}
