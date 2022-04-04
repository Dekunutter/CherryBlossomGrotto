package com.deku.cherryblossomgrotto.common.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class FallingCherryBlossomPetalProvider implements ParticleProvider<FallingCherryBlossomPetalOptions> {
    private final SpriteSet sprites;

    public FallingCherryBlossomPetalProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    /**
     * Builds the particle from its data class on the client.
     *
     * @param particleData Particle data
     * @param world The world instance to spawn the particle in
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @param z position on the z-axis
     * @param xSpeed speed of the particle on the x-axis
     * @param ySpeed speed of the particle on the y-axis
     * @param zSpeed speed of the particle on the z-axis
     * @return A particle instance
     */
    @Nullable
    @Override
    public Particle createParticle(FallingCherryBlossomPetalOptions particleData, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        FallingCherryBlossomPetal petal = new FallingCherryBlossomPetal(world, particleData.getTint(), particleData.getDiameter(), sprites, x, y, z, xSpeed, ySpeed, zSpeed);
        petal.pickSprite(sprites);
        return petal;
    }
}
