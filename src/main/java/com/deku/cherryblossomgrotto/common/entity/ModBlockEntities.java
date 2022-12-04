package com.deku.cherryblossomgrotto.common.entity;

import com.deku.cherryblossomgrotto.common.blockEntities.CherryLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blocks.ModBlocks;
import com.deku.cherryblossomgrotto.common.entity.sign.ModSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static BlockEntityType<CherryLeavesBlockEntity> CHERRY_LEAVES_TYPE = BlockEntityType.Builder.of(CherryLeavesBlockEntity::new, ModBlocks.CHERRY_LEAVES).build(null);

    public static BlockEntityType<ModSignBlockEntity> SIGN_ENTITY_TYPE = BlockEntityType.Builder.of(ModSignBlockEntity::new, ModBlocks.CHERRY_SIGN, ModBlocks.CHERRY_WALL_SIGN).build(null);
}
