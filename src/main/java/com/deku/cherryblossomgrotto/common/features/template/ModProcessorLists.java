package com.deku.cherryblossomgrotto.common.features.template;

import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModProcessorLists {
    private static final StructureProcessorList STREET_CHERRY_BLOSSOM_GROTTO_LIST = new StructureProcessorList(
        ImmutableList.of(
            new RuleProcessor(
                ImmutableList.of(
                    new ProcessorRule(
                        new BlockMatchTest(Blocks.DIRT_PATH),
                        new BlockMatchTest(Blocks.WATER),
                        ModBlocks.CHERRY_PLANKS.defaultBlockState()
                    ),
                    new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.DIRT_PATH, 0.1F),
                            AlwaysTrueTest.INSTANCE,
                        Blocks.GRASS_BLOCK.defaultBlockState()
                    ),
                    new ProcessorRule(
                        new BlockMatchTest(Blocks.DIRT_PATH),
                        new BlockMatchTest(Blocks.WATER),
                        Blocks.WATER.defaultBlockState()
                    ),
                    new ProcessorRule(
                        new BlockMatchTest(Blocks.DIRT),
                        new BlockMatchTest(Blocks.WATER),
                        Blocks.WATER.defaultBlockState()
                    )
                )
            )
        )
    );

    private static final StructureProcessorList KOI_POND_BOTTOM_GRAVEL_40_PERCENT_LIST = new StructureProcessorList(
        ImmutableList.of(
            new RuleProcessor(
                ImmutableList.of(
                    new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.SAND, 0.4F),
                            AlwaysTrueTest.INSTANCE,
                        Blocks.GRAVEL.defaultBlockState()
                    )
                )
            )
        )
    );

    private static final StructureProcessorList STONE_BRICKS_CRACKED_20_PERCENT_LIST = new StructureProcessorList(
        ImmutableList.of(
            new RuleProcessor(
                ImmutableList.of(
                    new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.2F),
                            AlwaysTrueTest.INSTANCE,
                        Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                    )
                )
            )
        )
    );

    public static Holder<StructureProcessorList> STREET_CHERRY_BLOSSOM_GROTTO;
    public static Holder<StructureProcessorList> KOI_POND_BOTTOM_GRAVEL_40_PERCENT;
    public static Holder<StructureProcessorList> STONE_BRICKS_CRACKED_20_PERCENT;

    /**
     * Registers processor lists for village random generation.
     */
    public static void register() {
        STREET_CHERRY_BLOSSOM_GROTTO = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID, "streets_cherry_blossom_grotto"), STREET_CHERRY_BLOSSOM_GROTTO_LIST);
        KOI_POND_BOTTOM_GRAVEL_40_PERCENT = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID, "koi_pond_bottom_gravel_40_percent"), KOI_POND_BOTTOM_GRAVEL_40_PERCENT_LIST);
        STONE_BRICKS_CRACKED_20_PERCENT = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, new ResourceLocation(MOD_ID, "stone_bricks_cracked_20_percent"), STONE_BRICKS_CRACKED_20_PERCENT_LIST);
    }
}
