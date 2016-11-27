package com.engine.GUIWindows;

import com.engine.EngineHelpers.WindowText;
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

    public static OptionsMenu getInstance() {
        if (optionsMenu == null) {optionsMenu = new OptionsMenu();}frame.toFront(); return optionsMenu;
    }

    private OptionsMenu() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame("Options Menu");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(478, 479);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

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

        JLabel lblHey = new JLabel(WindowText.generalOptions());
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
                        if (InputWrapper.canParseStringInt(textField.getText())) {
                            EngineMethods.getOptions(Integer.parseInt(textField.getText()));
                        }
                    }
                }
            }
        });
        frame.setVisible(true);
    }

    private void close(){optionsMenu = null;frame.dispose();}
}
