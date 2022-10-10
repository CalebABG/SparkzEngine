package com.cabg.gui;

import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class InstructionsWindow {
    private static final InstructionsWindow[] windows = new InstructionsWindow[2];
    private final int index;
    public JFrame frame;

    public static void getInstance(int type, JFrame parent, int w, int h, String title, String instructions) {
        if (windows[type] == null)
            windows[type] = new InstructionsWindow(type, parent, w, h, title, instructions);

        windows[type].frame.toFront();
    }

    private InstructionsWindow(int type, JFrame parent, int w, int h, String title, String instructions) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }

        frame = new JFrame(title);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);
        frame.addKeyListener(new ExtendedKeyAdapter.KeyReleased(this::close));

        index = type;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel label = new JLabel(instructions);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void close() {
        frame.dispose();
        windows[index] = null;
    }

    private void close(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) close();
    }
}
