package com.engine.GUIWindows;

import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.engine.ThinkingParticles.SCChoices.*;

public class ParticlePresets {
    private static ParticlePresets scPresetsUI = null;
    private static JFrame frame;
    private static JButton btnNewButton, button, button_1, button_2, button_3, button_4,
            button_5, button_6, button_7, button_8, button_9, button_10, button_11, button_12,
            button_13;

    public static ParticlePresets getInstance() {
        if (scPresetsUI == null) {scPresetsUI = new ParticlePresets();}frame.toFront(); return scPresetsUI;
    }

    private ParticlePresets() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Color Presets :D");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(323, 290);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent)
        {scPresetsUI = null; frame.dispose();}});
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ParticleColor.frame);

        btnNewButton = new JButton("Propane");
        btnNewButton.setBounds(10, 11, 89, 39);
        btnNewButton.addActionListener(e -> {if (e.getSource() == btnNewButton) setPresetColors(propaneColor());});
        frame.add(btnNewButton);

        button = new JButton("Maritime");
        button.setBounds(219, 11, 89, 39);
        button.addActionListener(e -> {if (e.getSource() == button) setPresetColors(maritime());});
        frame.add(button);

        button_1 = new JButton("Day & Night");
        button_1.setBounds(109, 11, 100, 39);
        button_1.addActionListener(e -> {if (e.getSource() == button_1) setPresetColors(dayAndNight());});
        frame.add(button_1);

        button_2 = new JButton("Plant Life");
        button_2.setBounds(10, 61, 89, 39);
        button_2.addActionListener(e -> {if (e.getSource() == button_2) setPresetColors(plantLife());});
        frame.add(button_2);

        button_3 = new JButton("Coffee");
        button_3.setBounds(109, 61, 100, 39);
        button_3.addActionListener(e -> {if (e.getSource() == button_3) setPresetColors(fancyCoffee());});
        frame.add(button_3);

        button_4 = new JButton("Cinnamon");
        button_4.setBounds(219, 61, 89, 39);
        button_4.addActionListener(e -> {if (e.getSource() == button_4) setPresetColors(cinnamonSpice());});
        frame.add(button_4);

        button_5 = new JButton("Pastels");
        button_5.setBounds(10, 111, 89, 39);
        button_5.addActionListener(e -> {if (e.getSource() == button_5) setPresetColors(pastels());});
        frame.add(button_5);

        button_6 = new JButton("Exotic Berry");
        button_6.setBounds(109, 111, 100, 39);
        button_6.addActionListener(e -> {if (e.getSource() == button_6) setPresetColors(exoticBerries());});
        frame.add(button_6);

        button_7 = new JButton("Citrus");
        button_7.setBounds(219, 111, 89, 39);
        button_7.addActionListener(e -> {if (e.getSource() == button_7) setPresetColors(citrus());});
        frame.add(button_7);

        button_8 = new JButton("Candy");
        button_8.setBounds(10, 161, 89, 39);
        button_8.addActionListener(e -> {if (e.getSource() == button_8) setPresetColors(candy());});
        frame.add(button_8);

        button_9 = new JButton("Ocean");
        button_9.setBounds(109, 161, 100, 39);
        button_9.addActionListener(e -> {if (e.getSource() == button_9) setPresetColors(oceanBlues());});
        frame.add(button_9);

        button_10 = new JButton("Hazy Day");
        button_10.setBounds(219, 161, 89, 39);
        button_10.addActionListener(e -> {if (e.getSource() == button_10) setPresetColors(hazyday());});
        frame.add(button_10);

        button_11 = new JButton("Retro");
        button_11.setBounds(10, 211, 89, 39);
        button_11.addActionListener(e -> {if (e.getSource() == button_11) setPresetColors(retro());});
        frame.add(button_11);

        button_12 = new JButton("Shadow");
        button_12.setBounds(109, 211, 100, 39);
        button_12.addActionListener(e -> {if (e.getSource() == button_12) setPresetColors(shadowslate());});
        frame.add(button_12);

        button_13 = new JButton("Onyx");
        button_13.setBounds(219, 211, 89, 39);
        button_13.addActionListener(e -> {if (e.getSource() == button_13) setPresetColors(onyximpact());});
        frame.add(button_13);
        frame.setVisible(true);
    }
}
