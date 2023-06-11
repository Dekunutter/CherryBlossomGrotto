package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blocks.AbstractLeafPileBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.deku.cherryblossomgrotto.common.blocks.ModBlockStateProperties.HALF_LAYERS;

public class MapleLeafPile extends AbstractLeafPileBlock {
    private static final int MAX_LAYERS = 2;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{
            Shapes.empty(),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
    };

    public MapleLeafPile() {
        super(Properties.of().strength(0.1f).mapColor(MapColor.PLANT).ignitedByLava().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY).noOcclusion(), MAX_LAYERS, SHAPE_BY_LAYER);
        this.registerDefaultState(this.defaultBlockState().setValue(HALF_LAYERS, 1));
    }
}
