package com.engine.ParticleTypes;

import com.engine.ParticleTypes.Interfaces.MoleculeBehavior;
import java.awt.*;
import static com.engine.EngineHelpers.EConstants.canvas;
import static com.engine.EngineHelpers.EConstants.random;
import static com.engine.Verlet.Vect2.clamp;
import static org.apache.commons.math3.util.FastMath.*;

public abstract class Molecule implements MoleculeBehavior {
    public  static Color plain_color   = new Color(63, 138, 242);
    private static Color default_color = Color.WHITE;

    private static float max_speed = 180.0f;
    public float x                 = 0.0f;
    public float y                 = 0.0f;
    public float radius            = 1.0f;
    public float vx                = 0.0f;
    public float vy                = 0.0f;
    public float ax                = 0.0f;
    public float ay                = 0.0f;

    public Color color = default_color;

    public Molecule() {
        radius = (random.nextFloat() * 10f) + 0.9f;
        x = (random.nextFloat() * canvas.getWidth()) - radius;
        y = (random.nextFloat() * canvas.getHeight()) - radius;
        vx = (float) (cos(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
        vy = (float) (sin(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
    }

    public Molecule(float x, float y, float radius) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vx = (float) (cos(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
        this.vy = (float) (sin(toRadians(random.nextFloat() * 360f)) * (random.nextFloat() * 10f));
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
        //add acceleration to velocity
        vx += ax;
        vy += ay;

        //limit velocity to max speed
        vx = clamp(vx, -max_speed, max_speed);
        vy = clamp(vy, -max_speed, max_speed);

        //add velocity to current position
        x += vx;
        y += vy;
        
        //reset acceleration
        ax = 0.0f;
        ay = 0.0f;
    }

    public final int getX() {return (int) x;}
    public final int getY() {return (int) y;}
    public final int getRadius() {return (int) radius;}
    public final int getVx() {return (int) vx;}
    public final int getVy() {return (int) vy;}
    public final int getAx() {return (int) ax;}
    public final int getAy() {return (int) ay;}

    public void boundsCheck() {
        float canvaswidth = canvas.getWidth();
        float canvasheight = canvas.getHeight();

        if (x - radius < 0) {
            x = 2 * radius - x;
            vx = -vx;
        }
        else if (x + radius > canvaswidth) {
            x = 2 * (canvaswidth - radius) - x;
            vx = -vx;
        }
        if (y - radius < 0) {
            y = 2 * radius - y;
            vy = -vy;
        }
        else if (y + radius > canvasheight) {
            y = 2 * (canvasheight - radius) - y;
            vy = -vy;
        }
    }

    public void gravitate(Molecule p, float gravStrength) {
        float dx = p.x - x;
        float dy = p.y - y;
        float dist = (float) sqrt(dx * dx + dy * dy);
        float forceX = (dx / gravStrength / dist);
        float forceY = (dy / gravStrength / dist);

        ax = forceX;
        ay = forceY;
    }
    
    //Helper Functions
    public float length() {return (float) sqrt(x * x + y * y);}
    public float lengthSQ() {return x * x + y * y;}

    public float velocity() {return (float) sqrt(vx * vx + vy * vy);}
    public float velocitySQ() {return vx * vx + vy * vy;}

    public float acceleration() {return (float) sqrt(ax * ax + ay * ay);}
    public float accelerationSQ() {return ax * ax + ay * ay;}
}
