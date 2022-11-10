package com.cabg.gui;

import com.cabg.core.EngineMethods;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.utilities.HTMLUtil;
import com.cabg.utilities.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.cabg.core.EngineVariables.eFrame;

public class OptionsMenu {
    private static OptionsMenu optionsMenu = null;
    public static JFrame frame;
    private static JTextField textField;

    public static void getInstance() {
        if (optionsMenu == null) optionsMenu = new OptionsMenu();
        frame.toFront();
    }

    private OptionsMenu() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Options Menu");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(495, 480);
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

    private void getOption() {
        String text = textField.getText();
        if (InputUtil.canParseInt(text))
            EngineMethods.handleMenuOptionsSelection(Integer.parseInt(text));
    }

    private void close() {
        frame.dispose();
        optionsMenu = null;
    }
}
