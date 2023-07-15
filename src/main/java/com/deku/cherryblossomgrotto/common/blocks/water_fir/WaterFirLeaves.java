package com.deku.cherryblossomgrotto.common.blocks.water_fir;

import com.deku.cherryblossomgrotto.common.AbstractLeavesBlock;
import com.deku.cherryblossomgrotto.common.blockEntities.MapleLeavesBlockEntity;
import com.deku.cherryblossomgrotto.common.blockEntities.ModBlockEntityType;
import com.deku.cherryblossomgrotto.common.blocks.AbstractFallingLeavesBlock;
import com.deku.cherryblossomgrotto.common.particles.FallingMapleLeafOptions;
import com.deku.cherryblossomgrotto.utils.ModConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class WaterFirLeaves extends AbstractLeavesBlock {
    public WaterFirLeaves() {
        super(Properties.of().strength(0.2f).sound(SoundType.GRASS).noOcclusion().mapColor(MapColor.COLOR_GREEN).pushReaction(PushReaction.DESTROY).ignitedByLava().isValidSpawn(WaterFirLeaves::validSpawns).isSuffocating(WaterFirLeaves.never()).isViewBlocking(WaterFirLeaves.never()).randomTicks());
    }
}
