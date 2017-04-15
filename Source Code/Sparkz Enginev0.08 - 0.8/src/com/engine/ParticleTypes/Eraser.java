package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Eraser extends Molecule {
    public int life = (int) (Math.random() * 400 + 100);

    public Eraser(){super();}
    public Eraser(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
        color = Color.orange;
    }

    private void destroy() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            double dx = p.x - x;
            double dy = p.y - y;
            double distance = dx * dx + dy * dy;
            if (distance < 2*radius * radius) {color = Color.red; ParticlesArray.remove(p);}
        }
    }

    public void render(){
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x - radius / 2, y - radius / 2, 2 * radius, 2 * radius));
    }
    public void update () {
        boundsCheck();
        accelerateTo(vx, vy);
        destroy();
        if (life == 0) {EraserArray.remove(this); ParticleModes.fireworksMode(x, y, 2, 5, 30);}
        life -= 1;
    }
}
