package com.deku.cherryblossomgrotto.common.entity.animal.tanooki;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.Optional;

// NOTE: Based on the piglin item admiration behaviour
public class TanookiStopAdmiringItemIfTooFarAway<E extends Tanooki> {
    /**
     * Builds the behaviour that will stop the entity from admiring an item given particular criteria.
     * Calls into the entity's memory banks to see if a nearby entity with its attention is still holding an item it has interest in.
     * Also checks if a nearby item within the world is still within a reasonable distance.
     * If this behaviour determines that the item being admired is now too far away, then the memory of that item is erased from the entity.
     *
     * @param maxDistance The max distance within which the entity will admire an item held by a nearby entity
     * @return Whether the entity will stop admiring the item
     */
    public static BehaviorControl<LivingEntity> create(int maxDistance) {
        return BehaviorBuilder.create((behaviour) -> {
            return behaviour.group(behaviour.present(MemoryModuleType.ADMIRING_ITEM), behaviour.registered(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)).apply(behaviour, (admiringItemMemory, desiringItemMemory) -> {
                // TODO: Really not sure what some of these variables are since they are unused in this case
                return (p_259613_, entity, p_259748_) -> {
                    if (!entity.getOffhandItem().isEmpty()) {
                        return false;
                    } else {
                        Optional<ItemEntity> optional = behaviour.tryGet(desiringItemMemory);
                        if (optional.isPresent() && optional.get().closerThan(entity, (double)maxDistance)) {
                            return false;
                        } else {
                            admiringItemMemory.erase();
                            return true;
                        }
                    }
                };
            });
        });
    }
}
