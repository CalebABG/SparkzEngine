package com.engine.Verlet;

import com.engine.EngineHelpers.EngineMethods;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.engine.GUIWindows.VPhysicsEditor.selectionshowpoint_checkbox;
import static com.engine.Utilities.Settings.dcFormat;
import static com.engine.Verlet.VSim.*;

public class Point {
    public static List<Point> POINTS = Collections.synchronizedList(new ArrayList<>());
    public Vect2 currPos;
    public Vect2 prevPos;
    public double radius = 10;
    public double mass = 1;
    public double damping = 0.85;
    public double stiffness = 1;
    public int index;
    public boolean pinned = false;
    public boolean collidable = true;
    public double pinX, pinY;
    public Color color = Color.white;
    public List<Constraint> constraints = Collections.synchronizedList(new ArrayList<>());

    public Point(double xPos, double yPos) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public Point(double xPos, double yPos, Color c) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        color = c;
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public Point(double xPos, double yPos, double radius, Color c) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        this.radius = radius;
        color = c;
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public Point(double xPos, double yPos, double radius, double mass, Color c) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        this.radius = radius;
        this.mass = mass;
        color = c;
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public Point(double xPos, double yPos, double radius, double mass, double stiffness, Color c) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        this.radius = radius;
        this.mass = mass;
        this.stiffness = stiffness;
        color = c;
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public Point(double xPos, double yPos, double radius, double mass, double stiffness, double damping, boolean collidable, boolean pinned, Color c) {
        currPos = new Vect2(xPos, yPos);
        prevPos = new Vect2(currPos.x, currPos.y);
        this.radius = radius;
        this.mass = mass;
        this.stiffness = stiffness;
        this.damping = damping;
        this.collidable = collidable;
        this.pinned = pinned;
        color = c;
        POINTS.add(this);
        index = POINTS.size() - 1;
    }

    public void accelerate(){
        if (pinned) {currPos.x = pinX; currPos.y = pinY; return;}

        gravity = (ZERO_GRAVITY) ? 0.0 : GConstant;
        Vect2 temp = new Vect2(currPos.x, currPos.y);
        currPos.add((air_viscosity * currPos.x - air_viscosity * prevPos.x), (air_viscosity * currPos.y - air_viscosity * prevPos.y) + gravity);
        prevPos = temp;
    }

    public void solveConstraints() {for (int i = 0; i < constraints.size(); i++) constraints.get(i).solve();}

    public void solveCollisions (Point p2, boolean preserveImpulse) {
        double x = currPos.x;
        double y = currPos.y;
        double vx = 0;
        double vy = 0;
        double prevX = prevPos.x;
        double prevY = prevPos.y;
        double canvaswidth = canvas.getWidth();
        double canvasheight = canvas.getHeight();

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

    public void collide(Point p2) {
        if (p2 != this) {
            double radii = radius + p2.radius;
            double diffX = currPos.x - p2.currPos.x;
            double diffY = currPos.y - p2.currPos.y;
            double diffSquared = diffX * diffX + diffY * diffY;

            if (diffSquared <= radii * radii) { // first make sure they're intersecting
                // distance between centers
                double d = Math.sqrt(diffSquared);

                // minimum translation distance to push balls apart after intersecting
                if (d <= 0) d = radius / 2 + p2.radius / 2;

                // find the difference, or the ratio of how far along the restingDistance the actual distance is.
                double difference = (radii - d) / d;

                // Inverse the mass quantities
                double im1 = 1 / mass;
                double im2 = 1 / p2.mass;
                double scalarP1 = (im1 / (im1 + im2)) * stiffness;
                double scalarP2 = stiffness - scalarP1;

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
        if (this == dragPoint) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Double(Mouse.x, Mouse.y, currPos.x, currPos.y));
            graphics2D.fill(new Ellipse2D.Double(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
        }
        if (this == selectedPoint && (selectionshowpoint_checkbox != null && selectionshowpoint_checkbox.isSelected())){
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Double(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
            graphics2D.setColor(Color.green);
            double scale = 3.0;
            graphics2D.draw(new Ellipse2D.Double(currPos.x - ((scale / 2) * radius), currPos.y - ((scale / 2) * radius), scale * radius, scale * radius));
        }
        else {
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Double(currPos.x - radius, currPos.y - radius, 2 * radius, 2 * radius));
        }

        if (constraints.size() > 0) {for (int i = 0; i < constraints.size(); i++) constraints.get(i).draw();}
    }

    public double getArea() {return Math.PI * Math.pow(radius, 2);}
    public double getCircumference() {return 2 * Math.PI * radius;}

    public boolean contains(java.awt.Point p) {
        double dx = Math.abs(p.x - currPos.x), dy = Math.abs(p.y - currPos.y);
        return (dx * dx + dy * dy <= radius * radius);
    }

    public boolean contains(Vect2 p) {
        double dx = Math.abs(p.x - currPos.x), dy = Math.abs(p.y - currPos.y);
        return (dx * dx + dy * dy <= radius * radius);
    }

    public double getVelocityX(){return currPos.x - prevPos.x;}
    public double getVelocityY(){return currPos.y - prevPos.y;}
    public Vect2 getVelocity(){return new Vect2(getVelocityX(), getVelocityY());}
    public double getDistance(java.awt.Point p) {return p.distance(currPos.x, currPos.y);}
    public double getDistanceSq(java.awt.Point p) {return p.distanceSq(currPos.x, currPos.y);}
    public double getDistanceSq(Vect2 p) {return p.distSq(currPos);}
    public double getDistanceSq(Point p) {return p.currPos.distSq(currPos);}

    public void attachTo(Point P, double restingDist, double stiff) {attachTo(P, restingDist, stiff, 30, true);}
    public void attachTo(Point P, double restingDist, double stiff, boolean drawLink) {attachTo(P, restingDist, stiff, 30, drawLink);}
    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity) {attachTo(P, restingDist, stiff, tearSensitivity, true);}

    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink);
        constraints.add(lnk);
    }

    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink, Color c) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink, c);
        constraints.add(lnk);
    }

    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink, boolean tearable, Color c) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink, tearable, c);
        constraints.add(lnk);
    }

    public void removeLink(Constraint lnk) {constraints.remove(lnk);}
    public void togglePin(){pinned = EngineMethods.toggle(pinned); pinX = currPos.x; pinY = currPos.y;}
    public void pinTo(double pX, double pY) {pinned = true; pinX = pX; pinY = pY;}
    public void unPin(){pinned = false;}

    public String toString() {
        return "[ " + "CurrPos: [" + dcFormat.format(currPos.x) + " , " + dcFormat.format(currPos.y) +
                " ] | Acceleration: [" + dcFormat.format(getVelocityX()) + " , " + dcFormat.format(getVelocityY()) +
                "] | Radius: " + dcFormat.format(radius) +
                " | Index: " + index +
                " | Mass: " + dcFormat.format(mass) +
                " | Heading: " + dcFormat.format(currPos.heading()) + " ]";
    }
}