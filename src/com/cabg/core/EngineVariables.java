package com.cabg.core;

import com.cabg.components.CMenuBar;
import com.cabg.particletypes.*;
import com.cabg.verlet.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class EngineVariables {
    public static EngineSettings engineSettings                     = new EngineSettings();

    public static int frameCount                                    = 0;
    public static int framesPerSecond                               = 0;

    public static final float PI                                    = 3.141592653589793f;
    public static final float HALF_PI                               = PI / 2f;
    public static final float TWO_PI                                = 2f * PI;

    public static String title                                      = "Sparkz Engine";
    public static String baseParticleText                           = "*";
    public static String fireworksParticleText                      = "*";

    public static final Random random                               = new Random();

    public static Color backgroundColor                             = Color.black;


    public static final Vec2 Mouse                                 = new Vec2();

    public static final List<Particle> Particles                    = new ArrayList<>(125_000);
    public static final List<Fireworks> Fireworks                   = new ArrayList<>(55_000);
    public static final List<GravityPoint> GravityPoints            = new ArrayList<>(150);
    public static final List<Emitter> Emitters                      = new ArrayList<>(100);
    public static final List<Flux> Fluxes                           = new ArrayList<>(100);
    public static final List<Eraser> Erasers                        = new ArrayList<>(150);
    public static final List<QED> QEDs                              = new ArrayList<>(150);
    public static final List<Ion> Ions                              = new ArrayList<>(150);
    public static final List<BlackHole> BlackHoles                  = new ArrayList<>(100);
    public static final List<Duplex> Duplexes                       = new ArrayList<>(100);
    public static final List<Portal> Portals                        = new ArrayList<>(2);

    public static JFrame EFrame;
    public static final CMenuBar menuBar                            = new CMenuBar();
    public static BufferStrategy buff;
    public static Graphics2D graphics2D;
    public static Canvas canvas                                     = new Canvas();
    public static Timer renderer                                    = new Timer();
    public static Toolkit toolkit                                   = Toolkit.getDefaultToolkit();
}
