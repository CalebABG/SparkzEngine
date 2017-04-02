package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;

import java.awt.geom.Line2D;
import java.util.List;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.GUIWindows.VPhysicsEditor.showselectionconstraint_checkbox;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.VSim.*;

public class Physics {
    public static void update() {
        if (!isPaused) {
            solveConstraints();
            handleMouseInteration();
            integrate();
            collisionsNoVelPres();    //Experiment w/ both collisions; disabling one will increase performance
            collisionsWithVelPres();
        } else handleMouseInteration();
    }

    public static void render() {
        for (int i = 0; i < POINTS.size(); i++) POINTS.get(i).draw();
        if (VPhysicsEditor.isActive() && selectedPoint != null && selectedPoint.constraints != null) {
            List<String> selectedValues = VPhysicsEditor.constraint_jlist.getSelectedValuesList();

            //  Check for all possibilities for list of selected values: if the list is null,
            //  if the list is empty, if the only value in the list is "-1"
            if (selectedValues == null || showselectionconstraint_checkbox == null ||
                    selectedValues.isEmpty() ||
                    selectedValues.get(0).equals("-1") ||
                    !showselectionconstraint_checkbox.isSelected()) return;
            else {
                for (int i = 0; i < selectedValues.size(); i++) {
                    Point constraintpoint = selectedPoint.constraints.get(Integer.parseInt(selectedValues.get(i))).p2;
                    graphics2D.setColor(Color.red);
                    graphics2D.draw(new Line2D.Double(selectedPoint.currPos.x, selectedPoint.currPos.y,
                                                      constraintpoint.currPos.x, constraintpoint.currPos.y));
                    graphics2D.setColor(Color.red);
                    double scale = 3.0;
                    graphics2D.draw(new Ellipse2D.Double(constraintpoint.currPos.x - ((scale / 2) * constraintpoint.radius),
                                                         constraintpoint.currPos.y - ((scale / 2) * constraintpoint.radius),
                                                         scale * constraintpoint.radius,
                                                         scale * constraintpoint.radius));
                }
            }
        }
    }

    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++) {
            for (int j = 0; j < POINTS.size(); j++) {
                POINTS.get(j).solveConstraints();
            }
        }
    }

    private static void handleMouseInteration() {
        if (VPhysicsEditor.EDITOR_MODE.equals(VPhysicsEditor.DRAG)) {
            //  Handle if the Left mouse button is held down in drag mode
            if (isLeftMouseDown) {
                //  If the the point we want to drag isn't ull and if the engine isn't paused move it around
                if (dragPoint != null) {
                    if (!isPaused) {
                        dragPoint.currPos.x += (Mouse.x - dragPoint.currPos.x) / (dragForce);
                        dragPoint.currPos.y += (Mouse.y - dragPoint.currPos.y) / (dragForce);
                    }
                }

                //  Else, if the point we want to drag is null, then search through the list of points we have so far
                //  Check the distance between a given point in the list and the mouse. If the mouse point (x, y) is within the radius
                //  of the given point in the list, then we've found a point to drag. Set the dragPoint variable to that point and break out of searching
                else {
                    for (int i = 0; i < POINTS.size(); i++) {
                        Point searchPoint = POINTS.get(i);
                        if (searchPoint.contains(Mouse)) {
                            dragPoint = searchPoint;
                            selectedPoint = searchPoint;
                            VPhysicsEditor.updateJListConstraints(selectedPoint.constraints);
                            break;
                        }
                    }
                }
            }

            //  Else, if the Right mouse button is held down, then look through the list of points and search for
            else if (isRightMouseDown) {
                for (int i = 0; i < POINTS.size(); i++) {
                    Point searchPoint = POINTS.get(i);
                    double tear_distance = searchPoint.getDistanceSq(Mouse);

                    if (tear_distance < mouseTearSize * mouseTearSize) {
                        if (searchPoint == selectedPoint) {
                            searchPoint.constraints.clear();
                            VPhysicsEditor.updateJListConstraints(selectedPoint.constraints);
                        }
                        else searchPoint.constraints.clear();
                    }
                }
            }
        }
    }

    private static void collisionsNoVelPres() {
        for (int j = 0; j < POINTS.size(); j++) {
            for (int k = 0; k < POINTS.size(); k++) {
                POINTS.get(j).solveCollisions(POINTS.get(k), false);
            }
        }
    }

    private static void collisionsWithVelPres() {
        for (int j = 0; j < POINTS.size(); j++) {
            for (int k = 0; k < POINTS.size(); k++) {
                POINTS.get(j).solveCollisions(POINTS.get(k), true);
            }
        }
    }

    private static void integrate() {
        for (int i = 0; i < POINTS.size(); i++) POINTS.get(i).accelerate();
    }
}