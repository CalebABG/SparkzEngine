package com.cabg.verlet;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.gui.PhysicsEditor.selectionShowPointCheckbox;
import static org.apache.commons.math3.util.FastMath.*;

public class Vertex {
    public static final DecimalFormat dcFormat = new DecimalFormat("0.000");
    public float currX, currY, prevX, prevY;
    public float radius = 10.0f, mass = 1.0f, damping = 0.997f, stiffness = 1.0f;
    public boolean pinned = false, collidable = true;

    public Color color = Color.white;
    public ArrayList<Edge> edges = new ArrayList<>(30);

    public Vertex(float x, float y) {
        currX = x;
        currY = y;
        prevX = currX;
        prevY = currY;
    }

    public Vertex(float x, float y, Color color) {
        this(x, y);
        this.color = color;
    }

    public Vertex(float x, float y, float radius, Color color) {
        this(x, y, color);
        this.radius = radius;
    }

    public Vertex(float x, float y, float radius, float mass, Color color) {
        this(x, y, radius, color);
        this.mass = mass;
    }

    public Vertex(float x, float y, float radius, float mass, float stiffness, Color color) {
        this(x, y, radius, mass, color);
        this.stiffness = stiffness;
    }

    public Vertex(float x, float y, float radius, float mass, float stiffness,
                  float damping, boolean collidable, boolean pinned, Color color) {
        this(x, y, radius, mass, stiffness, color);
        this.damping = damping;
        this.collidable = collidable;
        this.pinned = pinned;
    }

    public void accelerate() {
        if (pinned) {
            currX = prevX;
            currY = prevY;
        } else {
            float tempX = currX, tempY = currY;
            float gravity = Physics.ZERO_GRAVITY ? 0.0f : Physics.GRAVITY;

            currX += ((Physics.AIR_VISCOSITY * currX) - (Physics.AIR_VISCOSITY * prevX)) * damping;
            currY += ((Physics.AIR_VISCOSITY * currY) - (Physics.AIR_VISCOSITY * prevY)) + gravity;

            prevX = tempX;
            prevY = tempY;
        }
    }

    public void solveConstraints() {
        for (int i = 0; i < edges.size(); i++)
            edges.get(i).solve();
    }

    public void solveCollisions(Vertex vertex) {
        if (currX - radius < 0) {
            currX = 2 * radius - currX;
        }

        if (currX + radius > canvas.getWidth()) {
            currX = 2 * (canvas.getWidth() - radius) - currX;
        }

        if (currY - radius < 0) {
            currY = 2 * radius - currY;
        }

        if (currY + radius > canvas.getHeight()) {
            currY = 2 * (canvas.getHeight() - radius) - currY;
        }

        if (Physics.COLLISION_DETECTION && collidable)
            collide(vertex);
    }

    public void collide(Vertex vertex) {
        if (vertex == this) return;

        float collisionDampening = .97f;

        float radii = radius + vertex.radius;
        float diffX = currX - vertex.currX;
        float diffY = currY - vertex.currY;

        float lenDiff = (float) sqrt(diffX * diffX + diffY * diffY);

        // First make sure they're intersecting
        if (!(lenDiff <= radii)) return;

        // Distance between centers
        float d = lenDiff;

        // Minimum translation distance to push balls apart after intersecting
        if (d <= 0) {
            d = radius / 2 + vertex.radius / 2;
        }

        // Find the difference, or the ratio of how far along the restingDistance the actual distance is.
        float difference = (radii - d) / d;

        // Inverse the mass quantities
        float im1 = 1 / mass;
        float im2 = 1 / vertex.mass;
        float scalarP1 = (im1 / (im1 + im2)) * stiffness;
        float scalarP2 = stiffness - scalarP1;

        // Push / Pull based on mass
        // Heavier objects will be pushed/pulled less than attached light objects
        currX += (diffX * scalarP1 * difference) * collisionDampening;
        currY += (diffY * scalarP1 * difference) * collisionDampening;
        vertex.currX -= (diffX * scalarP2 * difference) * collisionDampening;
        vertex.currY -= (diffY * scalarP2 * difference) * collisionDampening;
    }

    public void draw() {
        if (!edges.isEmpty()) {
            for (int i = 0; i < edges.size(); i++)
                edges.get(i).draw();
        }

        if (this == Physics.selectedVertex && engineSettings.leftMouseButtonIsDown) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Float(MouseVec.x, MouseVec.y, currX, currY));
            graphics2D.fill(new Ellipse2D.Float(currX - radius, currY - radius, 2 * radius, 2 * radius));
        }

        graphics2D.setColor(color);
        graphics2D.fill(new Ellipse2D.Float(currX - radius, currY - radius, 2 * radius, 2 * radius));

        if (this == Physics.selectedVertex && (selectionShowPointCheckbox != null && selectionShowPointCheckbox.isSelected())) {
            final float scale = 3.0f;
            graphics2D.setColor(Color.green);
            graphics2D.draw(new Ellipse2D.Float(currX - ((scale / 2) * radius), currY - ((scale / 2) * radius), scale * radius, scale * radius));
        }
    }

    public boolean contains(float x, float y) {
        float dx = abs(currX - x);
        float dy = abs(currY - y);
        return hypot(dx, dy) < radius;
    }

    public final float getVelocityX() {
        return currX - prevX;
    }

    public final float getVelocityY() {
        return currY - prevY;
    }

    public float getDistance(Vertex p) {
        return (float) hypot(currX - p.currX, currY - p.currY);
    }

    public float getDistance(Vec2 p) {
        return (float) hypot(currX - p.x, currY - p.y);
    }

    public void attachTo(Vertex P, float restingDist, float stiff) {
        attachTo(P, restingDist, stiff, 30, true);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, boolean drawLink) {
        attachTo(P, restingDist, stiff, 30, drawLink);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity) {
        attachTo(P, restingDist, stiff, tearSensitivity, true);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink);
        edges.add(lnk);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink, boolean severable, Color c) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink, severable, c);
        edges.add(lnk);
    }

    public void removeLink(Edge lnk) {
        edges.remove(lnk);
    }

    public void togglePin() {
        pinned = !pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void unPin() {
        pinned = false;
    }
    public void pin() {
        pinned = true;
    }

    public String toString() {
        return "Pos: (" + dcFormat.format(currX) + " , " + dcFormat.format(currY) +
                " ) | Velocity: (" + dcFormat.format(getVelocityX()) + " , " + dcFormat.format(getVelocityY()) +
                ") | Radius: " + dcFormat.format(radius) +
                " | Mass: " + dcFormat.format(mass);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Float.compare(vertex.currX, currX) == 0 &&
                Float.compare(vertex.currY, currY) == 0 && Float.compare(vertex.prevX, prevX) == 0 &&
                Float.compare(vertex.prevY, prevY) == 0 && Float.compare(vertex.radius, radius) == 0 &&
                Float.compare(vertex.mass, mass) == 0 && Float.compare(vertex.damping, damping) == 0 &&
                Float.compare(vertex.stiffness, stiffness) == 0 &&
                pinned == vertex.pinned && collidable == vertex.collidable &&
                Objects.equals(color, vertex.color) &&
                Objects.equals(edges, vertex.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currX, currY, prevX, prevY, radius, mass,
                damping, stiffness, pinned, collidable, color, edges);
    }
}