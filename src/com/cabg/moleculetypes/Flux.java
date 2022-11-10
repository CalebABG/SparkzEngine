package com.cabg.moleculetypes;

import java.awt.*;
import com.cabg.moleculehelpers.MoleculeFactory;
import static com.cabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.*;

public class Flux extends Molecule {
    public int life = (int) ((random.nextFloat() * 400) + 100);

    public Flux(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public void giveStyle() {
        Polygon poly = new Polygon();
        for (int i = 0, edges = 9, scale = 5; i < edges; i++) {
            poly.addPoint((int) (x + radius * cos(i * scale * TWO_PI / edges)), (int) (y + radius * sin(i * scale * TWO_PI / edges)));
        }

        graphics2D.setColor(color);
        graphics2D.draw(poly);
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        accelerate();
        checkBounds();

        if (life % 140 == 0) radius += 10;
        if (life % 40 == 1) color = Color.red;
        else color = Color.white;

        if (--life < 0) {
            if (Particles.size() < 13_000) MoleculeFactory.createEraser(x, y, 3);
            Fluxes.remove(this);
        }

        radius -= 0.08;
    }
}
