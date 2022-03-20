package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.item.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

// NOTE: This may seem redundant due to ModEntityData already existing as an object holder class but those objects are only initialized on registration
//  Since there are cases where we need entity types BEFORE all objects are registered, we have an alternative storage used during initialization for reading them
public class EntityTypeInitializer {
    public static EntityType<Entity> BOAT_ENTITY_TYPE = EntityType.Builder.of(ModBoatEntity::new, EntityClassification.MISC).setCustomClientFactory(ModBoatEntity::new).sized(1.375f, 0.5625f).clientTrackingRange(10).build("mod_boat_entity");

    public static EntityType<Entity> KUNAI_ENTITY_TYPE = EntityType.Builder.of(KunaiEntity::new, EntityClassification.MISC).setCustomClientFactory(KunaiEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("kunai_entity");

    public static EntityType<Entity> SHURIKEN_ENTITY_TYPE = EntityType.Builder.of(ShurikenEntity::new, EntityClassification.MISC).setCustomClientFactory(ShurikenEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("shuriken_entity");

    public static EntityType<KoiEntity> KOI_ENTITY_TYPE = EntityType.Builder.<KoiEntity>of(KoiEntity::new, EntityClassification.WATER_AMBIENT).sized(0.7f, 0.4f).clientTrackingRange(4).build("koi_entity");
}
