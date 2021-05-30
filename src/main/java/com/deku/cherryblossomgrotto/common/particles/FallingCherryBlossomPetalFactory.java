package com.deku.cherryblossomgrotto.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nullable;

public class FallingCherryBlossomPetalFactory implements IParticleFactory<FallingCherryBlossomPetalData> {
    private final IAnimatedSprite sprites;

    public FallingCherryBlossomPetalFactory(IAnimatedSprite sprites) {
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
    public Particle createParticle(FallingCherryBlossomPetalData particleData, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        FallingCherryBlossomPetal petal = new FallingCherryBlossomPetal(world, particleData.getTint(), particleData.getDiameter(), sprites, x, y, z, xSpeed, ySpeed, zSpeed);
        petal.pickSprite(sprites);
        return petal;
    }
}
