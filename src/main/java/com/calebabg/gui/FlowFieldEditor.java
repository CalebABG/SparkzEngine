package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.calebabg.core.EngineVariables.eFrame;
import static com.calebabg.utilities.InputUtil.floatTextFieldGuardDefault;

public class FlowFieldEditor {
    public static FlowFieldEditor instance = null;

    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 18);

    public static float noiseX = 0.008f, noiseY = 0.004f, noiseZ = 0.002f;
    public static float scaleC1 = 5.0f, scaleC2 = 0.6f;
    public static float startAngle = 0.0f, velocityLimit = 4.3f, velocityMagnitude = 0.2f;

    private final JFrame frame;
    private final JTextField[] fields = new JTextField[8];

    public static void getInstance() {
        if (instance == null) instance = new FlowFieldEditor(eFrame);
        instance.frame.toFront();
    }

    private FlowFieldEditor(JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Flow Field Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(350, 440);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JLabel lblFlowFieldEditor = new JLabel("Flow Field Editor");
        lblFlowFieldEditor.setFont(font);
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
        lblNewLabel.setFont(font);
        GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
        gbcLblNewLabel.fill = GridBagConstraints.BOTH;
        gbcLblNewLabel.insets = new Insets(5, 5, 5, 5);
        gbcLblNewLabel.gridx = 0;
        gbcLblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbcLblNewLabel);

        fields[0] = new JTextField("" + noiseX);
        fields[0].setFont(font);
        GridBagConstraints gbcNoiseXField = new GridBagConstraints();
        gbcNoiseXField.gridwidth = 10;
        gbcNoiseXField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseXField.fill = GridBagConstraints.BOTH;
        gbcNoiseXField.gridx = 1;
        gbcNoiseXField.gridy = 0;
        panel.add(fields[0], gbcNoiseXField);
        fields[0].setColumns(10);

        JLabel lblNoiseYConstant = new JLabel("Noise Y Constant");
        lblNoiseYConstant.setFont(font);
        GridBagConstraints gbcLblNoiseYConstant = new GridBagConstraints();
        gbcLblNoiseYConstant.fill = GridBagConstraints.BOTH;
        gbcLblNoiseYConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblNoiseYConstant.gridx = 0;
        gbcLblNoiseYConstant.gridy = 1;
        panel.add(lblNoiseYConstant, gbcLblNoiseYConstant);

        fields[1] = new JTextField("" + noiseY);
        fields[1].setFont(font);
        fields[1].setColumns(10);
        GridBagConstraints gbcNoiseYField = new GridBagConstraints();
        gbcNoiseYField.gridwidth = 10;
        gbcNoiseYField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseYField.fill = GridBagConstraints.BOTH;
        gbcNoiseYField.gridx = 1;
        gbcNoiseYField.gridy = 1;
        panel.add(fields[1], gbcNoiseYField);

        JLabel lblNoiseZConstant = new JLabel("Noise Z Constant");
        lblNoiseZConstant.setFont(font);
        GridBagConstraints gbcLblNoiseZConstant = new GridBagConstraints();
        gbcLblNoiseZConstant.anchor = GridBagConstraints.WEST;
        gbcLblNoiseZConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblNoiseZConstant.gridx = 0;
        gbcLblNoiseZConstant.gridy = 2;
        panel.add(lblNoiseZConstant, gbcLblNoiseZConstant);

        fields[2] = new JTextField("" + noiseZ);
        fields[2].setFont(font);
        fields[2].setColumns(10);
        GridBagConstraints gbcNoiseZField = new GridBagConstraints();
        gbcNoiseZField.gridwidth = 10;
        gbcNoiseZField.insets = new Insets(0, 0, 5, 0);
        gbcNoiseZField.fill = GridBagConstraints.BOTH;
        gbcNoiseZField.gridx = 1;
        gbcNoiseZField.gridy = 2;
        panel.add(fields[2], gbcNoiseZField);

        JLabel lblScaleConstant = new JLabel("Scale Constant 1");
        lblScaleConstant.setFont(font);
        GridBagConstraints gbcLblScaleConstant = new GridBagConstraints();
        gbcLblScaleConstant.anchor = GridBagConstraints.WEST;
        gbcLblScaleConstant.insets = new Insets(0, 5, 5, 5);
        gbcLblScaleConstant.gridx = 0;
        gbcLblScaleConstant.gridy = 3;
        panel.add(lblScaleConstant, gbcLblScaleConstant);

        fields[3] = new JTextField("" + scaleC1);
        fields[3].setFont(font);
        fields[3].setColumns(10);
        GridBagConstraints gbcScaleConstant1Field = new GridBagConstraints();
        gbcScaleConstant1Field.gridwidth = 10;
        gbcScaleConstant1Field.insets = new Insets(0, 0, 5, 0);
        gbcScaleConstant1Field.fill = GridBagConstraints.BOTH;
        gbcScaleConstant1Field.gridx = 1;
        gbcScaleConstant1Field.gridy = 3;
        panel.add(fields[3], gbcScaleConstant1Field);

        JLabel lblScaleConstant1 = new JLabel("Scale Constant 2");
        lblScaleConstant1.setFont(font);
        GridBagConstraints gbcLblScaleConstant1 = new GridBagConstraints();
        gbcLblScaleConstant1.anchor = GridBagConstraints.WEST;
        gbcLblScaleConstant1.insets = new Insets(0, 5, 5, 5);
        gbcLblScaleConstant1.gridx = 0;
        gbcLblScaleConstant1.gridy = 4;
        panel.add(lblScaleConstant1, gbcLblScaleConstant1);

        fields[4] = new JTextField("" + scaleC2);
        fields[4].setFont(font);
        fields[4].setColumns(10);
        GridBagConstraints gbcScaleConstant2Field = new GridBagConstraints();
        gbcScaleConstant2Field.gridwidth = 10;
        gbcScaleConstant2Field.insets = new Insets(0, 0, 5, 0);
        gbcScaleConstant2Field.fill = GridBagConstraints.BOTH;
        gbcScaleConstant2Field.gridx = 1;
        gbcScaleConstant2Field.gridy = 4;
        panel.add(fields[4], gbcScaleConstant2Field);

        JLabel lblStartAngle = new JLabel("Start Angle");
        lblStartAngle.setFont(font);
        GridBagConstraints gbc_lblStartAngle = new GridBagConstraints();
        gbc_lblStartAngle.anchor = GridBagConstraints.WEST;
        gbc_lblStartAngle.insets = new Insets(0, 5, 5, 5);
        gbc_lblStartAngle.gridx = 0;
        gbc_lblStartAngle.gridy = 5;
        panel.add(lblStartAngle, gbc_lblStartAngle);

        fields[5] = new JTextField("" + startAngle);
        fields[5].setFont(font);
        fields[5].setColumns(10);
        GridBagConstraints gbc_startAngleField = new GridBagConstraints();
        gbc_startAngleField.gridwidth = 10;
        gbc_startAngleField.insets = new Insets(0, 0, 5, 0);
        gbc_startAngleField.fill = GridBagConstraints.BOTH;
        gbc_startAngleField.gridx = 1;
        gbc_startAngleField.gridy = 5;
        panel.add(fields[5], gbc_startAngleField);

        JLabel lblVelocityLimit = new JLabel("Velocity Limit");
        lblVelocityLimit.setFont(font);
        GridBagConstraints gbc_lblVelocityLimit = new GridBagConstraints();
        gbc_lblVelocityLimit.anchor = GridBagConstraints.WEST;
        gbc_lblVelocityLimit.insets = new Insets(0, 5, 5, 5);
        gbc_lblVelocityLimit.gridx = 0;
        gbc_lblVelocityLimit.gridy = 6;
        panel.add(lblVelocityLimit, gbc_lblVelocityLimit);

        fields[6] = new JTextField("" + velocityLimit);
        fields[6].setFont(font);
        fields[6].setColumns(10);
        GridBagConstraints gbc_velocityLimitField = new GridBagConstraints();
        gbc_velocityLimitField.insets = new Insets(0, 0, 5, 0);
        gbc_velocityLimitField.gridwidth = 10;
        gbc_velocityLimitField.fill = GridBagConstraints.BOTH;
        gbc_velocityLimitField.gridx = 1;
        gbc_velocityLimitField.gridy = 6;
        panel.add(fields[6], gbc_velocityLimitField);

        JLabel lblVelocityMagnitude = new JLabel("Velocity Magnitude");
        lblVelocityMagnitude.setFont(font);
        GridBagConstraints gbc_lblVelocityMagnitude = new GridBagConstraints();
        gbc_lblVelocityMagnitude.anchor = GridBagConstraints.WEST;
        gbc_lblVelocityMagnitude.insets = new Insets(0, 5, 5, 5);
        gbc_lblVelocityMagnitude.gridx = 0;
        gbc_lblVelocityMagnitude.gridy = 7;
        panel.add(lblVelocityMagnitude, gbc_lblVelocityMagnitude);

        fields[7] = new JTextField("" + velocityMagnitude);
        fields[7].setFont(font);
        fields[7].setColumns(10);
        GridBagConstraints gbc_velocityMagnitudeField = new GridBagConstraints();
        gbc_velocityMagnitudeField.gridwidth = 10;
        gbc_velocityMagnitudeField.insets = new Insets(0, 0, 5, 0);
        gbc_velocityMagnitudeField.fill = GridBagConstraints.BOTH;
        gbc_velocityMagnitudeField.gridx = 1;
        gbc_velocityMagnitudeField.gridy = 7;
        panel.add(fields[7], gbc_velocityMagnitudeField);

        JButton setFlowBtn = new JButton("Set Flow");
        setFlowBtn.addActionListener(e -> setFlow());
        setFlowBtn.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        GridBagConstraints gbc_setFlowBtn = new GridBagConstraints();
        gbc_setFlowBtn.fill = GridBagConstraints.BOTH;
        gbc_setFlowBtn.gridwidth = 11;
        gbc_setFlowBtn.insets = new Insets(0, 5, 4, 5);
        gbc_setFlowBtn.gridx = 0;
        gbc_setFlowBtn.gridy = 8;
        panel.add(setFlowBtn, gbc_setFlowBtn);

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
        noiseX = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), noiseX, fields[0].getText());
        noiseY = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), noiseY, fields[1].getText());
        noiseZ = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), noiseZ, fields[2].getText());
        scaleC1 = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), scaleC1, fields[3].getText());
        scaleC2 = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), scaleC2, fields[4].getText());
        startAngle = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), startAngle, fields[5].getText());
        velocityLimit = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), velocityLimit, fields[6].getText());
        velocityMagnitude = floatTextFieldGuardDefault(-(Float.MIN_VALUE + 1), velocityMagnitude, fields[7].getText());
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
