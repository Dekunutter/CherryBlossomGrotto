package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.deku.cherryblossomgrotto.common.features.template.ModProcessorLists;
import com.deku.cherryblossomgrotto.common.world.gen.placements.ModVillagePlacements;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class CherryBlossomGrottoVillagePools {
    public static final Holder<StructureTemplatePool> START = Pools.register(
        new StructureTemplatePool(
            new ResourceLocation( "village/cherry_blossom_grotto/town_centers"),
            new ResourceLocation("empty"),
            ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/town_centers/well", ProcessorLists.MOSSIFY_20_PERCENT), 50),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/town_centers/cherry_tree", ProcessorLists.MOSSIFY_20_PERCENT), 50),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/town_centers/torii_gates"), 50),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/town_centers/open_air_shrine"), 50),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/town_centers/koi_pond"), 35)
            ),
                StructureTemplatePool.Projection.RIGID
        )
    );

    /**
     * Bootstrapping method. Does nothing except acts as an invocation point to force the static configuration to load into memory
     */
    public static void bootstrap() {

    }

    /**
     * Static initializer for all the jigsaw patterns needed to generate the village
     */
    static {
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/streets"),
                new ResourceLocation("village/cherry_blossom_grotto/terminators"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/corner_01", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/corner_02", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/corner_03", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_01", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 4),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_02", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 4),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_03", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 7),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_04", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 7),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_05", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 3),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/straight_06", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 4),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_01", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_02", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_03", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_04", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_05", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/crossroad_06", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/streets/turn_01", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 3)
                ),
                    StructureTemplatePool.Projection.TERRAIN_MATCHING
            )
        );

        // NOTE: Jigsaw blocks for sub-components to houses DO NOT WORK in village generation. The entire building needs to be in a single structure.
        //  I'm not sure why this is since I thought if the generation wasnt at its final level it would work but seems village generation cuts buildings off as the final level
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/houses"),
                new ResourceLocation("village/cherry_blossom_grotto/terminators"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/armoury"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/cartography"), 5),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/archery", ModProcessorLists.STONE_BRICKS_CRACKED_20_PERCENT), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/butcher"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/caligraphy_hut"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/masonary"), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/shrine"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/sushi_shop", ModProcessorLists.STONE_BRICKS_CRACKED_20_PERCENT), 5),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_1"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_2"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_3"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_4"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_5"), 3),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/small_house_6"), 3),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/medium_house_1"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/medium_house_2"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/large_house_1"), 2),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/rice_paddies"), 4),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/beetroot_farm"), 4),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/animal_pen"), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/animal_pen_large"), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/houses/lilac_box"), 1),
                    Pair.of(StructurePoolElement.empty(), 10)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/terminators"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/terminators/terminator_01", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/terminators/terminator_02", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/terminators/terminator_03", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 1),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/terminators/terminator_04", ModProcessorLists.STREET_CHERRY_BLOSSOM_GROTTO), 1)
                ),
                StructureTemplatePool.Projection.TERRAIN_MATCHING)
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/trees"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.feature(ModVillagePlacements.CHERRY_BLOSSOM_TREE_VILLAGE), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/decor"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/decor/zen_lantern"), 2),
                    Pair.of(StructurePoolElement.feature(ModVillagePlacements.CHERRY_BLOSSOM_TREE_VILLAGE), 1),
                    Pair.of(StructurePoolElement.feature(ModVillagePlacements.FLOWER_FOREST_VILLAGE), 1),
                    Pair.of(StructurePoolElement.feature(VillagePlacements.PILE_HAY_VILLAGE), 1),
                    Pair.of(StructurePoolElement.empty(), 2)),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/plains/villagers"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/plains/villagers/nitwit"), 1),
                    Pair.of(StructurePoolElement.legacy("village/plains/villagers/baby"), 1),
                    Pair.of(StructurePoolElement.legacy("village/plains/villagers/unemployed"), 10)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/common/animals"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cows_1"), 7),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/pigs_1"), 7),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/horses_1"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/horses_2"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/horses_3"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/horses_4"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/horses_5"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_1"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_2"), 1),
                    Pair.of(StructurePoolElement.empty(), 5)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/common/sheep"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_1"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_2"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/common/cats"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_black"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_british"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_calico"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_persian"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_ragdoll"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_red"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_siamese"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_tabby"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_white"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cat_jellie"), 1),
                    Pair.of(StructurePoolElement.empty(), 3)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/fish"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/fish/koi"), 10),
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/fish/tropical_fish"), 1)),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/common/butcher_animals"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/common/animals/cows_1"), 3),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/pigs_1"), 3),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_1"), 1),
                    Pair.of(StructurePoolElement.legacy("village/common/animals/sheep_2"), 1)),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/common/iron_golem"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/common/iron_golem"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/well_bottoms"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/well_bottoms/well_bottom"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation("village/cherry_blossom_grotto/pond_bottoms"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy("village/cherry_blossom_grotto/pond_bottoms/koi_pond_bottom", ModProcessorLists.KOI_POND_BOTTOM_GRAVEL_40_PERCENT), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
    }
}