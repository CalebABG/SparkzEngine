package com.cabg.moleculetypes;

import com.cabg.moleculehelpers.MoleculeFactory;
import com.cabg.reactivecolors.ReactiveColors;

import java.awt.*;

import static com.cabg.core.EngineVariables.*;

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
            case 0: MoleculeFactory.singleParticle((int) x, (int) y); break;
            case 1: MoleculeFactory.singleFlux(x, y); break;
            case 2: MoleculeFactory.singleEmitter(x, y); break;
            case 3: MoleculeFactory.singleGravityPoint(x, y); break;
            case 4: MoleculeFactory.singleIon(x, y); break;
            case 5: MoleculeFactory.createEraser(x, y, 2); break;
            case 6: MoleculeFactory.singleBlackHole(x, y); break;
            case 7: MoleculeFactory.singleDuplex(x, y); break;
            case 9: MoleculeFactory.singleQED(x, y); break;
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
            MoleculeFactory.fireworksMode(x, y, 2, 2, 30);
            quantumEntanglement(particleType);
            QEDs.remove(this);
        }
    }
}