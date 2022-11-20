package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.enums.MoleculeRenderMode;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.utilities.FontUtil;
import com.calebabg.utilities.HTMLUtil;
import com.calebabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.calebabg.core.EngineVariables.*;
import static com.calebabg.utilities.HTMLUtil.headingTag;
import static com.calebabg.utilities.InputUtil.valueGuardString;

public class MoleculeRenderModePicker {
    private static final MoleculeRenderModePicker[] instances = new MoleculeRenderModePicker[2];

    private final JFrame frame;
    private final JTextField textField;

    public static void getInstance(int type, String title, JFrame parent) {
        if (instances[type] == null) instances[type] = new MoleculeRenderModePicker(type, title, parent);
        instances[type].frame.toFront();
    }

    public static void getInstance(int type, String title) {
        getInstance(type, title, null);
    }

    private MoleculeRenderModePicker(int type, String title, JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(title);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(320, 520);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close(type)));
        frame.setLocationRelativeTo(parent);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JButton jButton1 = new JButton();
        jButton1.setFont(FontUtil.BOLD_14);
        jButton1.setText("Enter");
        jButton1.addActionListener(e -> getOption(type));
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setFont(FontUtil.PLAIN_17);
        textField.addKeyListener(new ExtendedKeyAdapter(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption(type);
        }, e -> {}));
        jPanel1.add(textField, BorderLayout.CENTER);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel(HTMLUtil.MOLECULE_RENDER_OPTIONS);
        jLabel2.setFont(FontUtil.PLAIN_18);
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
        if (type == 0) particleOptions(amount);
        else if (type == 1) fireworksOptions(amount);
    }

    private static void particleOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.particleRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) baseParticleText = valueGuardString(1, instances[0].frame, baseParticleText, HTMLUtil.headingTag(3, "Enter Custom Text"));
        }
    }

    private static void fireworksOptions(int x) {
        if (x > -1 && x < MoleculeRenderMode.values().length) {
            engineSettings.fireworksRenderMode = MoleculeRenderMode.values()[x];
            if (x == 4) fireworksParticleText = valueGuardString(1, instances[1].frame, fireworksParticleText, HTMLUtil.headingTag(3, "Enter Custom Text"));
        }
    }
}
