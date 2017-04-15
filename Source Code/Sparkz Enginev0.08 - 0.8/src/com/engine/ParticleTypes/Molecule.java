package com.engine.ParticleTypes;

import com.engine.J8Helpers.Interfaces.MoleculeRender;
import com.engine.Verlet.Vect2;

import static java.lang.Math.*;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;

public abstract class Molecule implements MoleculeRender {
    public Color color = DEFAULT_COLOR;
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double radius = 1;

    public Molecule() {
        x = (Math.random() * width - radius);
        y = (Math.random() * height - radius);
        radius = (Math.random() * 10) + 0.9;
        vx = cos(toRadians(Math.random() * 360)) * (Math.random() * 10);
        vy = sin(toRadians(Math.random() * 360)) * (Math.random() * 10);
    }

    public Molecule(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        vx = cos(toRadians(Math.random() * 360)) * (Math.random() * 10);
        vy = sin(toRadians(Math.random() * 360)) * (Math.random() * 10);
    }

    public Molecule(double x, double y, double vx, double vy, double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    public Molecule(double x, double y, double radius, double speed, double direction, int dummy) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.vx = cos(toRadians(direction)) * speed;
        this.vy = sin(toRadians(direction)) * speed;
    }

    public void accelerateTo (double _vx, double _vy) {x += _vx; y += _vy;}
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setPos(double x, double y) {this.x = x; this.y = y;}
    public void normalize(double v) {vx *= v; vy *= v;}
    public void setColor(Color color) {this.color = color;}
    public Color getColor() {return color;}
    public double getX () {return x;}
    public double getY () {
        return y;
    }
    public void setVx(double speed) {vx = speed;}
    public void setVy(double speed) {vy = speed;}
    public void setVel(double vx, double vy){this.vx = vx; this.vy = vy;}
    public double getVelX() {return vx;}
    public double getVelY() {return vy;}
    public double getRadius() {return radius;}
    public double getCenterX(){return (x + radius / 2);}
    public double getCenterY(){return (y + radius / 2);}
    public Point getCenter(){return new Point((int) (x + (radius / 2)), (int) ((y + (radius / 2))));}
    public Vect2 getCenterVect(){return new Vect2(x + radius / 2,y + radius / 2);}
    public double getDistance () {
        return Math.sqrt(x * x + y * y);
    }
    public void setRadius(double r){radius = r;}
    public double heading() {return Math.atan2(this.y, this.x);}
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) radius, (int) radius);
    }
    public void friction () {vx *= 0.9993; vy *= 0.9993;}
    public void friction (double fr) {vx *= fr; vy *= fr;}
    public void friction (double fx, double fy) {vx *= fx; vy *= fy;}
    public void invertVelocity(){setVx(-getVelX()); setVy(-getVelY());}

    public void boundsCheck() {
        double canvaswidth = canvas.getWidth();
        double canvasheight = canvas.getHeight();

        if (x - radius < 0) {
            x = 2 * (radius) - x;
            setVx(-getVelX());
        }
        if (x + radius > canvaswidth) {
            x = 2 * (canvaswidth - radius) - x;
            setVx(-getVelX());
        }
        if (y - radius < 0) {
            y = 2 * (radius) - y;
            setVy(-getVelY());
        }
        if (y + radius > canvasheight) {
            y = 2 * (canvasheight - radius) - y;
            setVy(-getVelY());
        }
    }

    public double angleTo (Molecule v) {
        double dx = v.x - x, dy = v.y - y; return Math.atan2(dy, dx);
    }

    public double distanceTo (Molecule v) {
        double dx = v.x - x, dy = v.y - y; return Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceTo (Point v) {
        double dx = v.x - x, dy = v.y - y; return Math.sqrt(dx * dx + dy * dy);
    }

    public void gravitateTo(Molecule p, double z) {
        double  dx = p.x - x;
        double dy = p.y - y;
        double dist = dx * dx + dy * dy;
//        double dist = Math.sqrt(dx * dx + dy * dy);

        double forceX = ((dx * z) / dist);
        double forceY = ((dy * z) / dist);

        vx += forceX;
        vy += forceY;

//        double forceX = ((dx / z) / dist);
//        double forceY = ((dy / z) / dist);
//        vx += forceX;
//        vy += forceY;
    }
}
