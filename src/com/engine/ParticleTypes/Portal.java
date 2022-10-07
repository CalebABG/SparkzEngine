package com.engine.ParticleTypes;

import com.engine.ThinkingParticles.ReactiveColors;
import static com.engine.EngineHelpers.EBOOLS.CONTROL_IS_DOWN;
import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Portal extends Molecule {
    public Portal(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
        color = ReactiveColors.getComponents()[(int) (random.nextFloat() * 4)];
    }

    private void movePortal() {
        for (int i = 0, len = Portals.size(); i < len; i++) {
            Portal portal = Portals.get(i);
            float dx = Mouse.x - portal.x;
            float dy = Mouse.y - portal.y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (CONTROL_IS_DOWN.value() && dist < portal.radius) {
                portal.x = Mouse.x;
                portal.y = Mouse.y;
            }
        }
    }

    private void transferMatter() {
        if (Portals.size() == 2) {
            Portal p1 = Portals.get(0);
            Portal p2 = Portals.get(1);

            for (int i = 0; i < Particles.size(); i++) {
                Particle particles = Particles.get(i);
                float dx = particles.x - p1.x;
                float dy = particles.y - p1.y;
                float dist = (float) sqrt(dx * dx + dy * dy);

                if (dist < p1.radius) {
                    particles.x = p2.x;
                    particles.y = p2.y;
                }
            }
        }
    }

    public void giveStyle() {
        int size = (int) (2 * radius);
        graphics2D.setColor(color);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);

    }

    public void render() {
        giveStyle();
    }

    public void update() {
        movePortal();
        boundsCheck();
        transferMatter();
    }
}
