package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;

public class Constraint {
    public double restingDistance, stiffness, tearSensitivity;
    public Color color = Color.white;
    public Point p1, p2;
    public boolean drawThis = true, tearable = true;

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe) {
        p1 = link1; p2 = link2;
        restingDistance = restingDist; stiffness = stiff;
        drawThis = drawMe;
        this.tearSensitivity = tearSensitivity;
    }

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe, Color c) {
        p1 = link1; p2 = link2;
        restingDistance = restingDist; stiffness = stiff;
        drawThis = drawMe; color = c;
        this.tearSensitivity = tearSensitivity;
    }

    Constraint(Point link1, Point link2, double restingDist, double stiff, double tearSensitivity, boolean drawMe, boolean tearable, Color c) {
        p1 = link1; p2 = link2;
        restingDistance = restingDist; stiffness = stiff;
        this.tearable = tearable; drawThis = drawMe; color = c;
        this.tearSensitivity = tearSensitivity;
    }

    public void solve(){
        // calculate the distance between the two Points
        double diffX = p1.currPos.x - p2.currPos.x;
        double diffY = p1.currPos.y - p2.currPos.y;
        double d = Math.sqrt(diffX * diffX + diffY * diffY);

        // find the difference, or the ratio of how far along the restingDistance the actual distance is.
        double difference = (restingDistance - d) / d;

        if (tearable) if (d > tearSensitivity) p1.removeLink(this);

        // Inverse the mass quantities
        double im1 = 1 / p1.mass, im2 = 1 / p2.mass;
        double scalarP1 = (im1 / (im1 + im2)) * stiffness, scalarP2 = stiffness - scalarP1;

        // Push/pull based on mass
        // heavier objects will be pushed/pulled less than attached light objects
        p1.currPos.x += diffX * scalarP1 * difference;
        p1.currPos.y += diffY * scalarP1 * difference;
        p2.currPos.x -= diffX * scalarP2 * difference;
        p2.currPos.y -= diffY * scalarP2 * difference;
    }

    public void draw() {
        if (drawThis) {
            graphics2D.setColor(color);
            graphics2D.drawLine((int) p1.currPos.x, (int) p1.currPos.y, (int) p2.currPos.x, (int) p2.currPos.y);
        }
    }
}
