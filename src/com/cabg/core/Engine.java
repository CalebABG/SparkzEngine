package com.cabg.core;

import com.cabg.gui.ExceptionLogger;
import com.cabg.gui.QuitWindow;
import com.cabg.gui.SplashScreen;
import com.cabg.gui.StatsPanel;
import com.cabg.inputhandlers.*;

import javax.swing.*;
import java.awt.*;

import static com.cabg.core.EngineVariables.*;

public class Engine {
    public static void main(String[] args) {
        new SplashScreen(1500).display();
        Engine e = new Engine();
        StatsPanel.getInstance();
        e.start();
    }

    // Adding slight performance boost + UI properties
    static {
        //Uncomment for slight performance kick: will cause gui tearing
        //System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.transaccel", "true");
        System.setProperty("sun.java2d.ddforcevram", "true");
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.macos.use-file-dialog-packages", "true");
    }

    public Engine() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        eFrame = new JFrame(title);
        eFrame.setIconImage(EngineVariables.iconImage);
        eFrame.setSize(980, 680);
        eFrame.setLocationRelativeTo(null);
        eFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        eFrame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> BackgroundThread.run(QuitWindow::getInstance)));

        // Load Saved Settings
        EngineSettings.loadSettings();

        // Setup Menu-bar
        eFrame.setJMenuBar(menuBar);

        // Mouse Click Listener
        canvas.addMouseListener(new MouseButtonHandler());

        // Mouse Move and Drag Listener
        canvas.addMouseMotionListener(new MouseMotionHandler());

        // Mouse Wheel Listener
        canvas.addMouseWheelListener(new MouseWheelHandler());

        // Keyboard Listener
        KeyboardHandler handler = new KeyboardHandler();
        canvas.addKeyListener(handler);
        eFrame.addKeyListener(handler);

        // Canvas
        eFrame.add(canvas);

        // Display Sparkz Engine
        eFrame.setVisible(true);
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
            ++framesPerSecond;

            render();

            while (((now - lastUpdateTime) > TIME_BETWEEN_UPDATES) && (updateCount < MAX_UPDATES_BEFORE_RENDER)) {
                if (!engineSettings.paused) update();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) lastUpdateTime = now - TIME_BETWEEN_UPDATES;

            int thisSecond = (int) (lastUpdateTime / tB);
            if (thisSecond > lastSecondTime) {
                StatsPanel.fps = framesPerSecond;
                framesPerSecond = 0;
                lastSecondTime = thisSecond;
            }

            while ((now - lastUpdateTime) < TIME_BETWEEN_UPDATES) now = System.nanoTime();
        }
    }

    private void start() {
        simulationLoop();
    }

    private void render() {
        if ((buff = canvas.getBufferStrategy()) == null) {
            canvas.createBufferStrategy(2);
            return;
        }

        graphics2D = (Graphics2D) buff.getDrawGraphics();

        try { EngineMethods.handleRenders(); }
        catch (Exception e){ ExceptionLogger.append(e); }

        if (graphics2D != null) graphics2D.dispose();
        buff.show();
        toolkit.sync();
    }

    private void update() {
        try { EngineMethods.handleUpdates(); }
        catch (Exception e){ ExceptionLogger.append(e); }
    }
}