package com.cabg.moleculetypes;

import com.cabg.moleculehelpers.MoleculeFactory;

import static com.cabg.core.EngineVariables.*;

public class Emitter extends Molecule {
    public int life = 800;

    public Emitter(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void emitAtPoint() {
        MoleculeFactory.fireworksMode(x, y, engineSettings.fireworksWind, engineSettings.fireworksSpeed, engineSettings.fireworksAmount);
    }

    public void render() {
        graphics2D.setColor(color);
    }

    public void update() {
        emitAtPoint();
        boundsCheck();
        if (--life < 0) Emitters.remove(this);
    }
}
