package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.utilities.FontUtil;
import com.calebabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class FlowFieldEditor {
    private static FlowFieldEditor instance = null;

    public static float noiseX = 0.008f, noiseY = 0.004f, noiseZ = 0.002f;
    public static float scaleC1 = 5.0f, scaleC2 = 0.6f;
    public static float startAngle = 0.0f, velocityLimit = 4.3f, velocityMagnitude = 0.2f;

    private final JFrame frame;
    private final JTextField[] fields = new JTextField[8];

    public static void getInstance() {
        if (instance == null) instance = new FlowFieldEditor(EngineVariables.eFrame);
        instance.frame.toFront();
    }

    private FlowFieldEditor(JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Flow Field Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(350, 440);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel lblFlowFieldEditor = new JLabel("Flow Field Editor");
        lblFlowFieldEditor.setFont(FontUtil.PLAIN_18);
        lblFlowFieldEditor.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setColumnHeaderView(lblFlowFieldEditor);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{147, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gblPanel.rowHeights = new int[]{37, 37, 37, 37, 37, 37, 36, 36, 49, 0};
        gblPanel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);

        JLabel lblNewLabel = new JLabel("Noise X Constant");
        lblNewLabel.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
        gbcLblNewLabel.fill = GridBagConstraints.BOTH;
        gbcLblNewLabel.insets = new Insets(5, 5, 5, 5);
        gbcLblNewLabel.gridx = 0;
        gbcLblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbcLblNewLabel);

        fields[0] = new JTextField("" + noiseX);
        fields[0].setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcNoiseXField = new GridBagConstraints();
        gbcNoiseXField.gridwidth = 10;
        gbcNoiseXField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseXField.fill = GridBagConstraints.BOTH;
        gbcNoiseXField.gridx = 1;
        gbcNoiseXField.gridy = 0;
        panel.add(fields[0], gbcNoiseXField);
        fields[0].setColumns(10);

        JLabel lblNoiseYConstant = new JLabel("Noise Y Constant");
        lblNoiseYConstant.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblNoiseYConstant = new GridBagConstraints();
        gbcLblNoiseYConstant.fill = GridBagConstraints.BOTH;
        gbcLblNoiseYConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblNoiseYConstant.gridx = 0;
        gbcLblNoiseYConstant.gridy = 1;
        panel.add(lblNoiseYConstant, gbcLblNoiseYConstant);

        fields[1] = new JTextField("" + noiseY);
        fields[1].setFont(FontUtil.PLAIN_18);
        fields[1].setColumns(10);
        GridBagConstraints gbcNoiseYField = new GridBagConstraints();
        gbcNoiseYField.gridwidth = 10;
        gbcNoiseYField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseYField.fill = GridBagConstraints.BOTH;
        gbcNoiseYField.gridx = 1;
        gbcNoiseYField.gridy = 1;
        panel.add(fields[1], gbcNoiseYField);

        JLabel lblNoiseZConstant = new JLabel("Noise Z Constant");
        lblNoiseZConstant.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblNoiseZConstant = new GridBagConstraints();
        gbcLblNoiseZConstant.anchor = GridBagConstraints.WEST;
        gbcLblNoiseZConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblNoiseZConstant.gridx = 0;
        gbcLblNoiseZConstant.gridy = 2;
        panel.add(lblNoiseZConstant, gbcLblNoiseZConstant);

        fields[2] = new JTextField("" + noiseZ);
        fields[2].setFont(FontUtil.PLAIN_18);
        fields[2].setColumns(10);
        GridBagConstraints gbcNoiseZField = new GridBagConstraints();
        gbcNoiseZField.gridwidth = 10;
        gbcNoiseZField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseZField.fill = GridBagConstraints.BOTH;
        gbcNoiseZField.gridx = 1;
        gbcNoiseZField.gridy = 2;
        panel.add(fields[2], gbcNoiseZField);

        JLabel lblScaleConstant = new JLabel("Scale Constant 1");
        lblScaleConstant.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblScaleConstant = new GridBagConstraints();
        gbcLblScaleConstant.anchor = GridBagConstraints.WEST;
        gbcLblScaleConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblScaleConstant.gridx = 0;
        gbcLblScaleConstant.gridy = 3;
        panel.add(lblScaleConstant, gbcLblScaleConstant);

        fields[3] = new JTextField("" + scaleC1);
        fields[3].setFont(FontUtil.PLAIN_18);
        fields[3].setColumns(10);
        GridBagConstraints gbcScaleConstant1Field = new GridBagConstraints();
        gbcScaleConstant1Field.gridwidth = 10;
        gbcScaleConstant1Field.insets = new Insets(0, 0, 5, 0);
        gbcScaleConstant1Field.fill = GridBagConstraints.BOTH;
        gbcScaleConstant1Field.gridx = 1;
        gbcScaleConstant1Field.gridy = 3;
        panel.add(fields[3], gbcScaleConstant1Field);

        JLabel lblScaleConstant1 = new JLabel("Scale Constant 2");
        lblScaleConstant1.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblScaleConstant1 = new GridBagConstraints();
        gbcLblScaleConstant1.anchor = GridBagConstraints.WEST;
        gbcLblScaleConstant1.insets = new Insets(0, 5, 5, 5);
        gbcLblScaleConstant1.gridx = 0;
        gbcLblScaleConstant1.gridy = 4;
        panel.add(lblScaleConstant1, gbcLblScaleConstant1);

        fields[4] = new JTextField("" + scaleC2);
        fields[4].setFont(FontUtil.PLAIN_18);
        fields[4].setColumns(10);
        GridBagConstraints gbcScaleConstant2Field = new GridBagConstraints();
        gbcScaleConstant2Field.gridwidth = 10;
        gbcScaleConstant2Field.insets = new Insets(0, 0, 5, 0);
        gbcScaleConstant2Field.fill = GridBagConstraints.BOTH;
        gbcScaleConstant2Field.gridx = 1;
        gbcScaleConstant2Field.gridy = 4;
        panel.add(fields[4], gbcScaleConstant2Field);

        JLabel lblStartAngle = new JLabel("Start Angle");
        lblStartAngle.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblStartAngle = new GridBagConstraints();
        gbcLblStartAngle.anchor = GridBagConstraints.WEST;
        gbcLblStartAngle.insets = new Insets(0, 5, 5, 5);
        gbcLblStartAngle.gridx = 0;
        gbcLblStartAngle.gridy = 5;
        panel.add(lblStartAngle, gbcLblStartAngle);

        fields[5] = new JTextField("" + startAngle);
        fields[5].setFont(FontUtil.PLAIN_18);
        fields[5].setColumns(10);
        GridBagConstraints gbcStartAngleField = new GridBagConstraints();
        gbcStartAngleField.gridwidth = 10;
        gbcStartAngleField.insets = new Insets(0, 0, 5, 0);
        gbcStartAngleField.fill = GridBagConstraints.BOTH;
        gbcStartAngleField.gridx = 1;
        gbcStartAngleField.gridy = 5;
        panel.add(fields[5], gbcStartAngleField);

        JLabel lblVelocityLimit = new JLabel("Velocity Limit");
        lblVelocityLimit.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblVelocityLimit = new GridBagConstraints();
        gbcLblVelocityLimit.anchor = GridBagConstraints.WEST;
        gbcLblVelocityLimit.insets = new Insets(0, 5, 5, 5);
        gbcLblVelocityLimit.gridx = 0;
        gbcLblVelocityLimit.gridy = 6;
        panel.add(lblVelocityLimit, gbcLblVelocityLimit);

        fields[6] = new JTextField("" + velocityLimit);
        fields[6].setFont(FontUtil.PLAIN_18);
        fields[6].setColumns(10);
        GridBagConstraints gbcVelocityLimitField = new GridBagConstraints();
        gbcVelocityLimitField.insets = new Insets(0, 0, 5, 0);
        gbcVelocityLimitField.gridwidth = 10;
        gbcVelocityLimitField.fill = GridBagConstraints.BOTH;
        gbcVelocityLimitField.gridx = 1;
        gbcVelocityLimitField.gridy = 6;
        panel.add(fields[6], gbcVelocityLimitField);

        JLabel lblVelocityMagnitude = new JLabel("Velocity Magnitude");
        lblVelocityMagnitude.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcLblVelocityMagnitude = new GridBagConstraints();
        gbcLblVelocityMagnitude.anchor = GridBagConstraints.WEST;
        gbcLblVelocityMagnitude.insets = new Insets(0, 5, 5, 5);
        gbcLblVelocityMagnitude.gridx = 0;
        gbcLblVelocityMagnitude.gridy = 7;
        panel.add(lblVelocityMagnitude, gbcLblVelocityMagnitude);

        fields[7] = new JTextField("" + velocityMagnitude);
        fields[7].setFont(FontUtil.PLAIN_18);
        fields[7].setColumns(10);
        GridBagConstraints gbcVelocityMagnitudeField = new GridBagConstraints();
        gbcVelocityMagnitudeField.gridwidth = 10;
        gbcVelocityMagnitudeField.insets = new Insets(0, 0, 5, 0);
        gbcVelocityMagnitudeField.fill = GridBagConstraints.BOTH;
        gbcVelocityMagnitudeField.gridx = 1;
        gbcVelocityMagnitudeField.gridy = 7;
        panel.add(fields[7], gbcVelocityMagnitudeField);

        JButton setFlowBtn = new JButton("Set Flow");
        setFlowBtn.addActionListener(e -> setFlow());
        setFlowBtn.setFont(FontUtil.PLAIN_18);
        GridBagConstraints gbcSetFlowBtn = new GridBagConstraints();
        gbcSetFlowBtn.fill = GridBagConstraints.BOTH;
        gbcSetFlowBtn.gridwidth = 11;
        gbcSetFlowBtn.insets = new Insets(0, 5, 4, 5);
        gbcSetFlowBtn.gridx = 0;
        gbcSetFlowBtn.gridy = 8;
        panel.add(setFlowBtn, gbcSetFlowBtn);

        for (JTextField f : fields) {
            f.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    try {
                        setFlow();
                    } catch (Exception g) {
                        ExceptionWindow.append(g);
                    }
            }));
        }

        frame.setVisible(true);
    }

    private void setFlow() {
        noiseX = floatGuardDefault(noiseX, fields[0].getText());
        noiseY = floatGuardDefault(noiseY, fields[1].getText());
        noiseZ = floatGuardDefault(noiseZ, fields[2].getText());
        scaleC1 = floatGuardDefault(scaleC1, fields[3].getText());
        scaleC2 = floatGuardDefault(scaleC2, fields[4].getText());
        startAngle = floatGuardDefault(startAngle, fields[5].getText());
        velocityLimit = floatGuardDefault(velocityLimit, fields[6].getText());
        velocityMagnitude = floatGuardDefault(velocityMagnitude, fields[7].getText());
    }

    public static float floatGuardDefault(float defaultVal, String input) {
        if (input == null || input.isEmpty()) {
            return defaultVal;
        } else {
            if (InputUtil.canParseFloat(input)) {
                return Float.parseFloat(input);
            } else {
                return defaultVal;
            }
        }
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
