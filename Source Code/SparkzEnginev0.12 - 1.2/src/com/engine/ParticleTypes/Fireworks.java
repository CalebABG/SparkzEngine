package com.engine.ParticleTypes;

import com.engine.ParticleTypes.Interfaces.ThinkingColors;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EBOOLS.THINKING_PARTICLES;
import static com.engine.ParticleHelpers.DrawModes.giveStyle;
import static com.engine.EngineHelpers.EINTS.*;

public class Fireworks extends Molecule implements ThinkingColors {
    private int life = (int) ((random.nextFloat() * FIREWORKS_LIFE.value()) + 3);
    private int wind = FIREWORKS_WIND.value();
    private int jitter = FIREWORKS_JITTER.value();

    public Fireworks(){super();}
    public Fireworks(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Fireworks(float x, float y, float radius, float speed, float direction, int wind) {
        super(x, y, radius, speed, direction, 0);
        this.wind = wind;
    }

    public void render() {
        if (THINKING_PARTICLES.value()) color = getSelfColor(velocity());
        else color = plain_color;

        giveStyle(x - radius, y - radius, 2 * radius, color, FIREWORKS_RENDER_MODE, fireworksParticleText);
    }

    public void update () {
        ax += (random.nextFloat() * wind - (wind / 2)) / jitter;
        ay += (random.nextFloat() * wind - (wind / 2)) / jitter;

        accelerate();

        float n = 0.9793f;
        vx *= n;
        vy *= n;

        boundsCheck();

        if (life < 0) FireworksArray.remove(this);
        life -= 1;
    }
}