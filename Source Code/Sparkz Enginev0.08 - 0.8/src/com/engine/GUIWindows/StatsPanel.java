package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EConstants.canvas;
import static com.engine.Utilities.Settings.getOS;

public class StatsPanel {
    private static StatsPanel statsUI = null;
    private static JFrame frame;
    private static java.util.Timer timer;
    public JPanel panel;
    public Color bgColor = new Color(20, 23, 25).brighter();
    public Font font = new Font(Font.SERIF, Font.PLAIN, 25);
    private static CLabel particleAmount, dragamount, freemem, safetyamount, ptMode, smartPt, connect, atm, ptFriction, screenSize;
    private static DecimalFormat decimalFormat = new DecimalFormat("#,###");

    //public static void main(String[] args) {getInstance();}

    public static StatsPanel getInstance() {if (statsUI == null) {statsUI = new StatsPanel();} frame.toFront(); return statsUI;}

    private StatsPanel() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Stats Panel");
        if (getOS().equals("mac")) {frame.setSize(638, 385);} else frame.setSize(727, 445);

        frame.setIconImage(Settings.getIcon());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnStyleOptions = new JMenu("Style Options");
        mnStyleOptions.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        menuBar.add(mnStyleOptions);

        JMenuItem mntmShowhideLeft = new JMenuItem("Show/Hide Left");
        mnStyleOptions.add(mntmShowhideLeft);

        JMenuItem mntmShowhideRight = new JMenuItem("Show/Hide Right");
        mnStyleOptions.add(mntmShowhideRight);

        JSplitPane split_pane = new JSplitPane();
        split_pane.setContinuousLayout(true);
        split_pane.setBackground(Color.GRAY);
        split_pane.setDividerSize(2);
        split_pane.setResizeWeight(0.5);
        frame.getContentPane().add(split_pane, BorderLayout.CENTER);

