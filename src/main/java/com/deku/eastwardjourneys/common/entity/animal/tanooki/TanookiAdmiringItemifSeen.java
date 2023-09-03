package com.deku.eastwardjourneys.common.entity.animal.tanooki;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;

// NOTE: Based on the piglin item admiration behaviour
public class TanookiAdmiringItemifSeen {
    /**
     * Builds the behaviour that will cause the entity to admire an item given particular criteria.
     * Calls into the entity's memory banks to see if a nearby item grabs its attention.
     * If this behaviour determines that the item is worth the entity's attention, then the item admiration memory begins but is given an expiry
     *
     * @param expiry The time after which this behaviour will end
     * @return Whether the entity will admire an item
     */
    public static BehaviorControl<LivingEntity> create(int expiry) {
        return BehaviorBuilder.create((behaviour) -> {
            return behaviour.group(behaviour.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), behaviour.absent(MemoryModuleType.ADMIRING_ITEM), behaviour.absent(MemoryModuleType.ADMIRING_DISABLED), behaviour.absent(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM)).apply(behaviour, (nearestItemMemory, admiringItemMemory, disabledAdmiringMemory, stopWalkingMemory) -> {
                // TODO: Really not sure what some of these variables are since they are unused in this case
                return (p_260130_, entity, p_259235_) -> {
                    ItemEntity itementity = behaviour.get(nearestItemMemory);
                    if (!TanookiAI.isLovedItem(itementity.getItem())) {
                        return false;
                    } else {
                        admiringItemMemory.setWithExpiry(true, (long)expiry);
                        return true;
                    }
                };
            });
        });
    }
}
