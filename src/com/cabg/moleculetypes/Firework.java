package com.cabg.moleculetypes;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.utilities.DrawingUtil.giveStyle;

public class Firework extends Molecule {
    private int life = (int) ((random.nextFloat() * engineSettings.fireworksLife) + 3);
    private int wind = engineSettings.fireworksWind;
    private int jitter = engineSettings.fireworksJitter;

    public Firework(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Firework(float x, float y, float radius, float speed, float direction, int wind) {
        super(x, y, radius, speed, direction, 0);
        this.wind = wind;
    }

    public void render() {
        giveStyle(x - radius, y - radius, 2 * radius, getReactiveColor(), engineSettings.fireworksRenderMode, fireworksParticleText);
    }

    public void update() {
        ax += (random.nextFloat() * wind - (wind / 2f)) / jitter;
        ay += (random.nextFloat() * wind - (wind / 2f)) / jitter;

        accelerate();

        final float dampening = 0.9793f;
        vx *= dampening;
        vy *= dampening;

        checkBounds();

        if (--life < 0) Fireworks.remove(this);
    }
}