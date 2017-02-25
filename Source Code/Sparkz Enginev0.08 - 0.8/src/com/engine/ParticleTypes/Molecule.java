package com.engine.ParticleTypes;

import com.engine.Interfaces_Extensions.MoleculeRender;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;

public abstract class Molecule implements MoleculeRender {
    public Color color = DEFAULT_COLOR;
    public double x, y, vx, vy, radius = 1;

    public Molecule() {
        x = (Math.random() * width - radius); y = (Math.random() * height - radius); radius = (int) (Math.random() * 10);
        vx = Math.cos(Math.random() * 360) * (Math.random() * 10); vy = Math.sin(Math.random() * 360) * (Math.random() * 10);
    }

    public Molecule(double x, double y, double radius) {
        this.x = x; this.y = y; this.radius = radius;
        vx = Math.cos(Math.random() * 360) * (Math.random() * 10); vy = Math.sin(Math.random() * 360) * (Math.random() * 10);
    }

    public Molecule(double x, double y, double vx, double vy, double radius) {
        this.x = x; this.y = y; this.vx = vx; this.vy = vy; this.radius = radius;
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
    public Point getCenter(){return new Point((int) (x + (radius / 2)), (int) ((y + (radius / 2))));}
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

    public void boundsCheck () {
        if ((x+radius) >= canvas.getWidth()) {x = canvas.getWidth() - radius - 1; setVx(-getVelX());}
        else if (x <= 0) {x = 1; setVx(-getVelX());}
        if (y >= canvas.getHeight() - radius) {y = canvas.getHeight() - radius - 1; setVy(-getVelY());}
        else if (y <= 0) {y = 1; setVy(-getVelY());}
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

    public void gravitateTo(Molecule p) {
        double dx = p.x - x,  dy = p.y - y, dist = Math.sqrt(dx * dx + dy * dy);
        double forceX = (((p.x - x) / 5) / dist); vx += forceX;
        double forceY = (((p.y - y) / 5) / dist); vy += forceY;
    }

    public void gravitateTo(Molecule p, double z) {
        double dx = p.x - x, dy = p.y - y, dist = Math.sqrt(dx * dx + dy * dy);
        double forceX = (((p.x - x) / z) / dist); vx += forceX;
        double forceY = (((p.y - y) / z) / dist); vy += forceY;
    }

    public void gravitateTo(Point p) {
        double dx = p.x - x, dy = p.y - y, dist = Math.sqrt(dx * dx + dy * dy);
        double forceX = (((p.x - x) / 5) / dist); vx += forceX;
        double forceY = (((p.y - y) / 5) / dist); vy += forceY;
    }
}
