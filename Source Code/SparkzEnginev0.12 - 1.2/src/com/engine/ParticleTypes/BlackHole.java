package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.*;

public class BlackHole extends Molecule {
    private Color midColor = new Color(20, 25, 30);
    public BlackHole() {super();}

    public BlackHole(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        color = new Color(5, 9, 11);
    }

    private void open() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            float dx = p.x - x;
            float dy = p.y - y;
            float bigR = 4 * radius;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < bigR) {
                float n = 0.37f;
                p.vx *= n;
                p.vy *= n;
                p.gravitate(this, 1);
            }

            if (dist < radius) ParticlesArray.remove(i);
        }
    }

    public void giveStyle() {
        int smallR = (int) (2 * radius);
        int bigR = (int) (4 * radius);

        graphics2D.setColor(color);
        graphics2D.fillOval((int) x - smallR, (int) y - smallR, 2 * smallR, 2 * smallR);

        graphics2D.setColor(midColor);
        graphics2D.drawOval((int) x - bigR, (int) y - bigR, 2 * bigR, 2 * bigR);
    }

    public void render() {
        giveStyle();
    }
    public void update() {
        open();
        boundsCheck();
        if (radius < 0) {
            ParticleModes.fireworksMode(x, y, 6, 4, 320);
            BlackHoleArray.remove(this);
        }
        radius -= 0.015;
    }
}
