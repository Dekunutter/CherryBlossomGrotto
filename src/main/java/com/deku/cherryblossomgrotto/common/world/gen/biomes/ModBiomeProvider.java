package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeProvider extends Region {
    public ModBiomeProvider() {
        // Adding low region weight since we only have one biome. Don't want it to be too common
        super(new ResourceLocation(MOD_ID, "eastern_region"), RegionType.OVERWORLD, 2);
    }

    /**
     * Adds biomes to a new region for this world
     * Ensures the new region is filled with vanilla biomes before effectively replacing flower forests with cherry blossom grottos
     *
     * @param registry Registry that the biome is registered with
     * @param mapper Pairs of climate parameters mapped to biome resource keys
     */
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        // TODO: REALLY COOL Sometimes. Generates on mountain slopes and tops which can lead to awesome cherry blossom covered mountains around valleys
        /*addModifiedVanillaOverworldBiomes(mapper, builder -> {
            List<Climate.ParameterPoint> cherryBlossomGrottoClimate = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.NEUTRAL, ParameterUtils.Temperature.WARM)
                    .humidity(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.WET, ParameterUtils.Humidity.HUMID)
                    .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.COAST, ParameterUtils.Continentalness.FAR_INLAND), ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.MID_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                    .erosion(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_1)
                    .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
                    .weirdness(ParameterUtils.Weirdness.HIGH_SLICE_VARIANT_ASCENDING, ParameterUtils.Weirdness.PEAK_VARIANT, ParameterUtils.Weirdness.HIGH_SLICE_VARIANT_DESCENDING)
                    .build();

            cherryBlossomGrottoClimate.forEach(point -> builder.replaceBiome(point, ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO));
        });*/

        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            builder.replaceBiome(Biomes.FLOWER_FOREST, ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO);
            builder.replaceBiome(Biomes.GROVE, ModBiomeInitializer.CHERRY_BLOSSOM_SLOPES);
            builder.replaceBiome(Biomes.SPARSE_JUNGLE, ModBiomeInitializer.CHERRY_BLOSSOM_BAMBOO_JUNGLE);
            builder.replaceBiome(Biomes.FOREST, ModBiomeInitializer.MAPLE_WOODS);
            builder.replaceBiome(Biomes.BIRCH_FOREST, ModBiomeInitializer.OAK_AND_MAPLE_FOREST);
        });
    }
}
