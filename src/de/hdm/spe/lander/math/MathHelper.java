
package de.hdm.spe.lander.math;

public class MathHelper {

    public static float clamp(float value, float min, float max) {
        value = Math.min(value, max);
        value = Math.max(value, min);

        return value;
    }

}
