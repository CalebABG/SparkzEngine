package com.calebabg.physics;

import com.calebabg.gui.PhysicsEditor;

import java.awt.*;
import java.awt.geom.Line2D;

import static com.calebabg.core.EngineVariables.graphics2D;
import static org.apache.commons.math3.util.FastMath.*;

public class Edge {
    public Color color = Color.white;
    public Vertex v1, v2;
    public float restingDistance, stiffness, tearDistance;
    public boolean render, severable = true;

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff,
         float tearDistance, boolean render) {
        v1 = link1;
        v2 = link2;
        restingDistance = restingDist;
        stiffness = stiff;
        this.render = render;
        this.tearDistance = tearDistance;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff,
         float tearDistance, boolean render, Color c) {
        this(link1, link2, restingDist, stiff, tearDistance, render);
        color = c;
    }

    Edge(Vertex link1, Vertex link2, float restingDist, float stiff,
         float tearDistance, boolean render, boolean severable, Color c) {
        this(link1, link2, restingDist, stiff, tearDistance, render, c);
        this.severable = severable;
    }

    public void solve() {
        // calculate the distance between the two Points
        float dx = v1.currX - v2.currX;
        float dy = v1.currY - v2.currY;
        float dist = (float) sqrt(dx * dx + dy * dy);

        // find the difference, or the ratio of how far along the restingDistance the actual distance is.
        float delta = (restingDistance / (dist + restingDistance)) - 0.5f;

        if (severable && dist > tearDistance) {
            v1.removeLink(this);

            if (v1 == Physics.selectedVertex || v2 == Physics.selectedVertex)
                PhysicsEditor.updateConstraintsList(Physics.selectedVertex.edges);
        }

        // Inverse the mass quantities
        float im1 = 1 / v1.mass;
        float im2 = 1 / v2.mass;
        float scalarP1 = (im1 / (im1 + im2)) * stiffness;
        float scalarP2 = stiffness - scalarP1;

        dx *= delta;
        dy *= delta;

        // Push/pull based on mass
        // heavier objects will be pushed/pulled less than attached light objects
        v1.currX += dx * scalarP1;
        v1.currY += dy * scalarP1;
        v2.currX -= dx * scalarP2;
        v2.currY -= dy * scalarP2;
    }

    public void draw() {
        if (render) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Float(v1.currX, v1.currY, v2.currX, v2.currY));
        }
    }
}
