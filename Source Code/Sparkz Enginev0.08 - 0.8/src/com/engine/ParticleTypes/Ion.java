package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ion extends Molecule {
    public Ion() {super();}

    public Ion(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);
    }

    private void coagulate() {
        for (int i = 0; i < IonArray.size(); i++) {
            double dx = Mouse.x - IonArray.get(i).getCenter().x;
            double dy = Mouse.y - IonArray.get(i).getCenter().y;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < 1.16 * IonArray.get(i).radius && !isCTRLDown) { //Lets user know Ion is inside another Ion
                graphics2D.setColor(Color.RED.darker());
                graphics2D.drawOval(
                        (int) (Mouse.x - IonArray.get(i).radius / 2),
                        (int) (Mouse.y - IonArray.get(i).radius / 2),
                        (int) IonArray.get(i).radius,
                        (int) IonArray.get(i).radius
                );
            } else if (dist < 1.5 * IonArray.get(i).radius && !isCTRLDown) { //Lets user know that Ion are in a safe range and not colliding
                graphics2D.setColor(Color.GREEN.darker());
                graphics2D.drawOval(
                        (int) (Mouse.x - IonArray.get(i).radius / 2),
                        (int) (Mouse.y - IonArray.get(i).radius / 2),
                        (int) IonArray.get(i).radius,
                        (int) IonArray.get(i).radius
                );
            } else if (dist < ((2 * (IonArray.get(i).radius + 1)) - 0.5) && !isCTRLDown) { // Lets user know Mouse is in range for collection
                graphics2D.setColor(Color.ORANGE.darker());
                graphics2D.drawOval(
                        (int) (Mouse.x - IonArray.get(i).radius / 2),
                        (int) (Mouse.y - IonArray.get(i).radius / 2),
                        (int) IonArray.get(i).radius,
                        (int) IonArray.get(i).radius
                );
            }

            boolean isMouseOver = (dist < (2 * (IonArray.get(i).radius + 1)));
            if (isMouseOver) {
                IonArray.get(i).color = new Color(65, 106, 236);
                graphics2D.setColor(new Color(202, 119, 48));
                graphics2D.drawLine(
                        (int) (IonArray.get(i).x + IonArray.get(i).radius / 2),
                        (int) (IonArray.get(i).y + IonArray.get(i).radius / 2),
                        (int) (x + radius / 2), (int) (y + radius / 2));
            } else{ IonArray.get(i).color = new Color(203, 215, 193);}
            if (isCTRLDown && isMouseOver) {
                IonArray.get(i).x = (Mouse.x - (IonArray.get(i).radius / 2));
                IonArray.get(i).y = (Mouse.y - (IonArray.get(i).radius / 2));
            }
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        graphics2D.draw(new Ellipse2D.Double(x, y, radius, radius));
    }
    public void render() {giveStyle(); coagulate();}
    public void update () {boundsCheck(); accelerateTo(vx, vy);}
}
