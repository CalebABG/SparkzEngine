package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;

import static com.engine.EngineHelpers.EConstants.*;

public class Emitter extends Molecule {
    public int life = 800;

    public Emitter() {super();}
    public Emitter(double _x, double _y, double _radius, double speed, int direction) {super(_x, _y, Math.cos(direction) * speed, Math.sin(direction) * speed, _radius);}

    private void emitAtPoint() {ParticleModes.fireworksMode(x, y, rfWind, (int) (fireworksSpeedVal), fireworksAmount);}
    public void giveStyle() {graphics2D.setColor(color);}
    public void render() {giveStyle();}

    public void update() {
        boundsCheck();
        emitAtPoint();
        if (life == 0) EmitterArray.remove(this);
        life -= 1;
    }
}
