package com.deku.eastwardjourneys.common.utils;

import net.minecraft.util.RandomSource;

public class Randomizer {
    /**
     * Generates a random number INCLUSIVELY
     *
     * @param random
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumberWithinBounds(RandomSource random, int min, int max) {
        if (min == max) {
            return min;
        }
        return random.nextInt((max - min) + 1) + min;
    }
}
