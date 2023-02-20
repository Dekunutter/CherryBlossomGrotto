package com.deku.cherryblossomgrotto.common.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

import java.awt.*;

public class FallingMapleLeaf extends AbstractFallingLeaf {
    public FallingMapleLeaf(ClientLevel world, Color color, double diameter, SpriteSet sprites, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, color, diameter, sprites, x, y, z, xSpeed, ySpeed, zSpeed);
    }
}
