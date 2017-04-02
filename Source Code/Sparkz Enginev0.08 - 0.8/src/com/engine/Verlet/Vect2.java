package com.engine.Verlet;

public class Vect2 {
    public double x, y;

    public Vect2() {x = Math.random(); y = Math.random();}
    public Vect2(double x, double y) {this.x = x; this.y = y;}

    public void set(double x, double y) {this.x = x; this.y = y;}
    public Vect2 set(double x, double y, int dummy) {this.x = x; this.y = y; return this;}
    public void set(Vect2 v) {this.x = v.x; this.y = v.y;}
    public void set(double[] source) {this.x = source[0]; this.y = source[1];}

    public static Vect2 random() {return new Vect2(Math.random() * Math.PI * 2.0, Math.random() * Math.PI * 2.0);}

    public Vect2 copy() {return new Vect2(this.x, this.y);}
    public double[] copyAsArray() {return new double[]{this.x, this.y};}

    public double length() {return Math.sqrt((this.x * this.x + this.y * this.y));}
    public double lengthSquared() {return this.x * this.x + this.y * this.y;}

    public void add(Vect2 v) {this.x += v.x; this.y += v.y;}
    public void add(double x, double y) {this.x += x; this.y += y;}
    public static Vect2 add(Vect2 v1, Vect2 v2) {return  new Vect2(v1.x + v2.x, v1.y + v2.y);}

    public Vect2 perp(Vect2 v) {
        this.x = -v.y;
        this.y =  v.x;
        return this;
    }

    public void sub(Vect2 v) {this.x -= v.x; this.y -= v.y;}
    public void sub(double x, double y) {this.x -= x; this.y -= y;}
    public Vect2 sub(Vect2 v0, Vect2 v1, int dummy) {
        this.x = v0.x - v1.x;
        this.y = v0.y - v1.y;
        return this;
    }
    public static Vect2 sub(Vect2 v1, Vect2 v2) {return new Vect2(v1.x - v2.x, v1.y - v2.y);}

    public void mult(double n) {this.x *= n; this.y *= n;}
    public void mult(Vect2 v) {this.x *= v.x; this.y *= v.y;}
    public static Vect2 mult(Vect2 v, double n) {return new Vect2(v.x * n, v.y * n);}
    public static Vect2 mult(Vect2 v1, Vect2 v2) {return new Vect2(v1.x * v2.x, v1.y * v2.y);}

    public void div(double n) {this.x /= n; this.y /= n;}
    public static Vect2 div(Vect2 v, double n) {return new Vect2(v.x / n, v.y / n);}
    public void div(Vect2 v) {this.x /= v.x; this.y /= v.y;}
    public static Vect2 div(Vect2 v1, Vect2 v2) {return new Vect2(v1.x / v2.x, v1.y / v2.y);}

    public double dist(Vect2 v) {
        double dx = this.x - v.x, dy = this.y - v.y; return Math.sqrt((dx * dx + dy * dy));
    }

    public double distSq(Vect2 v) {
        double dx = this.x - v.x, dy = this.y - v.y; return (dx * dx + dy * dy);
    }

    public static double dist(Vect2 v1, Vect2 v2) {
        double dx = v1.x - v2.x, dy = v1.y - v2.y; return Math.sqrt((dx * dx + dy * dy));
    }

    public static double distSq(Vect2 v1, Vect2 v2) {
        double dx = v1.x - v2.x, dy = v1.y - v2.y; return (dx * dx + dy * dy);
    }

    public double dot(Vect2 v) {return this.x * v.x + this.y * v.y;}
    public double dot(double x, double y) {return this.x * x + this.y * y;}
    public static double dot(Vect2 v1, Vect2 v2) {return v1.x * v2.x + v1.y * v2.y;}

    public void normalize() {double m = this.length(); if(m != 0.0D && m != 1.0D) {this.div(m);}}
    public void normal(Vect2 v0, Vect2 v1) {
        double nx = v0.y - v1.y,
                ny = v1.x - v0.x;
        // normalize
        double len = 1.0 / Math.sqrt(nx * nx + ny * ny);
        this.x = nx * len;
        this.y = ny * len;
    }
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }
    public void limit(double max) {if(this.length() > max) {this.normalize(); this.mult(max);}}
    public void setMag(double len) {this.normalize(); this.mult(len);}

    public double heading() {return Math.atan2(this.y, this.x);}

    public void rotate(double theta) {
        double xTemp = this.x;
        this.x = this.x * Math.cos(theta) - this.y * Math.sin(theta);
        this.y = xTemp * Math.sin(theta) + this.y * Math.cos(theta);
    }

    public static double angleBetween(Vect2 v1, Vect2 v2) {
        return (v1.dot(v2) / (v1.length() * v2.length()));
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public static double lerp(double start, double stop, double amt) {return start + (stop - start) * amt;}
    public static double norm(double value, double start, double stop) {return (value - start) / (stop - start);}
    public static double clamp(double val, double min, double max){return Math.min(Math.min(val, min), max);}
    public static double map(double value, double sMin, double sMax, double dMin, double dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}
}
