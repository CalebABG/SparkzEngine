package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Utilities.ColorConverter;
import java.awt.*;
import static com.engine.ParticleHelpers.DrawModes.giveStyle;

public class Fireworks extends Molecule {
    private int life = (int) (Math.random() * rfLife + 3), wind = rfWind, jitter = rfJitter;

    public Fireworks(){super();}
    public Fireworks(double _x, double _y, double _radius, double speed, int dc) {
        super(_x, _y, Math.cos(dc) * speed, Math.sin(dc) * speed, _radius);
    }

    public Fireworks(double _x, double _y, double _radius, double speed, int dc, int windval) {
        super(_x, _y, Math.cos(dc) * speed, Math.sin(dc) * speed, _radius); wind = windval;
    }

    public void accelerateTo(double _vx, double _vy) {
        x += _vx; y += _vy;
        vx += (Math.random() * wind - (wind / 2)) / jitter;
        vy += (Math.random() * wind - (wind / 2)) / jitter;
    }

    private Color getSelfColor() {
        Color c = null;
        if ((vx >= 1 || vx <= -1) || (vy >= 1 || vy <= -1)) {c = Particle.thinkingColors[0];}
        if ((vx >= 2 || vx <= -2) || (vy >= 2 || vy <= -2)) {c = Particle.thinkingColors[1];}
        if ((vx >= 3 || vx <= -3) || (vy >= 3 || vy <= -3)) {c = Particle.thinkingColors[2];}
        if ((vx >= 4 || vx <= -4) || (vy >= 4 || vy <= -4)) {c = Particle.thinkingColors[3];}
        if ((vx >= 5 || vx <= -5) || (vy >= 5 || vy <= -5)) {c = Particle.thinkingColors[4];}
        if (c == null) c = Particle.thinkingColors[0]; return c;
    }

    public void render() {
        if (thinkingParticles) {color = getSelfColor();} else {color = ColorConverter.getColor();}
        giveStyle(x, y, radius, color, rfParticleMode, rfCustomParticleText);
    }

    public void update () {
        boundsCheck(); accelerateTo(vx, vy); friction(0.9793);
        if (life == 0) {FireworksArray.remove(this);} life -= 1;
    }
}