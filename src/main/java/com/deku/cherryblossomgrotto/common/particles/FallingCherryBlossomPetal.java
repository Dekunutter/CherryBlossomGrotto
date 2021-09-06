package com.deku.cherryblossomgrotto.common.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;

import java.awt.*;

public class FallingCherryBlossomPetal extends SpriteTexturedParticle {
    private final IAnimatedSprite sprites;
    private int despawnTimer = 0;

    public FallingCherryBlossomPetal(ClientWorld world, Color color, double diameter, IAnimatedSprite sprites, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        setColor(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f);
        setSize((float) diameter, (float) diameter);
        this.sprites = sprites;

        this.lifetime = 10000;

        this.quadSize = 0.1f * (float) diameter;

        this.alpha = 1.0f;

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.hasPhysics = true;
    }

    /**
     * Gets the rendering type of this particle.
     * Opague rendered particles are given opacity
     *
     * @return The rendering type for this particle
     */
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    /**
     * Overrides the base tick method associated with this particle.
     * This implementation re-organizes some of the base game's tick logic.
     * Controls the movement and lifecycle of the particle.
     */
    @Override
    public void tick() {
        xo = x;
        yo = y;
        zo = z;

        move(xd, yd, zd);

        if (onGround) {
            despawnTimer++;
        }

        if (despawnTimer == 5) {
            remove();
        }

        if (yo == y && yd > 0) {
            remove();
        }

        if (age++ >= lifetime) {
            remove();
        }
    }
}
