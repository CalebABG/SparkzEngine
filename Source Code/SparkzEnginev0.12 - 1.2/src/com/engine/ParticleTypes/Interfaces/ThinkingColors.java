package com.engine.ParticleTypes.Interfaces;

import com.engine.ThinkingParticles.SCChoices;
import java.awt.*;
import static com.engine.Verlet.Vect2.clamp;

public interface ThinkingColors {
    Color[] COLORS = SCChoices.defaultColor();

    //1.4 seems to work best for colors, tweak as wanted
    default Color getSelfColor(float vel) {
        return COLORS[(int) clamp(vel / 1.4f, 0, COLORS.length - 1)];
    }
}
