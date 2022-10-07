package com.engine.EngineHelpers;

import com.engine.Enums.*;
import com.engine.JComponents.CMenuBar;
import com.engine.ParticleTypes.*;
import com.engine.Verlet.Vect2;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;
import java.util.Timer;
import static com.engine.Enums.EngineMode.*;
import static com.engine.Enums.GravitationMode.*;
import static com.engine.Enums.MoleculeRenderMode.*;
import static com.engine.Enums.ParticleType.*;

public abstract class EConstants {
    // Enum<Integer>
    public static EngineMode ENGINE_MODE                            = NORMAL;
    public static ParticleType PARTICLE_TYPE                        = PARTICLE;
    public static GravitationMode PARTICLE_GRAVITATION_MODE         = DEFAULT;
    public static MoleculeRenderMode PARTICLE_RENDER_MODE           = RECTANGLE_NO_FILL;
    public static MoleculeRenderMode FIREWORKS_RENDER_MODE          = RECTANGLE_NO_FILL;

    /*Ints*/
    public static int frameCount                                    = 0;
    public static int framesPerSecond                               = 0;

    /*Floats*/
    public static final float PI                                    = 3.141592653589793f;
    public static final float HALF_PI                               = PI / 2f;
    public static final float TWO_PI                                = 2f * PI;

    /*Strings*/
    public static String title                                      = "Sparkz Engine";
    public static String baseParticleText                           = "*";
    public static String fireworksParticleText                      = "*";

    /*Random Number Generator*/
    public static final Random random                               = new Random();

    /*Background Color*/
    public static Color backgroundColor                             = Color.black;

    /*MenuBar*/
    public static final CMenuBar menuBar                            = new CMenuBar();

    /*Mouse Point*/
    public static final Vect2 Mouse                                 = new Vect2();

    /*Molecule Lists*/
    public static final List<Particle> Particles                    = new ArrayList<>(150_000);
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

    /*Frame and Graphics*/
    public static JFrame EFrame;
    public static BufferStrategy buff;
    public static Graphics2D graphics2D;
    public static Canvas canvas                                     = new Canvas();
    public static Timer renderer                                    = new Timer();
    public static Toolkit toolkit                                   = Toolkit.getDefaultToolkit();
}
