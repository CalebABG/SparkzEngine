package com.calebabg.molecules;

import com.calebabg.reactivity.ReactiveColors;

import static com.calebabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Portal extends Molecule {
    public Portal(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
        color = ReactiveColors.getColors()[(int) (random.nextFloat() * 4)];
    }

    private void movePortal() {
        for (int i = 0, len = Portals.size(); i < len; i++) {
            Portal portal = Portals.get(i);
            float dx = MouseVec.x - portal.x;
            float dy = MouseVec.y - portal.y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (engineSettings.controlKeyIsDown && dist < portal.radius) {
                portal.x = MouseVec.x;
                portal.y = MouseVec.y;
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
        checkBounds();
        transferMatter();
    }
}
