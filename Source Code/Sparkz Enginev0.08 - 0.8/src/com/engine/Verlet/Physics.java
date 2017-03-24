package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Verlet.Point.POINTS;
import static com.engine.Verlet.VSim.*;

public class Physics {
    public static void update() {
        if (!isPaused) {
            solveConstraints();
            handleMouseInteration();
            accelerate();
            circleTcircleNoVelPres();
            circleTcircleWithVelPres();
        } else handleMouseInteration();
    }

    public static void render() {
        for (int i = 0; i < POINTS.size(); i++) POINTS.get(i).draw();
    }

    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++) {
            for (int j = 0; j < POINTS.size(); j++) {
                POINTS.get(j).solveConstraints();
            }
        }
    }

    private static void handleMouseInteration() {
        if (isLeftMouseDown) {
            if (VSim.dragPoint != null) {
                if (!isPaused) {
                    dragPoint.currPos.x += (Mouse.x - dragPoint.currPos.x) / (dragForce);
                    dragPoint.currPos.y += (Mouse.y - dragPoint.currPos.y) / (dragForce);
                }
            }
        }

        for (int i = 0; i < POINTS.size(); i++) {
            Point point = POINTS.get(i);
            double dist = point.getDistanceSq(Mouse);
            if (isLeftMouseDown && dragPoint == null) {
                if (dist < .6 * point.radius * point.radius) {
                    dragPoint = point;
                    break;
                }
            }
            else if (isRightMouseDown) {if (dist < (VSim.mouseTearSize * VSim.mouseTearSize)) point.constraints.clear();}
        }
    }

    private static void circleTcircleNoVelPres() {
        for (int j = 0; j < POINTS.size(); j++) {
            for (int k = 0; k < POINTS.size(); k++) {
                POINTS.get(j).solveCollisions(POINTS.get(k), false);
            }
        }
    }

    private static void circleTcircleWithVelPres() {
        for (int j = 0; j < POINTS.size(); j++) {
            for (int k = 0; k < POINTS.size(); k++) {
                POINTS.get(j).solveCollisions(POINTS.get(k), true);
            }
        }
    }

    private static void accelerate() {
        for (int i = 0; i < POINTS.size(); i++) POINTS.get(i).accelerate();
    }
}