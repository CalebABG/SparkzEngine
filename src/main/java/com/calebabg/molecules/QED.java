package com.calebabg.molecules;

import com.calebabg.reactivity.ReactiveColors;
import com.calebabg.utilities.FontUtil;

import java.awt.*;

import static com.calebabg.core.EngineVariables.*;

public class QED extends Molecule {
    public int life = (int) ((random.nextFloat() * 400) + 150);
    public Font font;

    public QED(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
        font = new Font(FontUtil.DEFAULT_FONT_NAME, Font.PLAIN, (int) (2 * radius));
    }

    private void quantumEntanglement(int type) {
        switch (type) {
            case 0: MoleculeFactory.singleParticle((int) x, (int) y); break;
            case 1: MoleculeFactory.singleFlux(x, y); break;
            case 2: MoleculeFactory.singleEmitter(x, y); break;
            case 3: MoleculeFactory.singleGravityPoint(x, y); break;
            case 4: MoleculeFactory.singleIon(x, y); break;
            case 5: MoleculeFactory.createEraser(x, y, 3); break;
            case 6: MoleculeFactory.singleBlackHole(x, y); break;
            case 7: MoleculeFactory.singleDuplex(x, y); break;
            case 8: MoleculeFactory.singleQED(x, y); break;
        }
    }

    public void giveStyle() {
        graphics2D.setFont(font);
        graphics2D.setColor(ReactiveColors.getColor(random.nextInt(4)).darker());

        int size = (int) (2 * radius);
        graphics2D.drawOval((int) (x - radius), (int) (y - radius), size, size);
        graphics2D.setColor(ReactiveColors.getColor(random.nextInt(4)));

        String mark = "?";
        float qx = (float) ((x + (radius / 5) - 2.5) - (graphics2D.getFontMetrics().stringWidth(mark) / 2f));
        float qy = y + (radius / 2) + 3;
        graphics2D.drawString(mark, qx, qy);
    }

    public void render() {
        giveStyle();
    }

    public void update() {
        accelerate();
        checkBounds();
        if (--life < 0) {
            MoleculeFactory.multiFireworks(x, y, 2, 2, 30);
            quantumEntanglement((int) (random.nextFloat() * 11));
            QEDs.remove(this);
        }
    }
}
