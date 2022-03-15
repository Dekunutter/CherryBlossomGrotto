package com.deku.cherryblossomgrotto.common.features.template;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.template.*;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModProcessorLists {
    public static final StructureProcessorList STREET_CHERRY_BLOSSOM_GROTTO = new StructureProcessorList(
        ImmutableList.of(
            new RuleStructureProcessor(
                ImmutableList.of(
                    new RuleEntry(
                        new BlockMatchRuleTest(Blocks.GRASS_PATH),
                        new BlockMatchRuleTest(Blocks.WATER),
                        ModBlocks.CHERRY_PLANKS.defaultBlockState()
                    ),
                    new RuleEntry(
                        new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.1F),
                        AlwaysTrueRuleTest.INSTANCE,
                        Blocks.GRASS_BLOCK.defaultBlockState()
                    ),
                    new RuleEntry(
                        new BlockMatchRuleTest(Blocks.GRASS_PATH),
                        new BlockMatchRuleTest(Blocks.WATER),
                        Blocks.WATER.defaultBlockState()
                    ),
                    new RuleEntry(
                        new BlockMatchRuleTest(Blocks.DIRT),
                        new BlockMatchRuleTest(Blocks.WATER),
                        Blocks.WATER.defaultBlockState()
                    )
                )
            )
        )
    );

    public static final StructureProcessorList KOI_POND_BOTTOM_GRAVEL_40_PERCENT = new StructureProcessorList(
        ImmutableList.of(
            new RuleStructureProcessor(
                ImmutableList.of(
                    new RuleEntry(
                        new RandomBlockMatchRuleTest(Blocks.SAND, 0.4F),
                        AlwaysTrueRuleTest.INSTANCE,
                        Blocks.GRAVEL.defaultBlockState()
                    )
                )
            )
        )
    );

    public static final StructureProcessorList STONE_BRICKS_CRACKED_20_PERCENT = new StructureProcessorList(
        ImmutableList.of(
            new RuleStructureProcessor(
                ImmutableList.of(
                    new RuleEntry(
                        new RandomBlockMatchRuleTest(Blocks.STONE_BRICKS, 0.2F),
                        AlwaysTrueRuleTest.INSTANCE,
                        Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                    )
                )
            )
        )
    );

    /**
     * Registers processor lists for village random generation.
     */
    public static void register() {
        Registry.register(WorldGenRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID + "streets_cherry_blossom_grotto"), STREET_CHERRY_BLOSSOM_GROTTO);
        Registry.register(WorldGenRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID + "koi_pond_bottom_gravel_40_percent"), KOI_POND_BOTTOM_GRAVEL_40_PERCENT);
        Registry.register(WorldGenRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID + "stone_bricks_cracked_20_percent"), STONE_BRICKS_CRACKED_20_PERCENT);
    }
}
