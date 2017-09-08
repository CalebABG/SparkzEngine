package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EConstants.random;
import static com.engine.Utilities.ColorUtility.randHSLColor;
import static com.engine.Verlet.Vertex.Vertices;
import static com.engine.Verlet.VSim.selectedVertex;

public class VPHandler {
    //Add functionality for pressing 'P' to pin point in Verlet mode
    public static final Color LCOLOR          = Color.ORANGE;
    public static final Color PCOLOR          = Color.ORANGE;
    public static int MODE              = 3;
    public static final int NUM_POINTS        = 50;
    public static final int MAX_MODE    = 6;
    public static final float SIZE           = 50;
    public static final float SPACING        = 10;
    public static final float RADIUS         = 6;
    public static final float STIFFNESS      = 0.0444f;
    public static final float TEARDIST       = 80;
    public static final boolean DRAW_LINKS    = true;
    public static final boolean TEARABLE      = false;

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
                    VCreations.singleBox(e, Color.blue, Color.cyan, 30, 8, 0.4f, TEARDIST, DRAW_LINKS, TEARABLE);
                    break;
                case 4:
                    VCreations.singleSolidMesh(e, 15, SIZE, 10, 0.1f, TEARDIST, true, TEARABLE, Color.blue, Color.cyan);
                    break;
                case 5:
                    VCreations.singleElasticMesh(e, 5, SIZE, 17, 0.4f, TEARDIST, DRAW_LINKS, TEARABLE, Color.blue, Color.cyan);
                    break;
                case 6:
                    createCloth();
                    break;
                default:
                    break;
            }
        }
        else if (VPhysicsEditor.EDITOR_MODE.equals(VPhysicsEditor.SELECT)) {
            for (int i = 0; i < Vertices.size(); i++) {
                Vertex point = Vertices.get(i);
                if (point.contains(e.getPoint())) {
                    selectedVertex = point;
                    VPhysicsEditor.updateJListConstraints(selectedVertex.edges);
                    break;
                }
                else {
                    selectedVertex = null; VPhysicsEditor.updateJListConstraints(null);}
            }
        }
    }

    public static void createCloth(){
        Color LINK_COLOR            = Color.pink;
        Color DYNAMIC_POINT_COLOR   = Color.white;
        Color STATIC_POINT_COLOR    = Color.orange;
        float RADIUS               = 4;
        float STIFFNESS            = 0.8f;
        float TEARDISTANCE         = 50;
        int CLOTH_SPACING           = 20;
        int CLOTH_WIDTH             = 10;
        int CLOTH_HEIGHT            = 10;

        VCreations.createCloth(CLOTH_WIDTH, CLOTH_HEIGHT, CLOTH_SPACING, 25,
                STIFFNESS, TEARDISTANCE, RADIUS, DYNAMIC_POINT_COLOR, STATIC_POINT_COLOR, LINK_COLOR, true, true);
    }

    public static void dragMode(MouseEvent e){
        float radius = (random.nextFloat() * 14) + 7;
        VCreations.createPoint(e, randHSLColor(5000, 1000, (float) 0.9), radius, 10);
    }
}
