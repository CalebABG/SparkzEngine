package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static org.apache.commons.math3.util.FastMath.*;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;

public class Eraser extends Molecule {
    public int life = (int) ((random.nextFloat() * 400) + 100);

    public Eraser(){super();}
    public Eraser(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        color = Color.orange;
    }

    private void destroy() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            float dx = p.x - x;
            float dy = p.y - y;
            float distance = (float) sqrt(dx * dx + dy * dy);
            if (distance < 2 * radius) {
                color = Color.red;
                ParticlesArray.remove(p);
            }
        }
    }

    public void render(){
        int lgr = (int) (2 * radius);
        graphics2D.setColor(color);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), lgr, lgr);
    }
    public void update () {
        accelerate();
        boundsCheck();
        destroy();
        if (life < 0) {
            ParticleModes.fireworksMode(x, y, 2, 5, 30);
            EraserArray.remove(this);
        }
        life -= 1;
    }
}
