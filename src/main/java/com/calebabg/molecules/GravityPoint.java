package com.calebabg.molecules;

import com.calebabg.reactivity.ReactiveColors;

import java.awt.geom.QuadCurve2D;

import static com.calebabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class GravityPoint extends Molecule {
    public GravityPoint(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void attract() {
        for (int i = 0; i < Particles.size(); i++)
            Particles.get(i).gravitateToPosition(x, y);
    }

    private void gravProximity() {
        for (int i = 0; i < GravityPoints.size(); i++) {
            GravityPoint gPoint = GravityPoints.get(i);
            float dx = gPoint.x - x;
            float dy = gPoint.y - y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            if (dist < 400) {
                graphics2D.setColor(ReactiveColors.getColor(0));
                drawGravConnections(gPoint, x, y);
            }

            if (dist < 300) {
                graphics2D.setColor(ReactiveColors.getColor(1));
                drawGravConnections(gPoint, x, y);
            }

            if (dist < 200) {
                graphics2D.setColor(ReactiveColors.getColor(2));
                drawGravConnections(gPoint, x, y);
            }

            if (dist < 100) {
                graphics2D.setColor(ReactiveColors.getColor(3));
                drawGravConnections(gPoint, x, y);
            }

            if (dist < 50) {
                graphics2D.setColor(ReactiveColors.getColor(4));
                drawGravConnections(gPoint, x, y);
            }
        }
    }

    public static void drawGravConnections(GravityPoint point, float x, float y) {
        if (engineSettings.linkMolecules) {
            if (engineSettings.showGravityPointsLink) graphics2D.draw(new QuadCurve2D.Float(point.x, point.y, point.x, y, x, y));
            else graphics2D.drawLine((int) point.x, (int) point.y, (int) x, (int) y);
        }
    }

    public void giveStyle() {
        int size = (int) (2 * radius);
        graphics2D.setColor(ReactiveColors.getReactiveColor(velocity()));
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);
        gravProximity();
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        accelerate();
        checkBounds();
        attract();
    }
}
