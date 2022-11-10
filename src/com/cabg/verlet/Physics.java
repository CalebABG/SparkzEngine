package com.cabg.verlet;

import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.PhysicsEditor;

import java.awt.*;
import java.util.List;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.gui.PhysicsEditor.showSelectionConstraintCheckbox;
import static org.apache.commons.math3.util.FastMath.pow;

public class Physics {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 14);
    public static Color LINK_COLOR = Color.ORANGE.darker();
    public static Color ITEM_COLOR = Color.ORANGE;
    public static final int MAX_COLLISIONS = 1000;
    public static float MESH_SIZE = 50;
    public static float TEAR_DISTANCE = 80;
    public static float GRAVITY = 0.251f;
    public static float AIR_VISCOSITY = 1.0f;
    public static float DRAG_FORCE = 5.0f; // The lower the stronger
    public static boolean DRAW_LINKS = true;
    public static boolean SEVERABLE = false;
    public static boolean ZERO_GRAVITY = false;
    public static boolean DEBUG_MODE = false;
    public static boolean COLLISION_DETECTION = false;
    public static int SIM_ACCURACY = 12; // The higher = better accuracy but slightly slower render
    public static Vertex dragVertex;
    public static Vertex selectedVertex;
    public static int mouseTearSize = (int) pow(3, 2);

    public static void toggleGravity() {
        ZERO_GRAVITY = !ZERO_GRAVITY;
    }
    public static void toggleDebug() {
        DEBUG_MODE = !DEBUG_MODE;
    }
    public static void resetDragVertex() {
        dragVertex = null;
    }
    public static void resetSelectedVertex() {
        selectedVertex = null;
    }

    public static void debugPhysics() {
        if (!DEBUG_MODE) return;

        graphics2D.setFont(renderFont);
        graphics2D.setColor(Color.white);

        if (dragVertex != null) {
            String text = "Drag - " + dragVertex;
            graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2 + 20);
        }

        if (selectedVertex != null) {
            String text = "Select - " + selectedVertex;
            graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2 + 40);
        }
    }

    public static void pinSelectedPoint() {
        if (dragVertex != null) dragVertex.togglePin();
        else if (selectedVertex != null) selectedVertex.togglePin();
    }

    public static void update() {
        Physics.COLLISION_DETECTION = Vertices.size() <= Physics.MAX_COLLISIONS;

        if (!engineSettings.paused) {
            solveConstraints();
            handleMovement();
            handleMouseInteraction();
            solveCollisions();
        }
    }

    public static void render() {
        debugPhysics();

        for (int i = 0; i < Vertices.size(); i++) {
            Vertices.get(i).draw();
        }

        if (PhysicsEditor.instance != null && selectedVertex != null) {
            List<Integer> constraintList = PhysicsEditor.constraintsList.getSelectedValuesList();

            if (!constraintList.isEmpty() && showSelectionConstraintCheckbox.isSelected()) {
                for (int i = 0; i < constraintList.size(); i++) {
                    Integer constraintIndex = constraintList.get(i);
                    Vertex constraintVertex = selectedVertex.edges.get(constraintIndex).v2;

                    float scale = 3.0f;
                    graphics2D.setColor(Color.red);
                    graphics2D.drawOval(
                            (int) (constraintVertex.currX - ((scale / 2) * constraintVertex.radius)),
                            (int) (constraintVertex.currY - ((scale / 2) * constraintVertex.radius)),
                            (int) (scale * constraintVertex.radius),
                            (int) (scale * constraintVertex.radius)
                    );
                }
            }
        }
    }

    /**
     * Calculates the interactions between Vertex objects with length constraints(Edges)
     * This calculation is performed various times (up to SIM_ACCURACY times)
     *
     * @see Edge
     */
    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++)
            for (int j = 0; j < Vertices.size(); j++)
                Vertices.get(j).solveConstraints();
    }

    /**
     * Handles the interaction between the mouse pointer and Verlet physics objects
     */
    private static void handleMouseInteraction() {
        if (PhysicsEditor.EDITOR_MODE == PhysicsEditorMode.Drag) {
            //  Handle if the Left mouse button is held down in drag mode
            if (engineSettings.leftMouseButtonIsDown) {
                //  If the point we want to drag isn't null and if the engine isn't paused move it around
                if (dragVertex != null) {
                    if (!engineSettings.paused) {
                        float s = dragVertex.mass * DRAG_FORCE;
                        dragVertex.currX += (MouseVec.x - dragVertex.currX) / s;
                        dragVertex.currY += (MouseVec.y - dragVertex.currY) / s;
                    }
                } else {
                    for (int i = 0; i < Vertices.size(); i++) {
                        Vertex searchVertex = Vertices.get(i);

                        if (searchVertex.contains(MouseVec.x, MouseVec.y)) {
                            dragVertex = searchVertex;
                            selectedVertex = searchVertex;
                            PhysicsEditor.setObjectProperties(selectedVertex);
                            PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                            break;
                        }
                    }
                }
            }
            else if (engineSettings.rightMouseButtonIsDown) {
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex vertex = Vertices.get(i);
                    float tearDistance = vertex.getDistance(MouseVec);

                    if (tearDistance < mouseTearSize) {
                        if (vertex == selectedVertex) {
                            vertex.edges.clear();
                            PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                        } else {
                            vertex.edges.clear();
                        }
                    }
                }
            }
        }
    }

    private static void solveCollisions() {
        for (int i = 0; i < Vertices.size(); i++)
            for (int j = 0; j < Vertices.size(); j++)
                Vertices.get(i).solveCollisions(Vertices.get(j));
    }

    private static void handleMovement() {
        for (int i = 0; i < Vertices.size(); i++)
            Vertices.get(i).accelerate();
    }

    public static void clearItems() {
        if (Vertices.size() > 0)
            Vertices.clear();
    }
}