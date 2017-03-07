package com.engine.Verlet;

public class Vect2D {
    public double x, y;

    public Vect2D() {x = Math.random(); y = Math.random();}
    public Vect2D(double x, double y) {this.x = x; this.y = y;}

    public void set(double x, double y) {this.x = x; this.y = y;}
    public void set(Vect2D v) {this.x = v.x; this.y = v.y;}
    public void set(double[] source) {this.x = source[0]; this.y = source[1];}

    public static Vect2D random() {return new Vect2D(Math.random() * Math.PI * 2.0, Math.random() * Math.PI * 2.0);}

    public Vect2D copy() {return new Vect2D(this.x, this.y);}
    public double[] copyAsArray() {return new double[]{this.x, this.y};}

    public double mag() {return Math.sqrt((this.x * this.x + this.y * this.y));}
    public double magSq() {return this.x * this.x + this.y * this.y;}

    public void add(Vect2D v) {this.x += v.x; this.y += v.y;}
    public void add(double x, double y) {this.x += x; this.y += y;}
    public static Vect2D add(Vect2D v1, Vect2D v2) {return  new Vect2D(v1.x + v2.x, v1.y + v2.y);}

    public void sub(Vect2D v) {this.x -= v.x; this.y -= v.y;}
    public void sub(double x, double y) {this.x -= x; this.y -= y;}
    public static Vect2D sub(Vect2D v1, Vect2D v2) {return new Vect2D(v1.x - v2.x, v1.y - v2.y);}

    public void mult(double n) {this.x *= n; this.y *= n;}
    public void mult(Vect2D v) {this.x *= v.x; this.y *= v.y;}
    public static Vect2D mult(Vect2D v, double n) {return new Vect2D(v.x * n, v.y * n);}
    public static Vect2D mult(Vect2D v1, Vect2D v2) {return new Vect2D(v1.x * v2.x, v1.y * v2.y);}

    public void div(double n) {this.x /= n; this.y /= n;}
    public static Vect2D div(Vect2D v, double n) {return new Vect2D(v.x / n, v.y / n);}
    public void div(Vect2D v) {this.x /= v.x; this.y /= v.y;}
    public static Vect2D div(Vect2D v1, Vect2D v2) {return new Vect2D(v1.x / v2.x, v1.y / v2.y);}

    public double dist(Vect2D v) {
        double dx = this.x - v.x, dy = this.y - v.y; return Math.sqrt((dx * dx + dy * dy));
    }

    public double distSq(Vect2D v) {
        double dx = this.x - v.x, dy = this.y - v.y; return (dx * dx + dy * dy);
    }

    public static double dist(Vect2D v1, Vect2D v2) {
        double dx = v1.x - v2.x, dy = v1.y - v2.y; return Math.sqrt((dx * dx + dy * dy));
    }

    public static double distSq(Vect2D v1, Vect2D v2) {
        double dx = v1.x - v2.x, dy = v1.y - v2.y; return (dx * dx + dy * dy);
    }

    public double dot(Vect2D v) {return this.x * v.x + this.y * v.y;}
    public double dot(double x, double y) {return this.x * x + this.y * y;}
    public static double dot(Vect2D v1, Vect2D v2) {return v1.x * v2.x + v1.y * v2.y;}

    public void normalize() {double m = this.mag(); if(m != 0.0D && m != 1.0D) {this.div(m);}}
    public void limit(double max) {if(this.mag() > max) {this.normalize(); this.mult(max);}}
    public void setMag(double len) {this.normalize(); this.mult(len);}

    public double heading() {return Math.atan2(this.y, this.x);}

    public void rotate(double theta) {
        double xTemp = this.x;
        this.x = this.x * Math.cos(theta) - this.y * Math.sin(theta);
        this.y = xTemp * Math.sin(theta) + this.y * Math.cos(theta);
    }

    public static double angleBetween(Vect2D v1, Vect2D v2) {
        return (v1.dot(v2) / (v1.mag() * v2.mag()));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static double lerp(double start, double stop, double amt) {return start + (stop - start) * amt;}
    public static double norm(double value, double start, double stop) {return (value - start) / (stop - start);}
    public static double clamp(double val, double min, double max){return Math.min(Math.min(val, min), max);}
    public static double map(double value, double sMin, double sMax, double dMin, double dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}
}
