package com.cabg.gui;

import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.enums.MoleculeRenderMode;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.utilities.HTMLUtil.HeadingTag;
import static com.cabg.utilities.InputUtil.valueGuardString;

public class MoleculeTypePicker {
    private static final MoleculeTypePicker[] instances = new MoleculeTypePicker[2];

    private final JFrame frame;
    private final JTextField textField;

    public static void getInstance(int type, String title, JFrame parent) {
        if (instances[type] == null) instances[type] = new MoleculeTypePicker(type, title, parent);
        instances[type].frame.toFront();
    }

    public static void getInstance(int type, String title) {
        getInstance(type, title, null);
    }

    private MoleculeTypePicker(int type, String title, JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(title);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(320, 520);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close(type)));
        frame.setLocationRelativeTo(parent);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JButton jButton1 = new JButton();
        jButton1.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        jButton1.setText("Enter");
        jButton1.addActionListener(e -> getOption(type));
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        textField.addKeyListener(new ExtendedKeyAdapter(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption(type);
        }, e -> {}));
        jPanel1.add(textField, BorderLayout.CENTER);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel(HTMLUtil.ParticleDrawOptions);
        jLabel2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void close(int index) {
        frame.dispose();
        instances[index] = null;
    }

    private void getOption(int type) {
        String text = textField.getText();
        if (!InputUtil.canParseInt(text)) return;

        int amount = Integer.parseInt(text);
        switch (type) {
            case 0: particleOptions(amount); break;
            case 1: fireworksOptions(amount); break;
        }
    }

    private static void particleOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.particleRenderMode = MoleculeRenderMode.values()[x];

            if (x == 4) {
                baseParticleText = valueGuardString(1, instances[0].frame, baseParticleText, HeadingTag(3, "Enter Custom Text"));
            }
        }
    }

    private static void fireworksOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.fireworksRenderMode = MoleculeRenderMode.values()[x];

            if (x == 4) {
                fireworksParticleText = valueGuardString(1, instances[1].frame, fireworksParticleText, HeadingTag(3, "Enter Custom Text"));
            }
        }
    }
}
