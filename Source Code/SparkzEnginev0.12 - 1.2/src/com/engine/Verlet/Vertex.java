package com.engine.Verlet;

import com.engine.EngineHelpers.EngineMethods;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EConstants.PI;
import static com.engine.GUIWindows.VPhysicsEditor.selectionshowpoint_checkbox;
import static com.engine.Verlet.VSim.*;
import static org.apache.commons.math3.util.FastMath.*;

public class Vertex {
    public static final DecimalFormat dcFormat = new DecimalFormat("0.000");
    public static final List<Vertex> Vertices = new ArrayList<>();

    public Vect2 currPos;
    public Vect2 prevPos;
    public float radius = 10;
    public float mass = 1;
    public float damping = 0.85f;
    public float stiffness = 1;
    public final int index;
    public boolean pinned = false;
    public boolean collidable = true;
    public float pinX, pinY;
    public Color color = Color.white;
    public final List<Edge> edges = new ArrayList<>();

    public Vertex(float xPos, float yPos) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        Vertices.add(this);
        index = Vertices.size() - 1;
    }

    public Vertex(float xPos, float yPos, Color c) {
        this(xPos, yPos);
        color = c;
    }

    public Vertex(float xPos, float yPos, float radius, Color c) {
        this(xPos, yPos, c);
        this.radius = radius;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, Color c) {
        this(xPos, yPos, radius, c);
        this.mass = mass;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, float stiffness, Color c) {
        this(xPos, yPos, radius, mass, c);
        this.stiffness = stiffness;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, float stiffness, float damping, boolean collidable, boolean pinned, Color c) {
        this(xPos, yPos, radius, mass, stiffness, c);
        this.damping = damping;
        this.collidable = collidable;
        this.pinned = pinned;
    }

    public void accelerate(){
        if (pinned) {currPos.x = pinX; currPos.y = pinY; return;}

        gravity = (ZERO_GRAVITY) ? 0.0f : GConstant;
        Vect2 temp = currPos.copy();
        currPos.add(air_viscosity * currPos.x - air_viscosity * prevPos.x, (air_viscosity * currPos.y - air_viscosity * prevPos.y) + gravity);
        prevPos = temp;
    }

    public void solveConstraints() {for (int i = edges.size() - 1; i >= 0; i--) edges.get(i).solve();}

    public void solveCollisions (Vertex p2, boolean preserveImpulse) {
        float x = currPos.x;
        float y = currPos.y;
        float vx = 0;
        float vy = 0;
        float prevX = prevPos.x;
        float prevY = prevPos.y;
        float canvaswidth = canvas.getWidth();
        float canvasheight = canvas.getHeight();

        if (preserveImpulse) {
            vx = (prevX - x) * damping;
            vy = (prevY - y) * damping;
        }

        if (x - radius < 0) {
            x = 2 * (radius) - x;
            if (preserveImpulse) prevX = (x - vx);
        }
        if (x + radius > canvaswidth) {
            x = 2 * (canvaswidth - radius) - x;
            if (preserveImpulse) prevX = (x - vx);
        }
        if (y - radius < 0) {
            y = 2 * (radius) - y;
            if (preserveImpulse) prevY = (y - vy);
        }
        if (y + radius > canvasheight) {
            y = 2 * (canvasheight - radius) - y;
            if (preserveImpulse) prevY = (y - vy);
        }

        currPos.x = x;
        currPos.y = y;
        prevPos.x = prevX;
        prevPos.y = prevY;

        if (COLLISION_DETECTION) {if (collidable) {collide(p2);}}
    }

    public void collide(Vertex p2) {
        if (p2 != this) {
            float radii = radius + p2.radius;
            float diffX = currPos.x - p2.currPos.x;
            float diffY = currPos.y - p2.currPos.y;
            float lenDiff = (float) sqrt(diffX * diffX + diffY * diffY);

            if (lenDiff <= radii) { // first make sure they're intersecting
                // distance between centers
                float d = lenDiff;
                //float d = sqrt(diffSquared);

                // minimum translation distance to push balls apart after intersecting
                if (d <= 0) d = radius / 2 + p2.radius / 2;

                // find the difference, or the ratio of how far along the restingDistance the actual distance is.
                float difference = (radii - d) / d;

                // Inverse the mass quantities
                float im1 = 1 / mass;
                float im2 = 1 / p2.mass;
                float scalarP1 = (im1 / (im1 + im2)) * stiffness;
                float scalarP2 = stiffness - scalarP1;

                // Push/pull based on mass
                // heavier objects will be pushed/pulled less than attached light objects
                currPos.x    += diffX * scalarP1 * difference;
                currPos.y    += diffY * scalarP1 * difference;
                p2.currPos.x -= diffX * scalarP2 * difference;
                p2.currPos.y -= diffY * scalarP2 * difference;
            }
        }
    }

    public void draw() {
        if (edges.size() > 0) {for (int i = 0; i < edges.size(); i++) edges.get(i).draw();}

        if (this == dragVertex) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Float(Mouse.x, Mouse.y, currPos.x, currPos.y));
            graphics2D.fill(new Ellipse2D.Float(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
        }
        if (this == selectedVertex && (selectionshowpoint_checkbox != null && selectionshowpoint_checkbox.isSelected())){
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Float(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
            graphics2D.setColor(Color.green);
            float scale = 3.0f;
            graphics2D.draw(new Ellipse2D.Float(currPos.x - ((scale / 2) * radius), currPos.y - ((scale / 2) * radius), scale * radius, scale * radius));
        }
        else {
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Float(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
        }
    }

    public float getArea() {return (float) (PI * pow(radius, 2));}
    public float getCircumference() {return 2 * PI * radius;}

    public boolean contains(Point p) {
        float dx = abs(p.x - currPos.x);
        float dy = abs(p.y - currPos.y);
        return dx * dx + dy * dy <= radius * radius;
    }

    public boolean contains(Vect2 p) {
        float dx = abs(p.x - currPos.x);
        float dy = abs(p.y - currPos.y);
        return dx * dx + dy * dy <= radius * radius;
    }


    public Vect2 getCenter(){return new Vect2(currPos.x + (radius / 2), currPos.y + (radius / 2));}
    public float getVelocityX(){return currPos.x - prevPos.x;}
    public float getVelocityY(){return currPos.y - prevPos.y;}
    public Vect2 getVelocity(){return new Vect2(getVelocityX(), getVelocityY());}
    //public float getDistance(Point p) {return p.distance(currPos.x, currPos.y);}
    public float getDistanceSq(Point p) {return (float) p.distanceSq(currPos.x, currPos.y);}
    public float getDistance(Vertex p) {return p.currPos.dist(currPos);}
    public float getDistanceSq(Vect2 p) {return p.distSq(currPos);}
    public float getDistanceSq(Vertex p) {return p.currPos.distSq(currPos);}

    public void attachTo(Vertex P, float restingDist, float stiff) {attachTo(P, restingDist, stiff, 30, true);}
    public void attachTo(Vertex P, float restingDist, float stiff, boolean drawLink) {attachTo(P, restingDist, stiff, 30, drawLink);}
    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity) {attachTo(P, restingDist, stiff, tearSensitivity, true);}

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink);
        edges.add(lnk);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink, Color c) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink, c);
        edges.add(lnk);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink, boolean tearable, Color c) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink, tearable, c);
        edges.add(lnk);
    }

    public void removeLink(Edge lnk) {
        edges.remove(lnk);}
    public void togglePin(){pinned = EngineMethods.toggle(pinned); pinX = currPos.x; pinY = currPos.y;}
    public void pinTo(float pX, float pY) {pinned = true; pinX = pX; pinY = pY;}
    public void unPin(){pinned = false;}

    public String toString() {
        return "Pos: (" + dcFormat.format(currPos.x) + " , " + dcFormat.format(currPos.y) +
                " ) | Accel: (" + dcFormat.format(getVelocityX()) + " , " + dcFormat.format(getVelocityY()) +
                ") | Radius: " + dcFormat.format(radius) +
                " | Mass: " + dcFormat.format(mass);
    }
}