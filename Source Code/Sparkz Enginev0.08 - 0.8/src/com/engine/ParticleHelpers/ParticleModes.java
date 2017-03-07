package com.engine.ParticleHelpers;

import com.engine.ParticleTypes.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Interfaces_Extensions.EModes.RAGDOLL_MODE;

import com.engine.Verlet.*;
import javax.swing.*;
import java.awt.event.MouseEvent;

public class ParticleModes {
    /**
     * Creates a particle object at the currposition of the passed in x and y.
     *
     * @param x X currposition.
     * @param y Y currposition.
     */
    public static void singleParticle(int x, int y) {
        Particle p = new Particle((x), (y), Math.random()* singleClickSizeMaxVal + singleClickSizeMinVal,
                Math.random() * singleClickSpeedVal, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    /**
     * Creates a particle objects at the coords of the event x and y.
     *
     * @param e Mouse Event.
     */
    public static void singleParticle(MouseEvent e) {
        double randRadius = Math.random() * singleClickSizeMaxVal + singleClickSizeMinVal;
        Particle p = new Particle((e.getX() - randRadius / 2), (e.getY() - randRadius / 2), randRadius,
                Math.random() * singleClickSpeedVal, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    public static void singleParticle(MouseEvent e, double max, double min, double speed) {
        double randRadius = Math.random() * max + min;
        Particle p = new Particle((e.getX() - randRadius / 2), (e.getY() - randRadius / 2), randRadius,
                Math.random() * speed, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    public static void singleParticle(double x, double y, double max, double min, double speed) {
        double randRadius = Math.random() * max + min;
        Particle p = new Particle((x - randRadius / 2), (y - randRadius / 2), randRadius,
                Math.random() * speed, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    public static void singleParticle(double x, double y) {
        Particle p = new Particle(x,y, 13, Math.random() * singleClickSpeedVal, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    public static void singleParticle(){
        Particle p = new Particle((int) (Math.random() * canvas.getWidth()),
                (int) (Math.random() * canvas.getHeight()), 13, Math.random() * singleClickSpeedVal, (int) (Math.random() * 360));
        ParticlesArray.add(p);
    }

    /**
     * Creates multiple particle objects (from Engine fireworks amount) at the coords of the event x and y.
     *
     * @param e Mouse Event.
     */
    public static void multiParticle(MouseEvent e) {
        for (int i = 0; i < fireworksAmount; i++) {
            singleParticle(e, multiClickSizeMaxVal, multiClickSizeMinVal, multiClickSpeedVal);
        }
    }

    public static void multiParticle(double x, double y) {
        for (int i = 0; i < fireworksAmount; i++) {
            singleParticle(x, y);
        }
    }

    /**
     * Creates multiple particle objects (from Engine fireworks amount) at the currposition of the passed in x and y.
     *
     * @param x X currposition.
     *
     * @param y Y currposition.
     */
    public static void fireworksMode(double x, double y) {
        for (int i = 0; i < fireworksAmount; i++) {
            Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                    Math.random() * fireworksSpeedVal, (int) (Math.random() * 360));
            FireworksArray.add(z);
        }
    }

    public static void fireworksMode(double x, double y, int wind, int speed) {
        for (int i = 0; i < fireworksAmount; i++) {
            Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                    Math.random() * speed, (int) (Math.random() * 360), wind);
            FireworksArray.add(z);
        }
    }

    public static void fireworksMode(double x, double y, int wind, double speed, int amount) {
        for (int i = 0; i < amount; i++) {
            Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                    Math.random() * speed, (int) (Math.random() * 360), wind);
            FireworksArray.add(z);
        }
    }

    public static void singleFirework(double x, double y) {
        Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                Math.random() * 5.32, (int) (Math.random() * 360));
        FireworksArray.add(z);
    }

    public static void singleGravityPoint() {
        GravityPoint g = new GravityPoint((int) (Math.random() * canvas.getWidth()),
                (int) (Math.random() * canvas.getHeight()), 8, Math.random() * 6.53, (int) (Math.random() * 360));
        GravityPointsArray.add(g);
    }

    public static void singleGravityPoint(MouseEvent e) {
        GravityPoint g = new GravityPoint(e.getX(), e.getY(), 8, Math.random() * 6.53, (int) (Math.random() * 360));
        GravityPointsArray.add(g);
    }

    public static void singleGravityPoint(double x, double y) {
        GravityPoint g = new GravityPoint(x, y, 8, Math.random() * 6.53, (int) (Math.random() * 360));
        GravityPointsArray.add(g);
    }

    public static void singleEmitter(MouseEvent e) {
        Emitter g = new Emitter(e.getX(), e.getY(), 8, Math.random() * 6.53, (int) (Math.random() * 360));
        EmitterArray.add(g);
    }

    public static void singleEmitter(double x, double y) {
        Emitter g = new Emitter(x, y, 8, Math.random() * 6.53, (int) (Math.random() * 360));
        EmitterArray.add(g);
    }

    public static void singleSemtex(MouseEvent e) {
        Flux g = new Flux(e.getX(), e.getY(), 25, Math.random() * 6.53, (int) (Math.random() * 360));
        FluxArray.add(g);
    }

    public static void singleSemtex() {
        Flux g = new Flux((int)(Math.random()* canvas.getWidth()),
                (int)(Math.random()* canvas.getHeight()), 25, Math.random() * 6.53, (int) (Math.random() * 360));
        FluxArray.add(g);
    }

    public static void singleSemtex(double x, double y) {
        Flux g = new Flux(x, y, 25, Math.random() * 6.53, (int) (Math.random() * 360));
        FluxArray.add(g);
    }

    public static void singleQED(MouseEvent e) {
        double radius = 25;
        QED g = new QED((e.getX()-radius/2), (e.getY()-(radius/2)), radius, Math.random() * 6.53, (int) (Math.random() * 360));
        QEDArray.add(g);
    }

    public static void singleQED(double x, double y) {
        QED g = new QED(x, y, 25, Math.random() * 6.53, (int) (Math.random() * 360));
        QEDArray.add(g);
    }

    public static void singleQED() {
        QED g = new QED((int)(Math.random()* canvas.getWidth()),
                (int)(Math.random()* canvas.getHeight()), 25, Math.random() * 6.53, (int) (Math.random() * 360));
        QEDArray.add(g);
    }

    public static void singleIon() {
        Ion g = new Ion((int)(Math.random()* canvas.getWidth()),
                (int)(Math.random()* canvas.getHeight()), 13, Math.random() * .53, (int) (Math.random() * 360));
        IonArray.add(g);
    }

    public static void singleIon(MouseEvent e) {
        int radius = 14;
        Ion g = new Ion((e.getX()-radius/2), (e.getY()-radius/2), radius, Math.random() * .53, (int) (Math.random() * 360));
        IonArray.add(g);
    }

    public static void singleIon(double x, double y) {
        Ion g = new Ion(x, y, 13, Math.random() * 6.53, (int) (Math.random() * 360));
        IonArray.add(g);
    }

    public static void singleBlackHole(double x, double y) {
        BlackHole g = new BlackHole(x, y, (Math.random()*20+10), Math.random() * 6.53, (int) (Math.random() * 360));
        BlackHoleArray.add(g);
    }

    public static void singleBlackHole(MouseEvent e) {
        BlackHole g = new BlackHole(e.getX(), e.getY(), (Math.random()*20+10), Math.random() * .53, (int) (Math.random() * 360));
        BlackHoleArray.add(g);
    }

    public static void singleBlackHole() {
        BlackHole g = new BlackHole((int) (Math.random() * canvas.getWidth()),
                (int) (Math.random() * canvas.getHeight()), (Math.random()*13+10), Math.random() * .53, (int) (Math.random() * 360));
        BlackHoleArray.add(g);
    }

    public static void singleDuplex(double x, double y) {
        Duplex g = new Duplex(x, y, 25, Math.random() * 6.53, (int) (Math.random() * 360));
        DuplexArray.add(g);
    }

    public static void singleDuplex(MouseEvent e) {
        Duplex g = new Duplex(e.getX(), e.getY(), (Math.random() * 20 + 10), Math.random() * .53, (int) (Math.random() * 360));
        DuplexArray.add(g);
    }

    public static void singleDuplex() {
        Duplex g = new Duplex((int) (Math.random() * canvas.getWidth()),
                (int) (Math.random() * canvas.getHeight()),
                (Math.random() * 13 + 10), Math.random() * .53, (int) (Math.random() * 360));
        DuplexArray.add(g);
    }

    public static void singlePortal(MouseEvent e) {
        if (PortalArray.size() <= 1) {
            double rad = (27);
            Portal g = new Portal((e.getX() - rad / 2), (e.getY() - rad / 2), rad);
            PortalArray.add(g);
        }
    }

    public static void multiGravityPoint(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) {
            singleGravityPoint(e);
        }
    }

    public static void createEraser(double x, double y, int amount) {
        for (int i = 0; i < amount; i++) {
            Eraser g = new Eraser(x, y, 5, Math.random() * 2.53, (int) (Math.random() * 360));
            EraserArray.add(g);
        }
    }

    public static void multiSemtex(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) {
            singleSemtex(e);
        }
    }

    public static void multiQED(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) {
            singleQED(e);
        }
    }

    public static void multiIon(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) {
            singleIon(e);
        }
    }

    /**
     * Creates multiple particle objects while dragging mpt (from Engine drag amount) at the coords of the event x and y.
     */
    public static void dragMode(MouseEvent e) {
        if (switchMode == RAGDOLL_MODE){
            if (SwingUtilities.isLeftMouseButton(e) && isCTRLDown) {
                VPHandler.dragMode(e);
            }
        }
        else {
            if (SwingUtilities.isLeftMouseButton(e)) {
                for (int i = 0; i < dragAmount; i++) {
                    singleParticle(e.getX(), e.getY(), dragSizeMaxVal, dragSizeMinVal, dragSpeedVal);
                }
            }
        }
    }

    /**
     * This method spawns a particle from half the screen width and the screen height-1 dimension.
     *
     * The particle accelerates in the direction (angle) calculated between the bottom of the screen and the location of the
     * mpt at the given moment.
     *
     * @see com.engine.Main.Engine
     */
    public static void fireworksTarget(MouseEvent e) {
        int w = canvas.getWidth(), h = canvas.getHeight();
        ParticlesArray.add(new Particle((w / 2), (h - 1),
                Math.random() * singleClickSizeMaxVal + singleClickSizeMinVal,
                Math.random() * singleClickSpeedVal + 3, (w / 2), (h - 1), e.getX(), e.getY()));
    }
}