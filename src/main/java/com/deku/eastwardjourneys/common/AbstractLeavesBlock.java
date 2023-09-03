package com.deku.eastwardjourneys.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;

public abstract class AbstractLeavesBlock extends LeavesBlock implements IForgeBlock {
    public AbstractLeavesBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    /**
     * A false predicate that is used to ensure that the leaves block does not suffocate an entity if the entity spawns within it
     *
     * @return Block behaviour indicating returning false
     */
    protected static BlockBehaviour.StatePredicate never() {
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
    protected static boolean validSpawns(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityType.OCELOT || entityType == EntityType.PARROT;
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
}
