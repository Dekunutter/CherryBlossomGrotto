package com.deku.cherryblossomgrotto.common.blockEntities;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;

import static com.deku.cherryblossomgrotto.Main.MOD_ID;

public class ModBlockEntityType {
    @ObjectHolder(registryName = "minecraft:block_entity_type", value = MOD_ID + ":cherry_leaves_tile_entity")
    public static BlockEntityType<CherryLeavesBlockEntity> CHERRY_LEAVES_TILE_DATA;
}
