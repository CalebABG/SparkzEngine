package com.engine.ParticleTypes;

import java.awt.*;

import static com.engine.EngineHelpers.EBOOLS.CONTROL_IS_DOWN;
import static com.engine.EngineHelpers.EConstants.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Ion extends Molecule {
    private static Color hover = new Color(65, 106, 236);

    public Ion() {super();}
    public Ion(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void coagulate() {
        for (int i = 0; i < IonArray.size(); i++) {
            Ion ion = IonArray.get(i);

            float dx = Mouse.x - ion.x;
            float dy = Mouse.y - ion.y;
            float dist = (float) sqrt(dx * dx + dy * dy);
            boolean isMouseOver = dist < ion.radius;

            if (isMouseOver) {
                ion.color = hover;
                graphics2D.setColor(Color.orange);
                graphics2D.drawLine(ion.getX(), ion.getY(), getX(), getY());
            }
            else ion.color = Color.white;

            if (CONTROL_IS_DOWN.value() && isMouseOver) {
                ion.x = Mouse.x;
                ion.y = Mouse.y;
            }
        }
    }

    public void giveStyle() {
        graphics2D.setColor(color);
        int lgr = (int) (2 * radius);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), lgr, lgr);
    }

    public void render() {
        giveStyle();
        coagulate();
    }

    public void update() {
        accelerate();
        boundsCheck();
    }
}
