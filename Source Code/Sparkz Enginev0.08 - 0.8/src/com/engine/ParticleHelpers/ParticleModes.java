package com.engine.ParticleHelpers;

import com.engine.ParticleTypes.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.J8Helpers.Interfaces.EModes.RAGDOLL_MODE;

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
        Particle p = new Particle(x, y, Math.random()* singleClickSizeMaxVal + singleClickSizeMinVal,
                Math.random() * singleClickSpeedVal, Math.random() * 361);
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
                Math.random() * singleClickSpeedVal, Math.random() * 361);
        ParticlesArray.add(p);
    }

    public static void singleParticle(MouseEvent e, double max, double min, double speed) {
        double randRadius = Math.random() * max + min;
        Particle p = new Particle((e.getX() - randRadius / 2), (e.getY() - randRadius / 2), randRadius,
                Math.random() * speed, Math.random() * 361);
        ParticlesArray.add(p);
    }

    public static void singleParticle(double x, double y, double max, double min, double speed) {
        double randRadius = Math.random() * max + min;
        Particle p = new Particle((x - randRadius / 2), (y - randRadius / 2), randRadius,
                Math.random() * speed, Math.random() * 361);
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
                    Math.random() * fireworksSpeedVal, Math.random() * 361);
            FireworksArray.add(z);
        }
    }

    public static void fireworksMode(double x, double y, int wind, int speed) {
        for (int i = 0; i < fireworksAmount; i++) {
            Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                    Math.random() * speed, Math.random() * 361, wind);
            FireworksArray.add(z);
        }
    }

    public static void fireworksMode(double x, double y, int wind, double speed, int amount) {
        for (int i = 0; i < amount; i++) {
            Fireworks z = new Fireworks(x, y, Math.random() * fireworksSizeMaxVal + fireworksSizeMinVal,
                    Math.random() * speed, Math.random() * 361, wind);
            FireworksArray.add(z);
        }
    }

    public static void singleGravityPoint(MouseEvent e) {
        GravityPoint g = new GravityPoint(e.getX(), e.getY(), 4, Math.random() * 6.53, Math.random() * 361);
        GravityPointsArray.add(g);
    }

    public static void singleGravityPoint(double x, double y) {
        GravityPoint g = new GravityPoint(x, y, 4, Math.random() * 6.53, Math.random() * 361);
        GravityPointsArray.add(g);
    }

    public static void singleEmitter(MouseEvent e) {
        Emitter g = new Emitter(e.getX(), e.getY(), 4, Math.random() * 6.53, Math.random() * 361);
        EmitterArray.add(g);
    }

    public static void singleEmitter(double x, double y) {
        Emitter g = new Emitter(x, y, 4, Math.random() * 6.53, Math.random() * 361);
        EmitterArray.add(g);
    }

    public static void singleSemtex(MouseEvent e) {
        Flux g = new Flux(e.getX(), e.getY(), 11, Math.random() * 6.53, Math.random() * 361);
        FluxArray.add(g);
    }

    public static void singleSemtex(double x, double y) {
        Flux g = new Flux(x, y, 11, Math.random() * 6.53, Math.random() * 361);
        FluxArray.add(g);
    }

    public static void singleQED(MouseEvent e) {
        double radius = 16;
        QED g = new QED((e.getX() - radius / 2), (e.getY() - (radius / 2)), radius, Math.random() * 6.53, Math.random() * 361);
        QEDArray.add(g);
    }

    public static void singleQED(double x, double y) {
        QED g = new QED(x, y, 16, Math.random() * 6.53, Math.random() * 361);
        QEDArray.add(g);
    }

    public static void singleIon(MouseEvent e) {
        double radius = 11;
        Ion g = new Ion((e.getX() - radius / 2), (e.getY() - radius / 2), radius, Math.random() * .53, Math.random() * 361);
        IonArray.add(g);
    }

    public static void singleIon(double x, double y) {
        Ion g = new Ion(x, y, 11, Math.random() * 6.53, Math.random() * 361);
        IonArray.add(g);
    }

    public static void singleBlackHole(double x, double y) {
        BlackHole g = new BlackHole(x, y, (Math.random() * 20) + 10, Math.random() * 6.53, Math.random() * 361);
        BlackHoleArray.add(g);
    }

    public static void singleBlackHole(MouseEvent e) {
        BlackHole g = new BlackHole(e.getX(), e.getY(), (Math.random() * 20) + 10, Math.random() * .53, Math.random() * 361);
        BlackHoleArray.add(g);
    }

    public static void singleDuplex(double x, double y) {
        double r = 25;
        Duplex g = new Duplex(x, y, r, Math.random() * 6.53, Math.random() * 361);
        DuplexArray.add(g);
    }

    public static void singleDuplex(MouseEvent e) {
        Duplex g = new Duplex(e.getX(), e.getY(), (Math.random() * 20) + 10, Math.random() * .53, Math.random() * 361);
        DuplexArray.add(g);
    }

    public static void singlePortal(MouseEvent e) {
        if (PortalArray.size() <= 1) {
            double rad = 10;
            Portal g = new Portal((e.getX() - rad / 2), (e.getY() - rad / 2), rad);
            PortalArray.add(g);
        }
    }

    public static void createEraser(double x, double y, int amount) {
        for (int i = 0; i < amount; i++) {
            Eraser g = new Eraser(x, y, 3, Math.random() * 2.53, Math.random() * 361);
            EraserArray.add(g);
        }
    }

    public static void multiGravityPoint(MouseEvent e, int amount) {for (int i = 0; i < amount; i++) singleGravityPoint(e);}
    public static void multiSemtex(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) singleSemtex(e);
    }
    public static void multiQED(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) singleQED(e);
    }
    public static void multiIon(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) singleIon(e);
    }

    /**
     * Creates multiple particle objects while dragging mpt (from Engine drag amount) at the coords of the event x and y.
     */
    public static void dragMode(MouseEvent e) {
        if (engineMode == RAGDOLL_MODE) {
            if (isLeftMouseDown && isCTRLDown) {
                VPHandler.dragMode(e);
            }
        }
        else {
            if (isLeftMouseDown) {
                for (int i = 0; i < dragAmount; i++) {
                    singleParticle(e, dragSizeMaxVal, dragSizeMinVal, dragSpeedVal);
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