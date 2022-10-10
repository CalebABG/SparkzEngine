package com.cabg.moleculehelpers;

import com.cabg.core.Engine;
import com.cabg.moleculetypes.Fireworks;
import com.cabg.moleculetypes.*;
import com.cabg.utilities.PhysicsUtil;
import com.cabg.verlet.Vec2;

import java.awt.event.MouseEvent;

import static com.cabg.core.EngineVariables.*;

public class MoleculeFactory {
    /**
     * Creates a particle object at the currposition of the passed in x and y.
     *
     * @param x X currposition.
     * @param y Y currposition.
     */
    public static void singleParticle(int x, int y) {
        Particle p = new Particle(x, y, (random.nextFloat() * engineSettings.singleClickSizeMax) + engineSettings.singleClickSizeMin,
                random.nextFloat() * engineSettings.singleClickSpeed, random.nextFloat() * 361);
        Particles.add(p);
    }

    /**
     * Creates a particle objects at the coords of the event x and y.
     *
     * @param e Mouse Event.
     */
    public static void singleParticle(MouseEvent e) {
        float randRadius = (random.nextFloat() * engineSettings.singleClickSizeMax) + engineSettings.singleClickSizeMin;
        Particle p = new Particle((e.getX() - randRadius / 2), (e.getY() - randRadius / 2), randRadius,
                random.nextFloat() * engineSettings.singleClickSpeed, random.nextFloat() * 361);
        Particles.add(p);
    }

    public static void singleParticle(MouseEvent e, float max, float min, float speed) {
        float randRadius = (random.nextFloat() * max) + min;
        Particle p = new Particle((e.getX() - randRadius / 2), (e.getY() - randRadius / 2), randRadius,
                random.nextFloat() * speed, random.nextFloat() * 361);
        Particles.add(p);
    }

    public static void singleParticle(float x, float y, float max, float min, float speed) {
        float randRadius = random.nextFloat() * max + min;
        Particle p = new Particle((x - randRadius / 2), (y - randRadius / 2), randRadius,
                random.nextFloat() * speed, random.nextFloat() * 361);
        Particles.add(p);
    }

    /**
     * Creates multiple particle objects (from Engine fireworks amount) at the coords of the event x and y.
     *
     * @param e Mouse Event.
     */
    public static void multiParticle(MouseEvent e) {
        for (int i = 0; i < engineSettings.fireworksAmount; i++) {
            singleParticle(e, engineSettings.multiClickSizeMax, engineSettings.multiClickSizeMin, engineSettings.multiClickSpeed);
        }
    }

    /**
     * Creates multiple particle objects (from Engine fireworks amount) at the currposition of the passed in x and y.
     *
     * @param x X currposition.
     * @param y Y currposition.
     */
    public static void fireworksMode(float x, float y) {
        for (int i = 0; i < engineSettings.fireworksAmount; i++) {
            Fireworks z = new Fireworks(x, y, (random.nextFloat() * engineSettings.fireworksSizeMax) + engineSettings.fireworksSizeMin,
                    random.nextFloat() * engineSettings.fireworksSpeed, random.nextFloat() * 361);
            Fireworks.add(z);
        }
    }

    public static void fireworksMode(float x, float y, int wind, int speed) {
        for (int i = 0; i < engineSettings.fireworksAmount; i++) {
            Fireworks z = new Fireworks(x, y, (random.nextFloat() * engineSettings.fireworksSizeMax) + engineSettings.fireworksSizeMin,
                    random.nextFloat() * speed, random.nextFloat() * 361, wind);
            Fireworks.add(z);
        }
    }

    public static void fireworksMode(float x, float y, int wind, float speed, int amount) {
        for (int i = 0; i < amount; i++) {
            Fireworks z = new Fireworks(x, y, (random.nextFloat() * engineSettings.fireworksSizeMax) + engineSettings.fireworksSizeMin,
                    random.nextFloat() * speed, random.nextFloat() * 361, wind);
            Fireworks.add(z);
        }
    }

