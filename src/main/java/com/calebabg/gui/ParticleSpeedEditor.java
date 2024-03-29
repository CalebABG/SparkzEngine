package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CTextField;
import com.calebabg.jcomponents.RButton;
import com.calebabg.jcomponents.RLabel;
import com.calebabg.utilities.FontUtil;
import com.calebabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;

import static com.calebabg.core.EngineVariables.engineSettings;

public class ParticleSpeedEditor {
    private static ParticleSpeedEditor instance = null;

    public final JFrame frame;
    public final CTextField speedTextField;

    public static void getInstance(int type, JFrame parent) {
        if (instance == null) instance = new ParticleSpeedEditor(type, parent);
        instance.frame.toFront();
    }

    public static void getInstance(int type) {
        getInstance(type, null);
    }

    private ParticleSpeedEditor(int type, JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(setTitle(type));
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(402, 145);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0, 0};
        gblPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);

        RLabel speedLabel = new RLabel("Speed", FontUtil.BOLD_17, new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedLabel, speedLabel.gridBagConstraints);

        speedTextField = new CTextField("", FontUtil.PLAIN_17, 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedTextField, speedTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", FontUtil.BOLD_14, 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 2, 98, 10, .9f, .9f);
        ok.addActionListener(e -> setSpeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", FontUtil.BOLD_14, 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 2, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);

        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        switch (type) {
            case 0: return "Single Click Speed";
            case 1: return "Fireworks Speed";
            case 2: return "Drag Size Speed";
            default: return null;
        }
    }

    private void setSpeed(int type) {
        String text = speedTextField.getText();

        if (InputUtil.canParseFloat(text)) {
            float amount = Float.parseFloat(text);

            switch (type) {
                case 0: engineSettings.singleClickSpeed = amount; break;
                case 1: engineSettings.fireworksSpeed = amount; break;
                case 2: engineSettings.particleDragSpeed = amount; break;
            }
        }

        close();
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
