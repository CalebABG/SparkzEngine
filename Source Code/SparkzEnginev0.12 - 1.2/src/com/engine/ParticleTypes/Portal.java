package com.engine.ParticleTypes;

import com.engine.ParticleTypes.Interfaces.ThinkingColors;

import static com.engine.EngineHelpers.EBOOLS.CONTROL_IS_DOWN;
import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Portal extends Molecule {
    public Portal() {
        super();
    }
    public Portal(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
        color = ThinkingColors.COLORS[(int) (random.nextFloat() * 4)];
    }

    private void movePortal() {
        for (int i = 0, len = PortalArray.size(); i < len; i++) {
            Portal portal = PortalArray.get(i);
            float dx = Mouse.x - portal.x;
            float dy = Mouse.y - portal.y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < portal.radius && CONTROL_IS_DOWN.value()) {
                portal.x = Mouse.x;
                portal.y = Mouse.y;
            }
        }
    }

    private void transferMatter() {
        if (PortalArray.size() == 2) {
            Portal p1 = PortalArray.get(0);
            Portal p2 = PortalArray.get(1);

            for (int i = 0; i < ParticlesArray.size(); i++) {
                Particle particles = ParticlesArray.get(i);
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
        graphics2D.setColor(color);
        int lgr = (int) (2 * radius);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), lgr, lgr);

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
