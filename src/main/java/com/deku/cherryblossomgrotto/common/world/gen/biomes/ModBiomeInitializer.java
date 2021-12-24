package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeMaker;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeInitializer {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);

    static {
        createBiome("cherry_blossom_grotto", BiomeMaker::theVoidBiome);
        //createBiome("cherry_blossom_cliffside", BiomeMaker::theVoidBiome);
    }

    public static RegistryKey<Biome> CHERRY_BLOSSOM_GROTTO = registerBiome("cherry_blossom_grotto");
    //public static RegistryKey<Biome> CHERRY_BLOSSOM_CLIFFSIDE = registerBiome("cherry_blossom_cliffside");

    /**
     * Registers the biome into the vanilla game by the biome registry
     *
     * @param biomeName The registry name of the biome
     * @return The registered key for the custom biome
     */
    public static RegistryKey<Biome> registerBiome(String biomeName) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, biomeName));
    }

    /**
     * Initializes the custom biomes by registering them into the biome deferred register
     *
     * @param biomeName The registry name of the biome
     * @param biome Supplier containing the biome to be registered
     * @return The registered object containing the biome
     */
    public static RegistryObject<Biome> createBiome(String biomeName, Supplier<Biome> biome) {
        return BIOMES.register(biomeName, biome);
    }

    /**
     * Registers all custom biomes into the overworld so that they actually show up during world generation.
     * The weight determines the commonality of the biome.
     */
    public static void registerBiomes() {
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_GROTTO, 1));
        //BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_CLIFFSIDE, 1));
    }
}
