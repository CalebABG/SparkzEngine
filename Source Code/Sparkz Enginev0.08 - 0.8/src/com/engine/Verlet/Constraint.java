package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Verlet.VSim.selectedPoint;

import java.awt.*;
import java.awt.geom.Line2D;

public class Constraint {
    public double restingDistance;
    public double stiffness;
    public double tearSensitivity;
    public Color color = Color.white;
    public Point p1, p2;
    public boolean drawThis = true;
    public boolean tearable = true;

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe) {
        p1 = link1;
        p2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        drawThis = drawMe;
        this.tearSensitivity = tearSensitivity;
    }

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe, Color c) {
        p1 = link1;
        p2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        drawThis = drawMe;
        color = c;
        this.tearSensitivity = tearSensitivity;
    }

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe, boolean tearable, Color c) {
        p1 = link1;
        p2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        this.tearable = tearable;
        drawThis = drawMe;
        color = c;
        this.tearSensitivity = tearSensitivity;
    }

    public void solve() {
        // calculate the distance between the two Points
        double dx = p1.currPos.x - p2.currPos.x;
        double dy = p1.currPos.y - p2.currPos.y;
        double dist = dx * dx + dy * dy;

        // find the difference, or the ratio of how far along the restingDistance the actual distance is.
        double delta = restingDistance / (dist + restingDistance) - 0.5;

        if (tearable) if (dist > tearSensitivity * tearSensitivity) {
            p1.removeLink(this);
            if (p1 == selectedPoint || p2 == selectedPoint) VPhysicsEditor.updateJListConstraints(selectedPoint.constraints);
        }
        //if (tearable) if (d > tearSensitivity) p2.removeLink(this);

        // Inverse the mass quantities
        double im1 = 1 / p1.mass, im2 = 1 / p2.mass;
        double scalarP1 = (im1 / (im1 + im2)) * stiffness;
        double scalarP2 = stiffness - scalarP1;

        dx *= delta;
        dy *= delta;

        // Push/pull based on mass
        // heavier objects will be pushed/pulled less than attached light objects
        p1.currPos.x += dx * scalarP1;
        p1.currPos.y += dy * scalarP1;
        p2.currPos.x -= dx * scalarP2;
        p2.currPos.y -= dy * scalarP2;
    }

    public void draw() {
        if (drawThis) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Double(p1.currPos.x, p1.currPos.y, p2.currPos.x, p2.currPos.y));
        }
    }
}
