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

public class ParticleSpeedEditor {
    private static ParticleSpeedEditor particleSpeedEditor = null;
    public static JFrame frame;
    public static CTextField speedTextField;

    public static void getInstance(int type) {
        if (particleSpeedEditor == null) particleSpeedEditor = new ParticleSpeedEditor(type);
        frame.toFront();
    }

    private ParticleSpeedEditor(int type) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        frame = new JFrame(setTitle(type));
        frame.setIconImage(Settings.iconImage);
        frame.setSize(402, 145);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(OptionsMenu.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        RLabel speedLabel = new RLabel("Speed", new Font(Font.SERIF, Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedLabel, speedLabel.gridBagConstraints);

        speedTextField = new CTextField("", new Font(Font.SERIF, Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedTextField, speedTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 2, 98, 10, .9f, .9f);
        ok.addActionListener(e -> handleSeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font(Font.SERIF, Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 2, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        if (type == 0) return "Single Click Speed Seed";
        else if (type == 1) return "Fireworks Speed Seed";
        else if (type == 2) return "Drag Size Speed Seed";
        return null;
    }

    private void handleSeed(int type) {
        try {
            switch (type) {
                case 0:
                    engineSettings.singleClickSpeed = stringNotNullOrEmpty(speedTextField.getText())
                            ? Float.parseFloat(speedTextField.getText())
                            : engineSettings.singleClickSpeed;
                    break;
                case 1:
                    engineSettings.fireworksSpeed = stringNotNullOrEmpty(speedTextField.getText())
                            ? Float.parseFloat(speedTextField.getText())
                            : engineSettings.fireworksSpeed;
                    break;
                case 2:
                    engineSettings.particleDragSpeed = stringNotNullOrEmpty(speedTextField.getText())
                            ? Float.parseFloat(speedTextField.getText())
                            : engineSettings.particleDragSpeed;
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
        particleSpeedEditor = null;
    }
}
