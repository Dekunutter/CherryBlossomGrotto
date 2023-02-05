package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalOptions;
import com.deku.cherryblossomgrotto.common.blockEntities.CherryLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blockEntities.ModBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
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
import net.minecraftforge.common.extensions.IForgeBlock;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class CherryBlossomLeaves extends LeavesBlock implements IForgeBlock, EntityBlock {
    public CherryBlossomLeaves() {
        super(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.COLOR_PINK).strength(0.2f).sound(SoundType.GRASS).noOcclusion().isValidSpawn(CherryBlossomLeaves::validSpawns).isSuffocating(CherryBlossomLeaves.never()).isViewBlocking(CherryBlossomLeaves.never()).randomTicks());
    }

    /**
     * A false predicate that is used to ensure that the leaves block does not suffocate an entity if the entity spawns within it
     *
     * @return Block behaviour indicating returning false
     */
    private static BlockBehaviour.StatePredicate never() {
        BlockBehaviour.StatePredicate statePredicate = (p_152641_, p_152642_, p_152643_) -> false;
        return statePredicate;
    }

    /**
     * Ensures that ocelots and parrots can still spawn on these leave blocks.
     * Re-implementation of privatized logic in vanilla code that is passed into the constructor for all leaves blocks
     *
     * @param blockState State of the block
     * @param blockGetter Getter for the block
     * @param blockPos Position of the block in the level
     * @param entityType Entity type that is attempting to spawn on this block
     * @return Whether this entity can spawn on this block
     */
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
    public void animateTick(BlockState state, Level world, BlockPos position, RandomSource random) {
        super.animateTick(state, world, position, random);
        if (random.nextInt(32) == 0) {
            FallingCherryBlossomPetalOptions cherryBlossomPetal = new FallingCherryBlossomPetalOptions(new Color(255, 255, 255, 255), 1.0f);
            world.addParticle(cherryBlossomPetal, (float) position.getX() + random.nextFloat(), (float) position.getY() - 1.0f, (float) position.getZ() + random.nextFloat(), 0.0d, -0.1d, 0.0d);
        }
    }

    /**
     * Determines the flammability of the block
     * The higher the number the more likely it is to catch fire
     *
     * @param state State of the block being burned
     * @param blockGetter Getter for the block being burned
     * @param position Position of the block being burned
     * @param face Face of the block that is being burned
     * @return The flammability of this block
     */
    @Override
    public int getFlammability(BlockState state, BlockGetter blockGetter, BlockPos position, Direction face)
    {
        return 5;
    }

    /**
     * Determines the likelihood that fire will spread to this block
     * The higher the number the more likely it is to burn up.
     *
     * @param state State of the block being burned
     * @param blockGetter Getter for the block being burned
     * @param position Position of the block being burned
     * @param face Face of the block that is being burned
     * @return The likelihood that fire will spread to this block
     */
    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter blockGetter, BlockPos position, Direction face)
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
       return level.isClientSide ? null : createTickerHelper(entityType, ModBlockEntityType.CHERRY_LEAVES_TILE_DATA, CherryLeavesBlockEntity::tick);
    }

    /**
     * Creates a ticker helper for this block
     *
     * @param entityType Block entity type for this block
     * @param cherryLeavesTileData The data for this block's block entity type
     * @param tick Block entity ticker for this block
     * @param <T> Generic that represents block entities
     * @param <E> Generic that represents another block entity
     * @return The block entity ticker for this block
     */
    private <T extends BlockEntity, E extends BlockEntity> BlockEntityTicker<T> createTickerHelper(BlockEntityType<T> entityType, BlockEntityType<CherryLeavesBlockEntity> cherryLeavesTileData, BlockEntityTicker<E> tick) {
        return entityType == cherryLeavesTileData ? (BlockEntityTicker<T>)tick : null;
    }
}
