package com.cabg.verlet;

import static com.cabg.core.EngineVariables.TWO_PI;
import static com.cabg.core.EngineVariables.random;
import static org.apache.commons.math3.util.FastMath.*;

public class Vec2 {
    public float x, y;

    public Vec2() {}

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2 v) {
        x = v.x;
        y = v.y;
    }

    public void set(float[] source) {
        x = source[0];
        y = source[1];
    }

    public static Vec2 random() {
        return new Vec2(random.nextFloat() * TWO_PI, random.nextFloat() * TWO_PI);
    }

    public Vec2 copy() {
        return new Vec2(x, y);
    }

    public float[] copyAsArray() {
        return new float[]{x, y};
    }

    public float length() {
        return (float) sqrt((x * x + y * y));
    }

    public float lengthSquared() {
        return x * x + y * y;
    }

    public static Vec2 fromAngle(float angle) {
        return new Vec2((float) cos(toRadians(angle)), (float) sin(toRadians(angle)));
    }

    public void add(Vec2 v) {
        x += v.x;
        y += v.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public static Vec2 add(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.x + v2.x, v1.y + v2.y);
    }

    public Vec2 perp(Vec2 v) {
        x = -v.y;
        y = v.x;
        return this;
    }

    public void sub(Vec2 v) {
        x -= v.x;
        y -= v.y;
    }

    public void sub(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void sub(Vec2 v1, Vec2 v2, int dummy) {
        x = v1.x - v2.x;
        y = v1.y - v2.y;
    }

    public void mult(float n) {
        x *= n;
        y *= n;
    }

    public void mult(Vec2 v) {
        x *= v.x;
        y *= v.y;
    }

    public static Vec2 mult(Vec2 v, float n) {
        return new Vec2(v.x * n, v.y * n);
    }

    public static Vec2 mult(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.x * v2.x, v1.y * v2.y);
    }

    public void div(float n) {
        x /= n;
        y /= n;
    }

    public static Vec2 div(Vec2 v, float n) {
        return new Vec2(v.x / n, v.y / n);
    }

    public void div(Vec2 v) {
        x /= v.x;
        y /= v.y;
    }

    public static Vec2 div(Vec2 v1, Vec2 v2) {
        return new Vec2(v1.x / v2.x, v1.y / v2.y);
    }

    public float dist(Vec2 v) {
        float dx = x - v.x, dy = y - v.y;
        return (float) sqrt((dx * dx + dy * dy));
    }

    public float distSq(Vec2 v) {
        float dx = x - v.x, dy = y - v.y;
        return (dx * dx + dy * dy);
    }

    public static float dist(Vec2 v1, Vec2 v2) {
        float dx = v1.x - v2.x, dy = v1.y - v2.y;
        return (float) sqrt((dx * dx + dy * dy));
    }

    public static float distSq(Vec2 v1, Vec2 v2) {
        float dx = v1.x - v2.x, dy = v1.y - v2.y;
        return dx * dx + dy * dy;
    }

    public float dot(Vec2 v) {
        return x * v.x + y * v.y;
    }

    public float dot(float x, float y) {
        return this.x * x + this.y * y;
    }

    public static float dot(Vec2 v1, Vec2 v2) {
        return v1.x * v2.x + v1.y * v2.y;
    }

    public void normalize() {
        float m = length();
        if (m != 0.0D && m != 1.0D)
            div(m);
    }

    public void normal(Vec2 v0, Vec2 v1) {
        float nx = v0.y - v1.y,
                ny = v1.x - v0.x;
        // Scale
        float len = (float) (1.0 / sqrt(nx * nx + ny * ny));
        x = nx * len;
        y = ny * len;
    }

    public void negate() {
        x = -x;
        y = -y;
    }

    public void limit(float max) {
        if (length() > max) {
            normalize();
            mult(max);
        }
    }

    public void setMag(float len) {
        normalize();
        mult(len);
    }

    public float heading() {
        return (float) atan2(y, x);
    }

    public void rotate(float theta) {
        float xTemp = x;
        x = (float) (x * cos(theta) - y * sin(theta));
        y = (float) (xTemp * sin(theta) + y * cos(theta));
    }

    public static float angleBetween(Vec2 v1, Vec2 v2) {
        return (v1.dot(v2) / (v1.length() * v2.length()));
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
