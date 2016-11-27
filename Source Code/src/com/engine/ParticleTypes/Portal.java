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
        for (int i = 0; i < PortalArray.size(); i++) {
            Portal portals = PortalArray.get(i);
            double dx = Mouse.x - portals.x, dy = Mouse.y - portals.y, dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < (2 * (portals.radius + 1)) && isCTRLDown) {
                portals.setColor(Particle.thinkingColors[(int) (Math.random() * 4)]);
                portals.setPos(Mouse.x - (PortalArray.get(i).radius / 2),
                        (Mouse.y - (PortalArray.get(i).radius / 2)));
            }
        }
    }

    private void transferMatter() {
        if (PortalArray.size() > 1) {
            for (int i = 0; i < ParticlesArray.size(); i++) {
                Particle particles = ParticlesArray.get(i);
                Portal p1 = PortalArray.get(0); Portal p2 = PortalArray.get(1);
                double dx = particles.x - p1.getCenter().x, dy = particles.y - p1.getCenter().y, dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < (0.6 * (p1.radius))) {particles.setPos(p2.getCenter().x, p2.getCenter().y);}
            }
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x, y, radius, radius));
    }

    public void render() {
        giveStyle();
    }
    public void update() {boundsCheck(); movePortal();transferMatter();}
}
