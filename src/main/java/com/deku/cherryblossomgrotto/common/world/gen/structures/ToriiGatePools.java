package com.deku.cherryblossomgrotto.common.world.gen.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

// NOTE: Unused but kept for possible conversion in the future. Tested and works out-of-the-box
//  Structure NBTs already have jigsaw blocks in place to connect the peices
public class ToriiGatePools {
    public static final Holder<StructureTemplatePool> START = Pools.register(
        new StructureTemplatePool(
            new ResourceLocation(MOD_ID, "torii_gate_bottoms"),
            new ResourceLocation("empty"),
            ImmutableList.of(
                Pair.of(StructurePoolElement.legacy(MOD_ID + ":torii_gate_bottom"), 1)
            ),
            StructureTemplatePool.Projection.RIGID
        )
    );

    static {
        Pools.register(
            new StructureTemplatePool(
                new ResourceLocation(MOD_ID, "torii_gate_tops"),
                new ResourceLocation("empty"),
                ImmutableList.of(
                    Pair.of(StructurePoolElement.legacy(MOD_ID + ":torii_gate_top"), 1)
                ),
                StructureTemplatePool.Projection.RIGID
            )
        );
    }
}
