package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BlackHole extends Molecule {
    public BlackHole() {super();}

    public BlackHole(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    private void open() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            double dx = p.x - x;
            double dy = p.y - y;
            double bigR = 4 * radius;
            double dist = dx * dx + dy * dy;

            if (dist < bigR * bigR) {
                p.normalize(0.6);
                p.gravitateTo(this, 70);
            }

            if (dist < radius * radius) ParticlesArray.remove(i);
        }
    }

    public void giveStyle() {
        double smallR = 2 * radius;
        double bigR = 4 * radius;

        graphics2D.setColor(new Color(5, 9, 11));
        graphics2D.fill(new Ellipse2D.Double(x - smallR / 2, y - smallR / 2, smallR, smallR));

        graphics2D.setColor(new Color(20, 25, 30));
        graphics2D.draw(new Ellipse2D.Double(x - bigR, y - bigR, 2 * bigR, 2 * bigR));
    }

    public void render() {
        giveStyle();
    }
    public void update() {
        boundsCheck();
        open();
        if (radius <= 0) {
            BlackHoleArray.remove(this);
            ParticleModes.fireworksMode(x, y, 6, 4, 320);
        }
        radius -= 0.015;
    }
}
