package com.deku.eastwardjourneys.common.world.gen.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.deku.eastwardjourneys.Main.MOD_ID;

// NOTE: This is essentially a copy of RuinedPortalStructure lazily done for my custom portal structure in cherry blossom grottos
public class RuinedToriiPortal extends Structure {
    // TODO: chest loot tables not working. Maybe need to add a ruined_portal loot table json to a directory somewhere? ruined_torii_portal wont work since I use ruined portal pieces
    private static final ResourceLocation STRUCTURE_LOCATION_PORTAL = new ResourceLocation(MOD_ID, "ruined_portal/ruined_torii_portal");

    private final List<RuinedToriiPortal.Setup> setups;
    public static final Codec<RuinedToriiPortal> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(settingsCodec(instance), ExtraCodecs.nonEmptyList(RuinedToriiPortal.Setup.CODEC.listOf()).fieldOf("setups").forGetter((portal) -> {
            return portal.setups;
        })).apply(instance, RuinedToriiPortal::new);
    });

    public RuinedToriiPortal(StructureSettings structureSettings, List<Setup> setupConfig) {
        super(structureSettings);
        this.setups = setupConfig;
    }

    public RuinedToriiPortal(StructureSettings structureSettings, Setup setupConfig) {
        this(structureSettings, List.of(setupConfig));
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext generationContext) {
        RuinedPortalPiece.Properties ruinedportalpiece$properties = new RuinedPortalPiece.Properties();
        WorldgenRandom worldgenrandom = generationContext.random();
        RuinedToriiPortal.Setup ruinedportalstructure$setup = null;
        if (this.setups.size() > 1) {
            float f = 0.0F;

            for(RuinedToriiPortal.Setup ruinedportalstructure$setup1 : this.setups) {
                f += ruinedportalstructure$setup1.weight();
            }

            float f1 = worldgenrandom.nextFloat();

            for(RuinedToriiPortal.Setup ruinedportalstructure$setup2 : this.setups) {
                f1 -= ruinedportalstructure$setup2.weight() / f;
                if (f1 < 0.0F) {
                    ruinedportalstructure$setup = ruinedportalstructure$setup2;
                    break;
                }
            }
        } else {
            ruinedportalstructure$setup = this.setups.get(0);
        }

        if (ruinedportalstructure$setup == null) {
            throw new IllegalStateException();
        } else {
            RuinedToriiPortal.Setup ruinedportalstructure$setup3 = ruinedportalstructure$setup;
            ruinedportalpiece$properties.airPocket = sample(worldgenrandom, ruinedportalstructure$setup3.airPocketProbability());
            ruinedportalpiece$properties.mossiness = ruinedportalstructure$setup3.mossiness();
            ruinedportalpiece$properties.overgrown = ruinedportalstructure$setup3.overgrown();
            ruinedportalpiece$properties.vines = ruinedportalstructure$setup3.vines();
            ruinedportalpiece$properties.replaceWithBlackstone = ruinedportalstructure$setup3.replaceWithBlackstone();
            ResourceLocation resourcelocation;

            resourcelocation = STRUCTURE_LOCATION_PORTAL;

            StructureTemplate structuretemplate = generationContext.structureTemplateManager().getOrCreate(resourcelocation);
            Rotation rotation = Util.getRandom(Rotation.values(), worldgenrandom);
            Mirror mirror = worldgenrandom.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
            BlockPos blockpos = new BlockPos(structuretemplate.getSize().getX() / 2, 0, structuretemplate.getSize().getZ() / 2);
            ChunkGenerator chunkgenerator = generationContext.chunkGenerator();
            LevelHeightAccessor levelheightaccessor = generationContext.heightAccessor();
            RandomState randomstate = generationContext.randomState();
            BlockPos blockpos1 = generationContext.chunkPos().getWorldPosition();
            BoundingBox boundingbox = structuretemplate.getBoundingBox(blockpos1, rotation, blockpos, mirror);
            BlockPos blockpos2 = boundingbox.getCenter();
            int i = chunkgenerator.getBaseHeight(blockpos2.getX(), blockpos2.getZ(), RuinedPortalPiece.getHeightMapType(ruinedportalstructure$setup3.placement()), levelheightaccessor, randomstate) - 1;
            int j = findSuitableY(worldgenrandom, chunkgenerator, ruinedportalstructure$setup3.placement(), ruinedportalpiece$properties.airPocket, i, boundingbox.getYSpan(), boundingbox, levelheightaccessor, randomstate);
            BlockPos blockpos3 = new BlockPos(blockpos1.getX(), j, blockpos1.getZ());
            return Optional.of(new Structure.GenerationStub(blockpos3, (p_229297_) -> {
                if (ruinedportalstructure$setup3.canBeCold()) {
                    ruinedportalpiece$properties.cold = isCold(blockpos3, generationContext.chunkGenerator().getBiomeSource().getNoiseBiome(QuartPos.fromBlock(blockpos3.getX()), QuartPos.fromBlock(blockpos3.getY()), QuartPos.fromBlock(blockpos3.getZ()), randomstate.sampler()));
                }

                p_229297_.addPiece(new RuinedPortalPiece(generationContext.structureTemplateManager(), blockpos3, ruinedportalstructure$setup3.placement(), ruinedportalpiece$properties, resourcelocation, structuretemplate, rotation, mirror, blockpos));
            }));
        }
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypeInitializer.RUINED_TORII_PORTAL.get();
    }

    private static boolean sample(WorldgenRandom generatorRandomizer, float airPocketProbability) {
        if (airPocketProbability == 0.0F) {
            return false;
        } else if (airPocketProbability == 1.0F) {
            return true;
        } else {
            return generatorRandomizer.nextFloat() < airPocketProbability;
        }
    }

    private static int findSuitableY(RandomSource randomSource, ChunkGenerator chunkGenerator, RuinedPortalPiece.VerticalPlacement verticalPlacement, boolean hasAirPocket, int baseHeight, int boundingBoxYSpan, BoundingBox boundingbox, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int j = heightAccessor.getMinBuildHeight() + 15;
        int i;
        if (verticalPlacement == RuinedPortalPiece.VerticalPlacement.IN_NETHER) {
            if (hasAirPocket) {
                i = Mth.randomBetweenInclusive(randomSource, 32, 100);
            } else if (randomSource.nextFloat() < 0.5F) {
                i = Mth.randomBetweenInclusive(randomSource, 27, 29);
            } else {
                i = Mth.randomBetweenInclusive(randomSource, 29, 100);
            }
        } else if (verticalPlacement == RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN) {
            int k = baseHeight - boundingBoxYSpan;
            i = getRandomWithinInterval(randomSource, 70, k);
        } else if (verticalPlacement == RuinedPortalPiece.VerticalPlacement.UNDERGROUND) {
            int j1 = baseHeight - boundingBoxYSpan;
            i = getRandomWithinInterval(randomSource, j, j1);
        } else if (verticalPlacement == RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
            i = baseHeight - boundingBoxYSpan + Mth.randomBetweenInclusive(randomSource, 2, 8);
        } else {
            i = baseHeight;
        }

        List<BlockPos> list1 = ImmutableList.of(new BlockPos(boundingbox.minX(), 0, boundingbox.minZ()), new BlockPos(boundingbox.maxX(), 0, boundingbox.minZ()), new BlockPos(boundingbox.minX(), 0, boundingbox.maxZ()), new BlockPos(boundingbox.maxX(), 0, boundingbox.maxZ()));
        List<NoiseColumn> list = list1.stream().map((p_229280_) -> {
            return chunkGenerator.getBaseColumn(p_229280_.getX(), p_229280_.getZ(), heightAccessor, randomState);
        }).collect(Collectors.toList());
        Heightmap.Types heightmap$types = verticalPlacement == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;

        int l;
        for(l = i; l > j; --l) {
            int i1 = 0;

            for(NoiseColumn noisecolumn : list) {
                BlockState blockstate = noisecolumn.getBlock(l);
                if (heightmap$types.isOpaque().test(blockstate)) {
                    ++i1;
                    if (i1 == 3) {
                        return l;
                    }
                }
            }
        }

        return l;
    }

    private static boolean isCold(BlockPos position, Holder<Biome> biomeHolder) {
        return biomeHolder.value().coldEnoughToSnow(position);
    }

    private static int getRandomWithinInterval(RandomSource randomSource, int lowerBound, int upperBound) {
        return lowerBound < upperBound ? Mth.randomBetweenInclusive(randomSource, lowerBound, upperBound) : upperBound;
    }

    public static record Setup(RuinedPortalPiece.VerticalPlacement placement, float airPocketProbability, float mossiness, boolean overgrown, boolean vines, boolean canBeCold, boolean replaceWithBlackstone, float weight) {
        public static final Codec<RuinedToriiPortal.Setup> CODEC = RecordCodecBuilder.create((p_229327_) -> {
            return p_229327_.group(RuinedPortalPiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(RuinedToriiPortal.Setup::placement), Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(RuinedToriiPortal.Setup::airPocketProbability), Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(RuinedToriiPortal.Setup::mossiness), Codec.BOOL.fieldOf("overgrown").forGetter(RuinedToriiPortal.Setup::overgrown), Codec.BOOL.fieldOf("vines").forGetter(RuinedToriiPortal.Setup::vines), Codec.BOOL.fieldOf("can_be_cold").forGetter(RuinedToriiPortal.Setup::canBeCold), Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(RuinedToriiPortal.Setup::replaceWithBlackstone), ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(RuinedToriiPortal.Setup::weight)).apply(p_229327_, RuinedToriiPortal.Setup::new);
        });
    }
}