    public static void singleGravityPoint(MouseEvent e) {
        GravityPoint g = new GravityPoint(e.getX(), e.getY(), 4, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        GravityPoints.add(g);
    }

    public static void singleGravityPoint(float x, float y) {
        GravityPoint g = new GravityPoint(x, y, 4, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        GravityPoints.add(g);
    }

    public static void singleEmitter(MouseEvent e) {
        Emitter g = new Emitter(e.getX(), e.getY(), 4, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Emitters.add(g);
    }

    public static void singleEmitter(float x, float y) {
        Emitter g = new Emitter(x, y, 4, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Emitters.add(g);
    }

    public static void singleFlux(MouseEvent e) {
        Flux g = new Flux(e.getX(), e.getY(), 12, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Fluxes.add(g);
    }

    public static void singleFlux(float x, float y) {
        Flux g = new Flux(x, y, 12, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Fluxes.add(g);
    }

    public static void singleQED(MouseEvent e) {
        float radius = 16;
        QED g = new QED((e.getX() - radius / 2), e.getY() - (radius / 2), radius, (float) (random.nextFloat() * 6.53), random.nextFloat() * 361);
        QEDs.add(g);
    }

    public static void singleQED(float x, float y) {
        QED g = new QED(x, y, 16, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        QEDs.add(g);
    }

    public static void singleIon(MouseEvent e) {
        float radius = 12;
        Ion g = new Ion((e.getX() - radius / 2), (e.getY() - radius / 2), radius, (float) (random.nextFloat() * .53), random.nextFloat() * 361);
        Ions.add(g);
    }

    public static void singleIon(float x, float y) {
        Ion g = new Ion(x, y, 12, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Ions.add(g);
    }

    public static void singleBlackHole(float x, float y) {
        BlackHole g = new BlackHole(x, y, (random.nextFloat() * 20) + 10, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        BlackHoles.add(g);
    }

    public static void singleBlackHole(MouseEvent e) {
        BlackHole g = new BlackHole(e.getX(), e.getY(), (random.nextFloat() * 20) + 10, (float) (random.nextFloat() * .53), random.nextFloat() * 361);
        BlackHoles.add(g);
    }

    public static void singleDuplex(float x, float y) {
        float r = 25;
        Duplex g = new Duplex(x, y, r, random.nextFloat() * 6.53f, random.nextFloat() * 361);
        Duplexes.add(g);
    }

    public static void singleDuplex(MouseEvent e) {
        Duplex g = new Duplex(e.getX(), e.getY(), (random.nextFloat() * 20) + 10, (float) (random.nextFloat() * .53), random.nextFloat() * 361);
        Duplexes.add(g);
    }

    public static void singlePortal(MouseEvent e) {
        if (Portals.size() < 2) {
            float rad = 10;
            Portal g = new Portal((e.getX() - rad / 2), (e.getY() - rad / 2), rad);
            Portals.add(g);
        }
    }

    public static void createEraser(float x, float y, int amount) {
        for (int i = 0; i < amount; i++) {
            Eraser g = new Eraser(x, y, 3, random.nextFloat() * 2.53f, random.nextFloat() * 361);
            Erasers.add(g);
        }
    }

    public static void multiGravityPoint(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) singleGravityPoint(e);
    }

    public static void multiFlux(MouseEvent e, int amount) {
        for (int i = 0; i < amount; i++) singleFlux(e);
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
    public static void handleDrag(MouseEvent e) {
        switch (engineSettings.engineMode) {
            case RAGDOLL:
                if (engineSettings.leftMouseButtonIsDown && engineSettings.controlKeyIsDown) {
                    PhysicsUtil.handleDrag(e);
                }
                break;
            case GRAPH:
                if (engineSettings.leftMouseButtonIsDown) {
                    for (int i = 0; i < engineSettings.particleDragAmount; i++) {
                        float mouseX = Vec2.map(Mouse.x, 0, canvas.getWidth(), -canvas.getWidth() / 2.0f, canvas.getWidth() / 2.0f);

                        float mouseY = Vec2.map(Mouse.y, 0, canvas.getHeight(), -canvas.getHeight() / 2.0f, canvas.getHeight() / 2.0f);

                        singleParticle(mouseX, mouseY, engineSettings.particleDragSizeMax, engineSettings.particleDragSizeMin, engineSettings.particleDragSpeed);
                    }
                }
                break;
            default:
                for (int i = 0; i < engineSettings.particleDragAmount; i++) {
                    singleParticle(e, engineSettings.particleDragSizeMax, engineSettings.particleDragSizeMin, engineSettings.particleDragSpeed);
                }
                break;
        }
    }

    /**
     * This method spawns a particle from half the screen width and the screen height-1 dimension.
     * <p>
     * The particle accelerates in the direction (angle) calculated between the bottom of the screen and the location of the
     * mpt at the given moment.
     *
     * @see Engine
     */
    public static void fireworksTarget(MouseEvent e) {
        int w = canvas.getWidth(), h = canvas.getHeight();
        Particles.add(new Particle((w / 2f), (h - 1),
                (random.nextFloat() * engineSettings.singleClickSizeMax) + engineSettings.singleClickSizeMin,
                (random.nextFloat() * engineSettings.singleClickSpeed) + 3, (w / 2), (h - 1), e.getX(), e.getY()));
    }
}