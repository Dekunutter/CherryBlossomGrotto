package com.deku.eastwardjourneys.common.entity.animal.tanooki;

import com.deku.eastwardjourneys.common.entity.ModEntityTypeInitializer;
import com.deku.eastwardjourneys.common.items.ModItemTags;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;

// NOTE: Most logic in here is originally copied from AxolotlAi and PiglinAi and re-jigged for my needs
public class TanookiAI {
    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);

    // Swimming modifiers
    private static final float SWIM_JUMP_CHANCE = 0.8F;

    // Movement speed modifiers
    private static final float SPEED_MODIFIER_STROLLING = 1.0F;
    private static final float SPEED_MODIFIER_SLOW_STROLLING = 0.6F;
    private static final float SPEED_MODIFIER_ITEM_SIGHTED = 1.5F;
    private static final float SPEED_MODIFIER_PANICKED = 2.0F;
    private static final float SPEED_MODIFIER_BREEDING = 0.2F;
    private static final float SPEED_MODIFIER_FOLLOWING_ADULT = 1.25F;
    private static final float SPEED_MODIFIER_TEMPTED = 1.25F;
    private static final float SPEED_MODIFIER_CHASING = 1.5F;

    // Look variables
    private static final int LOOK_MIN_DURATION = 45;
    private static final int LOOK_MAX_DURATION = 90;
    private static final float LOOK_DISTANCE = 14.0F;

    // Memory expirations
    private static final long MEMORY_TIME = 2400L;
    private static final int ADMIRATION_MEMORY_EXPIRATION = 120;
    private static final int FIGHTING_MEMORY_EXPIRATION = 20;
    private static final int TRACKING_ITEM_MEMORY_EXPIRATION = 200;
    private static final int EATING_MEMORY_EXPIRATION = 200;

    // Distance variables
    private static final int STOPPING_DISTANCE_FROM_TARGET = 3;
    private static final int RETREAT_DISTANCE_FROM_TARGET = 12;
    private static final int INTERACTION_STARTING_DISTANCE_FROM_TARGET = 8;
    private static final int INTERACTION_STOPPING_DISTANCE_FROM_TARGET = 2;

    // Idling variables
    private static final int IDLE_MIN_DURATION = 30;
    private static final int IDLE_MAX_DURATION = 60;

    // Item chasing variables
    private static final int ITEM_SCOUTING_RANGE = 9;
    private static final int ITEM_TRACKING_TIMEOUT = 200;

    protected static Brain<?> makeBrain(Brain<Tanooki> brain) {
        // add activities
        initCoreActivity(brain);
        initIdleActivity(brain);
        initAdmireItemActivity(brain);
        initFightActivity(brain);
        initRetreatActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    /**
     * Initializes the core activity for the tanooki and the various behaviours it can take while the activity is active.
     * By setting a priority of 0 this will always be the default activity for the tanooki.
     * The behaviours taken during this activity are:
     * - Swimming
     * - Entering a standard animal panic behaviour when scared
     * - Looking at whatever the current target is
     * - Moving to whatever its current target is
     * - Admiring an item of interest
     * - Setting a short cooldown to temptations so that it can't be repeatedly teased with food
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initCoreActivity(Brain<Tanooki> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
            new Swim(SWIM_JUMP_CHANCE),
            new AnimalPanic(SPEED_MODIFIER_PANICKED),
            new LookAtTargetSink(LOOK_MIN_DURATION, LOOK_MAX_DURATION),
            new MoveToTargetSink(),
            TanookiAdmiringItemifSeen.create(ADMIRATION_MEMORY_EXPIRATION),
            new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
        ));
    }

    /**
     * Initializes the idle activity for the tanooki and the various behaviours it can take while the activity is active.
     * By setting a priority of 10 this will always be a frequent activity for the tanooki.
     * The behaviours taken during this activity are:
     * - Looking at a player that's holding an item of interest to the tanooki
     * - Making love with another tanooki
     * - Following a tempting item
     * - Following an adult (if it is a baby)
     * - Looking around idly
     * - Moving around idly
     * - Looking at players that interact with it
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initIdleActivity(Brain<Tanooki> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
            SetEntityLookTarget.create(TanookiAI::isPlayerHoldingLovedItem, LOOK_DISTANCE),
            new AnimalMakeLove(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), SPEED_MODIFIER_BREEDING),
            new FollowTemptation((entity) -> {
                return SPEED_MODIFIER_TEMPTED;
            }),
            // TODO: Baby following behaviour not working for some reason
            BabyFollowAdult.create(ADULT_FOLLOW_RANGE, SPEED_MODIFIER_FOLLOWING_ADULT),
            StartAttacking.create(TanookiAI::findNearestValidAttackTarget),
            createIdleLookBehaviors(),
            createIdleMovementBehaviors(),
            SetLookAndInteract.create(EntityType.PLAYER, 4)
        ));
    }

    /**
     * Initializes the admire item activity for the tanooki and the various behaviours it can take while the activity is active.
     * This is an activity that plays when a certain memory is active, the memory of an item that the tanooki wants to have.
     * The behaviours taken during this activity are:
     * - Navigating to the wanted item
     * - Giving up on the item if its too far away to grab
     * - Giving up on the item if its out of reach
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initAdmireItemActivity(Brain<Tanooki> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.ADMIRE_ITEM, 10, ImmutableList.of(
            GoToWantedItem.create((input) -> true, SPEED_MODIFIER_ITEM_SIGHTED, true, ITEM_SCOUTING_RANGE),
            TanookiStopAdmiringItemIfTooFarAway.create(ITEM_SCOUTING_RANGE),
            TanookiStopAdmiringItemifTiredOfTryingToReach.create(ITEM_TRACKING_TIMEOUT, TRACKING_ITEM_MEMORY_EXPIRATION)
        ), MemoryModuleType.ADMIRING_ITEM);
    }

    /**
     * Initializes the fight activity for the tanooki and the various behaviours it can take while the activity is active.
     * This is an activity that plays when a certain memory is active, the memory of a target that the tanooki wants to attack.
     * The behaviours taken during this activity are:
     * - Stopping an attack if the target is invalid (target has died or is out of reach for example)
     * - Walking away from a target it can no longer reach
     * - Performing a basic attack
     * - Forgetting to fight if breeding (ends the fight activity)
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initFightActivity(Brain<Tanooki> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(
                StopAttackingIfTargetInvalid.create(Tanooki::onStopAttacking),
                SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(TanookiAI::getSpeedModifierChasing),
                MeleeAttack.create(FIGHTING_MEMORY_EXPIRATION),
                EraseMemoryIf.<Mob>create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    /**
     * Initializes the retreat activity for the tanooki and the various behaviours it can take while the activity is active.
     * This is an activity that plays when a certain memory is active, the memory of an item that the tanooki is trying to avoid something.
     * The behaviours taken during this activity are:
     * - Walking away from the target
     * - Looking around idly
     * - Walking around idly
     * - Forgetting to flee (ends the retreat activity)
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initRetreatActivity(Brain<Tanooki> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.AVOID, 10, ImmutableList.of(
                SetWalkTargetAwayFrom.entity(MemoryModuleType.AVOID_TARGET, SPEED_MODIFIER_STROLLING, RETREAT_DISTANCE_FROM_TARGET, true),
                createIdleLookBehaviors(),
                createIdleMovementBehaviors(),
                EraseMemoryIf.<Tanooki>create(TanookiAI::wantsToStopFleeing, MemoryModuleType.AVOID_TARGET)
        ), MemoryModuleType.AVOID_TARGET);
    }

    /**
     * Creates a list of default look behaviours. All look activities in the list have the same weight.
     * Beaviours include:
     * - Looking at the player
     * - Looking at another tanooki
     * - Looking at another target
     *
     * @return A list of the defafult look behaviours
     */
    private static ImmutableList<Pair<OneShot<LivingEntity>, Integer>> createLookBehaviors() {
        return ImmutableList.of(
            Pair.of(SetEntityLookTarget.create(EntityType.PLAYER, LOOK_DISTANCE), 1),
            Pair.of(SetEntityLookTarget.create(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), LOOK_DISTANCE), 1),
            Pair.of(SetEntityLookTarget.create(LOOK_DISTANCE), 1)
        );
    }

    /**
     * Creates a list of idle look behaviours from which one will be chosen and run.
     * Beaviours include:
     * - All default look behaviours
     * - Equal chance to do nothing instead
     *
     * @return The idle look behaviour that will be run
     */
    private static RunOne<LivingEntity> createIdleLookBehaviors() {
        return new RunOne<>(ImmutableList.<Pair<? extends BehaviorControl<? super LivingEntity>, Integer>>builder().addAll(createLookBehaviors()).add(Pair.of(new DoNothing(IDLE_MIN_DURATION, IDLE_MAX_DURATION), 1)).build());
    }

    /**
     * Creates a list of idle movement behaviours from which one will be chosen and run
     * Behaviours include:
     * - Randomly strolling
     * - Interacting with another tanooki
     * - Walking over to a target that the tanooki is looking at (if it's not tempted by an item in a player's hands
     * - Small chance to do nothing
     *
     * @return The idle movement behaviour that will be run
     */
    private static RunOne<Tanooki> createIdleMovementBehaviors() {
        return new RunOne<>(ImmutableList.of(
            Pair.of(RandomStroll.stroll(SPEED_MODIFIER_SLOW_STROLLING), 2),
            Pair.of(InteractWith.of(ModEntityTypeInitializer.TANOOKI_ENTITY_TYPE.get(), INTERACTION_STARTING_DISTANCE_FROM_TARGET, MemoryModuleType.INTERACTION_TARGET, SPEED_MODIFIER_SLOW_STROLLING, INTERACTION_STOPPING_DISTANCE_FROM_TARGET), 2),
            Pair.of(BehaviorBuilder.triggerIf(TanookiAI::doesntSeeAnyPlayerHoldingLovedItem, SetWalkTargetFromLookTarget.create(SPEED_MODIFIER_SLOW_STROLLING, STOPPING_DISTANCE_FROM_TARGET)), 2),
            Pair.of(new DoNothing(IDLE_MIN_DURATION, IDLE_MAX_DURATION), 1)
        ));
    }

    /**
     * Updates the activities being run by the tanooki's brain every tick by taking the first valid activity given their conditions and having the tanooki do that activity
     * If the tanooki has finished attacking something, sets a cooldown so that it won't launch straight into another hunt
     *
     * @param tanooki The tanooki being updated
     */
    public static void updateActivity(Tanooki tanooki) {
        Brain<Tanooki> brain = tanooki.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity)null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.IDLE));
        if (activity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse((Activity)null) != Activity.FIGHT) {
            brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, MEMORY_TIME);
        }
    }

    /**
     * Gets the speed modifier for the tanooki when its chasing prey or an item it wants
     *
     * @param entity The entity who we are checking
     * @return The speed modifier for the current situation when the tanooki is chasing prey or an item it wants
     */
    private static float getSpeedModifierChasing(LivingEntity entity) {
        return entity.isInWaterOrBubble() ? (SPEED_MODIFIER_CHASING / 3) : SPEED_MODIFIER_CHASING;
    }

    /**
     * If the entity can see a player holding an item it wants
     *
     * @param entity The entity being checked
     * @return Whether the entity can see a player holding an item it wants
     */
    private static boolean seesPlayerHoldingLovedItem(LivingEntity entity) {
        return entity.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
    }

    /**
     * If the entity can't see a player holding an item it wants
     *
     * @param entity The entity being checked
     * @return Whether the entity can't see a player holding an item it wants
     */
    private static boolean doesntSeeAnyPlayerHoldingLovedItem(LivingEntity entity) {
        return !seesPlayerHoldingLovedItem(entity);
    }

    /**
     * Gets the list off items that tempt the tanooki
     *
     * @return The list of items that can tempt the tanooki
     */
    public static Ingredient getTemptations() {
        return Ingredient.of(ModItemTags.TANOOKI_TEMPTATIONS);
    }

    protected static Optional<? extends LivingEntity> findNearestValidAttackTarget(Tanooki tanooki) {
        return BehaviorUtils.isBreeding(tanooki) ? Optional.empty() : tanooki.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    /**
     * Forces the tanooki to stop walking
     *
     * @param tanooki The tanooki that will stop walking
     */
    private static void stopWalking(Tanooki tanooki) {
        tanooki.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        tanooki.getNavigation().stop();
    }

    /**
     * Removes one item from a given item entity's stack
     *
     * @param itemEntity The item entity we are removing an item from
     * @return The item entity which will have an item removed
     */
    private static ItemStack removeOneItemFromItemEntity(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        ItemStack splitStack = itemstack.split(1);
        if (itemstack.isEmpty()) {
            itemEntity.discard();
        } else {
            itemEntity.setItem(itemstack);
        }

        return splitStack;
    }

    /**
     * If the given item is one the tanooki loves
     *
     * @param itemStack Item being checked
     * @return Whether the tanooki loves the given item
     */
    protected static boolean isLovedItem(ItemStack itemStack) {
        return itemStack.is(ModItemTags.TANOOKI_DESIREABLES);
    }

    /**
     * Whether the tanooki has eaten recently
     *
     * @param tanooki Tanooki being checked
     * @return Whether the tanooki has a recent memory of eating
     */
    private static boolean hasEatenRecently(Tanooki tanooki) {
        return tanooki.getBrain().hasMemoryValue(MemoryModuleType.ATE_RECENTLY);
    }

    /**
     * Sets a memory for the tanooki that it has recently eaten
     * The memory is marked as expiring and given a timeout so that the tanooki will eat again when food is available and enough time has passed
     *
     * @param tanooki The tanooki that is going toe at
     */
    private static void eat(Tanooki tanooki) {
        tanooki.getBrain().setMemoryWithExpiry(MemoryModuleType.ATE_RECENTLY, true, EATING_MEMORY_EXPIRATION);
    }

    /**
     * Checks if the tanooki considers the given item food
     *
     * @param itemStack The iteam we are checking
     * @return If the item is considered to be food by a tanooki
     */
    private static boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItemTags.TANOOKI_TEMPTATIONS);
    }

    /**
     * Checks whether the tanooki may be interested in picking up a given item
     * Conditions are as follows:
     *  - The tanooki is an adult
     *  - It has not actively attacking a target
     *  - It has interest in the item in question
     *  - The item can fit within its inventory
     *  - The tanooki desires the item
     *  - If the item is food, that the tanooki has not recently eaten
     *
     * @param tanooki The tanooki being checked
     * @param itemStack The item that the tanooki may want to pick up
     * @return Whether the tanooki wants to pick up the given item
     */
    protected static boolean wantsToPickup(Tanooki tanooki, ItemStack itemStack) {
        if (tanooki.isBaby()) {
            return false;
        } else if (tanooki.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return false;
        } else if (!isLovedItem(itemStack)) {
            return false;
        } else {
            boolean canAdd = tanooki.canAddToInventory(itemStack);
            if (itemStack.is(ModItemTags.TANOOKI_DESIREABLES)) {
                return canAdd;
            } else if (isFood(itemStack)) {
                return !hasEatenRecently(tanooki) && canAdd;
            } else {
                return false;
            }
        }
    }

    /**
     * Picks up a given item and places it within the tanooki's inventory.
     * This forces the tanooki to stop walking and ensures the tanooki would still have interest in the item in question even though wantsToPickup runs prior.
     * If it does, then it attempts to place the item in its inventory, removing the item entity from the world or splitting the stack if it cannot fit the entire amount in its inventory.
     * If its inventory is full, the item will be ignored.
     *
     * @param tanooki The tanooki picking up the item
     * @param itemEntity The item being picked up
     */
    protected static void pickUpItem(Tanooki tanooki, ItemEntity itemEntity) {
        stopWalking(tanooki);
        ItemStack itemstack = itemEntity.getItem();

        int takenCount = itemstack.getCount();
        if (isLovedItem(itemstack)) {
            tanooki.getBrain().eraseMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            // TODO: Maybe see if I can change it so that the tanooki always tries to pick up new items on a cooldown, randomly dropping something else from its inventory if full
            ItemStack remainingItems = putInInventory(tanooki, itemstack);
            if (!remainingItems.isEmpty()) {
                takenCount -= remainingItems.getCount();
            }
        } else if (isFood(itemstack) && !hasEatenRecently(tanooki)) {
            // Split out one for a meal and if any remain, put them in the inventory
            removeOneItemFromItemEntity(itemEntity);

            if (!itemEntity.isRemoved()) {
                ItemStack remainingItems = putInInventory(tanooki, itemstack);
                if (!remainingItems.isEmpty()) {
                    takenCount -= remainingItems.getCount();
                }
            }

            // TODO: Might have this done as its own action somewhere else AFTER pick up where tanooki random eats meals from its inventory when its hungry
            eat(tanooki);
            takenCount += 1;
        }

        tanooki.take(itemEntity, takenCount);
        itemstack.split(takenCount);
        if (itemstack.isEmpty()) {
            itemEntity.discard();
        }
    }

    /**
     * Puts an item in the tanooki's inventory if possible.
     * If the item cannot be placed into its inventory, just returns the items that it was trying to place.
     *
     * @param tanooki The tanooki trying to put away an item
     * @param itemStack The item being put away by the tanooki
     * @return The remaining items in the stack that the tanooki could not put away
     */
    private static ItemStack putInInventory(Tanooki tanooki, ItemStack itemStack) {
        if (tanooki.getInventory().canAddItem(itemStack)) {
            return tanooki.addToInventory(itemStack);
        }
        return itemStack;
    }

    /**
     * Checks if the given entity is holding an item a tanooki may want
     *
     * @param entity The entity who is possibly holding an item
     * @return Whether the entity is holding an item a tanooki may want
     */
    public static boolean isPlayerHoldingLovedItem(LivingEntity entity) {
        return entity.getType() == EntityType.PLAYER && entity.isHolding(TanookiAI::isLovedItem);
    }

    /**
     * If the tanooki wants to stop fleeing.
     * Stops fleeing if the tanooki has forgotten what the target to avoid is
     *
     * @param tanooki The tanooki having its memories checked
     * @return Whether the tanooki will want to stop fleeing
     */
    private static boolean wantsToStopFleeing(Tanooki tanooki) {
        Brain<Tanooki> brain = tanooki.getBrain();
        if (!brain.hasMemoryValue(MemoryModuleType.AVOID_TARGET)) {
            return true;
        } else {
            return false;
        }
    }
}
