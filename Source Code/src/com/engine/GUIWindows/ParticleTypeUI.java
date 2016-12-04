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

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        frame.add(topPanel);

        JScrollPane scrollPane = new JScrollPane();
        topPanel.add(scrollPane, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.96);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        scrollPane.setViewportView(splitPane);

        JPanel panel = new JPanel();
        splitPane.setLeftComponent(panel);
        panel.setLayout(new BorderLayout(0, 0));

        JLabel lblHey = new JLabel(WindowText.particleDrawOptions());
        lblHey.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblHey, BorderLayout.CENTER);

        final JTextField textField = new JTextField(1);
        textField.setFont(new Font("Times", Font.PLAIN, 17));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        splitPane.setRightComponent(textField);
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
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
            }
        });
        frame.setVisible(true);
    }

    private void close(int index){particleTypeUIs[index] = null; frame.dispose();}
}
