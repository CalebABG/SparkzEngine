package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;


public class Duplex extends Molecule {
    private boolean duplexMode;

    public Duplex() {super();}
    public Duplex(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);
        duplexMode = DUPLEXMODE;
    }

    private void deter() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i); double dx = (p.x - x), dy = (p.y - y),
                    R = 2.6245 * (2 * radius), dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < R) {activeForces(p, duplexMode, dist);}
        }
    }

    private void activeForces(Molecule p, boolean m, double dist) {
        //Contain force
        if (m) {double forceX = (((p.x - x)) / 2 / dist), forceY = (((p.y - y)) / 2 / dist); p.vx -= forceX; p.vy -= forceY; p.normalize(0.997);}
        //Repel force
        else {double forceX = (((p.x - x)) / 0.09 / dist), forceY = (((p.y - y)) / 0.09 / dist); p.vx += forceX; p.vy += forceY; p.normalize(0.50);}
    }

    public void giveStyle() {
        graphics2D.setColor(new Color(56, 61, 72));
        graphics2D.draw(new Ellipse2D.Double(x - 10 * (radius / 2), y - 10 * (radius / 2), 10 * radius, 10 * radius));
    }

    public void render() {
        giveStyle();
    }
    public void update() {
        boundsCheck(); deter(); if (radius < 0) {DuplexArray.remove(this);} radius -= 0.002;
    }
}
