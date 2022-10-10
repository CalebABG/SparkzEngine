package com.cabg.moleculetypes;

import com.cabg.moleculehelpers.MoleculeFactory;

import java.awt.*;

import static com.cabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.*;

public class BlackHole extends Molecule {
    private static final Color centerColor = new Color(20, 25, 30);

    public BlackHole(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        color = new Color(5, 9, 11);
    }

    private void open() {
        for (int i = Particles.size() - 1; i >= 0; i--) {
            Particle p = Particles.get(i);

            float dx = p.x - x;
            float dy = p.y - y;
            float bigR = 4 * radius;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < bigR) {
                final float dampening = 0.37f;
                p.vx *= dampening;
                p.vy *= dampening;
                p.gravitateToMolecule(this, 1);
            }

            if (dist < radius) Particles.remove(i);
        }
    }

    public void giveStyle() {
        int smallR = (int) (2 * radius);
        int bigR = (int) (4 * radius);

        graphics2D.setColor(color);
        graphics2D.fillOval((int) x - smallR, (int) y - smallR, 2 * smallR, 2 * smallR);

        graphics2D.setColor(centerColor);
        graphics2D.drawOval((int) x - bigR, (int) y - bigR, 2 * bigR, 2 * bigR);
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        open();
        boundsCheck();
        if (radius < 0) {
            MoleculeFactory.fireworksMode(x, y, 6, 4, 320);
            BlackHoles.remove(this);
        }
        radius -= 0.015;
    }
}
