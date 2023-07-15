package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeInitializer {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);

    public static ResourceKey<Biome> CHERRY_BLOSSOM_GROTTO = registerBiomeKey("cherry_blossom_grotto");

    public static ResourceKey<Biome> CHERRY_BLOSSOM_SLOPES = registerBiomeKey("cherry_blossom_slopes");

    public static ResourceKey<Biome> CHERRY_BLOSSOM_BAMBOO_JUNGLE = registerBiomeKey("cherry_blossom_bamboo_jungle");

    public static ResourceKey<Biome> MAPLE_WOODS = registerBiomeKey("maple_woods");

    public static ResourceKey<Biome> OAK_AND_MAPLE_FOREST = registerBiomeKey("oak_and_maple_forest");

    public static ResourceKey<Biome> BLACK_PINE_FOREST = registerBiomeKey("black_pine_forest");

    public static ResourceKey<Biome> STONE_FOREST = registerBiomeKey("stone_forest");

    public static ResourceKey<Biome> HINOKI_FOREST = registerBiomeKey("hinoki_forest");

    public static ResourceKey<Biome> OLD_GROWTH_FIR_FOREST = registerBiomeKey("old_growth_fir_forest");

    /**
     * Registers the biome into the vanilla game by the biome registry
     *
     * @param biomeName The registry name of the biome
     * @return The registered key for the custom biome
     */
    public static ResourceKey<Biome> registerBiomeKey(String biomeName) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(MOD_ID, biomeName));
    }

    /**
     * Bootstraps the context needed to register the biomes for the mod
     *
     * @param context Bootstrap context needed to register biomes to the game
     */
    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placementGetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(CHERRY_BLOSSOM_GROTTO, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(CHERRY_BLOSSOM_SLOPES, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(CHERRY_BLOSSOM_BAMBOO_JUNGLE, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(MAPLE_WOODS, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(OAK_AND_MAPLE_FOREST, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(BLACK_PINE_FOREST, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(STONE_FOREST, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(HINOKI_FOREST, OverworldBiomes.theVoid(placementGetter, carverGetter));
        context.register(OLD_GROWTH_FIR_FOREST, OverworldBiomes.theVoid(placementGetter, carverGetter));
    }

    /**
     * Registers all custom biomes into the overworld so that they actually show up during world generation.
     * The weight determines the commonality of the biome.
     */
    public static void registerBiomes() {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_GROTTO, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_SLOPES, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_BAMBOO_JUNGLE, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(MAPLE_WOODS, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(OAK_AND_MAPLE_FOREST, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(BLACK_PINE_FOREST, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(HINOKI_FOREST, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(OLD_GROWTH_FIR_FOREST, 1));
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(STONE_FOREST, 1));
    }
}
