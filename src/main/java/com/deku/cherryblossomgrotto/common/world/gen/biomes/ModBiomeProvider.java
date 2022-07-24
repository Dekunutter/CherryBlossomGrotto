package com.deku.cherryblossomgrotto.common.world.gen.biomes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.worldgen.RegionUtils;

import java.util.List;
import java.util.function.Consumer;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBiomeProvider extends Region {
    public ModBiomeProvider() {
        super(new ResourceLocation(MOD_ID, "biome_provider"), RegionType.OVERWORLD, 10);
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

        // TODO: Only loads on mountain peaks, would be good for a snowy variant of the biome
        // Erosion is the higher the value, the flatter the terrian. Use smaller values for mountainous regions
        /*addModifiedVanillaOverworldBiomes(mapper, builder -> {
            List<Climate.ParameterPoint> cherryBlossomGrottoClimate = new ParameterUtils.ParameterPointListBuilder()
                    .temperature(ParameterUtils.Temperature.NEUTRAL, ParameterUtils.Temperature.WARM)
                    .humidity(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.WET, ParameterUtils.Humidity.HUMID)
                    .continentalness(ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.COAST, ParameterUtils.Continentalness.FAR_INLAND), ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.MID_INLAND, ParameterUtils.Continentalness.FAR_INLAND))
                    .erosion(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_1)
                    .depth(ParameterUtils.Depth.SURFACE)
                    .weirdness(ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.PEAK_NORMAL, ParameterUtils.Weirdness.LOW_SLICE_NORMAL_DESCENDING, ParameterUtils.Weirdness.VALLEY, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING)
                    .build();

            cherryBlossomGrottoClimate.forEach(point -> builder.replaceBiome(point, ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO));
        });*/

        addModifiedVanillaOverworldBiomes(mapper, builder -> {
            List<Climate.ParameterPoint> flowerForestPoints = RegionUtils.getVanillaParameterPoints(Biomes.FLOWER_FOREST).stream().collect(ImmutableList.toImmutableList());
            flowerForestPoints.forEach(point -> builder.replaceBiome(point, ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO));

            List<Climate.ParameterPoint> jaggedPeaksPoints = RegionUtils.getVanillaParameterPoints(Biomes.JAGGED_PEAKS).stream().collect(ImmutableList.toImmutableList());
            jaggedPeaksPoints.forEach(point -> builder.replaceBiome(point, ModBiomeInitializer.STONE_FOREST));

        });
    }
}
