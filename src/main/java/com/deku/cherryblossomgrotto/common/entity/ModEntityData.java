package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.vehicle.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.passive.fish.KoiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModEntityData {
    @ObjectHolder("mod_boat_entity")
    public static EntityType<ModBoatEntity> MOD_BOAT_DATA;

    @ObjectHolder("kunai_entity")
    public static EntityType<KunaiEntity> KUNAI_DATA;

    @ObjectHolder("shuriken_entity")
    public static EntityType<ShurikenEntity> SHURIKEN_DATA;

    @ObjectHolder("koi_entity")
    public static EntityType<KoiEntity> KOI_DATA;
}
