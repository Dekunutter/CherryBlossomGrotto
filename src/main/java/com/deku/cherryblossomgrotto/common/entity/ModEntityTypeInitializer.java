package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModEntityTypeInitializer {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);

    public static final RegistryObject<EntityType<KunaiEntity>> KUNAI_ENTITY_TYPE = ENTITY_TYPES.register("kunai_entity", () ->
        EntityType.Builder.<KunaiEntity>of(KunaiEntity::new, MobCategory.MISC)
                .setCustomClientFactory(KunaiEntity::new)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(20)
                .build(new ResourceLocation(MOD_ID, "kunai_entity").toString()
                )
    );

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT_ENTITY_TYPE = ENTITY_TYPES.register("mod_boat_entity", () ->
            EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).setCustomClientFactory(ModBoatEntity::new).sized(1.375f, 0.5625f).clientTrackingRange(10).build("cherryblossomgrotto:mod_boat_entity")
    );

    public static final RegistryObject<EntityType<ShurikenEntity>> SHURIKEN_ENTITY_TYPE = ENTITY_TYPES.register("shuriken_entity", () ->
            EntityType.Builder.<ShurikenEntity>of(ShurikenEntity::new, MobCategory.MISC).setCustomClientFactory(ShurikenEntity::new).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("cherryblossomgrotto:shuriken_entity")
    );

    public static final RegistryObject<EntityType<KoiEntity>> KOI_ENTITY_TYPE = ENTITY_TYPES.register("koi_entity", () ->
            EntityType.Builder.<KoiEntity>of(KoiEntity::new, MobCategory.WATER_AMBIENT).sized(0.7f, 0.4f).clientTrackingRange(4).build("cherryblossomgrotto:koi_entity")
    );
}