        ActionListener actl = new ActionListener() {
            private int loc = 0;
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                if(split_pane.getLeftComponent().isVisible() && split_pane.getRightComponent().isVisible()){
                    loc = split_pane.getDividerLocation();
                    split_pane.setDividerSize(0);
                    split_pane.getLeftComponent().setVisible(source == mntmShowhideLeft);
                    split_pane.getRightComponent().setVisible(source == mntmShowhideRight);
                }
                else{
                    split_pane.getLeftComponent().setVisible(true);
                    split_pane.getRightComponent().setVisible(true);
                    split_pane.setDividerLocation(loc);
                    split_pane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
                }
            }
        };

        mntmShowhideLeft.addActionListener(actl);
        mntmShowhideRight.addActionListener(actl);

        JScrollPane stats_scrollpane = new JScrollPane();
        split_pane.setLeftComponent(stats_scrollpane);

        ///////////

        JPanel stats_panel = new JPanel();
        stats_panel.setBackground(bgColor.darker());
        stats_scrollpane.setViewportView(stats_panel);
        GridBagLayout gbl_stats_panel = new GridBagLayout();
        gbl_stats_panel.columnWidths = new int[]{0, 0};
        gbl_stats_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_stats_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_stats_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        stats_panel.setLayout(gbl_stats_panel);

        particleAmount = new CLabel("Particles: 0", font, Color.white, bgColor);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 0);
        gbc_label.weighty = 1.0;
        gbc_label.weightx = 1.0;
        gbc_label.ipady = 10;
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.gridx = 0;
        gbc_label.gridy = 0;
        stats_panel.add(particleAmount, gbc_label);

        dragamount = new CLabel("Drag Amount: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.insets = new Insets(0, 0, 5, 0);
        gbc_label_1.weighty = 1.0;
        gbc_label_1.weightx = 1.0;
        gbc_label_1.fill = GridBagConstraints.BOTH;
        gbc_label_1.ipady = 10;
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        stats_panel.add(dragamount, gbc_label_1);

        safetyamount = new CLabel("Safety Amount: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.insets = new Insets(0, 0, 5, 0);
        gbc_label_2.weighty = 1.0;
        gbc_label_2.weightx = 1.0;
        gbc_label_2.fill = GridBagConstraints.BOTH;
        gbc_label_2.ipady = 10;
        gbc_label_2.gridx = 0;
        gbc_label_2.gridy = 2;
        stats_panel.add(safetyamount, gbc_label_2);

        ptMode = new CLabel("Particle Mode: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.insets = new Insets(0, 0, 5, 0);
        gbc_label_3.weighty = 1.0;
        gbc_label_3.weightx = 1.0;
        gbc_label_3.fill = GridBagConstraints.BOTH;
        gbc_label_3.ipady = 10;
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 3;
        stats_panel.add(ptMode, gbc_label_3);

        smartPt = new CLabel("Thinking Particles: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.insets = new Insets(0, 0, 5, 0);
        gbc_label_4.weighty = 1.0;
        gbc_label_4.weightx = 1.0;
        gbc_label_4.fill = GridBagConstraints.BOTH;
        gbc_label_4.ipady = 10;
        gbc_label_4.gridx = 0;
        gbc_label_4.gridy = 4;
        stats_panel.add(smartPt, gbc_label_4);

        connect = new CLabel("Link Mode: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 5, 0);
        gbc_label_5.weighty = 1.0;
        gbc_label_5.weightx = 1.0;
        gbc_label_5.fill = GridBagConstraints.BOTH;
        gbc_label_5.ipady = 10;
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 5;
        stats_panel.add(connect, gbc_label_5);

        atm = new CLabel("Mouse Attraction: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_6 = new GridBagConstraints();
        gbc_label_6.insets = new Insets(0, 0, 5, 0);
        gbc_label_6.weighty = 1.0;
        gbc_label_6.weightx = 1.0;
        gbc_label_6.fill = GridBagConstraints.BOTH;
        gbc_label_6.ipady = 10;
        gbc_label_6.gridx = 0;
        gbc_label_6.gridy = 6;
        stats_panel.add(atm, gbc_label_6);

        ptFriction = new CLabel("Particle Friction: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_7 = new GridBagConstraints();
        gbc_label_7.fill = GridBagConstraints.BOTH;
        gbc_label_7.weighty = 1.0;
        gbc_label_7.weightx = 1.0;
        gbc_label_7.ipady = 10;
        gbc_label_7.gridx = 0;
        gbc_label_7.gridy = 7;
        stats_panel.add(ptFriction, gbc_label_7);

        JScrollPane instructions_scrollpane = new JScrollPane();
        split_pane.setRightComponent(instructions_scrollpane);

        JPanel instructions_panel = new JPanel();
        instructions_panel.setBackground(bgColor.darker());
        instructions_scrollpane.setViewportView(instructions_panel);
        GridBagLayout gbl_instructions_panel = new GridBagLayout();
        gbl_instructions_panel.columnWidths = new int[]{0, 0};
        gbl_instructions_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_instructions_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_instructions_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        instructions_panel.setLayout(gbl_instructions_panel);
        
        CLabel shortCts = new CLabel("Stats Panel", new Font(Font.SERIF, Font.PLAIN, 26), new Color(98, 138, 137), new Color(29, 32, 34));
        GridBagConstraints gbc_label_8 = new GridBagConstraints();
        gbc_label_8.weighty = 1.0;
        gbc_label_8.weightx = 1.0;
        gbc_label_8.ipady = 10;
        gbc_label_8.fill = GridBagConstraints.BOTH;
        gbc_label_8.insets = new Insets(0, 0, 5, 0);
        gbc_label_8.gridx = 0;
        gbc_label_8.gridy = 0;
        instructions_panel.add(shortCts, gbc_label_8);

        CLabel a = new CLabel("Press: (On Sparkz Engine)", font, Color.white, bgColor);
        GridBagConstraints gbc_label_9 = new GridBagConstraints();
        gbc_label_9.weighty = 1.0;
        gbc_label_9.weightx = 1.0;
        gbc_label_9.ipady = 10;
        gbc_label_9.fill = GridBagConstraints.BOTH;
        gbc_label_9.insets = new Insets(0, 0, 5, 0);
        gbc_label_9.gridx = 0;
        gbc_label_9.gridy = 1;
        instructions_panel.add(a, gbc_label_9);

        CLabel b = new CLabel("Q = Engine Instructions", font, Color.white, bgColor);
        GridBagConstraints gbc_label_10 = new GridBagConstraints();
        gbc_label_10.weighty = 1.0;
        gbc_label_10.weightx = 1.0;
        gbc_label_10.ipady = 10;
        gbc_label_10.fill = GridBagConstraints.BOTH;
        gbc_label_10.insets = new Insets(0, 0, 5, 0);
        gbc_label_10.gridx = 0;
        gbc_label_10.gridy = 2;
        instructions_panel.add(b, gbc_label_10);

        CLabel c = new CLabel("7 = Options Menu", font, Color.white, bgColor);
        GridBagConstraints gbc_label_11 = new GridBagConstraints();
        gbc_label_11.weighty = 1.0;
        gbc_label_11.weightx = 1.0;
        gbc_label_11.ipady = 10;
        gbc_label_11.fill = GridBagConstraints.BOTH;
        gbc_label_11.insets = new Insets(0, 0, 5, 0);
        gbc_label_11.gridx = 0;
        gbc_label_11.gridy = 3;
        instructions_panel.add(c, gbc_label_11);

        CLabel e = new CLabel("W = Particle Color Editor", font, Color.white, bgColor);
        GridBagConstraints gbc_label_12 = new GridBagConstraints();
        gbc_label_12.weighty = 1.0;
        gbc_label_12.weightx = 1.0;
        gbc_label_12.ipady = 10;
        gbc_label_12.fill = GridBagConstraints.BOTH;
        gbc_label_12.insets = new Insets(0, 0, 5, 0);
        gbc_label_12.gridx = 0;
        gbc_label_12.gridy = 4;
        instructions_panel.add(e, gbc_label_12);

        CLabel d = new CLabel("R = Change Background Color", font, Color.white, bgColor);
        GridBagConstraints gbc_label_13 = new GridBagConstraints();
        gbc_label_13.weighty = 1.0;
        gbc_label_13.weightx = 1.0;
        gbc_label_13.ipady = 10;
        gbc_label_13.fill = GridBagConstraints.BOTH;
        gbc_label_13.insets = new Insets(0, 0, 5, 0);
        gbc_label_13.gridx = 0;
        gbc_label_13.gridy = 5;
        instructions_panel.add(d, gbc_label_13);

        screenSize = new CLabel("Window Size: 0 x 0", font, Color.white, bgColor);
        GridBagConstraints gbc_label_14 = new GridBagConstraints();
        gbc_label_14.weighty = 1.0;
        gbc_label_14.weightx = 1.0;
        gbc_label_14.ipady = 10;
        gbc_label_14.fill = GridBagConstraints.BOTH;
        gbc_label_14.insets = new Insets(0, 0, 5, 0);
        gbc_label_14.gridx = 0;
        gbc_label_14.gridy = 6;
        instructions_panel.add(screenSize, gbc_label_14);

        freemem = new CLabel("JRE Free Memory: ", font, Color.white, bgColor);
        GridBagConstraints gbc_label_15 = new GridBagConstraints();
        gbc_label_15.weighty = 1.0;
        gbc_label_15.weightx = 1.0;
        gbc_label_15.ipady = 10;
        gbc_label_15.fill = GridBagConstraints.BOTH;
        gbc_label_15.gridx = 0;
        gbc_label_15.gridy = 7;
        instructions_panel.add(freemem, gbc_label_15);

        startTimer();
        frame.setVisible(true);
    }

    private void addComps(JFrame root, JPanel panel, JComponent... components) {
        for (JComponent comps : components) {panel.add(comps);} root.add(panel, BorderLayout.CENTER);
    }

    private static void startTimer() {
        timer = new java.util.Timer();
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

    private void close() {
        statsUI = null;
        stopTimer();
        frame.dispose();
    }
}
