package com.engine.Main;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EngineMethods.*;
import com.engine.EngineHelpers.EngineSplash;
import com.engine.GUIWindows.StatsPanel;
import com.engine.GUIWindows.QuitWindow;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

// Project started on: 11/22/2015 :D -- REQUIRES Java version 1.8 (lambda functions used) or higher to edit

public class Engine {
    public static void main(String[] args) {
        new EngineSplash(2720).display(); SwingUtilities.invokeLater(() -> new Engine().start());
    }

    static {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.transaccel", "True");
        System.setProperty("sun.java2d.ddforcevram", "True");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
    }

    /**
     * Constructor for an Engine Object. Sets up all necessary components and adds needed listeners
     * as well as loading any saved Engine settings.
     */
    public Engine(){
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
    	EFrame = new JFrame(title);
        EFrame.setIconImage(Settings.getIcon());
        EFrame.setSize((int) (width / 1.8), (int) (height / 1.3));
        EFrame.setLocationRelativeTo(null);
        EFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        EFrame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {QuitWindow.getInstance();}});
        EFrame.getContentPane().setLayout(new BorderLayout(0, 0));

        Settings.loadSettings();

        canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.requestFocusInWindow();
        //Mouse Click Listener
        canvas.addMouseListener(mListener);
        //Mouse Move and Drag Listener
        canvas.addMouseMotionListener(mMotionListener);
        //Mouse Wheel Listener
        canvas.addMouseWheelListener(mWheelListener);
        //Keyboard Listener
        canvas.addKeyListener(kHandler);
        EFrame.addKeyListener(kHandler);
        EFrame.getContentPane().add(canvas, BorderLayout.CENTER);

        setupTimerTask();

        EFrame.setVisible(true);
    }

    /**
     * Engines Thread.run() method. Used as the main 'game-loop' to control the updates and renders of the program.
     * Tries to constrain the fps to around 60(capped). Credit for gameloop: http://www.java-gaming.org/index.php?topic=24220.0
     */
    private void setupTimerTask() {
        task = new TimerTask() {
            public void run() {
                final double GAME_HERTZ = FPS, TIME_BETWEEN_UPDATES = 1.0E9D / GAME_HERTZ;
                final int MAX_UPDATES_BEFORE_RENDER = 1;
                double lastUpdateTime = System.nanoTime(), lastRenderTime = System.nanoTime();
                final double TARGET_TIME_BETWEEN_RENDERS = 1.0E9D / FPS;
                int lastSecondTime = (int) (lastUpdateTime / 1.0E9D);

                while (running) {
                    double now = System.nanoTime();
                    int updateCount = 0;

                    if (!isPaused) {
                        while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                            update();
                            lastUpdateTime += TIME_BETWEEN_UPDATES;
                            updateCount++;
                        }
                    }

                    if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                        lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                    }

                    render();
                    lastRenderTime = now;

                    int thisSecond = (int) (lastUpdateTime / 1.0E9D);
                    if (thisSecond > lastSecondTime) {
                        lastSecondTime = thisSecond;
                    }

                    while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                        Thread.yield();
                        now = System.nanoTime();
                    }
                }
            }
        };
    }

    /**
     * Synchronized start of the program; Creates and then starts the programs Thread.
     */
    private synchronized void start() {
        running = true;
        renderer.scheduleAtFixedRate(task, 0, timerFPS);
        StatsPanel.getInstance();
    }

    /**
     * Synchronized stop of the program; Stops the Engines Thread, set's the disposes of the programs window, and then shuts down the program.
     */
    public static synchronized void stop(){running = false; EFrame.setVisible(false); stopRenderer(); System.exit(0);}
    public static void stopRenderer(){renderer.cancel(); renderer.purge();}
    /**
     * Method actively updates the programs Particle Arrays. It is used within the programs main Thread.
     */
    private void update() {handleUpdates();}

    /**
     * This method actively renders the canvas for the program; it creates and disposes of the Engines graphics Object each frame.
     * It is used within the programs main Thread.
     */
    private void render() {
        buff = canvas.getBufferStrategy();
        if(buff == null){canvas.createBufferStrategy(2); return;}
        graphics2D = (Graphics2D) buff.getDrawGraphics();
        handleGraphicsSmoothing();
        try {safetyBooleanChecks(); draw();} catch (Exception e) {EException.append(e);}
        if (graphics2D != null) {graphics2D.dispose();}
        buff.show(); Toolkit.getDefaultToolkit().sync();
    }

    /**
     * This method actively draws to the canvas of the program. It is used within the programs render method.
     */
    private void draw() {
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (switchMode == 3) graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        else graphics2D.translate(0, 0);
        handleRenders();
    }
}