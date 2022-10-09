package com.cabg.verlet;

import com.cabg.gui.PhysicsEditor;

import java.awt.*;
import java.awt.geom.Line2D;

import static com.cabg.core.EngineVariables.graphics2D;
import static com.cabg.verlet.PhysicsHandler.selectedVertex;
import static org.apache.commons.math3.util.FastMath.*;

public class Edge {
    public float restingDistance;
    public float stiffness;
    public float tearSensitivity;
    public Color color = Color.white;
    public Vertex p1, p2;
    public boolean render;
    public boolean tearable = true;

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean render) {
        p1 = link1;
        p2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        this.render = render;
        this.tearSensitivity = tearSensitivity;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean render, Color c) {
        this(link1, link2, restingDist, stiff, tearSensitivity, render);
        color = c;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff, float tearSensitivity, boolean render, boolean tearable, Color c) {
        this(link1, link2, restingDist, stiff, tearSensitivity, render, c);
        this.tearable = tearable;
    }

    public void solve() {
        // calculate the distance between the two Points
        float dx = p1.currX - p2.currX;
        float dy = p1.currY - p2.currY;
        float dist = (float) sqrt(dx * dx + dy * dy);

        // find the difference, or the ratio of how far along the restingDistance the actual distance is.
        float delta = (restingDistance / (dist + restingDistance)) - 0.5f;

        if (tearable) {
            if (dist > tearSensitivity) {
                p1.removeLink(this);

                if (p1 == selectedVertex || p2 == selectedVertex)
                    PhysicsEditor.updateConstraintsList(selectedVertex.edges);
            }
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
        p1.currX += dx * scalarP1;
        p1.currY += dy * scalarP1;
        p2.currX -= dx * scalarP2;
        p2.currY -= dy * scalarP2;
    }

    public void draw() {
        if (render) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Float(p1.currX, p1.currY, p2.currX, p2.currY));
        }
    }
}
