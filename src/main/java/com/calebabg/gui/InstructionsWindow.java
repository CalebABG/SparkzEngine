package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.utilities.FontUtil;
import com.calebabg.utilities.HTMLUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.calebabg.core.EngineVariables.toolkit;

public class InstructionsWindow {
    private static final InstructionsWindow[] instances = new InstructionsWindow[2];

    private final int index;
    private final JFrame frame;

    private static void getInstance(int index, JFrame parent, int width, int height, String title, String instructions) {
        if (instances[index] == null) instances[index] = new InstructionsWindow(index, parent, width, height, title, instructions);
        instances[index].frame.toFront();
    }

    public static void createEngineInstructionsWindow(JFrame parent) {
        Dimension screenSize = toolkit.getScreenSize();
        InstructionsWindow.getInstance(0, parent, (int) (screenSize.width * .44), (int) (screenSize.height * .55),
                "Engine Instructions", HTMLUtil.ENGINE_INSTRUCTIONS);
    }

    public static void createGraphInstructionsWindow(JFrame parent) {
        Dimension screenSize = toolkit.getScreenSize();
        InstructionsWindow.getInstance(1, parent, (int) (screenSize.width * .37), (int) (screenSize.height * .55),
                "Graph Instructions", HTMLUtil.GRAPH_INSTRUCTIONS);
    }

    private InstructionsWindow(int type, JFrame parent, int w, int h, String title, String instructions) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(title);
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);
        frame.addKeyListener(new ExtendedKeyAdapter.KeyReleased(this::close));

        index = type;

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel label = new JLabel(instructions);
        label.setFont(FontUtil.PLAIN_18);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void close() {
        frame.dispose();
        instances[index] = null;
    }

    private void close(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) close();
    }
}
