package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.DrawModes;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;

import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class GravityPoint extends Molecule implements ThinkingColors {
    public GravityPoint() {super();}
    public GravityPoint(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void attract() {
        for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).gravitateTo(x, y);
    }

    private void gravProximity() {
        for (int i = 0; i < GravityPointsArray.size(); i++) {
            GravityPoint gPoint = GravityPointsArray.get(i);
            float dx = gPoint.x - x;
            float dy = gPoint.y - y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < 400) {
                graphics2D.setColor(COLORS[0]);
                DrawModes.DrawGravConnections(gPoint, x, y);
            }

            if (dist < 300) {
                graphics2D.setColor(COLORS[1]);
                DrawModes.DrawGravConnections(gPoint, x, y);
            }

            if (dist < 200) {
                graphics2D.setColor(COLORS[2]);
                DrawModes.DrawGravConnections(gPoint, x, y);
            }

            if (dist < 100) {
                graphics2D.setColor(COLORS[3]);
                DrawModes.DrawGravConnections(gPoint, x, y);
            }

            if (dist < 50) {
                graphics2D.setColor(COLORS[4]);
                DrawModes.DrawGravConnections(gPoint, x, y);
            }
        }
    }

    public void giveStyle() {
        color = getSelfColor(velocity());
        graphics2D.setColor(color);
        int lgr = (int) (2 * radius);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), lgr, lgr);
        gravProximity();
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        accelerate();
        boundsCheck();
        attract();
    }
}
