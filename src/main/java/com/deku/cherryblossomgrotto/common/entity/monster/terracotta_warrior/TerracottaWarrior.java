package com.deku.cherryblossomgrotto.common.entity.monster.terracotta_warrior;

import com.deku.cherryblossomgrotto.common.entity.ai.control.JumpingMoveControl;
import com.deku.cherryblossomgrotto.common.entity.ai.navigation.JumpPathNavigation;
import com.deku.cherryblossomgrotto.common.entity.ai.sensing.ModSensorTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class TerracottaWarrior extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final int JUMP_DELAY = 15;

    private int INITIAL_JUMP_DELAY = 10;

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super TerracottaWarrior>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.HURT_BY,
            ModSensorTypes.TERRACOTTA_WARRIOR_ATTACKABLES.get()
    );

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.NEAREST_ATTACKABLE
    );

    public TerracottaWarrior(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        moveControl = new JumpingMoveControl(this, JUMP_DELAY, INITIAL_JUMP_DELAY);
        this.xpReward = 5;
        setPersistenceRequired();
    }

    /**
     * Sets basic attributes to the entity like its health, movement speed and attack damage.
     *
     * @return All attributes built into this entity
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 75.0D)
            .add(Attributes.MOVEMENT_SPEED, (double) 0.5F)
            .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    /**
     * Registers animation controllers to the entity
     * This entity (currently) has no animations anyway.
     *
     * @param controllers Animation controller register
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
            DefaultAnimations.genericLivingController(this)
        );
    }

    /**
     * Provider for the entity's brain with the brain's memory and sensor types set
     *
     * @return Provider for the entity's brain
     */
    protected Brain.Provider<TerracottaWarrior> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    /**
     * Ties the brain to the entity's AI controller
     *
     * @param memoryMap Map of memories that can be applied to the brain
     * @return Brain for this entity
     */
    protected Brain<?> makeBrain(Dynamic<?> memoryMap) {
        return TerracottaWarriorAI.makeBrain(this.brainProvider().makeBrain(memoryMap));
    }

    /**
     * Gets the brain of this entity
     *
     * @return Brain tied to this entity
     */
    public Brain<TerracottaWarrior> getBrain() {
        return (Brain<TerracottaWarrior>)super.getBrain();
    }

    /**
     * Sends debug packets about the entity's brain
     */
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    /**
     * Gets the animatable instance cache
     *
     * @return  Animatable instance cache
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    /**
     * Gets the ambient sound made by the entity at random intervals
     *
     * @return Ambient sound of the entity
     */
    protected SoundEvent getAmbientSound() {
        return null;
    }

    /**
     * Gets the sound the entity makes when it takes damage
     *
     * @param source The source of the damage made against this entity
     * @return Hurt sound of the entity
     */
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.STONE_BREAK;
    }

    /**
     * Gets the sound made by the entity upon death
     *
     * @return Death sound of the entity
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }

    /**
     * Plays the sound made by the entity when it steps on the ground
     *
     * @param position Position of the entity
     * @param blockState State of the block the entity is stepping on
     */
    protected void playStepSound(BlockPos position, BlockState blockState) {
        this.playSound(SoundEvents.STONE_STEP, 0.15F, 1.0F);
    }

    /**
     * Gets the default volume of sounds from the entity
     *
     * @return The volume of sounds from the entity by default
     */
    protected float getSoundVolume() {
        return 1.0F;
    }

    /**
     * Gets the standing eye height of this entity.
     * It's set to a value very similar to other human-like mobs, such as zombies.
     *
     * @param pose Current pose of the entity
     * @param dimensions Dimensions of the entity
     * @return The eye height of the entity
     */
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.75F;
    }

    /**
     * Defines data in the entity that needs to be synced with the server
     */
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    /**
     * Called whenever synced data is updated
     *
     * @param dataAccessor Entity data being updated
     */
    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        super.onSyncedDataUpdated(dataAccessor);
    }

    /**
     * Called whenever synced data is saved
     *
     * @param compoundTag The compound tag for the data to save under
     */
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
    }

    /**
     * Called whenever synced data is read
     *
     * @param compoundTag The compound tag for the saved data
     */
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
    }

    /**
     * Defines a custom navigation class to this entity
     *
     * @param level The level this entity is in
     * @return The navigation class tied to this entity
     */
    protected PathNavigation createNavigation(Level level) {
        return new JumpPathNavigation(this, level);
    }

    /**
     * Gets the mob's type
     *
     * @return Type of mob that this entity is
     */
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    /**
     * Logic that is stepped by the server for this entity's AI every tick.
     * Just ticks the entity's brain and updates any activities
     */
    protected void customServerAiStep() {
        this.level.getProfiler().push("terracottaWarriorBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("terracottaWarriorActivityUpdate");
        TerracottaWarriorAI.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    /**
     * Logic that runs when the entity kills another entity
     *
     * @param warrior The entity that performed the kill
     * @param target The target entity that was killed
     */
    public static void onStopAttacking(TerracottaWarrior warrior, LivingEntity target) {
        boolean hasTarget = TerracottaWarriorAI.findNearestValidAttackTarget(warrior).filter((nearestTarget) -> {
            return nearestTarget == target;
        }).isPresent();
    }

    /**
     * Rules to specify to the entity to determine if it should spawn into the world
     *
     * @param entityType The type of entity being spawned
     * @param levelAccessor Accessor for the world into which the entity is being spawned
     * @param spawnType The origin of the entity's spawning
     * @param position The position that the entity is spawning into the world at
     * @param random Random number generator
     * @return Whether the entity should spawn or not
     */
    public static boolean checkTerracottaWarriorSpawnRules(EntityType<? extends Monster> entityType, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return checkAnyLightMonsterSpawnRules(entityType, levelAccessor, spawnType, position, random);
    }

    /**
     * Performs some logic when the entity is spawned into the world.
     * In this case, we are ensuring that all warriors have persistence so that they are saved in the world once spawned.
     *
     * @param levelAccessor Accessor for the world this entity is spawning into
     * @param difficultyInstance The difficulty setting thats active for this world
     * @param spawnType The type of mob spawn
     * @param spawnGroupData The data associated with this mob spawn
     * @param compoundTag NBT tag
     * @return The spawn data for this entity
     */
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, spawnGroupData, compoundTag);

        setPersistenceRequired();

        return spawnGroupData;
    }
}