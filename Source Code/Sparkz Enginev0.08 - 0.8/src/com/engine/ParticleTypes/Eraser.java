package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Eraser extends Molecule {
    public int life = (int) (Math.random() * 400 + 100);

    public Eraser(){super();}
    public Eraser(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);
        color = Color.orange;
    }

    private void destroy() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            double dx = p.x - x, dy = p.y - y, distance = dx * dx + dy * dy;
            if (distance < (1.2 * radius*radius)) {color = Color.red; ParticlesArray.remove(p);}
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x, y, radius, radius));
    }

    public void render() {
        giveStyle();
    }
    public void update () {
        boundsCheck(); accelerateTo(vx, vy); destroy();
        if (life == 0) {EraserArray.remove(this); ParticleModes.fireworksMode(x, y, 2, 5, 30);} life -= 1;
    }
}
