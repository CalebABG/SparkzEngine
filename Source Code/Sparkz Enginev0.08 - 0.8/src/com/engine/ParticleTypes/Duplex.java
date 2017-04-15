package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import static java.lang.Math.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class Duplex extends Molecule {
    private boolean duplexMode;

    public Duplex() {super();}
    public Duplex(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
        duplexMode = DUPLEXMODE;
        color = new Color(56, 61, 72);
    }

    private void deter() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            double dx = p.x - x;
            double dy = p.y - y;
            double dist = sqrt(dx * dx + dy * dy);
            if (dist < 4.7345 * radius) {
                activeForces(p, duplexMode, dist);
            }
        }
    }

    private void activeForces(Molecule p, boolean m, double dist) {
        //Contain force
        if (m) {
            double forceX = (((p.x - x)) / 2 / dist);
            double forceY = (((p.y - y)) / 2 / dist);
            p.vx -= forceX;
            p.vy -= forceY;
            p.normalize(0.997);
        }
        //Repel force
        else {
            double forceX = (((p.x - x)) / 0.09 / dist);
            double forceY = (((p.y - y)) / 0.09 / dist);
            p.vx += forceX;
            p.vy += forceY;
            p.normalize(0.50);
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x - 10 * (radius / 2), y - 10 * (radius / 2), 10 * radius, 10 * radius));
    }

    public void render() {
        giveStyle();
    }
    public void update() {boundsCheck(); deter(); if (radius < 0) {DuplexArray.remove(this);} radius -= 0.02;}
}
