package com.calebabg.core;

import com.calebabg.molecules.*;
import com.calebabg.jcomponents.CMenuBar;
import com.calebabg.physics.Vec2;
import com.calebabg.physics.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EngineVariables {
    private EngineVariables(){}

    public static EngineSettings engineSettings                     = new EngineSettings();

    public static int frameCount                                    = 0;
    public static int framesPerSecond                               = 0;

    public static final float PI                                    = 3.141592653589793f;
    public static final float TWO_PI                                = 2f * PI;

    public static String title                                      = "Engine";
    public static String baseParticleText                           = "*";
    public static String fireworksParticleText                      = "*";

    public static final Random random                               = new Random();

    public static Color engineBackgroundColor                       = Color.black;

    public static final Vec2 MouseVec                               = new Vec2();

    public static final List<Particle> Particles                    = new ArrayList<>(150_000);
    public static final List<Firework> Fireworks                    = new ArrayList<>(125_000);
    public static final List<GravityPoint> GravityPoints            = new ArrayList<>(150);
    public static final List<Emitter> Emitters                      = new ArrayList<>(100);
    public static final List<Flux> Fluxes                           = new ArrayList<>(100);
    public static final List<Eraser> Erasers                        = new ArrayList<>(100);
    public static final List<QED> QEDs                              = new ArrayList<>(100);
    public static final List<Ion> Ions                              = new ArrayList<>(100);
    public static final List<BlackHole> BlackHoles                  = new ArrayList<>(100);
    public static final List<Duplex> Duplexes                       = new ArrayList<>(100);
    public static final List<Portal> Portals                        = new ArrayList<>(2);
    public static final List<Vertex> Vertices                       = new ArrayList<>(1000);

    public static CMenuBar eMenuBar                                 = new CMenuBar();
    public static JFrame eFrame                                     = new JFrame(title);
    public static Canvas eCanvas                                    = new Canvas();
    public static BufferStrategy bufferStrategy;
    public static Graphics2D graphics2D;
    public static Toolkit toolkit                                   = Toolkit.getDefaultToolkit();
    public static Image iconImage                                   = toolkit.getImage(EngineSettings.class.getResource("/images/EngineLogo.png"));
    public static Image splashImage                                 = toolkit.getImage(EngineSettings.class.getResource("/images/EngineSplash.png"));
}
