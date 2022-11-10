package com.cabg.gui;

import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.moleculehelpers.MoleculeRenderOptions;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MoleculeTypePicker {
    public static MoleculeTypePicker[] moleculeTypePickers = new MoleculeTypePicker[2];

    public JFrame frame;
    public JTextField textField;

    public static void getInstance(int type, String title) {
        if (moleculeTypePickers[type] == null) moleculeTypePickers[type] = new MoleculeTypePicker(type, title);
        moleculeTypePickers[type].frame.toFront();
    }

    private MoleculeTypePicker(int type, String title) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        frame = new JFrame(title);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(320, 520);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close(type)));
        frame.setLocationRelativeTo(OptionsMenu.frame);

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

    private void getOption(int type) {
        if (!textField.getText().isEmpty()) {
            try {
                if (type == 0) {
                    if (InputUtil.canParseInt(textField.getText())) {
                        MoleculeRenderOptions.particleOptions(Integer.parseInt(textField.getText()));
                    }
                } else if (type == 1) {
                    if (InputUtil.canParseInt(textField.getText())) {
                        MoleculeRenderOptions.fireworksOptions(Integer.parseInt(textField.getText()));
                    }
                }
            } catch (Exception ex) {
                ExceptionLogger.append(ex);
            }
        }
    }

    private void close(int index) {
        frame.dispose();
        moleculeTypePickers[index] = null;
    }
}
