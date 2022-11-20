package com.calebabg.utilities;

import static org.apache.commons.math3.util.FastMath.max;
import static org.apache.commons.math3.util.FastMath.min;

public class MathUtil {
    private MathUtil(){}

    public static int lerp(int start, int stop, int amt) {
        return start + (stop - start) * amt;
    }

    public static int norm(int value, int start, int stop) {
        return (value - start) / (stop - start);
    }

    public static int clamp(int val, int min, int max) {
        return max(min, min(max, val));
    }

    public static int map(int value, int sMin, int sMax, int dMin, int dMax) {
        return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));
    }

    public static float lerp(float start, float stop, float amt) {
        return start + (stop - start) * amt;
    }

    public static float norm(float value, float start, float stop) {
        return (value - start) / (stop - start);
    }

    public static float clamp(float val, float min, float max) {
        return max(min, min(max, val));
    }

    public static float map(float value, float sMin, float sMax, float dMin, float dMax) {
        return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));
    }
}
