package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.DrawModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GravityPoint extends Molecule {
    public GravityPoint() {super();}

    public GravityPoint(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);
    }

    private void attract() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            ParticlesArray.get(i).gravitateTo(this);
        }
    }

    private Color getSelfColor() {
        Color c = null;
        if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) {c = Particle.thinkingColors[0];}
        if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) {c = Particle.thinkingColors[1];}
        if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) {c = Particle.thinkingColors[2];}
        if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) {c = Particle.thinkingColors[3];}
        if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) {c = Particle.thinkingColors[4];}
        if (c == null) c = Particle.thinkingColors[0]; return c;
    }

    private void gravProximity() {
        for (int i = 0; i < GravityPointsArray.size(); i++) {
            GravityPoint gPoint = GravityPointsArray.get(i);
            double dx = x - GravityPointsArray.get(i).x, dy = y - GravityPointsArray.get(i).y,
                    distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 500) {
                graphics2D.setColor(Particle.thinkingColors[0]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(),getY());
            }

            if (distance < 400) {
                graphics2D.setColor(Particle.thinkingColors[1]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(),getY());
            }

            if (distance < 300) {
                graphics2D.setColor(Particle.thinkingColors[2]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(),getY());
            }

            if (distance < 200) {
                graphics2D.setColor(Particle.thinkingColors[3]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(),getY());
            }

            if (distance < 100) {
                graphics2D.setColor(Particle.thinkingColors[4]);
                DrawModes.DrawGravConnections(gPoint, radius, getX(),getY());
            }
        }
    }

    public void giveStyle() {
        color = getSelfColor(); graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x, y, radius, radius));
        if (GravityPointsArray.size() <= 100) {gravProximity();}
    }

    public void render() {
        giveStyle();
    }
    public void update () {boundsCheck(); accelerateTo(vx, vy); attract();}
}
