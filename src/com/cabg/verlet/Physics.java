package com.cabg.verlet;

import com.cabg.enums.PhysicsEditorMode;
import com.cabg.gui.PhysicsEditor;

import java.awt.*;
import java.util.List;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.gui.PhysicsEditor.showSelectionConstraintCheckbox;
import static com.cabg.verlet.PhysicsHandler.*;
import static com.cabg.verlet.Vertex.Vertices;

public class Physics {
    /**
     * Actively updates all Verlet Physics objects
     * If the engine is paused, then the solving of constraints and collision
     * detection is halted. When paused only mouse interactions with objects is updated.
     */
    public static void update() {
        if (!engineSettings.paused) {
            solveConstraints();
            handleMouseInteraction();
            integrate();
            collisionsNoVelPres();
        } else {
            handleMouseInteraction();
        }
    }

    /**
     * Renders all the Vertex objects, and also shows their constraints if selected
     *
     * @see Vertex
     * @see Edge
     */
    public static void render() {
        for (int i = 0; i < Vertices.size(); i++) {
            Vertices.get(i).draw();
        }

        if (PhysicsEditor.physicsEditor != null && selectedVertex != null) {
            List<Integer> selectedPointConstraintList = PhysicsEditor.constraintsList.getSelectedValuesList();

            // Check whether the list of constraints is empty; check if the point has any constrains and check whether to show the constraint at all (checkbox)
            if (!selectedPointConstraintList.isEmpty() && showSelectionConstraintCheckbox.isSelected()) {
                for (int i = 0; i < selectedPointConstraintList.size(); i++) {
                    Vertex constraintPoint = selectedVertex.edges.get(selectedPointConstraintList.get(i)).v2;

                    graphics2D.setColor(Color.red);
                    graphics2D.drawLine((int) selectedVertex.currX, (int) selectedVertex.currY, (int) constraintPoint.currX, (int) constraintPoint.currY);

                    float scale = 3.0f;
                    graphics2D.setColor(Color.red);
                    graphics2D.drawOval(
                            (int) (constraintPoint.currX - ((scale / 2) * constraintPoint.radius)),
                            (int) (constraintPoint.currY - ((scale / 2) * constraintPoint.radius)),
                            (int) (scale * constraintPoint.radius),
                            (int) (scale * constraintPoint.radius)
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
                        float s = dragVertex.mass * dragForce;
                        dragVertex.currX += (Mouse.x - dragVertex.currX) / s;
                        dragVertex.currY += (Mouse.y - dragVertex.currY) / s;
                    }
                } else {
                    for (int i = 0; i < Vertices.size(); i++) {
                        Vertex searchVertex = Vertices.get(i);

                        if (searchVertex.contains(Mouse.x, Mouse.y)) {
                            dragVertex = searchVertex;
                            selectedVertex = searchVertex;
                            PhysicsEditor.setObjectPropertiesOnSelect(selectedVertex);
                            PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                            break;
                        }
                    }
                }
            }
            else if (engineSettings.rightMouseButtonIsDown) {
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex searchVertex = Vertices.get(i);
                    float tearDistance = searchVertex.getDistance(Mouse);

                    if (tearDistance < mouseTearSize) {
                        if (searchVertex == selectedVertex) {
                            searchVertex.edges.clear();
                            PhysicsEditor.updateConstraintsList(selectedVertex.edges);
                        } else {
                            searchVertex.edges.clear();
                        }
                    }
                }
            }
        }
    }

    private static void collisionsNoVelPres() {
        for (int i = 0; i < Vertices.size(); i++) {
            for (int j = 0; j < Vertices.size(); j++) {
                Vertices.get(i).solveCollisions(Vertices.get(j), false);
            }
        }
    }

    private static void integrate() {
        for (int i = 0; i < Vertices.size(); i++)
            Vertices.get(i).accelerate();
    }
}