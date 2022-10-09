package com.cabg.verlet;

import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.PhysicsEditor;

import java.awt.*;
import java.awt.event.MouseEvent;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.core.EngineMethods.toggle;
import static com.cabg.utilities.ColorUtility.randHSLColor;
import static com.cabg.verlet.PhysicsUtil.*;
import static com.cabg.verlet.Vertex.Vertices;
import static org.apache.commons.math3.util.FastMath.*;

public class PhysicsHandler {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 14);
    public static Color LCOLOR = Color.ORANGE.darker();
    public static Color PCOLOR = Color.ORANGE;
    public static int NUM_POINTS = 25;
    public static float SIZE = 50;
    public static float SPACING = 10;
    public static float RADIUS = 6;
    public static float STIFFNESS = 0.0444f;
    public static float TEAR_DISTANCE = 80;
    public static boolean DRAW_LINKS = true;
    public static boolean TEARABLE = false;
    public static final int MAX_COLLISIONS = 1000;
    public static boolean ZERO_GRAVITY = false;
    public static boolean DEBUG_MODE = false;
    public static boolean COLLISION_DETECTION;
    public static int SIM_ACCURACY = 12; // The higher = better accuracy but slightly slower render
    public static Vertex dragVertex;
    public static Vertex selectedVertex;
    public static int mouseTearSize = (int) pow(3, 2);
    public static float gravity = 0.251f;
    public static float airViscosity = 1.0f;
    public static float dragForce = 5; // The lower the stronger

    public static void toggleGravity() {
        ZERO_GRAVITY = toggle(ZERO_GRAVITY);
    }

    public static void toggleDebug() {
        DEBUG_MODE = toggle(DEBUG_MODE);
    }

    public static void resetDragVertex() {
        dragVertex = null;
    }

    public static void resetSelectedVertex() {
        selectedVertex = null;
    }

    public static void handleRagdollClickEvent(MouseEvent e) {
        if (PhysicsEditor.EDITOR_MODE == PhysicsEditorMode.Add) {
            switch (PhysicsEditor.CREATION_MODE) {
                case Point:
                    createPoint(e, randHSLColor(1000, 7000, 0.8f), 25, 100);
                    break;
                case Stick:
                    createStick(e, LCOLOR, PCOLOR, 50, 10, 10, 1, 30, STIFFNESS, TEAR_DISTANCE, DRAW_LINKS, TEARABLE);
                    break;
                case IKChain:
                    createIKChain(e, NUM_POINTS, SPACING, RADIUS, 1, TEAR_DISTANCE, DRAW_LINKS, TEARABLE, Color.red, Color.orange);
                    break;
                case Box:
                    singleBox(e, Color.blue, Color.cyan, 30, 8, 0.4f, TEAR_DISTANCE, DRAW_LINKS, TEARABLE);
                    break;
                case SolidMesh:
                    singleSolidMesh(e, 15, SIZE, 10, 0.1f, TEAR_DISTANCE, true, TEARABLE, Color.blue, Color.cyan);
                    break;
                case ElasticMesh:
                    singleElasticMesh(e, 5, SIZE, 17, 0.4f, TEAR_DISTANCE, DRAW_LINKS, TEARABLE, Color.blue, Color.cyan);
                    break;
                case Cloth:
                    createCloth(e.getX(), e.getY(), 10, 10, 15);
                    break;
                default:
                    break;
            }
        } else if (PhysicsEditor.EDITOR_MODE == PhysicsEditorMode.Select || PhysicsEditor.EDITOR_MODE == PhysicsEditorMode.Drag) {
            for (int i = 0; i < Vertices.size(); i++) {
                Vertex point = Vertices.get(i);
                if (point.contains(e.getX(), e.getY())) {
                    selectedVertex = point;
                    PhysicsEditor.setObjectPropertiesOnSelect(selectedVertex);
                    PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                    break;
                } else {
                    selectedVertex = null;
                    PhysicsEditor.unsetObjectPropertiesOnDeselect();
                    PhysicsEditor.updateConstraintsList(null);
                }
            }
        }
    }

    public static void debugPhysics() {
        if (!PhysicsHandler.DEBUG_MODE) return;

        graphics2D.setFont(renderFont);
        graphics2D.setColor(Color.white);

        if (dragVertex == null) {
            graphics2D.drawString("Drag Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 20);
        } else {
            graphics2D.drawString(dragVertex.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(dragVertex.toString())) / 2, canvas.getHeight() / 2 + 20);
        }

        if (selectedVertex == null) {
            graphics2D.drawString("Selected Point is null ", canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 40);
        } else {
            graphics2D.drawString(selectedVertex.toString(), (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(selectedVertex.toString())) / 2, canvas.getHeight() / 2 + 40);
        }
    }

    public static void pinSelectedPoint() {
        if (dragVertex != null) dragVertex.togglePin();
        else if (selectedVertex != null) selectedVertex.togglePin();
    }
}