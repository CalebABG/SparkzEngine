package com.engine.ParticleTypes;

import com.engine.GUIWindows.FlowFieldEditor;
import com.engine.GUIWindows.ParticleGrapher;
import com.engine.ThinkingParticles.ReactiveColors;
import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;

import java.awt.*;

import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.PI;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EINTS.PARTICLE_BASE_LIFE;
import static com.engine.Enums.EngineMode.*;
import static com.engine.Enums.GravitationMode.*;
import static com.engine.GUIWindows.OrganicForcesEditor.*;
import static com.engine.ParticleHelpers.DrawingUtil.giveStyle;
import static com.engine.ParticleHelpers.ParticleModes.fireworksMode;
import static com.engine.Verlet.Vect2.*;
import static org.apache.commons.math3.util.FastMath.*;

public class Particle extends Molecule {
    public int life = PARTICLE_BASE_LIFE.value();
    public float angle = 0.0f;

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

    /**
     * The organic gravitation mode uses a profuse amount of memory, for some reason the JavaScript (Nashorn)
     * engine has a terrible memory usage problem with the eval method
     * If The object of type Molecule is not null, gravitation will be based on the molecule. * Also means that the object for type Point must be null,
     * and vice versa.
     */

    public void gravitateToPosition(float px, float py) {
        float dx = px - x;
        float dy = py - y;
        float dist = (float) sqrt(dx * dx + dy * dy);
        float forceX = dx / 5 / dist;
        float forceY = dy / 5 / dist;

        switch (PARTICLE_GRAVITATION_MODE) {
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
            case PARTICLE_REPELLENT:
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
        ParticleGrapher.engine.put("x", angle);

        Float tvx = ParticleGrapher.evaluateExpr(expressionForceX, false);
        Float tvy = ParticleGrapher.evaluateExpr(expressionForceY, false);

        vx = tvx == null ? vx : tvx;
        vy = tvy == null ? vy : tvy;

        angle += angleIncrement;

        if (angle >= 1000 * (2 * PI) * angleIncrement) angle = 0.0f;
    }

    private void handleFlowField() {
        float a = (float) (Noise.gradientCoherentNoise3D(
                x * FlowFieldEditor.noiseX,
                y * FlowFieldEditor.noiseY,
                frameCount * FlowFieldEditor.noiseZ,
                86317, NoiseQuality.FAST) * FlowFieldEditor.scaleC1 * PI * FlowFieldEditor.scaleC2);

        float x = (float) cos(FlowFieldEditor.startAngle);
        float y = (float) sin(FlowFieldEditor.startAngle);

        //Rotate
        float xTemp = x;
        x = (float) (x * cos(a) - y * sin(a));
        y = (float) (xTemp * sin(a) + y * cos(a));

        //Normalize
        float m = (float) sqrt(x * x + y * y);
        if (m != 0.0 && m != 1.0) {
            x /= m;
            y /= m;
        }

        //Set Magnitude
        x *= FlowFieldEditor.velocityMagnitude;
        y *= FlowFieldEditor.velocityMagnitude;

        //add to acceleration
        ax += x;
        ay += y;

        //Limit velocity
        vx = clamp(vx, -FlowFieldEditor.velocityLimit, FlowFieldEditor.velocityLimit);
        vy = clamp(vy, -FlowFieldEditor.velocityLimit, FlowFieldEditor.velocityLimit);
    }

    private void connectAll() {
        for (int i = 0; i < Particles.size(); i++) {
            Particle particle = Particles.get(i);
            graphics2D.setColor(particle.color);
            graphics2D.drawLine((int) particle.x, (int) particle.y, (int) x, (int) y);
        }
    }

    private void connectSequential() {
        Particle p2 = Particles.get((Particles.indexOf(this) + 1) % Particles.size());
        graphics2D.setColor(color);
        graphics2D.drawLine((int) p2.x, (int) p2.y, (int) x, (int) y);
    }

    public void boundsCheck() {
        float cw = canvas.getWidth();
        float ch = canvas.getHeight();

        if (ENGINE_MODE == GRAPH) {
            if (x > cw / 2) x = -cw / 2;
            if (x < -cw / 2) x = cw / 2;
            if (y > ch / 2) y = -ch / 2;
            if (y < -ch / 2) y = ch / 2;
        } else if (PARTICLE_BOUNDLESS.value()) {
            if (x > cw + radius) x = -radius;
            if (x < -radius) x = cw + radius;
            if (y > ch + radius) y = -radius;
            if (y < -radius) y = ch + radius;
        } else {
            super.boundsCheck();
        }
    }

    public void render() {
        Color color;
        if (REACTIVE_PARTICLES_ENABLED.value()) color = ReactiveColors.getReactiveComponent(velocity());
        else color = PLAIN_COLOR;

        if (CONNECT_PARTICLES.value()) {
            if (SHOW_PARTICLE_LINK.value()) connectSequential();
            else if (Particles.size() < 101) connectAll();
        }

        giveStyle(x - radius, y - radius, 2 * radius, color, PARTICLE_RENDER_MODE, baseParticleText);
    }

    public void update() {
        if (GRAVITATE_TO_MOUSE.value()) {
            if (ENGINE_MODE == GRAPH) {
                int cw = canvas.getWidth();
                int ch = canvas.getHeight();

                int mx = (int) map(Mouse.x, 0, cw, -cw / 2f, cw / 2f);
                int my = (int) map(Mouse.y, 0, ch, -ch / 2f, ch / 2f);

                gravitateToPosition(mx, my);
            } else {
                if (PARTICLE_GRAVITATION_MODE == ORGANIC && Particles.size() < 855) {
                    handleOrganic();
                } else if (PARTICLE_GRAVITATION_MODE == FLOW_FIELD) {
                    handleFlowField();
                } else {
                    gravitateToPosition(Mouse.x, Mouse.y);
                }
            }
        }

        accelerate();

        if (PARTICLE_FRICTION.value()) {
            final float dampening = 0.9993f;
            vx *= dampening;
            vy *= dampening;
        }

        boundsCheck();

        if (ENGINE_MODE == FIREWORKS && --life < 0) {
            if (PARTICLE_IS_SAFE_AMOUNT.value()) fireworksMode(x, y);
            Particles.remove(this);
        }
    }
}