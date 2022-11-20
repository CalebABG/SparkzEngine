package com.calebabg.molecules;

import com.calebabg.core.EngineVariables;
import com.calebabg.gui.FlowFieldEditor;
import com.calebabg.gui.OrganicForcesEditor;
import com.calebabg.gui.ParticleGraphEditor;
import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.enums.EngineMode.*;
import static com.calebabg.utilities.RenderUtil.giveStyle;
import static com.calebabg.utilities.MathUtil.clamp;
import static com.calebabg.utilities.MathUtil.map;
import static org.apache.commons.math3.util.FastMath.*;

public class Particle extends Molecule {
    private float angle = 0.0f;
    private int life = engineSettings.particleLife;

    public Particle(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
    }

    public Particle(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Particle(float x, float y, float radius, float speed, int pX, int pY, int tX, int tY) {
        super(x, y, (float) cos(atan2(toRadians(tY - pY), toRadians(tX - pX))) * speed,
                (float) sin(atan2(toRadians(tY - pY), toRadians(tX - pX))) * speed, radius);
    }

    public void gravitateToPosition(float px, float py) {
        float dx = px - x;
        float dy = py - y;
        float dist = (float) sqrt(dx * dx + dy * dy);
        float forceX = dx / 5 / dist;
        float forceY = dy / 5 / dist;

        switch (engineSettings.gravitationMode) {
            case COSINE_SINE:
                ax += cos(forceX);
                ay += sin(forceY);
                break;
            case ARC_TANGENT:
                float force = (float) atan2(forceY, forceX);
                ax += force;
                ay += force;
                break;
            case HORIZONTAL_WAVE:
                forceX = (dx / Float.POSITIVE_INFINITY / dist);
                ax += forceX;
                ay += forceY;
                break;
            case VERTICAL_WAVE:
                forceY = (dy / Float.POSITIVE_INFINITY) / dist;
                ax += forceX;
                ay += forceY;
                break;
            case SPIRALS:
                forceX = (float) (dx / atan2(10000f, atan2(py, px)) / dist);
                ax += forceX;
                ay += forceY;
                break;
            case REPELLENT:
                float scale = .35f;
                ax += -forceX * scale;
                ay += -forceY * scale;
                break;
            default:
                ax += forceX;
                ay += forceY;
                break;
        }
    }

    private void handleOrganic() {
        // Fps drops past ~1400
        if (Particles.size() > 1400) return;

        angle += OrganicForcesEditor.angleIncrement;
        if (angle > 1000 * (2 * EngineVariables.PI) * OrganicForcesEditor.angleIncrement) angle = 0.0f;

        ParticleGraphEditor.setParameter("x", angle);

        Float tvx = OrganicForcesEditor.evalForceXExpression();
        Float tvy = OrganicForcesEditor.evalForceYExpression();

        vx = tvx == null ? vx : tvx;
        vy = tvy == null ? vy : tvy;
    }

    private void handleFlowField() {
        float noise = (float) (Noise.gradientCoherentNoise3D(
                x * FlowFieldEditor.noiseX,
                y * FlowFieldEditor.noiseY,
                frameCount * FlowFieldEditor.noiseZ,
                86317, NoiseQuality.FAST) * FlowFieldEditor.scaleC1 * EngineVariables.PI * FlowFieldEditor.scaleC2);

        float x = (float) cos(FlowFieldEditor.startAngle);
        float y = (float) sin(FlowFieldEditor.startAngle);

        // Rotate
        float xTemp = x;
        x = (float) (x * cos(noise) - y * sin(noise));
        y = (float) (xTemp * sin(noise) + y * cos(noise));

        // Normalize
        float m = (float) sqrt(x * x + y * y);
        if (m != 0.0 && m != 1.0) {
            x /= m;
            y /= m;
        }

        // Set Magnitude
        x *= FlowFieldEditor.velocityMagnitude;
        y *= FlowFieldEditor.velocityMagnitude;

        // Add to acceleration
        ax += x;
        ay += y;

        // Limit velocity
        vx = clamp(vx, -FlowFieldEditor.velocityLimit, FlowFieldEditor.velocityLimit);
        vy = clamp(vy, -FlowFieldEditor.velocityLimit, FlowFieldEditor.velocityLimit);
    }

    private void connectAll() {
        for (int i = 0; i < Particles.size(); i++) {
            Particle particle = Particles.get(i);
            graphics2D.setColor(particle.getReactiveColor());
            graphics2D.drawLine((int) particle.x, (int) particle.y, (int) x, (int) y);
        }
    }

    private void connectSequential() {
        Particle p2 = Particles.get((Particles.indexOf(this) + 1) % Particles.size());
        graphics2D.setColor(getReactiveColor());
        graphics2D.drawLine((int) p2.x, (int) p2.y, (int) x, (int) y);
    }

    @Override
    public void checkBounds() {
        float cw = eCanvas.getWidth();
        float ch = eCanvas.getHeight();

        if (engineSettings.engineMode == GRAPH) {
            if (x > cw / 2) x = -cw / 2;
            if (x < -cw / 2) x = cw / 2;
            if (y > ch / 2) y = -ch / 2;
            if (y < -ch / 2) y = ch / 2;
        } else if (engineSettings.particleBoundless) {
            if (x > cw + radius) x = -radius;
            if (x < -radius) x = cw + radius;
            if (y > ch + radius) y = -radius;
            if (y < -radius) y = ch + radius;
        } else {
            super.checkBounds();
        }
    }

    public void render() {
        if (engineSettings.linkMolecules) {
            if (engineSettings.showParticlesLink) connectSequential();
            else if (Particles.size() < 101) connectAll();
        }

        giveStyle(x - radius, y - radius, 2 * radius, getReactiveColor(), engineSettings.particleRenderMode, baseParticleText);
    }

    public void update() {
        if (engineSettings.particlesGravitateToMouse) {
            if (engineSettings.engineMode == GRAPH) {
                int cw = eCanvas.getWidth();
                int ch = eCanvas.getHeight();

                int mx = (int) map(MouseVec.x, 0, cw, -cw / 2f, cw / 2f);
                int my = (int) map(MouseVec.y, 0, ch, -ch / 2f, ch / 2f);

                gravitateToPosition(mx, my);
            } else {
                switch (engineSettings.gravitationMode) {
                    case ORGANIC: handleOrganic(); break;
                    case FLOW_FIELD: handleFlowField(); break;
                    default: gravitateToPosition(MouseVec.x, MouseVec.y);break;
                }
            }
        }

        accelerate();

        if (engineSettings.particleFriction) {
            final float dampening = 0.9993f;
            vx *= dampening;
            vy *= dampening;
        }

        checkBounds();

        if (engineSettings.engineMode == FIREWORKS && --life < 0) {
            if (Particles.size() < engineSettings.fireworksParticleSafetyAmount) MoleculeFactory.multiFireworks(x, y);
            Particles.remove(this);
        }
    }
}