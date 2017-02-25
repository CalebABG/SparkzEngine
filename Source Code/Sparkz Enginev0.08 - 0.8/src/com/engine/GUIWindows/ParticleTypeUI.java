package com.engine.GUIWindows;

import com.engine.EngineHelpers.GUIText;
import com.engine.Interfaces_Extensions.KAdapter;
import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.ParticleHelpers.ParticleTypeOptions;
import com.engine.Utilities.InputWrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParticleTypeUI {
    public static ParticleTypeUI[] particleTypeUIs = new ParticleTypeUI[2];
    public JFrame frame;
    public JTextField textField;

    //Make sure type and int given when getInstance is called are the same! If values differ will cause indexOutOfBounds Error
    public static ParticleTypeUI getInstance(int type, String title) {
        if (particleTypeUIs[type] == null) {particleTypeUIs[type] = new ParticleTypeUI(type, title);} particleTypeUIs[type].frame.toFront(); return particleTypeUIs[type];
    }

    private ParticleTypeUI(int type, String title) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame(title);
        frame.setIconImage(Settings.getIcon());
        frame.setSize(320, 520);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close(type)));
        frame.setLocationRelativeTo(OptionsMenu.frame);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JButton jButton1 = new JButton();
        jButton1.setFont(new Font("Times", Font.BOLD, 14));
        jButton1.setText("Enter/Cancel");
        jButton1.addActionListener(e -> getOption(type));
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Times", Font.PLAIN, 17));
        textField.addKeyListener(new KAdapter(e -> {}, e -> {{if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption(type);}}));
        jPanel1.add(textField, BorderLayout.CENTER);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText(GUIText.particleDrawOptions());
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void getOption(int type){
        if (textField.getText() != null) {
            try {
                if (type == 0) {
                    if (InputWrapper.canParseStringInt(textField.getText())) {
                        ParticleTypeOptions.baseParticleOptions(Integer.parseInt(textField.getText()));
                    }
                }
                else if (type == 1) {
                    if (InputWrapper.canParseStringInt(textField.getText())) {
                        ParticleTypeOptions.realFireworksOptions(Integer.parseInt(textField.getText()));
                    }
                }
            }catch (Exception ex){EException.append(ex);}
        }
    }
    private void close(int index){particleTypeUIs[index] = null; frame.dispose();}
}
