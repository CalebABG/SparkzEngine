package com.engine.Verlet;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.engine.Utilities.Settings.dcFormat;
import static com.engine.Verlet.VSim.*;

public class Point {
    public static List<Point> POINTS = Collections.synchronizedList(new ArrayList<>());
    public Vect2D currPos;
    public Vect2D prevPos;
    public double radius;
    public double mass = 1;
    public double damping = .85;
    public int index;
    public boolean pinned = false;
    public boolean collidable = true;
    public double pinX, pinY;
    public Color color = Color.white;
    public List<Constraint> constraints = Collections.synchronizedList(new ArrayList<>());

    public Point(double xPos, double yPos) {
        currPos = new Vect2D(xPos, yPos); prevPos = new Vect2D(currPos.x, currPos.y); radius = 10; POINTS.add(this); this.index = POINTS.size()-1;
    }

    public Point(double xPos, double yPos, Color c) {
        currPos = new Vect2D(xPos, yPos); prevPos = new Vect2D(currPos.x, currPos.y); radius = 10; color = c; POINTS.add(this); this.index = POINTS.size()-1;
    }

    public Point(double xPos, double yPos, double radius, Color c) {
        currPos = new Vect2D(xPos, yPos); prevPos = new Vect2D(currPos.x, currPos.y); this.radius = radius; color = c; POINTS.add(this); this.index = POINTS.size()-1;
    }

    public Point(double xPos, double yPos, double radius, double mass, Color c) {
        currPos = new Vect2D(xPos, yPos); prevPos = new Vect2D(currPos.x, currPos.y); this.radius = radius; this.mass = mass; color = c; POINTS.add(this); this.index = POINTS.size()-1;
    }

    public void solveCollisions (Point p2, boolean preserveImpulse) {
        double x = currPos.x, y = currPos.y, vx = 0, vy = 0;
        double prevX = prevPos.x, prevY = prevPos.y;

        if (preserveImpulse) {
            vx = ((prevX - x) * damping); vy = ((prevY - y) * damping);
        }

        if (x - radius < 0) {
            x = 2 * (radius) - x;
            if (preserveImpulse) {prevX = (x - vx);}
        }
        if (x + radius > canvas.getWidth()) {
            x = 2 * (canvas.getWidth() - radius) - x;
            if (preserveImpulse) {prevX = (x - vx);}
        }
        if (y - radius < 0) {
            y = 2 * (radius) - y;
            if (preserveImpulse) {prevY = (y - vy);}
        }
        if (y + radius > canvas.getHeight()) {
            y = 2 * (canvas.getHeight() - radius) - y;
            if (preserveImpulse) {prevY = (y - vy);}
        }

        currPos.x = x;
        currPos.y = y;
        prevPos.x = prevX;
        prevPos.y = prevY;

        if (COLLISION_DETECTION) {if (collidable) {collide(p2, preserveImpulse);}}
    }

    public void collide(Point p2, boolean preserveImpulse) {
        if (p2 != this) {
            double x = currPos.x;
            double y = currPos.y;

            double otherX = p2.currPos.x;
            double otherY = p2.currPos.y;

            double radii = radius / 2 + p2.radius / 2;

            double diffX = x - otherX;
            double diffY = y - otherY;

            double diffSquared = diffX * diffX + diffY * diffY;

            if (diffSquared <= radii * radii) { // first make sure they're intersecting

                // Previous velocity
                double v1x = x - prevPos.x;
                double v1y = y - prevPos.y;
                double v2x = otherX - p2.prevPos.x;
                double v2y = otherY - p2.prevPos.y;

                // distance between centers
                double d = Math.sqrt(diffSquared);

                // minimum translation distance to push balls apart after intersecting
                double mtdX;
                double mtdY;
                if (d <= 0) {
                    d = radius / 2 + p2.radius / 2;
                    diffX = radius + p2.radius;
                    diffY = 0;
                }

                double difference = ((radius / 2 + p2.radius / 2) - d) / d;

                mtdX = diffX * difference;
                mtdY = diffY * difference;

                // resolve intersection
                double m0 = mass;
                double m1 = p2.mass;
                double tm = (m0 + m1);
                        m0 = m0 / tm;
                        m1 = m1 / tm;
//                double im1 = 1f / mass; // inverse mass quantities
//                double im2 = 1f / p2.mass;

                // push-pull them based on mass

                currPos.x += mtdX * m0;
                currPos.y += mtdY * m0;

                p2.currPos.x -= mtdX * m1;
                p2.currPos.y -= mtdY * m1;
//                currPos.x += mtdX * (im1 / (im1 + im2));
//                currPos.y += mtdY * (im1 / (im1 + im2));
//
//                p2.currPos.x -= mtdX * (im1 / (im1 + im2));
//                p2.currPos.y -= mtdY * (im1 / (im1 + im2));

                if (preserveImpulse) {
                    double f1 = (damping * (diffX * v1x + diffY * v1y)) / diffSquared;
                    double f2 = (damping * (diffX * v2x + diffY * v2y)) / diffSquared;

                    v1x += f2 * diffX - f1 * diffX;
                    v2x += f1 * diffX - f2 * diffX;
                    v1y += f2 * diffY - f1 * diffY;
                    v2y += f1 * diffY - f2 * diffY;

                    prevPos.x = currPos.x - v1x;
                    prevPos.y = currPos.y - v1y;

                    p2.prevPos.x = p2.currPos.x - v2x;
                    p2.prevPos.y = p2.currPos.y - v2y;
                }
            }
        }
    }

