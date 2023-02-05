package com.deku.cherryblossomgrotto.common.entity.animal.tanooki;

import com.deku.cherryblossomgrotto.common.entity.EntityTypeInitializer;
import com.deku.cherryblossomgrotto.common.entity.ai.sensing.ModSensorTypes;
import com.deku.cherryblossomgrotto.common.items.ModItemTags;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Tanooki extends Animal implements GeoEntity, InventoryCarrier {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation SIT_ANIM = RawAnimation.begin().thenPlay("misc.sit");

    private static final int INVENTORY_SIZE = 4;

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super Tanooki>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_ITEMS,
            SensorType.NEAREST_ADULT,
            SensorType.HURT_BY,
            ModSensorTypes.TANOOKI_ATTACKABLES.get(),
            ModSensorTypes.TANOOKI_TEMPTATIONS.get()
    );

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.TEMPTING_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_ADULT,
            MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.AVOID_TARGET,
            MemoryModuleType.ADMIRING_ITEM,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryModuleType.ADMIRING_DISABLED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryModuleType.HUNTED_RECENTLY,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
            MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
            MemoryModuleType.ATE_RECENTLY,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
            MemoryModuleType.IS_TEMPTED,
            MemoryModuleType.HAS_HUNTING_COOLDOWN,
            MemoryModuleType.IS_PANICKING
    );


    private final SimpleContainer inventory = new SimpleContainer(INVENTORY_SIZE);

    public Tanooki(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        setCanPickUpLoot(true);
    }

    /**
     * Sets basic entity attributes for the tanooki
     *
     * @return The attribute builder for this entity
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    /**
     * Controls animations for the tanooki entity.
     * If the tanooki is idle we cycling through its sitting and walking animations based on if it is currently moving.
     * Otherwise, default walk and living animations are used.
     *
     * @param controllers The object to register your controller instances to
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                DefaultAnimations.genericWalkIdleController(this),
                new AnimationController<>(this, "SitController", 10, state -> {
                    if (!state.isMoving()) {
                        return state.setAndContinue(SIT_ANIM);
                    } else {
                        //return state.setAndContinue(DefaultAnimations.IDLE);
                        return PlayState.STOP;
                    }
                    //return PlayState.STOP;
                }),
                DefaultAnimations.genericWalkController(this),
                DefaultAnimations.genericLivingController(this)
        );
    }

    /**
     * Gets the brain provider for the tanooki
     *
     * @return Brain provider for the tanooki
     */
    protected Brain.Provider<Tanooki> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    /**
     * Initializes the brain for this tanooki entity
     *
     * @param memoryMap The memory map for this tanooki
     * @return The brain initialized for this tanooki
     */
    protected Brain<?> makeBrain(Dynamic<?> memoryMap) {
        return TanookiAI.makeBrain(this.brainProvider().makeBrain(memoryMap));
    }

    /**
     * Gets the brain of this tanooki
     *
     * @return The brain for this tanooki
     */
    public Brain<Tanooki> getBrain() {
        return (Brain<Tanooki>)super.getBrain();
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
     * @return Animatable instance cache
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    /**
     * Gets the ambient sound made by the tanooki at random intervals
     *
     * @return Ambient sound of the tanooki
     */
    protected SoundEvent getAmbientSound() {
        // TODO: returning null till I actually add some sounds to sounds/entity/tanooki
        return null;
        //return ModSoundEvents.TANOOKI_AMBIENT.get();
    }

    /**
     * Gets the sound the tanooki makes when it takes damage
     *
     * @param source The source of the damage to the tanooki
     * @return Hurt sound of the tanooki
     */
    protected SoundEvent getHurtSound(DamageSource source) {
        // TODO: returning null till I actually add some sounds to sounds/entity/tanooki
        return null;
        //return ModSoundEvents.TANOOKI_HURT.get();
    }

    /**
     * Gets the sound of the tanooki when it is killed
     *
     * @return Death sound of the tanooki
     */
    protected SoundEvent getDeathSound() {
        // TODO: returning null till I actually add some sounds to sounds/entity/tanooki
        return null;
        //return ModSoundEvents.TANOOKI_DEATH.get();
    }

    /**
     * Plays the sound for the tanooki taking a step
     *
     * @param position The current position of the tanooki
     * @param blockState The state of the block the tanooki is currently walking over
     */
    protected void playStepSound(BlockPos position, BlockState blockState) {
        // TODO: Commented out till I actually add some sounds to sounds/entity/tanooki
        //this.playSound(ModSoundEvents.TANOOKI_STEP.get(), 0.15F, 1.0F);
    }

    /**
     * Gets the default volume of sounds from the tanooki
     *
     * @return The volume of sounds from the tanooki by default
     */
    protected float getSoundVolume() {
        return 0.0F;
    }

    /**
     * Checks whether the tanooki considers the given item a food item
     * NOTE: All the tanooki temptable items are food, the desirables (items it picks up that are not food) are stored in a separate list.
     *
     * @param itemStack The item being checked
     * @return If the given item is edible to the tanooki
     */
    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItemTags.TANOOKI_TEMPTATIONS);
    }

    /**
     * Entity that is the offspring of a tanooki
     *
     * @param level the level that the entity is being spawned into
     * @param mob The parent of the newly born entity
     * @return The newborn entity spawning from the given entity
     */
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        Tanooki offspring = EntityTypeInitializer.TANOOKI_ENTITY_TYPE.create(level);
        if (offspring != null) {
            offspring.setPersistenceRequired();
        }
        return offspring;
    }

    /**
     * Gets the standing eye height of a tanooki
     *
     * @param pose The current pose that the tanooki is in
     * @param dimensions The overall dimensions of the tanooki
     * @return The height of the tanooki's eyes in its current position
     */
    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
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
     * The inventory of the tanooki
     *
     * @return The inventory of the tanooki
     */
    @VisibleForDebug
    public SimpleContainer getInventory() {
        return inventory;
    }

    /**
     * Attempts to add an item to the tanooki's inventory
     *
     * @param itemStack The item being added to the inventory
     * @return The remaining items from the item stack after the rest were added to the tanooki's inventory. Item stack will be empty if all items were added to its inventory
     */
    protected ItemStack addToInventory(ItemStack itemStack) {
        return inventory.addItem(itemStack);
    }

    /**
     * Checks to see if the given item could be added to the tanooki's inventory
     *
     * @param itemStack The item we are checking to see if it is addable to the tanooki's inventory
     * @return Whether the given item can be placed into the tanooki's inventory
     */
    protected boolean canAddToInventory(ItemStack itemStack) {
        return inventory.canAddItem(itemStack);
    }

    /**
     * Checks whether the tanooki will want to pick up the given item
     * Checks if the entity is enabled for picking up world loot and if its an item that the tanooki may have interest in
     *
     * @param itemStack The item that the tanooki is checking
     * @return Whether the item is one that the tanooki will want
     */
    public boolean wantsToPickUp(ItemStack itemStack) {
        return net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this) && this.canPickUpLoot() && TanookiAI.wantsToPickup(this, itemStack);
    }

    /**
     * Picks up an item
     * Triggers any base pickup logic before calling custom logic for placing the item into the tanooki's inventory
     *
     * @param itemEntity The item being picked up
     */
    protected void pickUpItem(ItemEntity itemEntity) {
        this.onItemPickup(itemEntity);
        TanookiAI.pickUpItem(this, itemEntity);
    }

    /**
     * Custom logic that triggers when the tanooki is killed and is dropping its inventory contents
     * In our case we remove all items from the tanooki's inventory and spawn them into the world
     *
     * @param source The source of the damage that killed the tanooki
     * @param chance The chance of loot dropping
     * @param alwaysDrops Whether loot will always drop from the tanooki
     */
    // TODO: The last two params I'm pretty unsure what they actually do, just took guesses here
    protected void dropCustomDeathLoot(DamageSource source, int chance, boolean alwaysDrops) {
        super.dropCustomDeathLoot(source, chance, alwaysDrops);
        inventory.removeAllItems().forEach(this::spawnAtLocation);
    }

    /**
     * Gets the mob type for the tanooki
     *
     * @return Mob type
     */
    public MobType getMobType() {
        return MobType.UNDEFINED;
    }

    /**
     * Logic that is stepped by the server for this entity's AI every tick.
     * Just ticks the tanooki's brain and updates any activities
     */
    protected void customServerAiStep() {
        this.level.getProfiler().push("tanookiBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("tanookiActivityUpdate");
        TanookiAI.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    /**
     * Logic that runs when the tanooki is killed by another entity
     * Checks if the target is dead but otherwise doesn't really do anything with that information for now.
     *
     * @param tanooki The tanooki that was killed
     * @param target The entity that killed the tanooki
     */
    public static void onStopAttacking(Tanooki tanooki, LivingEntity target) {
        boolean hasTarget = TanookiAI.findNearestValidAttackTarget(tanooki).filter((nearestTarget) -> {
            return nearestTarget == target;
        }).isPresent();

        // TODO: Some on-death logic like celebrating on the kill or eating could be cool here
    }


    /**
     * Events that should trigger when a player uses an item on the tanooki.
     * If the item in use was considered food by the tanooki, then the item is consumed and an eating sound is played.
     * NOTE: The item is consumed by the super of this method
     *
     * @param player The player using an item on the tanooki
     * @param hand The hand with which the player used the item
     */
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        InteractionResult interactionresult = super.mobInteract(player, hand);
        if (interactionresult.consumesAction() && this.isFood(itemstack)) {
            // TODO: Commented out till I actually add some sounds to sounds/entity/tanooki
            //this.level.playSound((Player)null, this, this.getEatingSound(itemstack), SoundSource.NEUTRAL, 1.0F, Mth.randomBetween(this.level.random, 0.8F, 1.2F));
        }

        return interactionresult;
    }

    /**
     * Rules to specify to the tanooki to determine if it should spawn into the world
     *
     * @param entityType The type of entity being spawned
     * @param levelAccessor Accessor for the world into which the tanooki is being spawned
     * @param spawnType The origin of the entity's spawning
     * @param position The position that the entity is spawning into the world at
     * @param random Random number generator
     * @return Whether the entity should spawn or not
     */
    public static boolean checkTanookiSpawnRules(EntityType<? extends Animal> entityType, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        // TODO: Probably don't need this. Could remove and use basic animal spawn rules
        return true;
    }
}
