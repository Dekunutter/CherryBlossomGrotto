package com.deku.cherryblossomgrotto.common.entity.item;

import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("cherryblossomgrotto")
public class ModEntityData {
    @ObjectHolder("mod_boat_entity")
    public static EntityType<ModBoatEntity> MOD_BOAT_DATA;
}
