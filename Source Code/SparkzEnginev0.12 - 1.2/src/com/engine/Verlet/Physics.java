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
            handleMouseInteration();
            integrate();
            collisionsNoVelPres();    //Experiment w/ both collisions; disabling one will increase performance
            collisionsWithVelPres();
        } else handleMouseInteration();
    }

    public static void render() {
        for (int i = Vertices.size() - 1; i >= 0; i--) Vertices.get(i).draw();

        if (VPhysicsEditor.isActive() && selectedVertex != null && selectedVertex.edges != null) {
            List<String> selectedValues = VPhysicsEditor.constraint_jlist.getSelectedValuesList();

            //  Check for all possibilities for list of selected values: if the list is null,
            //  if the list is empty, if the only value in the list is "-1"
            if (selectedValues == null || showselectionconstraint_checkbox == null ||
                    selectedValues.isEmpty() ||
                    selectedValues.get(0).equals("-1") ||
                    !showselectionconstraint_checkbox.isSelected()) return;
            else {
                for (int i = 0; i < selectedValues.size(); i++) {
                    Vertex constraintpoint = selectedVertex.edges.get(Integer.parseInt(selectedValues.get(i))).p2;
                    graphics2D.setColor(Color.red);
                    graphics2D.draw(new Line2D.Float(selectedVertex.currPos.x, selectedVertex.currPos.y,
                                                      constraintpoint.currPos.x, constraintpoint.currPos.y));
                    graphics2D.setColor(Color.red);
                    float scale = 3.0f;
                    graphics2D.draw(new Ellipse2D.Float(constraintpoint.currPos.x - ((scale / 2) * constraintpoint.radius),
                                                         constraintpoint.currPos.y - ((scale / 2) * constraintpoint.radius),
                                                         scale * constraintpoint.radius,
                                                         scale * constraintpoint.radius));
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

    private static void handleMouseInteration() {
        if (VPhysicsEditor.EDITOR_MODE.equals(VPhysicsEditor.DRAG)) {
            //  Handle if the Left mouse button is held down in drag mode
            if (LEFT_MOUSE_IS_DOWN.value()) {
                //  If the the point we want to drag isn't ull and if the engine isn't paused move it around
                if (dragVertex != null) {
                    if (!ENGINE_IS_PAUSED.value()) {
                        float s = dragVertex.mass * dragForce;
                        dragVertex.currPos.x += (Mouse.x - dragVertex.currPos.x) / s;
                        dragVertex.currPos.y += (Mouse.y - dragVertex.currPos.y) / s;
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
                    float tear_distance = searchVertex.getDistanceSq(Mouse);

                    if (tear_distance < mouseTearSize * mouseTearSize) {
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