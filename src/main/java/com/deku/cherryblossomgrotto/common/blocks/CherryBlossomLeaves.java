package com.deku.cherryblossomgrotto.common.blocks;

import com.deku.cherryblossomgrotto.common.particles.FallingCherryBlossomPetalData;
import com.deku.cherryblossomgrotto.common.tileEntities.CherryLeavesTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.Random;

public class CherryBlossomLeaves extends LeavesBlock {
    public CherryBlossomLeaves() {
        super(AbstractBlock.Properties.of(Material.LEAVES, MaterialColor.COLOR_PINK).strength(0.2f).sound(SoundType.GRASS).noOcclusion().randomTicks());
        setRegistryName("cherry_blossom_leaves");
    }

    /**
     * Overrides the animateTick function for this block.
     * Still calls base-game tick logic but also randomly spawns particles into the world.
     *
     * @param state The state of the block
     * @param world The world instance containing the block instance
     * @param position The position of the block
     * @param random A random number generator
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos position, Random random) {
        super.animateTick(state, world, position, random);
        if (random.nextInt(8) == 0) {
            FallingCherryBlossomPetalData cherryBlossomPetal = new FallingCherryBlossomPetalData(new Color(255, 255, 255, 255), 1.0f);
            world.addParticle(cherryBlossomPetal, (float) position.getX() + random.nextFloat(), (float) position.getY() - 1.0f, (float) position.getZ() + random.nextFloat(), 0.0d, -0.1d, 0.0d);
        }
    }

    /**
     * Determines if the block has an associated tile entity.
     * Setting this to true means that a tile entity runs against this block.
     *
     * @param state State of the block
     * @return Whether the block has an associated tile entity
     */
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    /**
     * Creates a tile entity for this block
     *
     * @param state State of the block
     * @param world World that the block exists in
     * @return The tile entity associated with this block
     */
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CherryLeavesTileEntity();
    }
}
