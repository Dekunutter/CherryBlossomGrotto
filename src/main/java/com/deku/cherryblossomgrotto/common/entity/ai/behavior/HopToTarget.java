package com.deku.cherryblossomgrotto.common.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Optional;

// NOTE: Right now this is where an entity picks a random(?) target location and walks towards it.
//  The behaviour stops once the entity reaches that target and then a few frames later when it wants to move again, it picks another and starts all over
public class HopToTarget extends Behavior<Mob> {
    private static final int DEFAULT_MINIMUM_DURATION = 150;
    private static final int DEFAULT_MAXIMUM_DURATION = 250;

    private int remainingCooldown;

    @Nullable
    private Path path;

    @Nullable
    private BlockPos lastTargetPos;

    private float speedModifier;

    public HopToTarget() {
        this(DEFAULT_MINIMUM_DURATION, DEFAULT_MAXIMUM_DURATION);
    }

    public HopToTarget(int minDuration, int maxDuration) {
        super(ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryStatus.REGISTERED,
            MemoryModuleType.PATH,
            MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryStatus.VALUE_PRESENT
        ), minDuration, maxDuration);
    }

    /**
     * Checks special conditions that will determine if the behaviour can begin.
     * Typically the behaviour cannot start if it is on cooldown or if the target has already been reached.
     *
     * @param level The level this entity is in
     * @param mob The mob that this behaviour is tied to
     * @return
     */
    protected boolean checkExtraStartConditions(ServerLevel level, Mob mob) {
        if (remainingCooldown > 0) {
            --remainingCooldown;
            return false;
        } else {
            Brain<?> brain = mob.getBrain();
            WalkTarget jumpTarget = brain.getMemory(MemoryModuleType.WALK_TARGET).get();
            boolean targetReached = reachedTarget(mob, jumpTarget);
            if (!targetReached && tryComputePath(mob, jumpTarget, level.getGameTime())) {
                lastTargetPos = jumpTarget.getTarget().currentBlockPosition();
                return true;
            } else {
                brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                if (targetReached) {
                    brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
                }

                return false;
            }
        }
    }

    /**
     * Checks to see if the current behaviour is relevant.
     * If the entity has no path or doesn't know the target's last position, then the behaviour cannot continue executing.
     * If this returns false, the stop() method will be called to erase the behaviour from memory.
     *
     * @param level The level in which the entity exists
     * @param mob The mob that this behaviour is tied to
     * @param memoryExpiration The expiration on the current memory
     * @return Whether the current behaviour is relevant to this entity or not
     */
    protected boolean canStillUse(ServerLevel level, Mob mob, long memoryExpiration) {
        if (path != null && lastTargetPos != null) {
            Optional<WalkTarget> optional = mob.getBrain().getMemory(MemoryModuleType.WALK_TARGET);
            PathNavigation pathnavigation = mob.getNavigation();
            return !pathnavigation.isDone() && optional.isPresent() && !reachedTarget(mob, optional.get());
        } else {
            return false;
        }
    }

    /**
     * Stops the current behaviour from executing by erasing it from memory.
     * This will abruptly stop all navigation and clears the path from the entity's memory
     * This will often happen if the entity gets stuck while pathing.
     *
     * @param level The level in which the entity exists
     * @param mob The mob that this behaviour is tied to
     * @param memoryExpiration The expiration on the current memory
     */
    protected void stop(ServerLevel level, Mob mob, long memoryExpiration) {
        if (mob.getBrain().hasMemoryValue(MemoryModuleType.WALK_TARGET) && !reachedTarget(mob, mob.getBrain().getMemory(MemoryModuleType.WALK_TARGET).get()) && mob.getNavigation().isStuck()) {
            remainingCooldown = level.getRandom().nextInt(40);
        }

        mob.getNavigation().stop();
        mob.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        mob.getBrain().eraseMemory(MemoryModuleType.PATH);
        path = null;
    }

    /**
     * Begins the execution of this memory.
     * Sets the memory into the entity's brain and begins the navigation.
     *
     * @param level The level that this entity is in
     * @param mob The mob that this behaviour is tied to
     * @param memoryExpiration The expiration on the current memory
     */
    protected void start(ServerLevel level, Mob mob, long memoryExpiration) {
        mob.getBrain().setMemory(MemoryModuleType.PATH, path);
        mob.getNavigation().moveTo(path, (double) speedModifier);
    }

    /**
     * Function that processes this behaviour every update.
     * Updates the entity's navigation
     *
     * @param level The level that this entity is in
     * @param mob The mob that this behaviour is tied to
     * @param memoryExpiration The expiration on the current memory
     */
    protected void tick(ServerLevel level, Mob mob, long memoryExpiration) {
        Path path = mob.getNavigation().getPath();
        Brain<?> brain = mob.getBrain();
        if (this.path != path) {
            this.path = path;
            brain.setMemory(MemoryModuleType.PATH, path);
        }

        if (path != null && lastTargetPos != null) {
            WalkTarget jumpTarget = brain.getMemory(MemoryModuleType.WALK_TARGET).get();
            if (jumpTarget.getTarget().currentBlockPosition().distSqr(lastTargetPos) > 4.0D && tryComputePath(mob, jumpTarget, level.getGameTime())) {
                lastTargetPos = jumpTarget.getTarget().currentBlockPosition();
                this.start(level, mob, memoryExpiration);
            }

        }
    }

    /**
     * Attempts to compute a path to a desired position.
     * Will erase any memory on unreachable targets once the target has been reached
     *
     * @param mob The mob that this behaviour is tied to
     * @param memoryType the memory we are computing this path for
     * @param memoryExpiration The expiration on the current memory
     * @return Whether we were able to compute a path or not
     */
    private boolean tryComputePath(Mob mob, WalkTarget memoryType, long memoryExpiration) {
        BlockPos blockpos = memoryType.getTarget().currentBlockPosition();
        path = mob.getNavigation().createPath(blockpos, 0);
        speedModifier = memoryType.getSpeedModifier();
        Brain<?> brain = mob.getBrain();
        if (reachedTarget(mob, memoryType)) {
            brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        } else {
            boolean flag = path != null && path.canReach();
            if (flag) {
                brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            } else if (!brain.hasMemoryValue(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                brain.setMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, memoryExpiration);
            }

            if (path != null) {
                return true;
            }

            Vec3 vec3 = DefaultRandomPos.getPosTowards((PathfinderMob)mob, 10, 7, Vec3.atBottomCenterOf(blockpos), (double)((float)Math.PI / 2F));
            if (vec3 != null) {
                path = mob.getNavigation().createPath(vec3.x, vec3.y, vec3.z, 0);
                return path != null;
            }
        }

        return false;
    }

    /**
     * Determines whether the target has been reached
     *
     * @param mob The mob that this behaviour is tied to
     * @param memoryType the memory we are computing this path for
     * @return Whether the target was reached
     */
    private boolean reachedTarget(Mob mob, WalkTarget memoryType) {
        return memoryType.getTarget().currentBlockPosition().distManhattan(mob.blockPosition()) <= memoryType.getCloseEnoughDist();
    }
}
