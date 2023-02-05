package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.animal.tanooki.Tanooki;
import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import com.deku.cherryblossomgrotto.common.entity.vehicle.ModChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModEntityType {
    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":mod_boat_entity")
    public static EntityType<ModBoatEntity> MOD_BOAT;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":mod_chest_boat_entity")
    public static EntityType<ModChestBoatEntity> MOD_CHEST_BOAT;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":kunai_entity")
    public static EntityType<KunaiEntity> KUNAI;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":shuriken_entity")
    public static EntityType<ShurikenEntity> SHURIKEN;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":koi_entity")
    public static EntityType<KoiEntity> KOI;

    @ObjectHolder(registryName = "minecraft:entity_type", value = MOD_ID + ":tanooki")
    public static EntityType<Tanooki> TANOOKI;
}
