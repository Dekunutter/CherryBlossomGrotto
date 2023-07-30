package com.deku.eastwardjourneys.common.entity;

import com.deku.eastwardjourneys.common.entity.animal.tanooki.Tanooki;
import com.deku.eastwardjourneys.common.entity.monster.terracotta_warrior.TerracottaWarrior;
import com.deku.eastwardjourneys.common.entity.vehicle.ModBoatEntity;
import com.deku.eastwardjourneys.common.entity.passive.fish.KoiEntity;
import com.deku.eastwardjourneys.common.entity.projectile.KunaiEntity;
import com.deku.eastwardjourneys.common.entity.projectile.ShurikenEntity;
import com.deku.eastwardjourneys.common.entity.vehicle.ModChestBoatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModEntityTypeInitializer {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT_ENTITY_TYPE = ENTITY_TYPES.register("mod_boat_entity", () ->
        EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
            .setCustomClientFactory(ModBoatEntity::new)
            .sized(1.375f, 0.5625f)
            .clientTrackingRange(10)
            .build(new ResourceLocation(MOD_ID, "mod_boat_entity").toString())
    );

    public static final RegistryObject<EntityType<ModChestBoatEntity>> CHEST_BOAT_ENTITY_TYPE = ENTITY_TYPES.register("mod_chest_boat_entity", () ->
        EntityType.Builder.<ModChestBoatEntity>of(ModChestBoatEntity::new, MobCategory.MISC)
            .setCustomClientFactory(ModChestBoatEntity::new)
            .sized(1.375f, 0.5625f)
            .clientTrackingRange(10)
            .build(new ResourceLocation(MOD_ID, "mod_chest_boat_entity").toString())
    );

    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI_ENTITY_TYPE = ENTITY_TYPES.register("kunai_entity", () ->
        EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
            .setCustomClientFactory(KunaiEntity::new)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(new ResourceLocation(MOD_ID, "kunai_entity").toString())
    );

    public static final RegistryObject<EntityType<ShurikenEntity>> SHURIKEN_ENTITY_TYPE = ENTITY_TYPES.register("shuriken_entity", () ->
        EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC)
            .setCustomClientFactory(ShurikenEntity::new)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(20)
            .build(new ResourceLocation(MOD_ID, "shuriken_entity").toString())
    );

    public static final RegistryObject<EntityType<KoiEntity>> KOI_ENTITY_TYPE = ENTITY_TYPES.register("koi_entity", () ->
        EntityType.Builder.<KoiEntity>of(KoiEntity::new, MobCategory.WATER_AMBIENT)
            .sized(0.7f, 0.4f)
            .clientTrackingRange(4)
            .build(new ResourceLocation(MOD_ID, "koi_entity").toString())
    );

    public static final RegistryObject<EntityType<Tanooki>> TANOOKI_ENTITY_TYPE = ENTITY_TYPES.register("tanooki", () ->
        EntityType.Builder.<Tanooki>of(Tanooki::new, MobCategory.CREATURE)
            .sized(1.0f, 1.0f)
            .clientTrackingRange(8)
            .build(new ResourceLocation(MOD_ID, "tanooki").toString())
    );

    public static final RegistryObject<EntityType<TerracottaWarrior>> TERRACOTTA_WARRIOR_ENTITY_TYPE = ENTITY_TYPES.register("terracotta_warrior", () ->
        EntityType.Builder.<TerracottaWarrior>of(TerracottaWarrior::new, MobCategory.MONSTER)
            .sized(1.0f, 2.0f)
            .clientTrackingRange(8)
            .build(new ResourceLocation(MOD_ID, "terracotta_warrior").toString())
    );
}
