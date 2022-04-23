package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import com.deku.cherryblossomgrotto.Main;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeInitializer {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MOD_ID);

    static {
        createBiome("cherry_blossom_grotto", OverworldBiomes::theVoid);
    }

    public static ResourceKey<Biome> CHERRY_BLOSSOM_GROTTO = registerBiome("cherry_blossom_grotto");

    /**
     * Registers the biome into the vanilla game by the biome registry
     *
     * @param biomeName The registry name of the biome
     * @return The registered key for the custom biome
     */
    public static ResourceKey<Biome> registerBiome(String biomeName) {
        return ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MOD_ID, biomeName));
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
        Main.LOGGER.info("HELLO from Register Biomes");
        Main.LOGGER.info(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL));
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(CHERRY_BLOSSOM_GROTTO, 1));
        Main.LOGGER.info(BiomeManager.getBiomes(BiomeManager.BiomeType.COOL).get(3));
    }
}
