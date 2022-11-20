package com.calebabg.molecules;

import static com.calebabg.core.EngineVariables.*;

public class Emitter extends Molecule {
    public int life = 800;

    public Emitter(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void emitAtPoint() {
        MoleculeFactory.multiFireworks(x, y, engineSettings.fireworksWind, engineSettings.fireworksSpeed, engineSettings.fireworksAmount);
    }

    public void render() {}

    public void update() {
        emitAtPoint();
        checkBounds();
        if (--life < 0) Emitters.remove(this);
    }
}
