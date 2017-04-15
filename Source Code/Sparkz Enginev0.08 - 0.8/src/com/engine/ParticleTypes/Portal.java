package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.geom.Ellipse2D;

public class Portal extends Molecule {
    public Portal() {super();}

    public Portal(double _x, double _y, double _radius) {
        super(_x, _y, 0, 0, _radius);
        color = Particle.thinkingColors[(int) (Math.random() * 4)];
    }

    private void movePortal() {
        for (int i = 0, len = PortalArray.size(); i < len; i++) {
            Portal portal = PortalArray.get(i);
            double dx = Mouse.x - portal.x;
            double dy = Mouse.y - portal.y;
            double dist = dx * dx + dy * dy;

            if (dist < (portal.radius * portal.radius) && isCTRLDown) {
                portal.setColor(Particle.thinkingColors[(int) (Math.random() * 4)]);
                portal.setPos(Mouse.x - portal.radius / 2, Mouse.y - portal.radius / 2);
            }
        }
    }

    private void transferMatter() {
        if (PortalArray.size() == 2) {
            Portal p1 = PortalArray.get(0);
            Portal p2 = PortalArray.get(1);

            for (int i = 0; i < ParticlesArray.size(); i++) {
                Particle particles = ParticlesArray.get(i);
                double dx = particles.x - p1.getCenterX();
                double dy = particles.y - p1.getCenterY();
                double dist = dx * dx + dy * dy;

                if (dist < p1.radius * p1.radius) {
                    particles.setPos(p2.getCenterX(), p2.getCenterY());
                }
            }
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x - radius / 2, y - radius / 2, 2 * radius, 2 * radius));
    }

    public void render() {
        giveStyle();
    }
    public void update() {boundsCheck(); movePortal();transferMatter();}
}
