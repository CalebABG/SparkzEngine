package com.engine.Main;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EngineMethods.*;
import static com.engine.Interfaces_Extensions.EModes.GRAPH_MODE;
import static com.engine.JComponents.CMenuBar.createMenuBar;
import com.engine.EngineHelpers.EngineSplash;
import com.engine.GUIWindows.StatsPanel;
import com.engine.GUIWindows.QuitWindow;
import com.engine.GUIWindows.EException;
import com.engine.Interfaces_Extensions.TimerTaskX;
import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;

// Project started on: 11/22/2015 :D -- REQUIRES Java version 1.8 (lambda functions used) or higher to edit
// TODO: 2/23/2017 Create new SlideEditor
// TODO: 2/23/2017 Add Physics Editor
// TODO: 2/26/2017 Add Preset Editor
// TODO: 2/26/2017 Refactor Variables with "Good" naming schemes
// Notes:
// "./" for File, FileChooser, or File operations refers to the current working directory the program started from.
// Research into JVM Garbage Collection Optimization

public class Engine {
    public static void main(String[] args) {
        new EngineSplash(2700).display();
        SwingUtilities.invokeLater(() -> new Engine().start());
    }

    //Adding slight performance boost + UI properties
    static {
        //Uncomment for slight performance kick: will cause gui tearing
        //System.setProperty("sun.java2d.opengl", "True");
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
        EFrame.addWindowListener(new WindowClosing(windowEvent -> QuitWindow.getInstance()));

        Settings.loadSettings();
        EFrame.setJMenuBar(createMenuBar());

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
        EFrame.add(canvas);
        setupTimerTask();
        EFrame.setVisible(true);
    }

    /**
     * Engines Thread.run() method. Used as the main 'game-loop' to control the updates and renders of the program.
     * Tries to constrain the fps to around 60(capped). Credit for gameloop: http://www.java-gaming.org/index.php?topic=24220.0
     */
    private void setupTimerTask() {task = new TimerTaskX(this::simulationLoop);}

    private void simulationLoop(){
        final double SIM_HERTZ = fps, TIME_BETWEEN_UPDATES = 1.0E9D / SIM_HERTZ, TARGET_TIME_BETWEEN_RENDERS = 1.0E9D / SIM_HERTZ;
        final int MAX_UPDATES_BEFORE_RENDER = 1;
        double lastUpdateTime = System.nanoTime(), lastRenderTime;
        int lastSecondTime = (int) (lastUpdateTime / 1.0E9D);

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;

            render();
            lastRenderTime = now;

            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                if (!isPaused) {handleUpdates();}
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {lastUpdateTime = now - TIME_BETWEEN_UPDATES;}

            int thisSecond = (int) (lastUpdateTime / 1.0E9D);
            if (thisSecond > lastSecondTime) {lastSecondTime = thisSecond;}

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield(); now = System.nanoTime();
            }
        }
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
    public static synchronized void stop(){running = false; EFrame.setVisible(false); renderer.cancel(); renderer.purge(); System.exit(0);}

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
        if (graphics2D != null) graphics2D.dispose();
        buff.show(); Toolkit.getDefaultToolkit().sync();
    }

    /**
     * This method actively draws to the canvas of the program. It is used within the programs render method.
     */
    private void draw() {
        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (switchMode == GRAPH_MODE) graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        else graphics2D.translate(0, 0);
        handleRenders();
    }
}