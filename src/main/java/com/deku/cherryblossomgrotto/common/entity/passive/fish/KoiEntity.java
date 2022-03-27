package com.deku.cherryblossomgrotto.common.entity.passive.fish;

import com.deku.cherryblossomgrotto.common.items.ModItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class KoiEntity extends AbstractGroupFishEntity {
    private static final DataParameter<Integer> DATA_ID_TYPE_VARIANT = EntityDataManager.defineId(TropicalFishEntity.class, DataSerializers.INT);
    private static final ResourceLocation[] PATTERN_TEXTURE_LOCATIONS = new ResourceLocation[] {
            new ResourceLocation(MOD_ID,"textures/entity/fish/koi_pattern_1.png"),
            new ResourceLocation(MOD_ID,"textures/entity/fish/koi_pattern_2.png"),
            new ResourceLocation(MOD_ID,"textures/entity/fish/koi_pattern_3.png"),
            new ResourceLocation(MOD_ID,"textures/entity/fish/koi_pattern_4.png"),
            new ResourceLocation(MOD_ID,"textures/entity/fish/koi_pattern_5.png")
    };

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
     * Saves the NBT data of this entity to the instance of the bucket item generated from bucketing this fish
     *
     * @param itemStack The bucket item stack used to bucket this fish entity
     */
    protected void saveToBucketTag(ItemStack itemStack) {
        super.saveToBucketTag(itemStack);
        CompoundNBT compoundnbt = itemStack.getOrCreateTag();
        compoundnbt.putInt("BucketVariantTag", this.getVariant());
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

    /**
     * Gets the resource location of the layered pattern textures.
     * Which texture is used is determined by the ID of the pattern variant of this entity (0-4)
     *
     * @return Resource location of the pattern texture that will be used on this fish entity
     */
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getPatternTextureLocation() {
        return PATTERN_TEXTURE_LOCATIONS[getVariant()];
    }

    /**
     * Defines the data that needs to be synced over the internet.
     */
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    /**
     * Adds the data to the compound NBT that needs to be tied to this entity.
     * This is used for storing the entity's pattern ID.
     *
     * @param compoundNBT The NBT that we are putting additional information into
     */
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Variant", this.getVariant());
    }

    /**
     * Reads the data from the compound NBT that is to be tied to this entity.
     * Used to read the entity's pattern ID for this fish entity.
     *
     * @param compoundNBT The NBT that we are reading additional information into
     */
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.setVariant(compoundNBT.getInt("Variant"));
    }

    /**
     * Sets the variant ID into the entity data for this entity
     *
     * @param variantId The ID of the variant which determines the pattern we will use
     */
    public void setVariant(int variantId) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variantId);
    }

    /**
     * Gets the variant ID for this entity from its entity data
     *
     * @return An ID which will determine which pattern this fish has
     */
    public int getVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    /**
     * Finalizes the spawning of this entity.
     * If this is a fresh spawn we will generate a random pattern ID for this fish entity.
     * If this is not a fresh spawn (e.g: spawning from a bucket which already has an associated pattern ID) then we will associate that same pattern ID to this new spawn of the entity
     *
     * @param world The world this entity is spawning into
     * @param difficulty The difficulty that this game world is currently set to
     * @param spawnReason The reason that this entity is spawning
     * @param entityData Data associated with this entity
     * @param compoundNBT The NBT data for this entity
     *
     * @return The updated entity data for this entity having finished initializing for spawning into the world
     */
    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT compoundNBT) {
        entityData = super.finalizeSpawn(world, difficulty, spawnReason, entityData, compoundNBT);
        if (compoundNBT != null && compoundNBT.contains("BucketVariantTag", 3)) {
            this.setVariant(compoundNBT.getInt("BucketVariantTag"));
        } else {
            int randomPattern = random.nextInt(5);
            setVariant(randomPattern);
            entityData = new KoiEntity.KoiData(this, randomPattern);
        }
        return entityData;
    }

    /**
     * Inner static class outlining the structure of data tied to this entity.
     * Currently this is just the pattern ID for the given entity.
     */
    static class KoiData extends AbstractGroupFishEntity.GroupData {
        private final int patternId;

        private KoiData(KoiEntity entity, int patternId) {
            super(entity);
            this.patternId = patternId;
        }
    }
}
