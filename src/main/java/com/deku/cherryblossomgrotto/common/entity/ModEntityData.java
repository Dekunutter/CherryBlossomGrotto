package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModEntityData {
    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":mod_boat_entity")
    public static EntityType<ModBoatEntity> MOD_BOAT_DATA;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":kunai_entity")
    public static EntityType<KunaiEntity> KUNAI_DATA;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":shuriken_entity")
    public static EntityType<ShurikenEntity> SHURIKEN_DATA;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":koi_entity")
    public static EntityType<KoiEntity> KOI_DATA;
}
