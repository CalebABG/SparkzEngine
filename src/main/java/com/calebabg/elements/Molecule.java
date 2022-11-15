package com.calebabg.elements;

import com.calebabg.core.EngineEntity;
import com.calebabg.reactivity.ReactiveColors;
import com.calebabg.utilities.ColorUtil;

import java.awt.*;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.utilities.MathUtil.clamp;
import static org.apache.commons.math3.util.FastMath.*;

public abstract class Molecule implements EngineEntity {
    public static final float MAX_SPEED = 180.0f;
    public static Color DEFAULT_COLOR = ColorUtil.fromHex("#fcaa00");

    public float radius, x, y, vx, vy, ax, ay;

    public Color color = DEFAULT_COLOR;

    public Molecule() {
        x = (random.nextFloat() * canvas.getWidth()) - radius;
        y = (random.nextFloat() * canvas.getHeight()) - radius;
        radius = (random.nextFloat() * 10f) + 0.9f;
        vx = (float) (cos(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
        vy = (float) (sin(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
    }

    public Molecule(float x, float y, float radius) {
        this();
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public Molecule(float x, float y, float vx, float vy, float radius) {
        this(x, y, radius);
        this.vx = vx;
        this.vy = vy;
    }

    public Molecule(float x, float y, float radius, float speed, float direction, int dummy) {
        this(x, y, (float) (cos(toRadians(direction)) * speed), (float) (sin(toRadians(direction)) * speed), radius);
    }

    public void accelerate() {
        // Add acceleration to velocity
        vx += ax;
        vy += ay;

        // Limit velocity to max speed
        vx = clamp(vx, -MAX_SPEED, MAX_SPEED);
        vy = clamp(vy, -MAX_SPEED, MAX_SPEED);

        // Add velocity to current position
        x += vx;
        y += vy;

        // Reset acceleration
        ax = 0.0f;
        ay = 0.0f;
    }

    public void checkBounds() {
        if (x - radius < 0) {
            x = 2 * radius - x;
            vx = -vx;
        }

        if (x + radius > canvas.getWidth()) {
            x = 2 * (canvas.getWidth() - radius) - x;
            vx = -vx;
        }

        if (y - radius < 0) {
            y = 2 * radius - y;
            vy = -vy;
        }

        if (y + radius > canvas.getHeight()) {
            y = 2 * (canvas.getHeight() - radius) - y;
            vy = -vy;
        }
    }

    public void gravitateToMolecule(Molecule p, float strength) {
        float dx = p.x - x;
        float dy = p.y - y;
        float dist = (float) sqrt(dx * dx + dy * dy);
        float forceX = (dx / strength / dist);
        float forceY = (dy / strength / dist);

        ax = forceX;
        ay = forceY;
    }

    public Color getReactiveColor() {
        return engineSettings.reactiveColorsEnabled
                ? ReactiveColors.getReactiveColor(velocity())
                : color;
    }

    public float length() {
        return (float) sqrt(x * x + y * y);
    }

    public float lengthSQ() {
        return x * x + y * y;
    }

    public float velocity() {
        return (float) sqrt(vx * vx + vy * vy);
    }

    public float velocitySQ() {
        return vx * vx + vy * vy;
    }

    public float acceleration() {
        return (float) sqrt(ax * ax + ay * ay);
    }

    public float accelerationSQ() {
        return ax * ax + ay * ay;
    }
}
