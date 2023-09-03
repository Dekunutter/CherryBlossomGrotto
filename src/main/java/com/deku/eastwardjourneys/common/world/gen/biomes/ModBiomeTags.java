package com.deku.eastwardjourneys.common.world.gen.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static com.deku.eastwardjourneys.Main.MOD_ID;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_GIANT_BUDDHA = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/giant_buddha"));
    public static final TagKey<Biome> HAS_TORII_GATE = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/torii_gate"));
    public static final TagKey<Biome> HAS_CHERRY_BLOSSOM_VILLAGE = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/village_cherry_blossom"));
    public static final TagKey<Biome> HAS_RUINED_TORII_PORTAL = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/ruined_torii_portal"));
    public static final TagKey<Biome> HAS_HOTSPRING = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/hotspring"));
    public static final TagKey<Biome> HAS_GAZEBO = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/gazebo"));
    public static final TagKey<Biome> HAS_GREAT_WALL = TagKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, "has_structure/great_wall"));
}
