package com.engine.GUIWindows;

import com.engine.EngineHelpers.GUIText;
import com.engine.EngineHelpers.EngineMethods;

import static com.engine.EngineHelpers.EConstants.*;

import com.engine.Utilities.InputWrapper;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionsMenu {
    private static OptionsMenu optionsMenu = null;
    public static JFrame frame;
    private static JTextField textField;
    //public static void main(String[] args) {getInstance();}

    public static OptionsMenu getInstance() {
        if (optionsMenu == null) {
            optionsMenu = new OptionsMenu();
        }
        frame.toFront();
        return optionsMenu;
    }

    private OptionsMenu() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception x) {
            x.printStackTrace();
        }
        frame = new JFrame("Options Menu");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(478, 479);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                close();
            }
        });
        frame.setLocationRelativeTo(EFrame);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Times", Font.PLAIN, 17));
        textField.addKeyListener(new KeyAdapter() {
            //Needs to be keyPressed() handler - keyReleased() will cause windows to display twice
            public void keyPressed(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ENTER) {getOption();}}
        });
        jPanel1.add(textField, BorderLayout.CENTER);

        JButton jButton1 = new JButton();
        jButton1.setFont(new Font("Times", Font.BOLD, 14));
        jButton1.setText("Enter/Cancel");
        jButton1.addActionListener(e -> {if (e.getSource() == jButton1) {getOption();}});
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText(GUIText.generalOptions());
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void getOption() {
        if (textField.getText() != null) {
            if (InputWrapper.canParseStringInt(textField.getText())) {
                EngineMethods.getOptions(Integer.parseInt(textField.getText()));
            }
        }
    }

    private void close() {
        optionsMenu = null;
        frame.dispose();
    }
}
