package com.engine.GUIWindows;

import com.engine.J8Helpers.Interfaces.KeyAdapterX;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class InstructionsWindow implements KeyAdapterX {
    private static InstructionsWindow[] windows = new InstructionsWindow[2];
    private int index;
    public JFrame frame;

    //Make sure type and int given when getInstance is called are the same! If values differ will cause indexOutOfBounds Error
    public static InstructionsWindow getInstance(int type, JFrame parent, int w, int h, String title, String instructions) {
        if (windows[type] == null) {windows[type] = new InstructionsWindow(type, parent, w, h, title, instructions);} windows[type].frame.toFront(); return windows[type];
    }

    private InstructionsWindow(int type, JFrame parent, int w, int h, String title, String instructions) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception x){x.printStackTrace();}
        frame = new JFrame(title);
        frame.setIconImage(Settings.getIcon());
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);
        frame.addKeyListener(this);

        index = type;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel label = new JLabel(instructions);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void close(){windows[index] = null; frame.dispose();}
    public  void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {close();}}
}
