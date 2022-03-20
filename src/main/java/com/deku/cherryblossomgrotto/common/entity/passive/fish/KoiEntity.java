package com.deku.cherryblossomgrotto.common.entity.passive.fish;

import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class KoiEntity extends AbstractGroupFishEntity {
    public KoiEntity(EntityType<? extends KoiEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * The maximum size of a naturally spawning school of this fish entity
     *
     * @return The maximum size of a school for this fish entity
     */
    public int getMaxSchoolSize() {
        return 3;
    }

    /**
     * The bucket item that can be used to spawn this entity into the world
     *
     * @return The itemstack that spawns this entity
     */
    @Override
    protected ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.KOI_BUCKET);
    }

    /**
     * Gets the ambient sound event for this entity
     *
     * @return The ambient sound event for this entity
     */
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    /**
     * Gets the death sound event for this entity
     *
     * @return The death sound event for this entity
     */
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    /**
     * Gets the hurt sound event for this entity
     *
     * @return The hurt sound event for this entity
     */
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SALMON_HURT;
    }

    /**
     * Gets the flop sound event for this entity
     *
     * @return The flop sound event for this entity
     */
    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }
}
