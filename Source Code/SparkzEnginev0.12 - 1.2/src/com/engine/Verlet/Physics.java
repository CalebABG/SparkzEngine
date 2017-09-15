package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EBOOLS.ENGINE_IS_PAUSED;
import static com.engine.EngineHelpers.EBOOLS.LEFT_MOUSE_IS_DOWN;
import static com.engine.EngineHelpers.EBOOLS.RIGHT_MOUSE_IS_DOWN;
import static com.engine.GUIWindows.VPhysicsEditor.showselectionconstraint_checkbox;
import static com.engine.Verlet.Vertex.Vertices;
import static com.engine.Verlet.VSim.*;

public class Physics {
    public static void update() {
        if (!ENGINE_IS_PAUSED.value()) {
            solveConstraints();
            handleMouseInteraction();
            integrate();
            collisionsNoVelPres();    //Experiment w/ both collisions; disabling one will increase performance
            collisionsWithVelPres();
        } else handleMouseInteraction();
    }

    public static void render() {
        for (int i = Vertices.size() - 1; i >= 0; i--) Vertices.get(i).draw();

        if (VPhysicsEditor.instance != null && selectedVertex != null) {
            List<Integer> selectedPointConstraintList = VPhysicsEditor.constraint_jlist.getSelectedValuesList();

            //  Check for all possibilities for list of selected values: if the list is null,
            //  if the list is empty, if the only value in the list is "-1"
            if (selectedPointConstraintList != null && showselectionconstraint_checkbox != null &&
                !selectedPointConstraintList.isEmpty() && !(selectedPointConstraintList.get(0) == -1) && showselectionconstraint_checkbox.isSelected()){
                for (int i = 0; i < selectedPointConstraintList.size(); i++) {
                    Vertex constraintPoint = selectedVertex.edges.get(selectedPointConstraintList.get(i)).p2;
                    graphics2D.setColor(Color.red);
                    graphics2D.draw(new Line2D.Float(selectedVertex.currX, selectedVertex.currY,
                            constraintPoint.currX, constraintPoint.currY));
                    graphics2D.setColor(Color.red);
                    float scale = 3.0f;
                    graphics2D.draw(new Ellipse2D.Float(constraintPoint.currX - ((scale / 2) * constraintPoint.radius),
                            constraintPoint.currY - ((scale / 2) * constraintPoint.radius),
                            scale * constraintPoint.radius,
                            scale * constraintPoint.radius));
                }
            }
        }
    }

    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++) {
            for (int j = 0; j < Vertices.size(); j++) {
                Vertices.get(j).solveConstraints();
            }
        }
    }

    private static void handleMouseInteraction() {
        if (VPhysicsEditor.EDITOR_MODE == VModes.EditorModes.Drag) {
            //  Handle if the Left mouse button is held down in drag mode
            if (LEFT_MOUSE_IS_DOWN.value()) {
                //  If the the point we want to drag isn't ull and if the engine isn't paused move it around
                if (dragVertex != null) {
                    if (!ENGINE_IS_PAUSED.value()) {
                        float s = dragVertex.mass * dragForce;
                        dragVertex.currX += (Mouse.x - dragVertex.currX) / s;
                        dragVertex.currY += (Mouse.y - dragVertex.currY) / s;
                    }
                }

                //  Else, if the point we want to drag is null, then search through the list of points we have so far
                //  Check the distance between a given point in the list and the mouse. If the mouse point (x, y) is within the radius
                //  of the given point in the list, then we've found a point to drag. Set the dragPoint variable to that point and break out of searching
                else {
                    for (int i = 0; i < Vertices.size(); i++) {
                        Vertex searchVertex = Vertices.get(i);
                        if (searchVertex.contains(Mouse)) {
                            dragVertex = searchVertex;
                            selectedVertex = searchVertex;
                            VPhysicsEditor.updateJListConstraints(selectedVertex.edges);
                            break;
                        }
                    }
                }
            }

            //  Else, if the Right mouse button is held down, then look through the list of points and search for
            else if (RIGHT_MOUSE_IS_DOWN.value()) {
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex searchVertex = Vertices.get(i);
                    float tear_distance = searchVertex.getDistance(Mouse);

                    if (tear_distance < mouseTearSize) {
                        if (searchVertex == selectedVertex) {
                            searchVertex.edges.clear();
                            VPhysicsEditor.updateJListConstraints(selectedVertex.edges);
                        }
                        else searchVertex.edges.clear();
                    }
                }
            }
        }
    }

    private static void collisionsNoVelPres() {
        for (int j = 0; j < Vertices.size(); j++) {
            for (int k = 0; k < Vertices.size(); k++) {
                Vertices.get(j).solveCollisions(Vertices.get(k), false);
            }
        }
    }

    private static void collisionsWithVelPres() {
        for (int j = 0; j < Vertices.size(); j++) {
            for (int k = 0; k < Vertices.size(); k++) {
                Vertices.get(j).solveCollisions(Vertices.get(k), true);
            }
        }
    }

    private static void integrate() {
        for (int i = 0; i < Vertices.size(); i++) Vertices.get(i).accelerate();
    }
}