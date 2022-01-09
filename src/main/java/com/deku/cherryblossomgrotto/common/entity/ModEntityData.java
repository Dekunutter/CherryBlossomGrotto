package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.entity.item.ModBoatEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.KunaiEntity;
import com.deku.cherryblossomgrotto.common.entity.projectile.ShurikenEntity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModEntityData {
    @ObjectHolder("mod_boat_entity")
    public static EntityType<ModBoatEntity> MOD_BOAT_DATA;

    @ObjectHolder("kunai_entity")
    public static EntityType<KunaiEntity> KUNAI_DATA;

    @ObjectHolder("shuriken_entity")
    public static EntityType<ShurikenEntity> SHURIKEN_DATA;
}
