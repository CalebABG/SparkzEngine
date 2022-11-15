package com.calebabg.physics;

import com.calebabg.enums.PhysicsEditorMode;
import com.calebabg.gui.PhysicsEditor;

import java.awt.*;
import java.util.List;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.gui.PhysicsEditor.showSelectionConstraintCheckbox;
import static org.apache.commons.math3.util.FastMath.pow;

public class Physics {
    private static final Font renderFont = new Font(Font.SERIF, Font.PLAIN, 14);
    public static final Color LINK_COLOR = Color.ORANGE.darker();
    public static final Color ITEM_COLOR = Color.ORANGE;
    public static final int MAX_COLLISIONS = 1000;
    public static float MESH_SIZE = 50.0f;
    public static float TEAR_DISTANCE = 80.0f;
    public static float GRAVITY = 0.251f;
    public static float AIR_VISCOSITY = 1.0f;
    public static float DRAG_FORCE = 5.0f; // The lower the stronger
    public static boolean DRAW_LINKS = true;
    public static boolean SEVERABLE = false;
    public static boolean ZERO_GRAVITY = false;
    public static boolean DEBUG_MODE = false;
    public static boolean COLLISION_DETECTION = false;
    public static int SIM_ACCURACY = 12; // The higher = better accuracy but slightly slower render
    public static Vertex selectedVertex;
    public static int mouseTearSize = (int) pow(3, 2);

    public static void toggleGravity() {
        ZERO_GRAVITY = !ZERO_GRAVITY;
    }

    public static void toggleDebug() {
        DEBUG_MODE = !DEBUG_MODE;
    }

    public static void resetSelectedVertex() {
        selectedVertex = null;
    }

    public static void debugPhysics() {
        if (!DEBUG_MODE) return;

        graphics2D.setFont(renderFont);
        graphics2D.setColor(Color.white);

        if (selectedVertex != null) {
            String text = "Select - " + selectedVertex;
            graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2);
        }
    }

    public static void pinSelectedPoint() {
        if (selectedVertex != null) selectedVertex.togglePin();
    }

    public static void update() {
        Physics.COLLISION_DETECTION = Vertices.size() <= Physics.MAX_COLLISIONS;
        solveConstraints();
        handleMovement();
        handleMouseInteraction();
        solveCollisions();
    }

    public static void render() {
        debugPhysics();

        for (int i = 0; i < Vertices.size(); i++)
            Vertices.get(i).draw();

        if (PhysicsEditor.instance != null && selectedVertex != null) {
            List<Integer> constraintList = PhysicsEditor.constraintsList.getSelectedValuesList();

            if (!constraintList.isEmpty() && showSelectionConstraintCheckbox.isSelected()) {
                for (int i = 0; i < constraintList.size(); i++) {
                    Integer constraintIndex = constraintList.get(i);
                    Vertex constraintVertex = selectedVertex.edges.get(constraintIndex).v2;

                    final float scale = 3.0f;
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

    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++)
            for (int j = 0; j < Vertices.size(); j++)
                Vertices.get(j).solveConstraints();
    }

    private static void handleMouseInteraction() {
        if (PhysicsEditor.EDITOR_MODE == PhysicsEditorMode.Drag) {
            if (engineSettings.leftMouseButtonIsDown) {
                // Todo: Fix drag stickiness on deselect click
                if (selectedVertex != null) {
                    float force = selectedVertex.mass * DRAG_FORCE;
                    selectedVertex.currX += (MouseVec.x - selectedVertex.currX) / force;
                    selectedVertex.currY += (MouseVec.y - selectedVertex.currY) / force;
                } else {
                    for (int i = 0; i < Vertices.size(); i++) {
                        Vertex searchVertex = Vertices.get(i);
                        if (searchVertex.contains(MouseVec.x, MouseVec.y)) {
                            selectedVertex = searchVertex;
                            PhysicsEditor.setSelectedPhysicsItemUIFields(selectedVertex);
                            PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                            break;
                        }
                    }
                }
            }
            else if (engineSettings.rightMouseButtonIsDown) {
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex vertex = Vertices.get(i);
                    if (vertex.getDistance(MouseVec) > mouseTearSize) continue;

                    vertex.edges.clear();
                    if (vertex == selectedVertex)
                        PhysicsEditor.updateConstraintsList(selectedVertex.edges);
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

    public static void clearAllItems() {
        Physics.resetSelectedVertex();
        if (Vertices.size() > 0) Vertices.clear();
    }
}