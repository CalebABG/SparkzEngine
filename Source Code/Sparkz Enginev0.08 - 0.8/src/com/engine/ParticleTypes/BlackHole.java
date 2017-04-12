package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BlackHole extends Molecule {
    public BlackHole() {super();}

    public BlackHole(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    private void open() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            double dx = p.x - x, dy = p.y - y, dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < 5 * radius) {p.normalize(0.37); p.gravitateTo(this, 1);}
            if (dist < radius) {ParticlesArray.remove(i);}
        }
    }

    public void giveStyle() {
        graphics2D.setColor(new Color(5, 9, 11));
        graphics2D.fill(new Ellipse2D.Double(x - 2 * (radius / 2), y - 2 * (radius / 2), 2 * radius, 2 * radius));
        graphics2D.setColor(new Color(20, 25, 30));
        graphics2D.draw(new Ellipse2D.Double(x - 10 * (radius / 2), y - 10 * (radius / 2), 10 * radius, 10 * radius));
    }

    public void render() {
        giveStyle();
    }

    public void update () {
        boundsCheck(); open();
        if (radius < 0) {BlackHoleArray.remove(this); ParticleModes.fireworksMode(x, y, 6, 4, 320);}
        radius -= 0.01;
    }
}
