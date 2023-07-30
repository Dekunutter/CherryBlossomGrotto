package com.deku.eastwardjourneys.common.entity.animal.tanooki;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;

public class TanookiStopAdmiringItemifTiredOfTryingToReach {
    /**
     * Builds the behaviour that will stop the entity from admiring an item given particular criteria.
     * Calls into the entity's memory banks to see if a nearby item is still holding it's attention.
     * Then checks if that item is still reachable.
     * It is deemed unreachable if the entity has spent too long trying to reach the item and failing, or the item has disappeared from the world (entity holding it put it away or it despawned).
     * If this behaviour determines that the item being admired is unreachable, then the memory of that item is erased from the entity and the entity remembers that it stopped this item chase.
     *
     * @param timeToReach The max time the entity should try to chase the desired item for
     * @param admirationExpiry How long the entity will be tired of admiring items for after failing to find this one
     * @return Whether the entity will stop admiring the item
     */
    public static BehaviorControl<LivingEntity> create(int timeToReach, int admirationExpiry) {
        return BehaviorBuilder.create((behaviour) -> {
            return behaviour.group(behaviour.present(MemoryModuleType.ADMIRING_ITEM), behaviour.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM), behaviour.registered(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM), behaviour.registered(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM)).apply(behaviour, (admiringItemMemory, eyeingItemMemory, tryingToReachItemMemory, satopWalkingToAdmireMemory) -> {
                // TODO: Really not sure what some of these variables are since they are unused in this case
                return (p_259044_, entity, p_259125_) -> {
                    if (!entity.getOffhandItem().isEmpty()) {
                        return false;
                    } else {
                        Optional<Integer> optional = behaviour.tryGet(tryingToReachItemMemory);
                        if (optional.isEmpty()) {
                            tryingToReachItemMemory.set(0);
                        } else {
                            int i = optional.get();
                            if (i > timeToReach) {
                                admiringItemMemory.erase();
                                tryingToReachItemMemory.erase();
                                satopWalkingToAdmireMemory.setWithExpiry(true, (long)admirationExpiry);
                            } else {
                                tryingToReachItemMemory.set(i + 1);
                            }
                        }

                        return true;
                    }
                };
            });
        });
    }
}
