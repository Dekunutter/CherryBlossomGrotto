package com.deku.cherryblossomgrotto.common.entity.ai.sensing;

import com.deku.cherryblossomgrotto.common.entity.ModEntityTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;

// NOTE: Based on the Axolotl attack sensor
public class TerracottaWarriorAttackablesSensor extends NearestVisibleLivingEntitySensor {
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
            if (isHostileTarget(target)) {
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
     * If the given entity is a hostile target of the entity performing the attack
     * NOTE: No hostile targets exist for the tanooki currently, just hunt targets, so this returns false
     *
     * @param entity The potential target of the attack
     * @return
     */
    private boolean isHostileTarget(LivingEntity entity) {
        return entity.getType() == EntityType.PLAYER || entity.getType().is(ModEntityTypeTags.TERRACOTTA_WARRIOR_TARGETS);
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
