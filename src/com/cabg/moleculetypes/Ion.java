package com.cabg.moleculetypes;

import java.awt.*;

import static com.cabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.sqrt;

public class Ion extends Molecule {
    private static final Color hoverColor = new Color(65, 106, 236);

    public Ion(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void coagulate() {
        for (int i = 0; i < Ions.size(); i++) {
            Ion ion = Ions.get(i);

            float dx = MouseVec.x - ion.x;
            float dy = MouseVec.y - ion.y;
            float dist = (float) sqrt(dx * dx + dy * dy);

            boolean mouseIsOver = dist < ion.radius;

            if (mouseIsOver) {
                ion.color = hoverColor;
                graphics2D.setColor(Color.orange);
                graphics2D.drawLine((int) ion.x, (int) ion.y, (int) x, (int) y);
            } else {
                ion.color = Color.white;
            }

            if (engineSettings.controlKeyIsDown && mouseIsOver) {
                ion.x = MouseVec.x;
                ion.y = MouseVec.y;
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
        coagulate();
    }

    public void update() {
        accelerate();
        checkBounds();
    }
}
