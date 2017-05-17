package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Ion extends Molecule {
    private static Color hover = new Color(65, 106, 236);

    public Ion() {super();}
    public Ion(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    private void coagulate() {
        for (int i = 0; i < IonArray.size(); i++) {
            Ion ion = IonArray.get(i);
            double halfIonRad = ion.radius / 2;
            double dx = Mouse.x - ion.getCenterX();
            double dy = Mouse.y - ion.getCenterY();
            double mxdiffrad = Mouse.x - halfIonRad;
            double mydiffrad = Mouse.y - halfIonRad;
            double dist = dx * dx + dy * dy;
            boolean isMouseOver = dist < ion.radius * ion.radius;

            if (isMouseOver) {
                ion.color = hover;
                graphics2D.setColor(Color.ORANGE);
                graphics2D.draw(new Line2D.Double(ion.getCenterX(), ion.getCenterY(), getCenterX(), getCenterY()));
            } else ion.color = Color.white;

            if (isCTRLDown && isMouseOver) {
                ion.x = mxdiffrad;
                ion.y = mydiffrad;
            }
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x - radius / 2, y - radius / 2, 2 * radius, 2 * radius));
    }
    public void render() {giveStyle(); coagulate();}
    public void update() {boundsCheck(); accelerateTo(vx, vy);}
}
