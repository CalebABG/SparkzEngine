package com.calebabg.molecules;

import com.calebabg.reactivity.ReactiveColors;

import static com.calebabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Portal extends Molecule {
    public Portal(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
        color = ReactiveColors.getColor(random.nextInt(4));
    }

    private void movePortal() {
        for (int i = 0; i < Portals.size(); i++) {
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
        if (Portals.size() != 2) return;

        Portal entrance = Portals.get(0);
        Portal exit = Portals.get(1);

        for (int i = 0; i < Particles.size(); i++) {
            Particle particle = Particles.get(i);

            float dx = particle.x - entrance.x;
            float dy = particle.y - entrance.y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < entrance.radius) {
                particle.x = exit.x;
                particle.y = exit.y;
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
