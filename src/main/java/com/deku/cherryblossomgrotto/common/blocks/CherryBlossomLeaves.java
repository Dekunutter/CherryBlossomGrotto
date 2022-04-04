package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalOptions;
import com.deku.cherryblossomgrotto.common.blockEntities.CherryLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blockEntities.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Random;

public class CherryBlossomLeaves extends LeavesBlock implements IForgeBlockState, EntityBlock {
    public CherryBlossomLeaves() {
        super(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_PINK).strength(0.2f).sound(SoundType.GRASS).noOcclusion().isValidSpawn(CherryBlossomLeaves::validSpawns).isSuffocating(CherryBlossomLeaves.never()).isViewBlocking(CherryBlossomLeaves.never()).randomTicks());
        setRegistryName("cherry_blossom_leaves");
    }

    private static BlockBehaviour.StatePredicate never() {
        BlockBehaviour.StatePredicate statePredicate = (p_152641_, p_152642_, p_152643_) -> false;
        return statePredicate;
    }

    private static boolean validSpawns(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
    }

    /**
     * Overrides the animateTick function for this block.
     * Still calls base-game tick logic but also randomly spawns particles into the world.
     *
     * @param state The state of the block
     * @param world The world instance containing the block instance
     * @param position The position of the block
     * @param random A random number generator
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos position, Random random) {
        super.animateTick(state, world, position, random);
        if (random.nextInt(16) == 0) {
            FallingCherryBlossomPetalOptions cherryBlossomPetal = new FallingCherryBlossomPetalOptions(new Color(255, 255, 255, 255), 1.0f);
            world.addParticle(cherryBlossomPetal, (float) position.getX() + random.nextFloat(), (float) position.getY() - 1.0f, (float) position.getZ() + random.nextFloat(), 0.0d, -0.1d, 0.0d);
        }
    }


    @Override
    public int getFlammability(BlockGetter level, BlockPos pos, Direction face)
    {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockGetter level, BlockPos pos, Direction face)
    {
        return 20;
    }

    /**
     * Creates a tile entity for this block
     *
     * @param position Position of the block
     * @param state State of the block
     * @return The block entity associated with this block
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new CherryLeavesBlockEntity(position, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> entityType) {
       return world.isClientSide ? null : createTickerHelper(entityType, ModBlockEntityType.CHERRY_LEAVES_TILE_DATA, CherryLeavesBlockEntity::tick);
    }

    private <T extends BlockEntity, E extends BlockEntity> BlockEntityTicker<T> createTickerHelper(BlockEntityType<T> entityType, BlockEntityType<CherryLeavesBlockEntity> cherryLeavesTileData, BlockEntityTicker<E> tick) {
        return entityType == cherryLeavesTileData ? (BlockEntityTicker<T>)tick : null;
    }
}
