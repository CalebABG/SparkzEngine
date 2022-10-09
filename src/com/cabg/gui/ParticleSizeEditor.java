package com.cabg.gui;

import com.cabg.components.CTextField;
import com.cabg.components.RButton;
import com.cabg.components.RLabel;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.utilities.Settings;

import javax.swing.*;
import java.awt.*;

import static com.cabg.core.EngineVariables.engineSettings;
import static com.cabg.utilities.InputGuard.stringNotNullOrEmpty;

public class ParticleSizeEditor {
    private static ParticleSizeEditor particleSizeEditor = null;
    public static JFrame frame;
    public static CTextField minTextField, maxTextField;

    public static void getInstance(int type) {
        if (particleSizeEditor == null) particleSizeEditor = new ParticleSizeEditor(type);
        frame.toFront();
    }

    private ParticleSizeEditor(int type) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        frame = new JFrame(setTitle(type));
        frame.setIconImage(Settings.iconImage);
        frame.setSize(402, 215);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(OptionsMenu.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        RLabel minSize = new RLabel("Min Size(Integer or Float)", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        minSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minSize, minSize.gridBagConstraints);

        minTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        minTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minTextField, minTextField.gridBagConstraints);

        RLabel maxSize = new RLabel("Max Size(Integer or Float)", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 2);
        maxSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxSize, maxSize.gridBagConstraints);

        maxTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 3, 0, 10, 0, 0);
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxTextField, maxTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 4, 98, 10, 0, 0);
        ok.addActionListener(e -> handleSeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 4, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        if (type == 0) return "Single Click Size Seed";
        else if (type == 1) return "Fireworks Size Seed";
        else if (type == 2) return "Drag Size Seed";
        return null;
    }

    private void handleSeed(int type) {
        try {
            switch (type) {
                case 0:
                    engineSettings.singleClickSizeMin = stringNotNullOrEmpty(minTextField.getText())
                            ? Float.parseFloat(minTextField.getText())
                            : engineSettings.singleClickSizeMin;

                    engineSettings.singleClickSizeMax = stringNotNullOrEmpty(maxTextField.getText())
                            ? Float.parseFloat(maxTextField.getText())
                            : engineSettings.singleClickSizeMax;
                    break;
                case 1:
                    engineSettings.fireworksSizeMin = stringNotNullOrEmpty(minTextField.getText())
                            ? Float.parseFloat(minTextField.getText())
                            : engineSettings.fireworksSizeMin;

                    engineSettings.fireworksSizeMax = stringNotNullOrEmpty(maxTextField.getText())
                            ? Float.parseFloat(maxTextField.getText())
                            : engineSettings.fireworksSizeMax;
                    break;
                case 2:
                    engineSettings.particleDragSizeMin = stringNotNullOrEmpty(minTextField.getText())
                            ? Float.parseFloat(minTextField.getText())
                            : engineSettings.particleDragSizeMin;

                    engineSettings.particleDragSizeMax = stringNotNullOrEmpty(maxTextField.getText())
                            ? Float.parseFloat(maxTextField.getText())
                            : engineSettings.particleDragSizeMax;
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.append(e);
        } finally {
            close();
        }
    }

    private void close() {
        frame.dispose();
        particleSizeEditor = null;
    }
}