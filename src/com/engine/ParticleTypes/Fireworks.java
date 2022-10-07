package com.engine.ParticleTypes;

import java.awt.*;

import com.engine.ThinkingParticles.ReactiveColors;

import static com.engine.EngineHelpers.EBOOLS.REACTIVE_PARTICLES_ENABLED;
import static com.engine.EngineHelpers.EConstants.Fireworks;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EINTS.*;
import static com.engine.ParticleHelpers.DrawingUtil.giveStyle;

public class Fireworks extends Molecule {
    private int life = (int) ((random.nextFloat() * FIREWORKS_LIFE.value()) + 3);
    private int wind = FIREWORKS_WIND.value();
    private int jitter = FIREWORKS_JITTER.value();

    public Fireworks(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Fireworks(float x, float y, float radius, float speed, float direction, int wind) {
        super(x, y, radius, speed, direction, 0);
        this.wind = wind;
    }

    public void render() {
        Color color;
        if (REACTIVE_PARTICLES_ENABLED.value()) color = ReactiveColors.getReactiveComponent(velocity());
        else color = PLAIN_COLOR;

        giveStyle(x - radius, y - radius, 2 * radius, color, FIREWORKS_RENDER_MODE, fireworksParticleText);
    }

    public void update() {
        ax += (random.nextFloat() * wind - (wind / 2f)) / jitter;
        ay += (random.nextFloat() * wind - (wind / 2f)) / jitter;

        accelerate();

        final float dampening = 0.9793f;
        vx *= dampening;
        vy *= dampening;

        boundsCheck();

        if (--life < 0) Fireworks.remove(this);
    }
}