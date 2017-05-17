package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.DrawModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GravityPoint extends Molecule {
    public GravityPoint() {super();}

    public GravityPoint(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    private void attract() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            ParticlesArray.get(i).gravitateTo(this, null);
        }
    }

    private Color getSelfColor() {
        Color c = null;
        if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) c = Particle.thinkingColors[0];
        if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) c = Particle.thinkingColors[1];
        if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) c = Particle.thinkingColors[2];
        if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) c = Particle.thinkingColors[3];
        if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) c = Particle.thinkingColors[4];
        if (c == null) c = Particle.thinkingColors[0]; return c;
    }

    private void gravProximity() {
        for (int i = 0; i < GravityPointsArray.size(); i++) {
            GravityPoint gPoint = GravityPointsArray.get(i);
            double dx = gPoint.x - x;
            double dy = gPoint.y - y;
            double dradius = gPoint.radius + radius;
            double distance = dx * dx + dy * dy;
            //System.out.println(distance);

            if (distance < 950 * dradius * dradius) {
                graphics2D.setColor(Particle.thinkingColors[0]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(), getY());
            }

            if (distance < 850 * dradius * dradius) {
                graphics2D.setColor(Particle.thinkingColors[1]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(), getY());
            }

            if (distance < 750 * dradius * dradius) {
                graphics2D.setColor(Particle.thinkingColors[2]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(), getY());
            }

            if (distance < 650 * dradius * dradius) {
                graphics2D.setColor(Particle.thinkingColors[3]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(), getY());
            }

            if (distance < 550 * dradius * dradius) {
                graphics2D.setColor(Particle.thinkingColors[4]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(), getY());
            }
        }
    }

    public void giveStyle() {
        color = getSelfColor();
        graphics2D.setColor(color);
        graphics2D.drawOval((int)(x - radius / 2), (int)(y - radius / 2), (int) (2 * radius), (int) (2 * radius));
//        graphics2D.draw(new Ellipse2D.Double(x - radius / 2, y - radius / 2, 2 * radius, 2 * radius));
        if (GravityPointsArray.size() <= 100) {gravProximity();}
    }

    public void render() {
        giveStyle();
    }
    public void update () {boundsCheck(); accelerateTo(vx, vy); attract();}
}
