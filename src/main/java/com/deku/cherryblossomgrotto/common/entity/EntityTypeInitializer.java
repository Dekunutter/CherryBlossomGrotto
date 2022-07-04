package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import com.deku.cherryblossomgrotto.common.entity.vehicle.ModChestBoatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

// NOTE: This may seem redundant due to ModEntityData already existing as an object holder class but those objects are only initialized on registration
//  Since there are cases where we need entity types BEFORE all objects are registered, we have an alternative storage used during initialization for reading them
public class EntityTypeInitializer {
    public static EntityType<Entity> BOAT_ENTITY_TYPE = EntityType.Builder.of(ModBoatEntity::new, MobCategory.MISC).setCustomClientFactory(ModBoatEntity::new).sized(1.375f, 0.5625f).clientTrackingRange(10).build("mod_boat_entity");

    public static EntityType<Entity> CHEST_BOAT_ENTITY_TYPE = EntityType.Builder.of(ModChestBoatEntity::new, MobCategory.MISC).setCustomClientFactory(ModChestBoatEntity::new).sized(1.375f, 0.5625f).clientTrackingRange(10).build("mod_chest_boat_entity");

    public static EntityType<Entity> KUNAI_ENTITY_TYPE = EntityType.Builder.of(KunaiEntity::new, MobCategory.MISC).setCustomClientFactory(KunaiEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("kunai_entity");

    public static EntityType<Entity> SHURIKEN_ENTITY_TYPE = EntityType.Builder.of(ShurikenEntity::new, MobCategory.MISC).setCustomClientFactory(ShurikenEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("shuriken_entity");

    public static EntityType<KoiEntity> KOI_ENTITY_TYPE = EntityType.Builder.of(KoiEntity::new, MobCategory.WATER_AMBIENT).sized(0.7f, 0.4f).clientTrackingRange(4).build("koi_entity");
}
