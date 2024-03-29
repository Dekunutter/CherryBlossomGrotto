package com.deku.cherryblossomgrotto.common.blocks.maple;

import com.deku.cherryblossomgrotto.common.blockEntities.MapleLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blockEntities.ModBlockEntityType;
import com.deku.cherryblossomgrotto.common.blocks.AbstractFallingLeavesBlock;
import com.deku.cherryblossomgrotto.common.particles.FallingMapleLeafOptions;
import com.deku.cherryblossomgrotto.utils.ModConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
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
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MapleLeaves extends AbstractFallingLeavesBlock {
    public MapleLeaves() {
        super(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_RED).strength(0.2f).sound(SoundType.GRASS).noOcclusion().isValidSpawn(MapleLeaves::validSpawns).isSuffocating(MapleLeaves.never()).isViewBlocking(MapleLeaves.never()).randomTicks());
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
    public void animateTick(BlockState state, Level world, BlockPos position, RandomSource random) {
        super.animateTick(state, world, position, random);
        if (random.nextInt(ModConfiguration.fallingLeafParticleSpawnChance.get()) == 0) {
            FallingMapleLeafOptions mapleLeaf = new FallingMapleLeafOptions(new Color(255, 255, 255, 255), 1.0f);
            world.addParticle(mapleLeaf, (float) position.getX() + random.nextFloat(), (float) position.getY() - 1.0f, (float) position.getZ() + random.nextFloat(), 0.0d, -0.1d, 0.0d);
        }
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
        return new MapleLeavesBlockEntity(position, state);
    }

    /**
     * Handles tick events for this block's block entity
     * In this case we are insuring that ticks are only handled server side before doing anything
     *
     * @param level Level the block is in
     * @param state State of the block
     * @param entityType Block entity type for this block
     * @param <T> Generic that represents block entities
     * @return The block entity for this block
     */
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        return level.isClientSide ? null : createTickerHelper(entityType, ModBlockEntityType.MAPLE_LEAVES_TILE_DATA, MapleLeavesBlockEntity::tick);
    }

    /**
     * Creates a ticker helper for this block
     *
     * @param entityType Block entity type for this block
     * @param mapleLeavesTileData The data for this block's block entity type
     * @param tick Block entity ticker for this block
     * @param <T> Generic that represents block entities
     * @param <E> Generic that represents another block entity
     * @return The block entity ticker for this block
     */
    private <T extends BlockEntity, E extends BlockEntity> BlockEntityTicker<T> createTickerHelper(BlockEntityType<T> entityType, BlockEntityType<MapleLeavesBlockEntity> mapleLeavesTileData, BlockEntityTicker<E> tick) {
        return entityType == mapleLeavesTileData ? (BlockEntityTicker<T>)tick : null;
    }
}
