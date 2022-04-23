package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeTags extends TagsProvider<Biome> {
    public static final TagKey<Biome> HAS_GIANT_BUDDHA = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/giant_buddha"));
    public static final TagKey<Biome> HAS_TORII_GATE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/torii_gate"));
    public static final TagKey<Biome> HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, "has_structure/village_cherry_blossom_grotto"));

    public ModBiomeTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, BuiltinRegistries.BIOME, MOD_ID, helper);
    }

    // TODO: Do the new has_structure json files make all this redundant? Same for configured structure features. If we declare those in json do we need any of this logic or the logic in that initializer that uses these tags?
    @Override
    protected void addTags() {
        tag(HAS_GIANT_BUDDHA).add(Biomes.BAMBOO_JUNGLE).add(Biomes.JUNGLE).add(Biomes.SPARSE_JUNGLE).add(Biomes.SNOWY_SLOPES).add(Biomes.SNOWY_TAIGA).add(Biomes.OLD_GROWTH_SPRUCE_TAIGA).add(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO);
        tag(HAS_TORII_GATE).add(Biomes.BAMBOO_JUNGLE).add(Biomes.SNOWY_SLOPES).add(Biomes.BEACH).add(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO);
        tag(HAS_CHERRY_BLOSSOM_GROTTO_VILLAGE).add(ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO);
    }

    @Override
    public String getName() {
        return "CherryBlossomGrotto Tags";
    }
}
