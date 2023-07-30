package com.deku.eastwardjourneys.common.entity.ai.navigation;

import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

// NOTE: The actual jumping logic is controlled by JumpingMoveControl.
//  Basically a carbon-copy of GroundPathNavigation but could alter the navigation in the future so I'm keeping it in for reference later
public class JumpPathNavigation extends PathNavigation {
    public JumpPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    /**
     * Creates the path finder that will help this navigation evaluate the nodes its passing over.
     * Ensures the node evaluator allows this path finder to pass through doors.
     *
     * @param maxNodes The maximum number of nodes this path finder will evaluate
     * @return The path finder for this navigation class
     */
    @Override
    protected PathFinder createPathFinder(int maxNodes) {
        nodeEvaluator = new WalkNodeEvaluator();
        nodeEvaluator.setCanPassDoors(false);
        return new PathFinder(nodeEvaluator, maxNodes);
    }

    /**
     * Determines whether the path for this navigation class can update
     *
     * @return Whether the path for this navigation class can update
     */
    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    /**
     * Gets the current position of the mob
     *
     * @return Current position of the mob in a vector
     */
    @Override
    protected Vec3 getTempMobPos() {
        return new Vec3(mob.getX(), mob.getY(0.5D), mob.getZ());
    }

    /**
     * Updates the navigation on each tick.
     * This sets the desired position that the entity will want to navigate to and advances the path forward to the next position.
     */
    public void tick() {
        ++tick;
        if (hasDelayedRecomputation) {
            recomputePath();
        }

        if (!isDone()) {
            if (canUpdatePath()) {
                followThePath();
            } else if (path != null && !path.isDone()) {
                Vec3 vec3 = getTempMobPos();
                Vec3 vec31 = path.getNextEntityPos(mob);
                if (vec3.y > vec31.y && !mob.onGround() && Mth.floor(vec3.x) == Mth.floor(vec31.x) && Mth.floor(vec3.z) == Mth.floor(vec31.z)) {
                    path.advance();
                }
            }

            DebugPackets.sendPathFindingPacket(level, mob, path, maxDistanceToWaypoint);
            if (!isDone()) {

                Vec3 vec32 = path.getNextEntityPos(mob);
                mob.getMoveControl().setWantedPosition(vec32.x, getGroundY(vec32), vec32.z, speedModifier);
            }
        }
    }
}
