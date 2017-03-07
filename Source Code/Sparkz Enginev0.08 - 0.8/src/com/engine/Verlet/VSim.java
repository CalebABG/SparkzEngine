package com.engine.Verlet;

import static com.engine.EngineHelpers.EngineMethods.toggle;
import static com.engine.Verlet.Point.POINTS;

public class VSim {
    public static Point dragPoint;
    public static boolean ZERO_GRAVITY = false;
    public static boolean DEBUG_MODE = false;
    public static boolean COLLISION_DETECTION = true;
    public static int mouseTearSize = (int) Math.pow(3, 2);
    public static double gravity;
    public static double kViscosity = 1.0;
    public static final int yStart = 25;
    public static final double stiffnesses = .8;
    public static double dragForce = 30; // The lower the stronger
    public static final double curtainTearSensitivity = 80;
    public static int SIM_ACCURACY = 5;
    public static int MAX_COLLISIONS = 285;

    public static void toggleCollisions() {COLLISION_DETECTION = POINTS.size() <= MAX_COLLISIONS && toggle(COLLISION_DETECTION);}
    public static void toggleGravity() {ZERO_GRAVITY = toggle(ZERO_GRAVITY);}
    public static void toggleDebug() {DEBUG_MODE = toggle(DEBUG_MODE);}
}