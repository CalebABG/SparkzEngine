package com.cabg.gui;

import com.cabg.components.CTextField;
import com.cabg.components.RButton;
import com.cabg.components.RLabel;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;

import static com.cabg.core.EngineVariables.engineSettings;

public class ParticleSizeEditor {
    private static ParticleSizeEditor instance = null;

    private final JFrame frame;
    private final CTextField minTextField, maxTextField;

    public static void getInstance(int type, JFrame parent) {
        if (instance == null) instance = new ParticleSizeEditor(type, parent);
        instance.frame.toFront();
    }

    public static void getInstance(int type) {
        getInstance(type, null);
    }

    private ParticleSizeEditor(int type, JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(setTitle(type));
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(402, 215);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gblPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);

        RLabel minSize = new RLabel("Min Size", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        minSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minSize, minSize.gridBagConstraints);

        minTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        minTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minTextField, minTextField.gridBagConstraints);

        RLabel maxSize = new RLabel("Max Size", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 2);
        maxSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxSize, maxSize.gridBagConstraints);

        maxTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 3, 0, 10, 0, 0);
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxTextField, maxTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 4, 98, 10, 0, 0);
        ok.addActionListener(e -> setSize(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 4, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }

    private static String setTitle(int type) {
        switch (type) {
            case 0: return "Single Click Size";
            case 1: return "Fireworks Size";
            case 2: return "Drag Size";
            default: return null;
        }
    }

    private void setSize(int type) {
        String minText = minTextField.getText();
        String maxText = maxTextField.getText();

        if (InputUtil.canParseFloat(minText) && InputUtil.canParseFloat(maxText)) {
            float minAmount = Float.parseFloat(minText);
            float maxAmount = Float.parseFloat(maxText);

            switch (type) {
                case 0:
                    engineSettings.singleClickSizeMin = minAmount;
                    engineSettings.singleClickSizeMax = maxAmount;
                    break;
                case 1:
                    engineSettings.fireworksSizeMin = minAmount;
                    engineSettings.fireworksSizeMax = maxAmount;
                    break;
                case 2:
                    engineSettings.particleDragSizeMin = minAmount;
                    engineSettings.particleDragSizeMax = maxAmount;
                    break;
            }
        }

        close();
    }
}