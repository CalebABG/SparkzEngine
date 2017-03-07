package com.engine.Verlet;

import java.awt.*;
import java.awt.event.MouseEvent;
import static com.engine.Utilities.ColorConverter.randHSLColor;

public class VPHandler {
    public static Color LCOLOR = Color.ORANGE, PCOLOR = Color.ORANGE;
    public static int MODE = 0, NUM_POINTS = 25;
    public static final int MAX_MODE = 6;
    public static double SIZE = 50, SPACING = 8, RADIUS = 10, STIFFNESS = .0144, TEARDIST = 80;
    public static boolean DRAW_LINKS = true, TEARABLE = false;

    public static void handleRagdollMode(MouseEvent e){
        switch (MODE) {
            case 0: VCreations.createPoint(e, randHSLColor(1000, 7000, .8), 25, 1); break;
            case 1: VCreations.createStick(e, LCOLOR, PCOLOR, 50, 20, 30, 1, 30, STIFFNESS, TEARDIST, DRAW_LINKS, TEARABLE); break;
            case 2: VCreations.createIKChain(e, NUM_POINTS, SPACING, RADIUS, .2, TEARDIST, DRAW_LINKS, TEARABLE, Color.red, Color.orange); break;
            case 3: VCreations.singleBox(e, Color.blue, Color.cyan, 30, 8, .06, TEARDIST, DRAW_LINKS, TEARABLE); break;
            case 4: VCreations.singleSolidMesh(e, 15, SIZE, 10, .1, TEARDIST, true, TEARABLE, Color.blue, Color.cyan); break;
            case 5: VCreations.singleElasticMesh(e, 5, SIZE, RADIUS, .4, TEARDIST, DRAW_LINKS, TEARABLE, Color.blue, Color.cyan); break;
            case 6: createCloth(); break;
            default: break;
        }
    }

    public static void createCloth(){
        Color LINK_COLOR = Color.MAGENTA, DYNAMIC_POINT_COLOR = Color.white, STATIC_POINT_COLOR = Color.orange;
        double RADIUS = 4; int CLOTH_SPACING = 20, CLOTH_WIDTH = 10, CLOTH_HEIGHT = 10;
        VCreations.createCloth(CLOTH_WIDTH, CLOTH_HEIGHT, CLOTH_SPACING, RADIUS, DYNAMIC_POINT_COLOR, STATIC_POINT_COLOR, LINK_COLOR, true, true);
    }

    public static void dragMode(MouseEvent e){
        double radius = Math.random() * 32 + 7;
        VCreations.createPoint(e, randHSLColor(5000, 1000, (float) .9), radius, 10);
    }
}
