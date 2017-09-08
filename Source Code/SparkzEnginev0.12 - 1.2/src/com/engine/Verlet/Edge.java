package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import java.awt.*;
import java.awt.geom.Line2D;
import static com.engine.EngineHelpers.EConstants.graphics2D;
import static com.engine.Verlet.VSim.selectedVertex;
import static org.apache.commons.math3.util.FastMath.*;

public class Edge {
    public float restingDistance;
    public float stiffness;
    public float tearSensitivity;
    public Color color = Color.white;
    public Vertex p1, p2;
    public boolean drawThis = true;
    public boolean tearable = true;

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean drawMe) {
        p1 = link1;
        p2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        drawThis = drawMe;
        this.tearSensitivity = tearSensitivity;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean drawMe, Color c) {
        this(link1, link2, restingDist, stiff, tearSensitivity, drawMe);
        color = c;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean drawMe, boolean tearable, Color c) {
        this(link1, link2, restingDist, stiff, tearSensitivity, drawMe, c);
        this.tearable = tearable;
    }

    public void solve() {
        // calculate the distance between the two Points
        float dx = p1.currPos.x - p2.currPos.x;
        float dy = p1.currPos.y - p2.currPos.y;
        float dist = (float) sqrt(dx * dx + dy * dy);

        // find the difference, or the ratio of how far along the restingDistance the actual distance is.
        float delta = (float) (restingDistance / (dist + restingDistance) - 0.5);

        if (tearable) if (dist > tearSensitivity) {
            p1.removeLink(this);
            if (p1 == selectedVertex || p2 == selectedVertex) VPhysicsEditor.updateJListConstraints(selectedVertex.edges);
        }
        //if (tearable) if (d > tearSensitivity) p2.removeLink(this);

        // Inverse the mass quantities
        float im1 = 1 / p1.mass;
        float im2 = 1 / p2.mass;
        float scalarP1 = (im1 / (im1 + im2)) * stiffness;
        float scalarP2 = stiffness - scalarP1;

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
            graphics2D.draw(new Line2D.Float(p1.currPos.x, p1.currPos.y, p2.currPos.x, p2.currPos.y));
        }
    }
}
