package com.deku.cherryblossomgrotto.common.entity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> TANOOKI_HUNT_TARGETS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MOD_ID, "tanooki_hunt_targets"));
}
