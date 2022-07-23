package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeTags {
    public static final TagKey<Biome> HAS_GIANT_BUDDHA = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/giant_buddha"));
    public static final TagKey<Biome> HAS_TORII_GATE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/torii_gate"));
    public static final TagKey<Biome> HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/village_cherry_blossom_grotto"));
    public static final TagKey<Biome> HAS_RUINED_TORII_PORTAL = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/ruined_torii_portal"));
}
