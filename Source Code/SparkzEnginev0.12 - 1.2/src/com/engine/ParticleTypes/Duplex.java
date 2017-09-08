package com.engine.ParticleTypes;

import java.awt.*;
import static com.engine.EngineHelpers.EBOOLS.TOGGLE_DUPLEX_MODE;
import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Duplex extends Molecule {
    private boolean duplexMode;

    public Duplex() {super();}
    public Duplex(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        duplexMode = TOGGLE_DUPLEX_MODE.value();
        color = new Color(56, 61, 72);
    }

    private void deter() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            float dx = p.x - x;
            float dy = p.y - y;
            float dist = (float) sqrt(dx * dx + dy * dy);
            if (dist < 5 * radius) activeForces(p, duplexMode, dx, dy, dist);
        }
    }

    private void activeForces(Molecule p, boolean contain, float dx, float dy, float dist) {
        //Contain force
        if (contain) {
            float forceX = (dx / 2) / dist;
            float forceY = (dy / 2) / dist;
            float n = 0.997f;

            p.vx -= forceX;
            p.vy -= forceY;

            p.vx *= n;
            p.vy *= n;
        }
        //Repel force
        else {
            float forceX = (dx / 0.09f) / dist;
            float forceY = (dy / 0.09f) / dist;
            float n = 0.50f;

            p.vx += forceX;
            p.vy += forceY;

            p.vx *= n;
            p.vy *= n;
        }
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
        boundsCheck();

        if (radius < 0) DuplexArray.remove(this);

        radius -= 0.012;
    }
}
