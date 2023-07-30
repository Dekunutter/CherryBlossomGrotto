package com.deku.eastwardjourneys.common.features.decorators;

import com.deku.eastwardjourneys.common.blocks.ModBlocks;
import com.deku.eastwardjourneys.common.blocks.ShiitakeMushroom;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class ShiitakeMushroomDecorator extends TreeDecorator {
    public static final Codec<ShiitakeMushroomDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(ShiitakeMushroomDecorator::new, (mushroom) -> {
        return mushroom.probability;
    }).codec();

    private final float probability;

    public ShiitakeMushroomDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModTreeDecoratorTypes.SHIITAKE_MUSHROOM_TREE_DECORATOR_TYPE.get();
    }

    @Override
    public void place(Context context) {
        RandomSource random = context.random();
        if (!(random.nextFloat() >= this.probability)) {
            List<BlockPos> trunkLogs = context.logs();
            int positionY = trunkLogs.get(0).getY();
            trunkLogs.stream().filter((log) -> {
                return (log.getY() - positionY <= 4) && (log.getY() - positionY > 1);
            }).forEach((position) -> {
                for(Direction direction : Direction.Plane.HORIZONTAL) {
                    if (random.nextFloat() <= 0.25F) {
                        BlockPos blockpos = position.offset(direction.getStepX(), 0, direction.getStepZ());
                        if (context.isAir(blockpos)) {
                            context.setBlock(blockpos, ModBlocks.SHIITAKE_MUSHROOM.defaultBlockState().setValue(ShiitakeMushroom.FACING, direction));
                        }
                    }
                }
            });
        }
    }
}
