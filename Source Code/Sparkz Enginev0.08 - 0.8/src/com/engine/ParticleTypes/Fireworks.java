package com.engine.ParticleTypes;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Utilities.ColorUtility;
import java.awt.*;
import static com.engine.ParticleHelpers.DrawModes.giveStyle;
import static java.lang.Math.*;

public class Fireworks extends Molecule {
    private int life = (int) (random() * fireworksLife + 3);
    private int wind = fireworksWind;
    private int jitter = fireworksJitter;

    public Fireworks(){super();}
    public Fireworks(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
    }

    public Fireworks(double _x, double _y, double _radius, double speed, double direction, int windval) {
        super(_x, _y, _radius, speed, direction, 0);
        wind = windval;
    }

    public void accelerateTo(double _vx, double _vy) {
        x += _vx;
        y += _vy;
        vx += (random() * wind - (wind / 2)) / jitter;
        vy += (random() * wind - (wind / 2)) / jitter;
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
        if (thinkingParticles) color = getSelfColor();
        else color = ColorUtility.getColor();
        giveStyle(x - radius / 2, y - radius / 2, 2 * radius, color, fireworksRenderType, fireworksParticleText);
    }

    public void update () {
        boundsCheck(); 
        accelerateTo(vx, vy); 
        friction(0.9793);
        if (life == 0) FireworksArray.remove(this);
        life -= 1;
    }
}