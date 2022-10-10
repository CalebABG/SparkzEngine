package com.cabg.moleculetypes;

import com.cabg.reactivecolors.ReactiveColors;

import static com.cabg.core.EngineVariables.Particles;
import static com.cabg.core.EngineVariables.graphics2D;

public class GravityPoint extends Molecule {
    public GravityPoint(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void attract() {
        for (int i = 0; i < Particles.size(); i++)
            Particles.get(i).gravitateToPosition(x, y);
    }

    public void giveStyle() {
        int size = (int) (2 * radius);
        graphics2D.setColor(ReactiveColors.getReactiveComponent(velocity()));
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);
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
