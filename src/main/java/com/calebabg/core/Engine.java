package com.calebabg.core;

import com.calebabg.gui.ExceptionWindow;
import com.calebabg.gui.QuitWindow;
import com.calebabg.gui.StatsPanel;
import com.calebabg.inputs.*;

import javax.swing.*;
import java.awt.*;

import static com.calebabg.core.EngineVariables.*;

public class Engine {
    public Engine() {
        EngineThemes.setLookAndFeel();
        EngineSettings.loadSettings();

        eFrame.setSize(735, 550);
        eFrame.setLocationRelativeTo(null);
        eFrame.setIconImage(EngineVariables.iconImage);
        eFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        eFrame.addWindowListener(new ExtendedWindowAdapter(e -> BackgroundThread.run(QuitWindow::getInstance)));

        eFrame.setJMenuBar(menuBar);

        eCanvas.addMouseListener(new MouseButtonHandler());
        eCanvas.addMouseMotionListener(new MouseMotionHandler());
        eCanvas.addMouseWheelListener(new MouseWheelHandler());

        KeyboardHandler handler = new KeyboardHandler();
        eCanvas.addKeyListener(handler);
        eFrame.addKeyListener(handler);

        eFrame.add(eCanvas);
        eFrame.setVisible(true);
    }

    public void run() {
        engineSettings.running = true;

        final long tB = 1_000_000_000;
        final int TIME_BETWEEN_UPDATES = (int) (tB / engineSettings.desiredFramesPerSecond);
        final int MAX_UPDATES_BEFORE_RENDER = 1;
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
                update();

                lastUpdateTime += TIME_BETWEEN_UPDATES;
                ++updateCount;
            }

            if (now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                lastUpdateTime = now - TIME_BETWEEN_UPDATES;

            int thisSecond = (int) (lastUpdateTime / tB);
            if (thisSecond > lastSecondTime) {
                StatsPanel.fps = framesPerSecond;
                framesPerSecond = 0;
                lastSecondTime = thisSecond;
            }

            while ((now - lastUpdateTime) < TIME_BETWEEN_UPDATES) now = System.nanoTime();
        }
    }

    private void render() {
        try {
            if ((bufferStrategy = eCanvas.getBufferStrategy()) == null) {
                eCanvas.createBufferStrategy(2);
                return;
            }

            graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();

            EngineMethods.handleRenders();

            if (graphics2D != null) graphics2D.dispose();
            bufferStrategy.show();
            toolkit.sync();
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }

    private void update() {
        if (engineSettings.paused) return;
        try {
            EngineMethods.handleUpdates();
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }
}