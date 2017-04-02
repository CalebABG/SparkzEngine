package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EConstants.Mouse;
import static com.engine.EngineHelpers.EConstants.isLeftMouseDown;
import static com.engine.Utilities.ColorConverter.randHSLColor;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.VSim.selectedPoint;

public class VPHandler {
    //Add functionality for pressing 'P' to pin point in Verlet mode
    public static Color LCOLOR          = Color.ORANGE;
    public static Color PCOLOR          = Color.ORANGE;
    public static int MODE              = 3;
    public static int NUM_POINTS        = 50;
    public static final int MAX_MODE    = 6;
    public static double SIZE           = 50;
    public static double SPACING        = 10;
    public static double RADIUS         = 6;
    public static double STIFFNESS      = 0.0444;
    public static double TEARDIST       = 80;
    public static boolean DRAW_LINKS    = true;
    public static boolean TEARABLE      = false;

    public static void ragdollMode_ClickState(MouseEvent e){
        if (VPhysicsEditor.EDITOR_MODE.equals(VPhysicsEditor.ADD)) {
            switch (MODE) {
                case 0:
                    //VCreations.createPoint(e, randHSLColor(1000, 7000, 0.8), 25, 100);
                    break;
                case 1:
                    VCreations.createStick(e, LCOLOR, PCOLOR, 50, 10, 10, 1, 30, STIFFNESS, TEARDIST, DRAW_LINKS, TEARABLE);
                    break;
                case 2:
                    VCreations.createIKChain(e, NUM_POINTS, SPACING, RADIUS, 1, TEARDIST, DRAW_LINKS, TEARABLE, Color.red, Color.orange);
                    break;
                case 3:
                    VCreations.singleBox(e, Color.blue, Color.cyan, 30, 8, 0.4, TEARDIST, DRAW_LINKS, TEARABLE);
                    break;
                case 4:
                    VCreations.singleSolidMesh(e, 15, SIZE, 10, 0.1, TEARDIST, true, TEARABLE, Color.blue, Color.cyan);
                    break;
                case 5:
                    VCreations.singleElasticMesh(e, 5, SIZE, 17, 0.4, TEARDIST, DRAW_LINKS, TEARABLE, Color.blue, Color.cyan);
                    break;
                case 6:
                    createCloth();
                    break;
                default:
                    break;
            }
        }
        else if (VPhysicsEditor.EDITOR_MODE.equals(VPhysicsEditor.SELECT)) {
            for (int i = 0; i < POINTS.size(); i++) {
                Point point = POINTS.get(i);
                if (point.contains(e.getPoint())) {
                    selectedPoint = point;
                    VPhysicsEditor.updateJListConstraints(selectedPoint.constraints);
                    break;
                }
                else {selectedPoint = null; VPhysicsEditor.updateJListConstraints(null);}
            }
        }
    }

    public static void createCloth(){
        Color LINK_COLOR            = Color.pink;
        Color DYNAMIC_POINT_COLOR   = Color.white;
        Color STATIC_POINT_COLOR    = Color.orange;
        double RADIUS               = 4;
        double STIFFNESS            = 0.8;
        double TEARDISTANCE         = 50;
        int CLOTH_SPACING           = 20;
        int CLOTH_WIDTH             = 10;
        int CLOTH_HEIGHT            = 10;

        VCreations.createCloth(CLOTH_WIDTH, CLOTH_HEIGHT, CLOTH_SPACING, 25,
                STIFFNESS, TEARDISTANCE, RADIUS, DYNAMIC_POINT_COLOR, STATIC_POINT_COLOR, LINK_COLOR, true, true);
    }

    public static void dragMode(MouseEvent e){
        double radius = Math.random() * 32 + 7;
        //VCreations.createPoint(e, randHSLColor(5000, 1000, (float) 0.9), radius, 10);
    }
}
