package com.deku.cherryblossomgrotto.common.utils;

import java.util.Random;

public class Randomizer {
    /**
     * Generates a random number INCLUSIVELY
     *
     * @param random
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumberWithinBounds(Random random, int min, int max) {
        if (min == max) {
            return min;
        }
        return random.nextInt((max - min) + 1) + min;
    }
}
