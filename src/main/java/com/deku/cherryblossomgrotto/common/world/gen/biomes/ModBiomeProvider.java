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
        super(new ResourceLocation(MOD_ID, "biome_provider"), RegionType.OVERWORLD, 2);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        addBiomeSimilar(mapper, Biomes.FOREST, ModBiomeInitializer.CHERRY_BLOSSOM_GROTTO);
    }
}
