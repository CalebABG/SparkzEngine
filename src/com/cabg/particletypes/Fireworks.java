package com.cabg.particletypes;

import com.cabg.reactivecolors.ReactiveColors;

import java.awt.*;

import static com.cabg.particlehelpers.DrawingUtil.giveStyle;
import static com.cabg.core.EngineVariables.Fireworks;
import static com.cabg.core.EngineVariables.*;

public class Fireworks extends Molecule {
    private int life = (int) ((random.nextFloat() * engineSettings.fireworksLife) + 3);
    private int wind = engineSettings.fireworksWind;
    private int jitter = engineSettings.fireworksJitter;

    public Fireworks(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Fireworks(float x, float y, float radius, float speed, float direction, int wind) {
        super(x, y, radius, speed, direction, 0);
        this.wind = wind;
    }

    public void render() {
        Color color;
        if (engineSettings.reactiveColorsEnabled) color = ReactiveColors.getReactiveComponent(velocity());
        else color = PLAIN_COLOR;

        giveStyle(x - radius, y - radius, 2 * radius, color, engineSettings.fireworksRenderMode, fireworksParticleText);
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