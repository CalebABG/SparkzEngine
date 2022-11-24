package com.calebabg.gui;

import com.calebabg.core.EngineMethods;
import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CLabel;
import com.calebabg.utilities.FontUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.enums.EngineMode.PHYSICS;

public class StatsPanel {
    private static StatsPanel instance = null;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");
    public static final Color bgColor = new Color(20, 23, 25).brighter();

    private static JFrame frame;
    private static Timer timer;
    public static int framesPerSecond = 0;
    private static CLabel particleAmount, dragAmount, engineMode, smartPt, connect, atm, ptFriction, fpsLabel, screenSize;

    public static void getInstance() {
        if (instance == null) instance = new StatsPanel();
        frame.toFront();
    }

    private StatsPanel() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Stats Panel");
        frame.setSize(730, 455);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(eFrame);

        JMenuBar menuBar = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(bgColor);
                g2d.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
            }
        };
        menuBar.setBorder(BorderFactory.createLineBorder(bgColor, 2, false));
        frame.setJMenuBar(menuBar);

        JMenu mnStyleOptions = new JMenu("Style Options");
        mnStyleOptions.setForeground(Color.white);
        mnStyleOptions.setFont(FontUtil.PLAIN_17);
        menuBar.add(mnStyleOptions);

        JMenuItem mntmShowHideLeft = new JMenuItem("Show/Hide Right");
        mnStyleOptions.add(mntmShowHideLeft);

        JMenuItem mntmShowHideRight = new JMenuItem("Show/Hide Left");
        mnStyleOptions.add(mntmShowHideRight);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setDividerSize(2);
        splitPane.setResizeWeight(0.5);

        splitPane.setUI(new BasicSplitPaneUI() {
            @Override
            public BasicSplitPaneDivider createDefaultDivider() {
                return new BasicSplitPaneDivider(this) {
                    @Override
                    public void setBorder(Border border) {}

                    @Override
                    public void paint(Graphics g) {
                        super.paint(g);
                        g.setColor(Color.black);
                        g.fillRect(0, 0, getWidth(), getHeight());
                    }
                };
            }
        });
        splitPane.setBorder(null);

        frame.getContentPane().add(splitPane, BorderLayout.CENTER);

        ActionListener actl = e -> {
            int loc = 0;
            JMenuItem source = (JMenuItem) e.getSource();
            if (splitPane.getLeftComponent().isVisible() && splitPane.getRightComponent().isVisible()) {
                splitPane.setDividerSize(0);
                splitPane.getLeftComponent().setVisible(source == mntmShowHideLeft);
                splitPane.getRightComponent().setVisible(source == mntmShowHideRight);
            } else {
                splitPane.getLeftComponent().setVisible(true);
                splitPane.getRightComponent().setVisible(true);
                splitPane.setDividerLocation(loc);
                splitPane.setDividerSize((Integer) UIManager.get("SplitPane.dividerSize"));
            }
        };

        mntmShowHideLeft.addActionListener(actl);
        mntmShowHideRight.addActionListener(actl);

        JScrollPane statsScrollPane = new JScrollPane();
        splitPane.setLeftComponent(statsScrollPane);

        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(bgColor.darker());
        statsScrollPane.setViewportView(statsPanel);
        GridBagLayout gblStatsPanel = new GridBagLayout();
        gblStatsPanel.columnWidths = new int[]{0, 0};
        gblStatsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblStatsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblStatsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        statsPanel.setLayout(gblStatsPanel);

        particleAmount = new CLabel("Molecules: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.insets = new Insets(0, 0, 5, 0);
        gbcLabel.weighty = 1.0;
        gbcLabel.weightx = 1.0;
        gbcLabel.ipady = 10;
        gbcLabel.fill = GridBagConstraints.BOTH;
        gbcLabel.gridx = 0;
        gbcLabel.gridy = 0;
        statsPanel.add(particleAmount, gbcLabel);

        dragAmount = new CLabel("Drag Amount: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel1 = new GridBagConstraints();
        gbcLabel1.insets = new Insets(0, 0, 5, 0);
        gbcLabel1.weighty = 1.0;
        gbcLabel1.weightx = 1.0;
        gbcLabel1.fill = GridBagConstraints.BOTH;
        gbcLabel1.ipady = 10;
        gbcLabel1.gridx = 0;
        gbcLabel1.gridy = 1;
        statsPanel.add(dragAmount, gbcLabel1);

        fpsLabel = new CLabel("Frames Per Second: " + engineSettings.desiredFramesPerSecond, FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel2 = new GridBagConstraints();
        gbcLabel2.insets = new Insets(0, 0, 5, 0);
        gbcLabel2.weighty = 1.0;
        gbcLabel2.weightx = 1.0;
        gbcLabel2.fill = GridBagConstraints.BOTH;
        gbcLabel2.ipady = 10;
        gbcLabel2.gridx = 0;
        gbcLabel2.gridy = 2;
        statsPanel.add(fpsLabel, gbcLabel2);

        engineMode = new CLabel("Engine Mode: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel3 = new GridBagConstraints();
        gbcLabel3.insets = new Insets(0, 0, 5, 0);
        gbcLabel3.weighty = 1.0;
        gbcLabel3.weightx = 1.0;
        gbcLabel3.fill = GridBagConstraints.BOTH;
        gbcLabel3.ipady = 10;
        gbcLabel3.gridx = 0;
        gbcLabel3.gridy = 3;
        statsPanel.add(engineMode, gbcLabel3);

        smartPt = new CLabel("Reactive Colors: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel4 = new GridBagConstraints();
        gbcLabel4.insets = new Insets(0, 0, 5, 0);
        gbcLabel4.weighty = 1.0;
        gbcLabel4.weightx = 1.0;
        gbcLabel4.fill = GridBagConstraints.BOTH;
        gbcLabel4.ipady = 10;
        gbcLabel4.gridx = 0;
        gbcLabel4.gridy = 4;
        statsPanel.add(smartPt, gbcLabel4);

        connect = new CLabel("Link Mode: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel5 = new GridBagConstraints();
        gbcLabel5.insets = new Insets(0, 0, 5, 0);
        gbcLabel5.weighty = 1.0;
        gbcLabel5.weightx = 1.0;
        gbcLabel5.fill = GridBagConstraints.BOTH;
        gbcLabel5.ipady = 10;
        gbcLabel5.gridx = 0;
        gbcLabel5.gridy = 5;
        statsPanel.add(connect, gbcLabel5);

        atm = new CLabel("Mouse Attraction: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel6 = new GridBagConstraints();
        gbcLabel6.insets = new Insets(0, 0, 5, 0);
        gbcLabel6.weighty = 1.0;
        gbcLabel6.weightx = 1.0;
        gbcLabel6.fill = GridBagConstraints.BOTH;
        gbcLabel6.ipady = 10;
        gbcLabel6.gridx = 0;
        gbcLabel6.gridy = 6;
        statsPanel.add(atm, gbcLabel6);

        ptFriction = new CLabel("Particle Friction: ", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel7 = new GridBagConstraints();
        gbcLabel7.fill = GridBagConstraints.BOTH;
        gbcLabel7.weighty = 1.0;
        gbcLabel7.weightx = 1.0;
        gbcLabel7.ipady = 10;
        gbcLabel7.gridx = 0;
        gbcLabel7.gridy = 7;
        statsPanel.add(ptFriction, gbcLabel7);

        JScrollPane instructionsScrollPane = new JScrollPane();
        splitPane.setRightComponent(instructionsScrollPane);

        JPanel instructions_panel = new JPanel();
        instructions_panel.setBackground(bgColor.darker());
        instructionsScrollPane.setViewportView(instructions_panel);
        GridBagLayout gblInstructionsPanel = new GridBagLayout();
        gblInstructionsPanel.columnWidths = new int[]{0, 0};
        gblInstructionsPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblInstructionsPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblInstructionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        instructions_panel.setLayout(gblInstructionsPanel);

        CLabel shortCts = new CLabel("Press On Engine:", FontUtil.PLAIN_26, Color.white, bgColor);
        GridBagConstraints gbcLabel8 = new GridBagConstraints();
        gbcLabel8.weighty = 1.0;
        gbcLabel8.weightx = 1.0;
        gbcLabel8.ipady = 10;
        gbcLabel8.fill = GridBagConstraints.BOTH;
        gbcLabel8.insets = new Insets(0, 0, 5, 0);
        gbcLabel8.gridx = 0;
        gbcLabel8.gridy = 0;
        instructions_panel.add(shortCts, gbcLabel8);

        CLabel a = new CLabel("Esc = Exit Program", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel9 = new GridBagConstraints();
        gbcLabel9.weighty = 1.0;
        gbcLabel9.weightx = 1.0;
        gbcLabel9.ipady = 10;
        gbcLabel9.fill = GridBagConstraints.BOTH;
        gbcLabel9.insets = new Insets(0, 0, 5, 0);
        gbcLabel9.gridx = 0;
        gbcLabel9.gridy = 1;
        instructions_panel.add(a, gbcLabel9);

        CLabel b = new CLabel("Q = Engine Instructions", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel10 = new GridBagConstraints();
        gbcLabel10.weighty = 1.0;
        gbcLabel10.weightx = 1.0;
        gbcLabel10.ipady = 10;
        gbcLabel10.fill = GridBagConstraints.BOTH;
        gbcLabel10.insets = new Insets(0, 0, 5, 0);
        gbcLabel10.gridx = 0;
        gbcLabel10.gridy = 2;
        instructions_panel.add(b, gbcLabel10);

        CLabel c = new CLabel("7 = Options Menu", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbcLabel11 = new GridBagConstraints();
        gbcLabel11.weighty = 1.0;
        gbcLabel11.weightx = 1.0;
        gbcLabel11.ipady = 10;
        gbcLabel11.fill = GridBagConstraints.BOTH;
        gbcLabel11.insets = new Insets(0, 0, 5, 0);
        gbcLabel11.gridx = 0;
        gbcLabel11.gridy = 3;
        instructions_panel.add(c, gbcLabel11);

        CLabel e = new CLabel("W = Reactive Colors Editor", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbc_label_12 = new GridBagConstraints();
        gbc_label_12.weighty = 1.0;
        gbc_label_12.weightx = 1.0;
        gbc_label_12.ipady = 10;
        gbc_label_12.fill = GridBagConstraints.BOTH;
        gbc_label_12.insets = new Insets(0, 0, 5, 0);
        gbc_label_12.gridx = 0;
        gbc_label_12.gridy = 4;
        instructions_panel.add(e, gbc_label_12);

        CLabel d = new CLabel("R = Change Background Color", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbc_label_13 = new GridBagConstraints();
        gbc_label_13.weighty = 1.0;
        gbc_label_13.weightx = 1.0;
        gbc_label_13.ipady = 10;
        gbc_label_13.fill = GridBagConstraints.BOTH;
        gbc_label_13.insets = new Insets(0, 0, 5, 0);
        gbc_label_13.gridx = 0;
        gbc_label_13.gridy = 5;
        instructions_panel.add(d, gbc_label_13);

        screenSize = new CLabel("Canvas Size: 0 x 0", FontUtil.PLAIN_25, Color.white, bgColor);
        GridBagConstraints gbc_label_14 = new GridBagConstraints();
        gbc_label_14.weighty = 1.0;
        gbc_label_14.weightx = 1.0;
        gbc_label_14.ipady = 15;
        gbc_label_14.fill = GridBagConstraints.BOTH;
        gbc_label_14.insets = new Insets(0, 0, 5, 0);
        gbc_label_14.gridx = 0;
        gbc_label_14.gridy = 6;
        instructions_panel.add(screenSize, gbc_label_14);

        startTimer();
        frame.setVisible(true);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    update();
                } catch (Exception ex) {
                    ExceptionWindow.append(ex);
                }
            }
        }, 0, 16); // 16ms = 60 updates per second
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }

    public void update() {
        if (engineSettings.engineMode == PHYSICS) particleAmount.setText("Items: " + decimalFormat.format(Vertices.size()));
        else particleAmount.setText("Molecules: " + decimalFormat.format(EngineMethods.getTotalMoleculesCount()));

        fpsLabel.setText("Frames Per Second: " + framesPerSecond);
        dragAmount.setText("Drag Amount: " + decimalFormat.format(engineSettings.particleDragAmount));
        engineMode.setText("Engine Mode: " + engineSettings.engineMode.name());
        smartPt.setText("Reactive Colors: " + (engineSettings.reactiveColorsEnabled ? "On" : "Off"));
        connect.setText("Link Mode: " + (engineSettings.linkMolecules ? "On" : "Off"));
        atm.setText("Mouse Attraction: " + (engineSettings.particlesGravitateToMouse ? "On" : "Off"));
        ptFriction.setText("Particle Friction: " + (engineSettings.particleFriction ? "On" : "Off"));
        screenSize.setText(String.format("Canvas Size: %d x %d", eCanvas.getWidth(), eCanvas.getHeight()));
    }

    private void close() {
        stopTimer();
        frame.dispose();
        instance = null;
    }
}