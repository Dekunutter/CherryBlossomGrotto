package com.deku.eastwardjourneys.common.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

// NOTE: Adaption of the standard move control that sets the state to jumping instead of move_to and triggers constant jumping by the user
public class JumpingMoveControl extends MoveControl {
    private final int jumpDelay;

    private int timeSinceLastJump;

    public JumpingMoveControl(Mob mob, int jumpDelay, int initialJumpDelay) {
        super(mob);
        this.jumpDelay = jumpDelay;
        timeSinceLastJump = initialJumpDelay;
    }

    /**
     *
     * @return
     */
    public boolean hasWanted() {
        return this.operation == MoveControl.Operation.JUMPING;
    }

    /**
     * Sets the desired position for this controller
     *
     * @param wantedX Desired position on the X-axis
     * @param wantedY Desired position on the Y-axis
     * @param wantedZ Desired position on the Z-axis
     * @param speedModifier Modifier applied to the speed when navigating to this position
     */
    public void setWantedPosition(double wantedX, double wantedY, double wantedZ, double speedModifier) {
        this.wantedX = wantedX;
        this.wantedY = wantedY;
        this.wantedZ = wantedZ;
        this.speedModifier = speedModifier;
        operation = MoveControl.Operation.JUMPING;
    }

    /**
     * Update function called everytime the entity udpates itself
     * Processes the actual movement of the entity by making them jump towards the target.
     * The entity cannot move while on the ground and instead will only have momentum when actively jumping.
     * Jumps are set on a delay.
     */
    public void tick() {
        if (operation == MoveControl.Operation.JUMPING) {
            double d0 = wantedX - mob.getX();
            double d1 = wantedZ - mob.getZ();
            double d2 = wantedY - mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;
            if (d3 < (double)2.5000003E-7F) {
                mob.setZza(0.0F);
                return;
            }

            // TODO: Any way to fix the rotation on the entity? It keeps flipping around and jumping in weird ways. Would rather it beelines towards the player character
            float f9 = (float)(Mth.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            mob.setYRot(rotlerp(mob.getYRot(), f9, 90.0F));

            double currentSpeedModifier = speedModifier;
            if (mob.onGround()) {
                if (timeSinceLastJump-- <= 0) {
                    timeSinceLastJump = jumpDelay;
                    mob.getJumpControl().jump();
                } else {
                    currentSpeedModifier = 0;
                    this.operation = MoveControl.Operation.WAIT;
                }
            }

            mob.setSpeed((float)(currentSpeedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        }
    }
}
