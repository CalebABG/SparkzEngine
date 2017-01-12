package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.JComponents.CMenuBar.getMenuBar;
import static com.engine.Utilities.Settings.getOS;

import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Timer;

public class StatsPanel {
    private static StatsPanel statsUI = null;
    private static JFrame frame;
    private static Timer timer;
    public JPanel panel;
    private static CLabel particleAmount, dragamount, freemem,
            safetyamount, ptMode, smartPt, connect, atm, ptFriction, screenSize;
    private static DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private Point mpt = new Point();

    //public static void main(String[] args) {getInstance();}

    public static StatsPanel getInstance() {if (statsUI == null) {statsUI = new StatsPanel();} frame.toFront(); return statsUI;}

    private StatsPanel() {
        frame = new JFrame("Stats Panel");
        if (getOS().equals("mac")) {
            frame.setSize(638, 385);
        } else frame.setSize(638, 425);

        frame.setIconImage(Settings.getIcon());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

        frame.setJMenuBar(getMenuBar());

        frame.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {
            mpt.x = e.getX(); mpt.y = e.getY();}});
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {int xDrag = e.getXOnScreen(), yDrag = e.getYOnScreen();
                frame.setLocation((xDrag - mpt.x), (yDrag - mpt.y));}});

        panel = new JPanel();
        panel.setBackground(new Color(20, 23, 25));
        panel.setLayout(null);

        CLabel shortCts = new CLabel(new Rectangle(330 - 18, 5, 320, 40), "Stats Panel", new
                Font(Font.SERIF, Font.PLAIN, 35), new Color(98, 138, 137), new Color(29, 32, 34));

        int offset = -6;
        particleAmount = new CLabel(new Rectangle(5, 10 + offset, 300, 40), "Particles: 0", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        dragamount = new CLabel(new Rectangle(5, 55 + offset, 300, 40), "Drag Amount: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        safetyamount = new CLabel(new Rectangle(5, 100 + offset, 300, 40), "Safety Amount:  ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ptMode = new CLabel(new Rectangle(5, 145 + offset, 300, 40), "Particle Mode: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        smartPt = new CLabel(new Rectangle(5, 190 + offset, 300, 40), "Thinking Particles: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        connect = new CLabel(new Rectangle(5, 235 + offset, 300, 40), "Link Mode: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        atm = new CLabel(new Rectangle(5, 280 + offset, 300, 40), "Mouse Attraction: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ptFriction = new CLabel(new Rectangle(5, 325 + offset, 300, 40), "Particle Friction: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ////////////////////////////////////////////////////////////////////////////////////////////
        screenSize = new CLabel(new Rectangle(330 - 18, 325 + offset, 320, 40), "Window Size: 0 x 0", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel a = new CLabel(new Rectangle(330 - 18, 56 + offset, 320, 40), "Press: (On Sparkz Engine)", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel b = new CLabel(new Rectangle(330 - 18, 100 + offset, 320, 40), "Q = Engine Instructions", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel c = new CLabel(new Rectangle(330 - 18, 145 + offset, 320, 40), "7 = Options Menu", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel e = new CLabel(new Rectangle(330 - 18, 235 + offset, 320, 40), "W = Particle Color Editor", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel d = new CLabel(new Rectangle(330 - 18, 190 + offset, 320, 40), "R = Change Background Color", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        freemem = new CLabel(new Rectangle(330 - 18, 280 + offset, 320, 40), "JRE Free Memory: ", new
                Font(Font.SERIF, Font.PLAIN, 24), Color.white, new Color(20, 23, 25).brighter());

        addComps(frame, panel, particleAmount, dragamount, safetyamount,
                ptMode, smartPt, connect, atm, ptFriction, screenSize, shortCts,
                a, b, c, d, e, freemem);
        startTimer();
        frame.setVisible(true);
    }

    private void addComps(JFrame root, JPanel panel, JComponent... components) {
        for (JComponent comps : components) {panel.add(comps);} root.add(panel);
    }

    private static void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (freemem != null) {freemem.setText("JRE Free Memory: " + decimalFormat.format((Runtime.getRuntime().freeMemory() / (Math.pow(1024, 2)))) + " MB");}}
        }, 0, 1200);
        timer.scheduleAtFixedRate(new TimerTask() {public void run() {update();}}, 0, timerFPS);
    }

    private static void stopTimer() {timer.cancel(); timer.purge();}

    public static void update() {
        try {if (particleAmount != null) {
                particleAmount.setText("Particles: " + decimalFormat.format(
                        ParticlesArray.size() + GravityPointsArray.size() + FireworksArray.size() +
                                EmitterArray.size() + QEDArray.size() + IonArray.size() +
                                FluxArray.size() + EraserArray.size() + BlackHoleArray.size() +
                                DuplexArray.size() + PortalArray.size()));
            }
            if (dragamount != null) {dragamount.setText("Drag Amount: " + decimalFormat.format(dragAmount));}
            if(safetyamount != null) {safetyamount.setText("Safety Amount: " + decimalFormat.format(safetyAmount));}
            if(ptMode != null) {ptMode.setText(EngineMethods.getModeText());}
            if(smartPt != null) {smartPt.setText(EngineMethods.getThinkingText());}
            if(connect != null) {connect.setText(EngineMethods.getConnectText());}
            if(atm != null) {atm.setText(EngineMethods.getMouseAttraction());}
            if(ptFriction != null) {ptFriction.setText(EngineMethods.getFrictionText());}
            if(screenSize != null) {screenSize.setText(String.format("Window Size: %d x %d", canvas.getWidth(), canvas.getHeight()));}
        } catch (Exception ex) {EException.append(ex);}
    }

    private void close(){statsUI = null; stopTimer(); frame.dispose();}
}
