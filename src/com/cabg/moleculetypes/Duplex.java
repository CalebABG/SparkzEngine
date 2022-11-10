package com.cabg.moleculetypes;

import java.awt.*;

import static com.cabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Duplex extends Molecule {
    private final boolean contain;

    public Duplex(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        contain = engineSettings.duplexContain;
        color = new Color(56, 61, 72);
    }

    private void deter() {
        for (int i = 0; i < Particles.size(); i++) {
            Particle p = Particles.get(i);
            float dx = p.x - x;
            float dy = p.y - y;
            float dist = (float) sqrt(dx * dx + dy * dy);
            if (dist < 5 * radius) activeForces(p, contain, dx, dy, dist);
        }
    }

    private void activeForces(Molecule p, boolean contain, float dx, float dy, float dist) {
        // Contain force
        float n;
        float forceX;
        float forceY;

        if (contain) {
            forceX = (dx / 2f) / dist;
            forceY = (dy / 2f) / dist;
            n = 0.997f;

            p.vx -= forceX;
            p.vy -= forceY;

        }
        // Repel force
        else {
            forceX = (dx / 0.09f) / dist;
            forceY = (dy / 0.09f) / dist;
            n = 0.50f;

            p.vx += forceX;
            p.vy += forceY;
        }

        p.vx *= n;
        p.vy *= n;
    }

    public void giveStyle() {
        int smr = (int) (5 * radius);
        int lgr = (int) (10 * radius);
        graphics2D.setColor(color);
        graphics2D.drawOval((int) (x - smr), (int) (y - smr), lgr, lgr);
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        deter();
        checkBounds();
        if (radius < 0) Duplexes.remove(this);
        radius -= 0.012;
    }
}
