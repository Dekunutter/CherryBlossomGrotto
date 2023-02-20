package com.deku.cherryblossomgrotto.common.particles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.util.Locale;

public abstract class AbstractFallingLeafParticleOptions implements ParticleOptions {
    private Color tint;
    private double diameter;

    public AbstractFallingLeafParticleOptions(Color tint, double diameter) {
        this.tint = tint;
        this.diameter = diameter;
    }

    protected AbstractFallingLeafParticleOptions(int rgb, double diameter) {
        this.tint = new Color(rgb);
        this.diameter = diameter;
    }

    /**
     * Getter for the color of the particle
     *
     * @return Color associated with this particle
     */
    public Color getTint() {
        return tint;
    }

    /**
     * Getter for the diameter of the particle
     *
     * @return Diameter in metres
     */
    public double getDiameter() {
        return diameter;
    }

    /**
     * Write particle information to a PacketBuffer, ready for transmission to a client
     *
     * @param buffer Buffer to be sent to the client
     */
    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(tint.getRed());
        buffer.writeInt(tint.getGreen());
        buffer.writeInt(tint.getBlue());
        buffer.writeDouble(diameter);
    }

    /**
     * Prints data about the particle in human-readable format
     *
     * @return Particle data written to a string
     */
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %i %i %i", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
    }
}
