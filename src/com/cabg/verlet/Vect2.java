package com.cabg.verlet;

import static com.cabg.core.EngineVariables.TWO_PI;
import static com.cabg.core.EngineVariables.random;
import static org.apache.commons.math3.util.FastMath.*;

public class Vect2 {
    public float x, y;

    public Vect2() {x = 0; y = 0;}
    public Vect2(float x, float y) {this.x = x; this.y = y;}

    public void set(float x, float y) {this.x = x; this.y = y;}
    public Vect2 set(float x, float y, int dummy) {this.x = x; this.y = y; return this;}
    public void set(Vect2 v) {this.x = v.x; this.y = v.y;}
    public void set(float[] source) {this.x = source[0]; this.y = source[1];}

    public static Vect2 randomVect2() {return new Vect2(random.nextFloat() * TWO_PI, random.nextFloat() * TWO_PI);}

    public Vect2 copy() {return new Vect2(this.x, this.y);}
    public float[] copyAsArray() {return new float[]{this.x, this.y};}

    public float length() {return (float) sqrt((this.x * this.x + this.y * this.y));}
    public float lengthSquared() {return this.x * this.x + this.y * this.y;}

    public static Vect2 fromAngle(float angle){
//        return new Vect2(cos(angle), sin(angle));
        return new Vect2((float) cos(toRadians(angle)), (float) sin(toRadians(angle)));
    }

    public void add(Vect2 v) {this.x += v.x; this.y += v.y;}
    public void add(float x, float y) {this.x += x; this.y += y;}
    public static Vect2 add(Vect2 v1, Vect2 v2) {return  new Vect2(v1.x + v2.x, v1.y + v2.y);}

    @SuppressWarnings("SuspiciousNameCombination")
    public Vect2 perp(Vect2 v) {
        this.x = -v.y;
        this.y =  v.x;
        return this;
    }

    public void sub(Vect2 v) {this.x -= v.x; this.y -= v.y;}
    public void sub(float x, float y) {this.x -= x; this.y -= y;}
    public Vect2 sub(Vect2 v0, Vect2 v1, int dummy) {
        this.x = v0.x - v1.x;
        this.y = v0.y - v1.y;
        return this;
    }
    public static Vect2 sub(Vect2 v1, Vect2 v2) {return new Vect2(v1.x - v2.x, v1.y - v2.y);}

    public void mult(float n) {this.x *= n; this.y *= n;}
    public void mult(Vect2 v) {this.x *= v.x; this.y *= v.y;}
    public static Vect2 mult(Vect2 v, float n) {return new Vect2(v.x * n, v.y * n);}
    public static Vect2 mult(Vect2 v1, Vect2 v2) {return new Vect2(v1.x * v2.x, v1.y * v2.y);}

    public void div(float n) {this.x /= n; this.y /= n;}
    public static Vect2 div(Vect2 v, float n) {return new Vect2(v.x / n, v.y / n);}
    public void div(Vect2 v) {this.x /= v.x; this.y /= v.y;}
    public static Vect2 div(Vect2 v1, Vect2 v2) {return new Vect2(v1.x / v2.x, v1.y / v2.y);}

    public float dist(Vect2 v) {
        float dx = this.x - v.x, dy = this.y - v.y; return (float) sqrt((dx * dx + dy * dy));
    }

    public float distSq(Vect2 v) {
        float dx = this.x - v.x, dy = this.y - v.y; return (dx * dx + dy * dy);
    }

    public static float dist(Vect2 v1, Vect2 v2) {
        float dx = v1.x - v2.x, dy = v1.y - v2.y; return (float) sqrt((dx * dx + dy * dy));
    }

    public static float distSq(Vect2 v1, Vect2 v2) {
        float dx = v1.x - v2.x, dy = v1.y - v2.y; return (dx * dx + dy * dy);
    }

    public float dot(Vect2 v) {return this.x * v.x + this.y * v.y;}
    public float dot(float x, float y) {return this.x * x + this.y * y;}
    public static float dot(Vect2 v1, Vect2 v2) {return v1.x * v2.x + v1.y * v2.y;}

    public void normalize() {float m = this.length(); if(m != 0.0D && m != 1.0D) {this.div(m);}}
    public void normal(Vect2 v0, Vect2 v1) {
        float nx = v0.y - v1.y,
                ny = v1.x - v0.x;
        // scale
        float len = (float) (1.0 / sqrt(nx * nx + ny * ny));
        this.x = nx * len;
        this.y = ny * len;
    }
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    public void limit(float max) {if(this.length() > max) {this.normalize(); this.mult(max);}}
    public void setMag(float len) {this.normalize(); this.mult(len);}

    public float heading() {return (float) atan2(this.y, this.x);}

    public void rotate(float theta) {
        float xTemp = this.x;
        this.x = (float) (this.x * cos(theta) - this.y * sin(theta));
        this.y = (float) (xTemp * sin(theta) + this.y * cos(theta));
    }

    public static float angleBetween(Vect2 v1, Vect2 v2) {
        return (v1.dot(v2) / (v1.length() * v2.length()));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static int lerp(int start, int stop, int amt) {return start + (stop - start) * amt;}
    public static int norm(int value, int start, int stop) {return (value - start) / (stop - start);}
    public static int clamp(int val, int min, int max){return max(min, min(max, val));}
    public static int map(int value, int sMin, int sMax, int dMin, int dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}

    public static float lerp(float start, float stop, float amt) {return start + (stop - start) * amt;}
    public static float norm(float value, float start, float stop) {return (value - start) / (stop - start);}
    public static float clamp(float val, float min, float max){return max(min, min(max, val));}
    public static float map(float value, float sMin, float sMax, float dMin, float dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}

    public static float nextMultiple(float number, int multiple){ return (float) (ceil(number / multiple) * multiple);}
}
