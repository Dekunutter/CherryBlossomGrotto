package com.deku.cherryblossomgrotto.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.Locale;

public class FallingCherryBlossomPetalOptions implements ParticleOptions {
    private Color tint;
    private double diameter;

    public FallingCherryBlossomPetalOptions(Color tint, double diameter) {
        this.tint = tint;
        this.diameter = diameter;
    }

    private FallingCherryBlossomPetalOptions(int rgb, double diameter) {
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
     * Gets the type for the particle
     *
     * @return Particle type
     */
    @Override
    public ParticleType<FallingCherryBlossomPetalOptions> getType() {
        return ModParticles.CHERRY_PETAL;
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
        return String.format(Locale.ROOT, "%s %.2f %i %i %i", this.getType().getRegistryName(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
    }

    /**
     * Used to serialize and deserialize this particle
     */
    public static final Codec<FallingCherryBlossomPetalOptions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("int").forGetter(d -> d.tint.getRGB()),
                    Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
            ).apply(instance, FallingCherryBlossomPetalOptions::new)
    );

    /**
     * The deserializer used to constract this particle from a network packet or command line
     * This is deprecated in Minecraft's code but seems to still be a key component in setting up any particle for the time being
     */
    @Deprecated
    public static final ParticleOptions.Deserializer<FallingCherryBlossomPetalOptions> DESERIALIZER = new ParticleOptions.Deserializer<FallingCherryBlossomPetalOptions>() {

        /**
         * Parse the parameters for this particle from a /particle command
         *
         * @param particleType The particle type
         * @param reader The reader object containing the /particle commands
         * @return Particle data
         * @throws CommandSyntaxException Thrown when there was an error in the command
         */
        @Override
        public FallingCherryBlossomPetalOptions fromCommand(ParticleType<FallingCherryBlossomPetalOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double diameter = reader.readDouble();

            final int minColor = 0;
            final int maxColor = 255;
            reader.expect(' ');
            int red = Mth.clamp(reader.readInt(), minColor, maxColor);
            int green = Mth.clamp(reader.readInt(), minColor, maxColor);
            int blue = Mth.clamp(reader.readInt(), minColor, maxColor);
            Color color = new Color(red, green, blue);

            return new FallingCherryBlossomPetalOptions(color, diameter);
        }

        /**
         * Reads particle information from a PacketBuffer after the client has received it from the server
         *
         * @param particleType The particle type
         * @param buffer The buffer containing all the particle information
         * @return Particle data
         */
        @Override
        public FallingCherryBlossomPetalOptions fromNetwork(ParticleType<FallingCherryBlossomPetalOptions> particleType, FriendlyByteBuf buffer) {
            final int minColor = 0;
            final int maxColor = 255;
            int red = Mth.clamp(buffer.readInt(), minColor, maxColor);
            int green = Mth.clamp(buffer.readInt(), minColor, maxColor);
            int blue = Mth.clamp(buffer.readInt(), minColor, maxColor);
            Color color = new Color(red, green, blue);

            double diameter = buffer.readDouble();

            return new FallingCherryBlossomPetalOptions(color, diameter);
        }
    };
}
