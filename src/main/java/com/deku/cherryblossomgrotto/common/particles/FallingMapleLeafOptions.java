package com.deku.cherryblossomgrotto.common.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.awt.*;

public class FallingMapleLeafOptions extends AbstractFallingLeafParticleOptions {
    private Color tint;
    private double diameter;

    public FallingMapleLeafOptions(Color tint, double diameter) {
        super(tint, diameter);
    }

    private FallingMapleLeafOptions(int rgb, double diameter) {
        super(rgb, diameter);
    }

    /**
     * Gets the type for the particle
     *
     * @return Particle type
     */
    @Override
    public ParticleType<FallingMapleLeafOptions> getType() {
        return ModParticles.MAPLE_LEAF;
    }

    /**
     * Used to serialize and deserialize this particle
     */
    public static final Codec<FallingMapleLeafOptions> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("int").forGetter(d -> d.tint.getRGB()),
                    Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
            ).apply(instance, FallingMapleLeafOptions::new)
    );

    /**
     * The deserializer used to constract this particle from a network packet or command line
     * This is deprecated in Minecraft's code but seems to still be a key component in setting up any particle for the time being
     */
    @Deprecated
    public static final Deserializer<FallingMapleLeafOptions> DESERIALIZER = new Deserializer<FallingMapleLeafOptions>() {

        /**
         * Parse the parameters for this particle from a /particle command
         *
         * @param particleType The particle type
         * @param reader The reader object containing the /particle commands
         * @return Particle data
         * @throws CommandSyntaxException Thrown when there was an error in the command
         */
        @Override
        public FallingMapleLeafOptions fromCommand(ParticleType<FallingMapleLeafOptions> particleType, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            double diameter = reader.readDouble();

            final int minColor = 0;
            final int maxColor = 255;
            reader.expect(' ');
            int red = Mth.clamp(reader.readInt(), minColor, maxColor);
            int green = Mth.clamp(reader.readInt(), minColor, maxColor);
            int blue = Mth.clamp(reader.readInt(), minColor, maxColor);
            Color color = new Color(red, green, blue);

            return new FallingMapleLeafOptions(color, diameter);
        }

        /**
         * Reads particle information from a PacketBuffer after the client has received it from the server
         *
         * @param particleType The particle type
         * @param buffer The buffer containing all the particle information
         * @return Particle data
         */
        @Override
        public FallingMapleLeafOptions fromNetwork(ParticleType<FallingMapleLeafOptions> particleType, FriendlyByteBuf buffer) {
            final int minColor = 0;
            final int maxColor = 255;
            int red = Mth.clamp(buffer.readInt(), minColor, maxColor);
            int green = Mth.clamp(buffer.readInt(), minColor, maxColor);
            int blue = Mth.clamp(buffer.readInt(), minColor, maxColor);
            Color color = new Color(red, green, blue);

            double diameter = buffer.readDouble();

            return new FallingMapleLeafOptions(color, diameter);
        }
    };
}
