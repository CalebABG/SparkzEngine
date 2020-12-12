package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EFLOATS.FIREWORKS_SPEED;
import static com.engine.EngineHelpers.EINTS.FIREWORKS_AMOUNT;
import static com.engine.EngineHelpers.EINTS.FIREWORKS_WIND;

public class Emitter extends Molecule {
    public int life = 800;

    public Emitter() {
        super();
    }
    public Emitter(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    private void emitAtPoint() {
        ParticleModes.fireworksMode(x, y, FIREWORKS_WIND.value(), FIREWORKS_SPEED.value(), FIREWORKS_AMOUNT.value());
    }

    public void render() {
        graphics2D.setColor(color);
    }

    public void update() {
        emitAtPoint();
        boundsCheck();
        if (life < 0) EmitterArray.remove(this);
        life -= 1;
    }
}
