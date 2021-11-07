package com.deku.cherryblossomgrotto.common.utils;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class BlockPosUtils {
    /**
     * Gets the magnitude of a vector.
     * This is often used to then calculate the normal of a vector.
     *
     * @param vector The vector that we want to calculate the magnitude of
     * @return The magnitude of the given vector.
     */
    public static double getLengthOfVector(BlockPos vector) {
        return MathHelper.sqrt((vector.getX() * vector.getX()) + (vector.getY() * vector.getY()) + (vector.getZ() * vector.getZ()));
    }

    /**
     * Normalizes a given vector to a new vector representing it's direction.
     * A normalized vector is where all axises added together will equal 1, so we can get a "weight" of how much influence each axis has.
     *
     * @param vector The vector that we want to normalize to a direction vector.
     * @return The normalized direction vector
     */
    public static Vector3d normalizeToDirectionVector(BlockPos vector) {
        double length = getLengthOfVector(vector);
        return new Vector3d(vector.getX() / length, vector.getY() / length, vector.getZ() / length);
    }

    /**
     * Gets the axis in a vector with the largest value, regardless of that value's sign.
     *
     * @param vector The vector we want to find the biggest axis for
     * @return The absolute value of the biggest axis
     */
    public static int getMaxUnitOfAVector(BlockPos vector) {
        int absX = Math.abs(vector.getX());
        int absY = Math.abs(vector.getX());
        int absZ = Math.abs(vector.getX());

        if (absX > absY) {
            return Math.max(absX, absZ);
        } else return Math.max(absZ, absY);
    }
}
