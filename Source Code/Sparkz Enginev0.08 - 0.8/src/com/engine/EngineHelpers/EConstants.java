package com.engine.EngineHelpers;

import com.engine.InputHandlers.*;
import com.engine.ParticleTypes.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import static java.lang.Math.ceil;
import static com.engine.Interfaces_Extensions.EModes.*;

public abstract class EConstants {
    /*Ints*/
    /*Limit @ 4 - Add Constant to EModes for any additional modes*/
    public static int switchMode                = NORMAL_MODE;
    /*Limit @ 8*/
    public static int particleType              = PARTICLE;
    /*Limit @ 7*/
    public static int particleGravitationMode   = DEFAULT;
    /*Limit @ 19*/
    public static int particleRenderType        = RECTANGLE_NOFILL;
    /*Limit @ 19*/
    public static int fireworksRenderType       = RECTANGLE_NOFILL;
    public static int dragAmount                = 1;
    public static int baseLife                  = 65;
    public static int fireworksAmount           = 30;
    public static int fireworksLife             = 90;
    public static int fireworksWind             = 2;
    public static int fireworksJitter           = 5;
    public static int fps                       = 60;
    public static int timerFPS                  = (int) ceil(1000.0 / fps);
    public static int oldMouseX                 = -1;
    public static int oldMouseY                 = -1;
    public static int pMouseX                   = 0;
    public static int pMouseY                   = 0;
    public static int safetyAmount              = 120;
    public static int cycleRate                 = 5;

    /*Doubles + Seed Variables*/
    public static double singleClickSizeMaxVal  = 1.5;
    public static double singleClickSizeMinVal  = 0.8;
    public static double multiClickSizeMaxVal   = 0.90;
    public static double multiClickSizeMinVal   = 0.80;
    public static double fireworksSizeMaxVal    = 1.02;
    public static double fireworksSizeMinVal    = 0.82;
    public static double dragSizeMaxVal         = 1.0;
    public static double dragSizeMinVal         = 0.90;
    public static double singleClickSpeedVal    = 5.93;
    public static double multiClickSpeedVal     = 5.85;
    public static double fireworksSpeedVal      = 5.19;
    public static double dragSpeedVal           = 1.2354;

    /*Strings*/
    public static String title                  = "Sparkz Engine :D";
    public static String baseParticleText       = "*";
    public static String fireworksParticleText  = "*";

    /*Background Color*/
    public static Color backgroundColor         = Color.BLACK;

    /*Canvas*/
    public static Canvas canvas                 = new Canvas();

    /*Mouse Point*/
    public static Point Mouse                   = new Point();

    /*Booleans*/
    public static boolean running               = false;
    public static boolean isPaused              = false;
    public static boolean thinkingParticles     = true;
    public static boolean connectParticles      = false;
    public static boolean particleFriction      = true;
    public static boolean mouseGravitation      = true;
    public static boolean isSafeAmount          = true;
    public static boolean isCTRLDown            = false;
    public static boolean isLeftMouseDown       = false;
    public static boolean isRightMouseDown      = false;
    public static boolean cycleColors           = false;
    public static boolean GDMODEBOOL            = false;
    public static boolean PTMODEBOOL            = false;
    public static boolean DUPLEXMODE            = false;
    public static boolean SMOOTH                = true;

    /*Particle ArrayLists*/
    public static List<Particle> ParticlesArray         = Collections.synchronizedList(new ArrayList<>());
    public static List<GravityPoint> GravityPointsArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Fireworks> FireworksArray        = Collections.synchronizedList(new ArrayList<>());
    public static List<Emitter> EmitterArray            = Collections.synchronizedList(new ArrayList<>());
    public static List<Flux> FluxArray                  = Collections.synchronizedList(new ArrayList<>());
    public static List<Eraser> EraserArray              = Collections.synchronizedList(new ArrayList<>());
    public static List<QED> QEDArray                    = Collections.synchronizedList(new ArrayList<>());
    public static List<Ion> IonArray                    = Collections.synchronizedList(new ArrayList<>());
    public static List<BlackHole> BlackHoleArray        = Collections.synchronizedList(new ArrayList<>());
    public static List<Duplex> DuplexArray              = Collections.synchronizedList(new ArrayList<>());
    public static List<Portal> PortalArray              = Collections.synchronizedList(new ArrayList<>());

    /*Input Handlers: Keyboard and Mouse*/
    public static KHandler kHandler               = new KHandler();
    public static MMotionListener mMotionListener = new MMotionListener();
    public static MListener mListener             = new MListener();
    public static MWheelListener mWheelListener   = new MWheelListener();

    /*Frame and Graphics*/
    public static JFrame EFrame;
    public static BufferStrategy buff;
    public static Graphics2D graphics2D;
    public static Timer renderer = new Timer();
    public static TimerTask task;
    public static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static int width = toolkit.getScreenSize().width, height = toolkit.getScreenSize().height;
}
