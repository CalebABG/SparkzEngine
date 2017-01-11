package com.engine.Main;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EngineMethods.*;
import com.engine.EngineHelpers.EngineSplash;
import com.engine.GUIWindows.StatsPanel;
import com.engine.JComponents.CMenuBar;
import com.engine.GUIWindows.QuitWindow;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Project started on: 11/22/2015 :D -- REQUIRES Java version 1.8 (lambda functions used) or higher to edit
// TODO: 3/11/2016 Create Thinking Color Time-Machine
// TODO: 5/23/16 Reminder: Additions to Settings File - total lines in file cannot be an odd number

public class Engine implements Runnable {
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
        Settings.loadSettings();
        CMenuBar.setUpMenuBar(EFrame);
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
        EFrame.setVisible(true);
    }

    /**
     * Engines Thread.run() method. Used as the main 'game-loop' to control the updates and renders of the program.
     * Tries to constrain the fps to around 60(capped).
     */
    public void run() {
        long lastTime = System.nanoTime(), lastTimer = System.currentTimeMillis();
        double nsPerTick = 1.0E9D / FPS, deltaTime = 0.0D;
        int frames = 0, ticks = 0;

        while(running) {
            long now = System.nanoTime();
            deltaTime += (double)(now - lastTime) / nsPerTick;
            lastTime = now;

            boolean shouldRender;
            for(shouldRender = false; deltaTime >= 1.0D; shouldRender = true) {
                ++ticks;
                if (!isPaused) update();
                --deltaTime;
            }

            if(shouldRender) {++frames; render();}

            if(System.currentTimeMillis() - lastTimer >= 1000L) {
                lastTimer += 1000L; tps = ticks; fps = frames; frames = 0; ticks = 0;
            }
        }
    }

    /**
     * Synchronized start of the program; Creates and then starts the programs Thread.
     */
    private synchronized void start() {running = true; thread = new Thread(this); thread.start(); StatsPanel.getInstance();}

    /**
     * Synchronized stop of the program; Stops the Engines Thread, set's the disposes of the programs window, and then shuts down the program.
     */
    public static synchronized void stop(){running = false; EFrame.setVisible(false); try{thread.join();} catch(Exception e){EException.append(e);}}

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
        if(buff == null){canvas.createBufferStrategy(3); return;}
        graphics2D = (Graphics2D) buff.getDrawGraphics();
        handleGraphicsSmoothing();
        try {safetyBooleanChecks(); draw();} catch (Exception e) {EException.append(e);}
        if (graphics2D != null) {graphics2D.dispose();}
        buff.show(); toolkit.sync();
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

    public static void main(String[] args) {
        new EngineSplash(2720).display(); SwingUtilities.invokeLater(() -> new Engine().start());
    }
}