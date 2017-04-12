package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;

import static com.engine.EngineHelpers.EConstants.*;

public class Emitter extends Molecule {
    public int life = 800;

    public Emitter() {super();}
    public Emitter(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    private void emitAtPoint() {ParticleModes.fireworksMode(x, y, fireworksWind, (int) (fireworksSpeedVal), fireworksAmount);}
    public void render() {
        graphics2D.setColor(color);
    }

    public void update() {
        boundsCheck();
        emitAtPoint();
        if (life == 0) EmitterArray.remove(this);
        life -= 1;
    }
}
