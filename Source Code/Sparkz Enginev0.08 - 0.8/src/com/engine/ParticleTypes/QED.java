package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;

public class QED extends Molecule {
    public int life, particletype;

    public QED() {super();}
    public QED(double _x, double _y, double _radius, double speed, int direction) {
        super(_x, _y, _radius, speed, direction, 0);
        life = (int) (Math.random() * 400 + 150);
        particletype = (int) (Math.random() * 11);
    }

    private void quantumEntanglement(int ptType) {
        if (ptType == 0) {ParticleModes.singleParticle((int) (x), (int) (y));}
        if (ptType == 1) {ParticleModes.singleSemtex(x, y);}
        if (ptType == 2) {ParticleModes.singleEmitter(x, y);}
        if (ptType == 3) {ParticleModes.singleGravityPoint(x, y);}
        if (ptType == 4) {ParticleModes.singleIon(x, y);}
        if (ptType == 5) {ParticleModes.createEraser(x, y, 2);}
        if (ptType == 6) {ParticleModes.singleBlackHole(x, y);}
        if (ptType == 7) {ParticleModes.singleDuplex(x, y);}
        if (ptType == 9) {ParticleModes.singleQED(x, y);}
    }

    public void giveStyle() {
        graphics2D.setColor(Particle.thinkingColors[(int) (Math.random() * 4)].darker().darker().darker());
        graphics2D.drawOval((int) x, (int) y, (int) (5 + radius), (int) (5 + radius));
        graphics2D.setFont(new Font("Arial", Font.PLAIN, (int) radius));
        graphics2D.setColor(Particle.thinkingColors[(int) (Math.random() * 4)]);
        graphics2D.drawString("?", (int) (x + radius / 3.07), (float) ((int) y + radius / 1.05));
    }

    public void render() {
        giveStyle();
    }
    public void update () {
        boundsCheck(); accelerateTo(vx,vy); particletype = (int) (Math.random() * 11);
        if (life == 0) {QEDArray.remove(this); ParticleModes.fireworksMode(x, y, 2, 2, 30); quantumEntanglement(particletype);}
        life -= 1;
    }
}
