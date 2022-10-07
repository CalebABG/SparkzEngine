package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import com.engine.ThinkingParticles.ReactiveColors;

import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;

public class QED extends Molecule {
    public Font font;
    public int life, particleType;

    public QED(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        life = (int) ((random.nextFloat() * 400) + 150);
        particleType = (int) (random.nextFloat() * 11);
        font = new Font(Font.SERIF, Font.PLAIN, (int) (2 * radius));
    }

    private void quantumEntanglement(int ptType) {
        switch (ptType) {
            case 0: ParticleModes.singleParticle((int) x, (int) y); break;
            case 1: ParticleModes.singleSemtex(x, y); break;
            case 2: ParticleModes.singleEmitter(x, y); break;
            case 3: ParticleModes.singleGravityPoint(x, y); break;
            case 4: ParticleModes.singleIon(x, y); break;
            case 5: ParticleModes.createEraser(x, y, 2); break;
            case 6: ParticleModes.singleBlackHole(x, y); break;
            case 7: ParticleModes.singleDuplex(x, y); break;
            case 9: ParticleModes.singleQED(x, y); break;
            default: break;
        }
    }

    public void giveStyle() {
        graphics2D.setFont(font);
        graphics2D.setColor(ReactiveColors.getComponents()[(int) (random.nextFloat() * 4)].darker());

        int size = (int) (2 * radius);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);
        graphics2D.setColor(ReactiveColors.getComponents()[(int) (random.nextFloat() * 4)]);

        String mark = "?";
        float qx = (float) ((x + (radius / 5) - 2.5) - (graphics2D.getFontMetrics().stringWidth(mark) / 2));
        float qy = y + (radius / 2) + 3;
        graphics2D.drawString(mark, qx, qy);
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        accelerate();
        boundsCheck();
        particleType = (int) (random.nextFloat() * 11);
        if (--life < 0) {
            ParticleModes.fireworksMode(x, y, 2, 2, 30);
            quantumEntanglement(particleType);
            QEDs.remove(this);
        }
    }
}
