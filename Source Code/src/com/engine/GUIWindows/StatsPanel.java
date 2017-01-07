package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Timer;

public class StatsPanel {
    private static StatsPanel statsUI = null;
    private static JFrame frame;
    private static Timer timer;
    public JPanel panel;
    private int h = -6;
    private static CLabel exit, particleAmount, shortCts, dragamount, freemem,
            safetyamount, ptMode, smartPt, connect, atm, ptFriction, screenSize;
    private static DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private Point mpt = new Point();
    public int width = 638, height = 360;

    //public static void main(String[] args) {getInstance();}

    public static StatsPanel getInstance() {if (statsUI == null) {statsUI = new StatsPanel();}frame.toFront(); return statsUI;}

    private StatsPanel() {
        frame = new JFrame("Stats Panel");
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        //frame.setOpacity(0.96f);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

        frame.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {
            mpt.x = e.getX(); mpt.y = e.getY();}});
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {int xDrag = e.getXOnScreen(), yDrag = e.getYOnScreen();
                frame.setLocation((xDrag - mpt.x), (yDrag - mpt.y));}});

        panel = new JPanel();
        panel.setBackground(new Color(20, 23, 25));
        panel.setLayout(null);

        exit = new CLabel(new Rectangle((int) (frame.getWidth() - 40 * 1.5), -12, 60, 35), "-", new
                Font(Font.SERIF, Font.PLAIN, 43), Color.white, new Color(66, 66, 68));
        exit.setToolTipText(H5Wrapper.H(3,"Click to Minimize"));
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {frame.setExtendedState(Frame.ICONIFIED);}
            public void mouseEntered(MouseEvent e) {exit.setBackground(new Color(66, 66, 68).darker().darker());}
            public void mouseExited(MouseEvent e) {exit.setBackground(new Color(66, 66, 68));}
        });

        shortCts = new CLabel(new Rectangle(330-18, 5, 250, 40), "Short-Cuts", new
                Font(Font.SERIF, Font.PLAIN, 35), new Color(98, 138, 137), new Color(29, 32, 34));

        particleAmount = new CLabel(new Rectangle(5, 10+h, 300, 40), "Particles: 0", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        dragamount = new CLabel(new Rectangle(5, 55+h, 300, 40), "Drag Amount: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        safetyamount = new CLabel(new Rectangle(5, 100+h, 300, 40), "Safety Amount:  ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ptMode = new CLabel(new Rectangle(5, 145+h, 300, 40), "Particle Mode: " , new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        smartPt = new CLabel(new Rectangle(5, 190+h, 300, 40), "Thinking Particles: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        connect = new CLabel(new Rectangle(5, 235+h, 300, 40), "Link Mode: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        atm = new CLabel(new Rectangle(5, 280+h, 300, 40), "Mouse Attraction: " , new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ptFriction = new CLabel(new Rectangle(5, 325+h, 300, 40), "Particle Friction: ", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        ////////////////////////////////////////////////////////////////////////////////////////////
        screenSize = new CLabel(new Rectangle(330-18, 325+h, 320, 40), "Window Size: 0 x 0", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel a = new CLabel(new Rectangle(330-18, 56+h, 320, 40), "Press: (On Sparkz Engine)", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel b = new CLabel(new Rectangle(330-18, 100+h, 320, 40), "Q = Engine Instructions", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel c = new CLabel(new Rectangle(330-18, 145+h, 320, 40), "7 = Options Menu", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel e = new CLabel(new Rectangle(330-18, 235+h, 320, 40), "W = Particle Color Editor", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        CLabel d = new CLabel(new Rectangle(330-18, 190+h, 320, 40), "R = Change Background Color", new
                Font(Font.SERIF, Font.PLAIN, 25), Color.white, new Color(20, 23, 25).brighter());

        freemem = new CLabel(new Rectangle(330-18, 280+h, 320, 40), "Free Memory: ", new
                Font(Font.SERIF, Font.PLAIN, 24), Color.white, new Color(20, 23, 25).brighter());

        addComps(frame, panel, particleAmount, exit, dragamount, safetyamount,
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
        timer.scheduleAtFixedRate(new TimerTask() {public void run() {update();}}, 0, 16);
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
            if (freemem != null){freemem.setText("Free Memory: "+decimalFormat.format((Runtime.getRuntime().freeMemory() / (Math.pow(1024, 2)))) + " MB");}
            if(screenSize != null) {screenSize.setText(String.format("Window Size: %d x %d", canvas.getWidth(), canvas.getHeight()));}
        } catch (Exception ex) {EException.append(ex);}
    }

    private static void close(){statsUI = null; stopTimer(); frame.dispose();}
}
