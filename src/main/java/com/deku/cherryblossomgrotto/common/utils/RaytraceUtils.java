package com.deku.cherryblossomgrotto.common.utils;

import com.google.common.collect.Lists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class RaytraceUtils {
    private static double LINEAR_INTERPOLATION_FRACTION = -1.0E-7D;

    /**
     * Calls into an overloaded raytracing function that will traverse over all blocks intersecting a trace in the world and return them in a list.
     * Sets the fraction for interpolation to a defaulted value which is intentionally very low to get a very detailed trace through the world.
     *
     * @param context Context of the raytrace. Holds all data needed to start the trace through the world
     * @return A list of all block positions that this raytrace crossed through
     */
    public static List<BlockPos> traverseBlocks(RayTraceContext context) {
        return traverseBlocks(context, LINEAR_INTERPOLATION_FRACTION);
    }

    /**
     * Gets the position in world of each block that is crossed by the given raytrace from its starting point to its ending point.
     * This raytrace works be incrementing through the trace in steps of one block unit, always taking the shortest current route at any point in space.
     * This means that there's no control over which blocks are traced over, it will return ever block that is intersected, even though intersected at incredibly minor values.
     *
     * @param context Context of the raytrace. Holds all data needed to start the trace through the world
     * @param fraction The fraction at which the position will be interpolated. This should be a miniscule value to encourage the algorithm to step through at high clarity and not miss any blocks
     * @return A list of all block positions that this raytrace crossed through
     */
    public static List<BlockPos> traverseBlocks(RayTraceContext context, double fraction) {
        List<BlockPos> points = Lists.newArrayList();
        Vector3d startPosition = context.getFrom();
        Vector3d endPosition = context.getTo();

        if (startPosition.equals(endPosition)) {
            return Lists.newArrayList();
        } else {
            double backwardsInterpolatedPositionX = MathHelper.lerp(fraction, endPosition.x, startPosition.x);
            double backwardsInterpolatedPositionY = MathHelper.lerp(fraction, endPosition.y, startPosition.y);
            double backwardsInterpolatedPositionZ = MathHelper.lerp(fraction, endPosition.z, startPosition.z);
            double interpolatedPositionX = MathHelper.lerp(fraction, startPosition.x, endPosition.x);
            double interpolatedPositionY = MathHelper.lerp(fraction, startPosition.y, endPosition.y);
            double interpolatedPositionZ = MathHelper.lerp(fraction, startPosition.z, endPosition.z);

            int currentBlockX = MathHelper.floor(interpolatedPositionX);
            int currentBlockY = MathHelper.floor(interpolatedPositionY);
            int currentBlockZ = MathHelper.floor(interpolatedPositionZ);
            BlockPos.Mutable mutableBlockPosition = new BlockPos.Mutable(currentBlockX, currentBlockY, currentBlockZ);

            double differenceX = backwardsInterpolatedPositionX - interpolatedPositionX;
            double differenceY = backwardsInterpolatedPositionY - interpolatedPositionY;
            double differenceZ = backwardsInterpolatedPositionZ - interpolatedPositionZ;
            int directionX = MathHelper.sign(differenceX);
            int directionY = MathHelper.sign(differenceY);
            int directionZ = MathHelper.sign(differenceZ);

            double normalizedDifferenceX = directionX == 0 ? Double.MAX_VALUE : (double) directionX / differenceX;
            double normalizedDifferenceY = directionY == 0 ? Double.MAX_VALUE : (double) directionY / differenceY;
            double normalizedDifferenceZ = directionZ == 0 ? Double.MAX_VALUE : (double) directionZ / differenceZ;
            double maxX = normalizedDifferenceX * (directionX > 0 ? 1.0D - MathHelper.frac(interpolatedPositionX) : MathHelper.frac(interpolatedPositionX));
            double maxY = normalizedDifferenceY * (directionY > 0 ? 1.0D - MathHelper.frac(interpolatedPositionY) : MathHelper.frac(interpolatedPositionY));
            double maxZ = normalizedDifferenceZ * (directionZ > 0 ? 1.0D - MathHelper.frac(interpolatedPositionZ) : MathHelper.frac(interpolatedPositionZ));

            while (maxX <= 1.0D || maxY <= 1.0D || maxZ <= 1.0D) {
                if (maxX < maxY) {
                    if (maxX < maxZ) {
                        currentBlockX += directionX;
                        maxX += normalizedDifferenceX;
                    } else {
                        currentBlockZ += directionZ;
                        maxZ += normalizedDifferenceZ;
                    }
                } else if (maxY < maxZ) {
                    currentBlockY += directionY;
                    maxY += normalizedDifferenceY;
                } else {
                    currentBlockZ += directionZ;
                    maxZ += normalizedDifferenceZ;
                }

                mutableBlockPosition.set(currentBlockX, currentBlockY, currentBlockZ);
                points.add(mutableBlockPosition.immutable());
            }

            return points;
        }
    }
}
