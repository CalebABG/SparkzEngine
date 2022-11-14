package com.cabg.gui;

import com.cabg.components.CMenuBar;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.enums.GravitationMode;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.reactivecolors.ReactiveColorsRandomizer;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.moleculetypes.Molecule.DEFAULT_COLOR;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputUtil.minValueGuard;

public class OptionsMenu {
    private static OptionsMenu instance = null;

    private final JFrame frame;
    private final JTextField textField;

    public static void getInstance() {
        if (instance == null) instance = new OptionsMenu();
        instance.frame.toFront();
    }

    private OptionsMenu() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Options Menu");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(575, 480);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(eFrame);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        textField.addKeyListener(new ExtendedKeyAdapter.KeyPressed(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption();
        }));
        jPanel1.add(textField, BorderLayout.CENTER);

        JButton jButton1 = new JButton();
        jButton1.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        jButton1.setText("Enter");
        jButton1.addActionListener(e -> {
            if (e.getSource() == jButton1) getOption();
        });
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel(HTMLUtil.GeneralOptions);
        jLabel2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }

    private void getOption() {
        String text = textField.getText();

        if (InputUtil.canParseInt(text)) {
            int option = Integer.parseInt(text);

            switch (option) {
                case 1: showParticleSizeDialog(); break;
                case 2: showParticleDragDialog(); break;
                case 3: showFireworksAmountDialog(); break;
                case 4: MoleculeTypePicker.getInstance(0, "Particle Type Options", instance.frame); break;
                case 5: showParticleDefaultColorDialog(); break;
                case 6: showGravitationOptions(); break;
                case 7: showParticleSizeOptions(); break;
                case 8: showParticleSpeedOptions(); break;
                case 9: showFireworksOptions(); break;
                case 10: MoleculeTypePicker.getInstance(1, "Firework Type Options", instance.frame); break;
                case 11: ReactiveColorsEditor.getInstance(instance.frame); break;
                case 12: showFireworksSafetyAmountDialog(); break;
                case 13: showReactiveColorsCycleTimeOptions(); break;
            }
        }
    }

    private static void showFireworksOptions() {
        String input = JOptionPane.showInputDialog(instance.frame, HeadingTag(3, HTMLUtil.FireworksOptions), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (rfoInt) {
            case 1: ParticleSlideEditor.showFireworksWindAmountDialog(); break;
            case 2: ParticleSlideEditor.showParticleLifeAmountDialog(); break;
            case 3: ParticleSlideEditor.showFireworksJitterAmountDialog(); break;
        }
    }

    private static void showParticleSizeOptions() {
        String input = JOptionPane.showInputDialog(instance.frame, HTMLUtil.ParticleSizeOptions, null, JOptionPane.PLAIN_MESSAGE);
        int opt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (opt) {
            case 1: ParticleSizeEditor.getInstance(0, instance.frame); break;
            case 2: ParticleSizeEditor.getInstance(1, instance.frame); break;
            case 3: ParticleSizeEditor.getInstance(2, instance.frame); break;
        }
    }

    private static void showParticleSpeedOptions() {
        String input = JOptionPane.showInputDialog(instance.frame, HTMLUtil.ParticleSpeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int opt = InputUtil.canParseInt(input) ? Integer.parseInt(input) : -1;

        switch (opt) {
            case 1: ParticleSpeedEditor.getInstance(0, instance.frame); break;
            case 2: ParticleSpeedEditor.getInstance(1, instance.frame); break;
            case 3: ParticleSpeedEditor.getInstance(2, instance.frame); break;
        }
    }

    private static void showParticleSizeDialog() {
        float size = minValueGuard(0, .95f, HeadingTag(3, "Enter Particle Size"), instance.frame);
        if (Particles.size() > 0) {
            for (int i = 0; i < Particles.size(); i++)
                Particles.get(i).radius = size;
        }
    }

    public static void showParticleDefaultColorDialog() {
        Color color = JColorChooser.showDialog(instance.frame, "Particle Default Color", DEFAULT_COLOR);
        if (color != null) DEFAULT_COLOR = color;
    }

    private static void showGravitationOptions() {
        int gravityMode = (int) minValueGuard(0, engineSettings.gravitationMode.ordinal(), HTMLUtil.ParticleGravitationOptions, instance.frame);
        if (gravityMode < GravitationMode.values().length) engineSettings.gravitationMode = GravitationMode.values()[gravityMode];
        CMenuBar.updateGravitationModeRadios();
    }

    private static void showReactiveColorsCycleTimeOptions() {
        engineSettings.reactiveColorsCycleRateInSeconds = (int) minValueGuard(1, engineSettings.reactiveColorsCycleRateInSeconds,
                HeadingTag(3, "Enter Cycle Time (In Seconds)"), instance.frame);

        if (engineSettings.cycleReactiveColors)
            ReactiveColorsRandomizer.restartCycle();
    }

    private static void showParticleDragDialog() {
        engineSettings.particleDragAmount = (int) minValueGuard(1, engineSettings.particleDragAmount,
                HeadingTag(3, "Enter Particle Drag Amount"), instance.frame);
    }

    private static void showFireworksSafetyAmountDialog() {
        engineSettings.fireworksParticleSafetyAmount = (int) minValueGuard(0, engineSettings.fireworksParticleSafetyAmount,
                HeadingTag(3, "Enter Safety Amount"), instance.frame);
    }

    private static void showFireworksAmountDialog() {
        engineSettings.fireworksAmount = (int) minValueGuard(1, engineSettings.fireworksAmount,
                HeadingTag(3, "Enter Fireworks Amount"), instance.frame);
    }
}
