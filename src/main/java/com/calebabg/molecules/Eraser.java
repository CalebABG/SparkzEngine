package com.calebabg.molecules;

import java.awt.*;

import static com.calebabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.*;

public class Eraser extends Molecule {
    public int life = (int) ((random.nextFloat() * 400) + 100);

    public Eraser(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        color = Color.orange;
    }

    private void destroy() {
        for (int i = 0; i < Particles.size(); i++) {
            Particle p = Particles.get(i);

            float dx = p.x - x;
            float dy = p.y - y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < radius) {
                color = Color.red;
                Particles.remove(p);
            }
        }
    }

    public void render() {
        int size = (int) (2 * radius);
        graphics2D.setColor(color);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);
    }

    public void update() {
        accelerate();
        checkBounds();
        destroy();
        if (--life < 0) {
            MoleculeFactory.multiFireworks(x, y, 2, 5, 30);
            Erasers.remove(this);
        }
    }
}
