package com.engine.EngineHelpers;

import com.engine.InputHandlers.*;
import com.engine.ParticleTypes.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.util.List;
import java.awt.*;
import java.util.*;

abstract public class EConstants {
    //Ints
    public static int switchMode = 0; // Limit @ 4
    public static int particleType = 0; // Limit @ 8
    public static int ptGravitationInt = 0; // Limit @ 6
    public static int fireworksAmount = 30;
    public static int particleMode = 1; // Limit @ 19
    public static int dragAmount = 1;  //Change before building
    public static int baseLife = 65;
    public static int rfParticleMode = 1, /*Limit @ 19*/ rfLife = 90, rfWind = 2, rfJitter = 5;
    public static int FPS = 60, timerFPS = (int) Math.floor(1000.0 / FPS);
    public static int oldMouseX = -1, oldMouseY = -1, pMouseX = 0, pMouseY = 0;
    public static int safetyAmount = 120;
    public static int cycleRate = 5;
    public static int maxParticleType = 8; // Limit @ 8
    public static int tps = 0, fps = 0; //Used for proper sim-loop

    //Doubles + Seed Variables
    public static double singleClickSizeMaxVal = 1.5, singleClickSizeMinVal = .8;
    public static double multiClickSizeMaxVal = 0.90, multiClickSizeMinVal = 0.80;
    public static double fireworksSizeMaxVal = 1.02, fireworksSizeMinVal = 0.82;
    public static double dragSizeMaxVal = 1.0, dragSizeMinVal = 0.90;
    public static double singleClickSpeedVal = 5.93, multiClickSpeedVal = 5.85,
            fireworksSpeedVal = 5.19, dragSpeedVal = 1.2354;

    //Engine Title
    public static String title = "Sparkz Engine :D";

    //Particles Custom Text String
    public static String customParticleText = "*", rfCustomParticleText = "*";

    //Background Color
    public static Color BGColor = Color.BLACK;

    //Canvas
    public static Canvas canvas = new Canvas();

    //Mouse Point
    public static Point Mouse = new Point();

    //Booleans
    public static boolean running = false;
    public static boolean isPaused = false;
    public static boolean thinkingParticles = true;
    public static boolean connectParticles = false;
    public static boolean particleFriction = true;
    public static boolean mouseGravitation = true;
    public static boolean isSafeAmount = true;
    public static boolean isCTRLDown = false;
    public static boolean isLeftMouseDown = false;
    public static boolean isRightMouseDown = false;
    public static boolean cycleColors = false;
    public static boolean GDMODEBOOL = false;
    public static boolean PTMODEBOOL = false;
    public static boolean DUPLEXMODE = false;
    public static boolean SMOOTH = true;

    //Particle ArrayLists
    public static List<Particle> ParticlesArray = Collections.synchronizedList(new ArrayList<>());
    public static List<GravityPoint> GravityPointsArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Fireworks> FireworksArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Emitter> EmitterArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Flux> FluxArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Eraser> EraserArray = Collections.synchronizedList(new ArrayList<>());
    public static List<QED> QEDArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Ion> IonArray = Collections.synchronizedList(new ArrayList<>());
    public static List<BlackHole> BlackHoleArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Duplex> DuplexArray = Collections.synchronizedList(new ArrayList<>());
    public static List<Portal> PortalArray = Collections.synchronizedList(new ArrayList<>());

    //Input Handlers: Keyboard and Mouse
    public static KHandler kHandler = new KHandler();
    public static MMotionListener mMotionListener = new MMotionListener();
    public static MListener mListener = new MListener();
    public static MWheelListener mWheelListener = new MWheelListener();

    //Frame and Graphics
    public static JFrame EFrame;
    public static BufferStrategy buff;
    public static Graphics2D graphics2D;
    public static Thread thread;
    private static GraphicsDevice GD = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static int width = GD.getDisplayMode().getWidth(), height = GD.getDisplayMode().getHeight();
}
