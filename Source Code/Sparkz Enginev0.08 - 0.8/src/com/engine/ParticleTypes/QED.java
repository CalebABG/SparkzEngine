package com.engine.ParticleTypes;

import com.engine.ParticleHelpers.ParticleModes;
import static com.engine.EngineHelpers.EConstants.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class QED extends Molecule {
    public int life, particletype;
    public Font font;

    public QED() {super();}
    public QED(double _x, double _y, double _radius, double speed, double direction) {
        super(_x, _y, _radius, speed, direction, 0);
        life = (int) (Math.random() * 400 + 150);
        particletype = (int) (Math.random() * 11);
        font = new Font("Arial", Font.PLAIN, (int) radius);
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
        graphics2D.draw(new Ellipse2D.Double(x - radius / 2, y - radius / 2, 2 * radius, 2 * radius));
        graphics2D.setFont(font);
        graphics2D.setColor(Particle.thinkingColors[(int) (Math.random() * 4)]);
        String qmark = "?";
        graphics2D.drawString(qmark, (float) ((x + radius / 2 + 1) - graphics2D.getFontMetrics().stringWidth(qmark) / 2), (float) (y + radius - 2));
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
