package edu.rice.comp504.util;

import java.util.Random;

/**
 * Utility used to generate random numbers.
 */
public class RandUtil {
    /**
     * Generate a random number.
     * @param base  The mininum value
     * @param limit The maximum number from the base
     * @return A randomly generated number
     */
    public static int getRnd(int base, int limit) {
        return (int)Math.floor(Math.random() * limit + base);
    }

    /**
     * Operation directions.
     * @return string the Random Direction
     */
    public String randomDirection() {
        Random random = new Random();
        int num = random.nextInt(4);
        if (num == 0) {
            return "up";
        } else if (num == 1) {
            return "down";
        } else if (num == 2) {
            return "left";
        } else {
            return "right";
        }
    }
}
