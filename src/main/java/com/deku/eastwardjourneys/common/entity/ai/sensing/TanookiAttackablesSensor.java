package com.deku.eastwardjourneys.common.entity.ai.sensing;

import com.deku.eastwardjourneys.common.entity.ModEntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;

// NOTE: Based on the Axolotl attack sensor
public class TanookiAttackablesSensor extends NearestVisibleLivingEntitySensor {
    public static final float TARGET_DETECTION_DISTANCE = 8.0F;

    /**
     * Checks if the target is valid for attacking
     *
     * @param entity The entity that will be performing the attack
     * @param target The potential target of the attack
     * @return Whether the given target is valid for attacking
     */
    @Override
    protected boolean isMatchingEntity(LivingEntity entity, LivingEntity target) {
        if (isClose(entity, target)) {
            if (isHostileTarget(target) || isHuntTarget(entity, target)) {
                if (Sensor.isEntityAttackable(entity, target)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the memory associated to this sensor.
     *
     * @return Memory of this sensor
     */
    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }

    /**
     * If the given target is one that is huntable for the entity.
     * This not only considers if the entity considers the target prey, but also ensures that its hunting action is on cooldown
     *
     * @param entity The entity that will be performing the attack if valid
     * @param target The potential target of the attack
     * @return Whether the target is considered a huntable entity
     */
    private boolean isHuntTarget(LivingEntity entity, LivingEntity target) {
        return !entity.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().is(ModEntityTypeTags.TANOOKI_HUNT_TARGETS);
    }

    /**
     * If the given entity is a hostile target of the entity performing the attack
     * NOTE: No hostile targets exist for the tanooki currently, just hunt targets, so this returns false
     *
     * @param entity The potential target of the attack
     * @return
     */
    private boolean isHostileTarget(LivingEntity entity) {
        return false;
    }

    /**
     * If the entity is close enough to be considered an target for attacks
     *
     * @param entity The entity that will be performing the attack if valid
     * @param target The potential target of the attack
     * @return Whether the target is within the right range to the entity
     */
    private boolean isClose(LivingEntity entity, LivingEntity target) {
        return target.distanceToSqr(entity) <= 64.0D;
    }
}
