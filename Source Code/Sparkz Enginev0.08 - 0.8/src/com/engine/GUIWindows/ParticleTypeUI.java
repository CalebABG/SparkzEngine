package com.engine.GUIWindows;

import com.engine.EngineHelpers.WindowText;
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
    private static ParticleTypeUI[] particleTypeUIs = new ParticleTypeUI[2];
    public static JFrame frame;
    public static JTextField textField;

    public static ParticleTypeUI getInstance(int type) {
        if (type == 0) {if (particleTypeUIs[0] == null) {particleTypeUIs[0] = new ParticleTypeUI(0);}frame.toFront(); return particleTypeUIs[0];}
        else {if (particleTypeUIs[1] == null) {particleTypeUIs[1] = new ParticleTypeUI(1);}frame.toFront(); return particleTypeUIs[1];}
    }

    private ParticleTypeUI(int type) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setSize(320, 520);
        if (type == 0) {frame.setTitle("Particle Type Options");}else {frame.setTitle("Fireworks Type Options");}
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close(type);}});
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
        textField.addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption(type);}});
        jPanel1.add(textField, BorderLayout.CENTER);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel();
        jLabel2.setText(WindowText.particleDrawOptions());
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void getOption(int type){
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
