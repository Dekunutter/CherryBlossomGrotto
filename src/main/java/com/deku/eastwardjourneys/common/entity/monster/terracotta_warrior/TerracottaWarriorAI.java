package com.deku.eastwardjourneys.common.entity.monster.terracotta_warrior;

import com.deku.eastwardjourneys.common.entity.ai.behavior.HopToTarget;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.Optional;

// NOTE: Most logic in here is originally copied from AxolotlAi and PiglinAi and re-jigged for my needs
public class TerracottaWarriorAI {
    // Movement speed modifiers
    private static final float SPEED_MODIFIER_CHASING = 1F;

    // Memory expirations
    private static final int FIGHTING_MEMORY_EXPIRATION = 20;

    protected static Brain<?> makeBrain(Brain<TerracottaWarrior> brain) {
        // add activities
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    /**
     * Initializes the core activity for the terracotta warrior and the various behaviours it can take while this activity is active.
     * By setting a priority of 0 this will always be the default activity for the terracotta warrior.
     * The behaviours taken during this activity are:
     * - Jumping towards whatever is its current target
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initCoreActivity(Brain<TerracottaWarrior> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
            new HopToTarget()
        ));
    }

    /**
     * Initializes the idle activity for the terracotta warrior and the various behaviours it can take while the acitivty is active.
     * By setting a priority of 10 this will always be a frequent activity for the terracotta warrior.
     * The behaviours taken during this activity are:
     * - Attacking a nearby target
     * - Forgetting to attack a nearby target if underwater
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initIdleActivity(Brain<TerracottaWarrior> brain) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(
            StartAttacking.create(TerracottaWarriorAI::findNearestValidAttackTarget),
            EraseMemoryIf.create(TerracottaWarriorAI::isUnderwater, MemoryModuleType.WALK_TARGET)
        ));
    }

    /**
     * Initializes the fight activity for the terracotta warrior and the various behaviours it can take while the activity is active.
     * This is an activity that plays when a certain memory is active, the memory of a target that the terracotta warrior wants to attack.
     * The behaviours taken during this activity are:
     * - Stopping an attack if the target is invalid (target has died or is out of reach for example)
     * - Walking away from a target it can no longer reach
     * - Performing a basic attack
     * - Forgetting to fight if underwater
     * - Forgetting to fight if target is out of reach
     *
     * @param brain The brain that the activity is being added to
     */
    private static void initFightActivity(Brain<TerracottaWarrior> brain) {
        // TODO: Could I remove the out of reach activity, or reduce  memory to zero so its instantly forgotten when the target is lost? I'd like the warrior to stop jumping instantly instead of finishing that memory and hopping around
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT, 0, ImmutableList.of(
            StopAttackingIfTargetInvalid.create(TerracottaWarrior::onStopAttacking),
            SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(TerracottaWarriorAI::getSpeedModifierChasing),
            MeleeAttack.create(FIGHTING_MEMORY_EXPIRATION),
            EraseMemoryIf.create(TerracottaWarriorAI::isUnderwater, MemoryModuleType.ATTACK_TARGET),
            EraseMemoryIf.create(TerracottaWarriorAI::isTargetOutOfReach, MemoryModuleType.ATTACK_TARGET)
        ), MemoryModuleType.ATTACK_TARGET);
    }

    /**
     * Updates the activities being run by the terracotta warrior's brain every tick by taking the first valid activity given their conditions and having the entity do that activity
     *
     * @param warrior The terracotta warrior being updated
     */
    public static void updateActivity(TerracottaWarrior warrior) {
        Brain<TerracottaWarrior> brain = warrior.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse((Activity)null);
        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        warrior.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    /**
     * Gets the speed modifier for the teracotta warrior when its chasing a target.
     * Slows down the warrior heavily when in water.
     *
     * @param entity The entity who we are checking
     * @return The speed modifier for the current situation when the terracotta warrior is chasing prey or an item it wants
     */
    private static float getSpeedModifierChasing(LivingEntity entity) {
        return entity.isInWaterOrBubble() ? (SPEED_MODIFIER_CHASING / 3) : SPEED_MODIFIER_CHASING;
    }

    /**
     * Finds the nearest attackable target for this terracotta warrior
     *
     * @param warrior The warrior scanning for a target
     * @return A target to attack, if one was found
     */
    protected static Optional<? extends LivingEntity> findNearestValidAttackTarget(TerracottaWarrior warrior) {
        return warrior.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    /**
     * Checks if the terracotta warrior is currently underwater
     *
     * @param warrior The warrior being checked
     * @return Whether the warrior is underwater
     */
    private static boolean isUnderwater(TerracottaWarrior warrior) {
        return warrior.isUnderWater();
    }

    /**
     * Checks if the target is out of reach
     *
     * @param warrior The warrior that is checking for its target
     * @return Whether the target is out of reach
     */
    private static boolean isTargetOutOfReach(TerracottaWarrior warrior) {
        Brain<TerracottaWarrior> brain = warrior.getBrain();

        Optional<LivingEntity> target = brain.getMemory(MemoryModuleType.ATTACK_TARGET);
        return !(target.isPresent() && BehaviorUtils.canSee(warrior, target.get()));
    }
}