    public double getVelocityX(){return currPos.x - prevPos.x;}
    public double getVelocityY(){return currPos.y - prevPos.y;}
    public Vect2D getVelocity(){return new Vect2D(getVelocityX(), getVelocityY());}

    public void draw() {
        if (this == dragPoint) {
            if (DEBUG_MODE) {
                graphics2D.setColor(color);
                graphics2D.drawLine(Mouse.x, Mouse.y, (int) currPos.x, (int) currPos.y);
                graphics2D.fill(new Ellipse2D.Double((int) currPos.x - radius / 2, (int) currPos.y - radius / 2, radius, radius));
                graphics2D.setColor(Color.red);
                graphics2D.draw(new Ellipse2D.Double((int) currPos.x - radius, (int) currPos.y - radius, 2 * radius, 2 * radius));
            }
            else {
                graphics2D.setColor(color);
                graphics2D.drawLine(Mouse.x, Mouse.y, (int) currPos.x, (int) currPos.y);
                graphics2D.fill(new Ellipse2D.Double((int) currPos.x - radius / 2, (int) currPos.y - radius / 2, radius, radius));
            }
        }
        else {
            if (DEBUG_MODE) {
                graphics2D.setColor(Color.red);
                graphics2D.draw(new Ellipse2D.Double((int) currPos.x - radius, (int) currPos.y - radius, 2 * radius, 2 * radius));
                graphics2D.setColor(color);
                graphics2D.fill(new Ellipse2D.Double((int) currPos.x - radius / 2, (int) currPos.y - radius / 2, radius, radius));
            }
            else {
                graphics2D.setColor(color);
                graphics2D.fill(new Ellipse2D.Double((int) currPos.x - radius / 2, (int) currPos.y - radius / 2, radius, radius));
            }
        }

        if (constraints.size() > 0) {for (int i = 0; i < constraints.size(); i++) constraints.get(i).draw();}
    }

    public void solveConstraints() {for (int i = 0; i < constraints.size(); i++) constraints.get(i).solve();}

    public double distanceTo(Point p1){
        double dx = p1.currPos.x - currPos.x, dy = p1.currPos.y - currPos.y; return Math.sqrt(dx * dx + dy * dy);
    }
    public double getDistance(java.awt.Point p) {return p.distance(currPos.x, currPos.y);}
    public double getDistanceSq(java.awt.Point p) {
        return p.distanceSq(currPos.x, currPos.y);
    }

    public void attachTo(Point P, double restingDist, double stiff) {
        attachTo(P, restingDist, stiff, 30, true);
    }
    public void attachTo(Point P, double restingDist, double stiff, boolean drawLink) {attachTo(P, restingDist, stiff, 30, drawLink);}
    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity) {attachTo(P, restingDist, stiff, tearSensitivity, true);}
    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink); constraints.add(lnk);
    }

    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink, Color c) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink, c); constraints.add(lnk);
    }

    public void attachTo(Point P, double restingDist, double stiff, double tearSensitivity, boolean drawLink, boolean tearable, Color c) {
        Constraint lnk = new Constraint(this, P, restingDist, stiff, tearSensitivity, drawLink, tearable, c);
        constraints.add(lnk);
    }

    public void removeLink(Constraint lnk) {
        constraints.remove(lnk);
    }
    public void pinTo(double pX, double pY) {pinned = true; pinX = pX; pinY = pY;}
    public void unPin(){pinned = false;}

    public static void displayPointArraySize(){
        if (VSim.DEBUG_MODE) {
            graphics2D.setColor(Color.white);
            graphics2D.drawString("Point Array Size: " + POINTS.size(), canvas.getWidth() / 2 - 80, canvas.getHeight() / 2 + 20);
        }
    }

    public String toString() {
        return "[ " + "CurrPos: [" + dcFormat.format(currPos.x) + " , " + dcFormat.format(currPos.y) +
                " ] | Acceleration: [" + dcFormat.format(getVelocityX()) + " , " + dcFormat.format(getVelocityY()) +
                "] | Radius: " + dcFormat.format(radius) + " | Index: " + index + " | Mass: " + dcFormat.format(mass) +
                " | Mag: " + dcFormat.format(currPos.mag()) + " | Heading: " + dcFormat.format(currPos.heading()) + " ]";
    }
}