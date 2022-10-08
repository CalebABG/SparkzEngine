package com.cabg.core;

import com.cabg.gui.ExceptionLogger;
import com.cabg.gui.QuitWindow;
import com.cabg.gui.StatsPanel;
import com.cabg.inputhandlers.*;
import com.cabg.utilities.Settings;
import com.cabg.gui.SplashScreen;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

import static com.cabg.core.EngineMethods.*;
import static com.cabg.core.EngineVariables.*;
import static com.cabg.gui.Notifier.headsUpNotifications;

// Project started on: 11/22/2015 :D -- REQUIRES Java version 1.8 (lambda functions used) or higher to edit
// TODO: 2/23/2017 Create new SlideEditor
// TODO: 2/26/2017 Refactor Variables with "Good" naming schemes
// TODO: 3/31/2017 Add Selective Adding(Adding children of Molecule class and Physics objects depending on mode)
// TODO: 7/3/2017 GO BACK TO: CMenbar, KHandler, Settings, SettingsEditor
// Notes:
// "./" for File, FileChooser, or File operations refers to the current working directory the program started from.

public class Engine {
    public static void main(String[] args) {
        new SplashScreen(2000).display();
        SwingUtilities.invokeLater(() -> new Engine().start());
    }

    //Adding slight performance boost + UI properties
    static {
        //Uncomment for slight performance kick: will cause gui tearing
        //System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.transaccel", "true");
        System.setProperty("sun.java2d.ddforcevram", "true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
    }

    /**
     * Constructor for an Engine Object. Sets up all necessary components and adds needed listeners
     * as well as loading any saved Engine settings.
     */
    public Engine() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        EFrame = new JFrame(title);
        EFrame.setIconImage(Settings.iconImage);
        EFrame.setSize(980, 680);
        EFrame.setLocationRelativeTo(null);
        EFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        EFrame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> BackgroundThread.run(QuitWindow::getInstance)));

        // Load Saved Settings
        Settings.loadSettings();

        // Setup Menu-bar
        EFrame.setJMenuBar(menuBar);

        // Mouse Click Listener
        canvas.addMouseListener(new MouseButtonHandler());

        // Mouse Move and Drag Listener
        canvas.addMouseMotionListener(new MouseMotionHandler());

        // Mouse Wheel Listener
        canvas.addMouseWheelListener(new MouseWheelHandler());

        // Keyboard Listener
        KeyboardHandler handler = new KeyboardHandler();
        canvas.addKeyListener(handler);
        EFrame.addKeyListener(handler);
        EFrame.add(canvas);

        // Display Sparkz Engine
        EFrame.setVisible(true);
    }

    private void simulationLoop() {
        engineSettings.running = true;

        final long tB = 1_000_000_000;
        final int TIME_BETWEEN_UPDATES = (int) (tB / engineSettings.desiredFramesPerSecond);
        final short MAX_UPDATES_BEFORE_RENDER = 1;
        long lastUpdateTime = System.nanoTime();
        long lastSecondTime = lastUpdateTime / tB;

        while (engineSettings.running) {
            long now = System.nanoTime();
            short updateCount = 0;

            //  Keep track of frameCount
            if (++frameCount > Integer.MAX_VALUE - 1) frameCount = 0;

            // Keep track of fps
            framesPerSecond++;

            render();

            while (((now - lastUpdateTime) > TIME_BETWEEN_UPDATES) && (updateCount < MAX_UPDATES_BEFORE_RENDER)) {
                if (!engineSettings.paused) handleUpdates();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) lastUpdateTime = now - TIME_BETWEEN_UPDATES;

            int thisSecond = (int) (lastUpdateTime / tB);
            if (thisSecond > lastSecondTime) {
                StatsPanel.framesPerSec = framesPerSecond;
                framesPerSecond = 0;
                lastSecondTime = thisSecond;
            }

            while ((now - lastUpdateTime) < TIME_BETWEEN_UPDATES) now = System.nanoTime();
        }
    }

    /**
     * Synchronized start of the program; Creates and then starts the programs Thread.
     */
    private synchronized void start() {
        renderer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                simulationLoop();
            }
        }, 0, 1000 / engineSettings.desiredFramesPerSecond);
        StatsPanel.getInstance();
    }

    /**
     * This method actively renders the canvas for the program. It creates and disposes of the Engines graphics object each frame.
     * It is used within the programs main Thread.
     */
    private void render() {
        if ((buff = canvas.getBufferStrategy()) == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        graphics2D = (Graphics2D) buff.getDrawGraphics();

        handleGraphicsSmoothing();
        safetyBooleanChecks();
        draw();
        headsUpNotifications();

        if (graphics2D != null) graphics2D.dispose();
        buff.show();
        toolkit.sync();
    }

    /**
     * This method actively draws to the canvas of the program. It is used within the programs render method.
     */
    private void draw() {
        handleRenders();
    }
}