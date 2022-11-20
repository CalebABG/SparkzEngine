package com.calebabg.gui;

import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CLabel;
import com.calebabg.utilities.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.calebabg.core.EngineVariables.eFrame;

public class QuitWindow {
    private static QuitWindow instance = null;

    private static final Color NO_COLOR = new Color(72, 0, 18, 255);
    private static final Color YES_COLOR = new Color(21, 50, 21, 255);

    private final JFrame frame;

    public static void getInstance() {
        if (instance == null) instance = new QuitWindow();
        instance.frame.toFront();
    }

    private QuitWindow() {
        frame = new JFrame("Exit");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setSize(350, 80);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(eFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(null);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        CLabel label = new CLabel(new Rectangle((frame.getWidth() / 2) - 100, (frame.getHeight() / 2) - 45, 200, 90), "Exit?",
                FontUtil.PLAIN_45, Color.white, new Color(0.0f, 0.0f, 0.0f, 0.0f));
        panel.add(label);

        CLabel label2 = new CLabel(new Rectangle(20, (frame.getHeight() / 2) - 26, 90, 50), "Yes", FontUtil.PLAIN_45, Color.white, YES_COLOR);

        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label2.setBackground(YES_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label2.setBackground(YES_COLOR.darker());
            }
        });

        panel.add(label2);

        CLabel label3 = new CLabel(new Rectangle(frame.getWidth() - 109, (frame.getHeight() / 2) - 26, 90, 50), "No", FontUtil.PLAIN_45, Color.white, NO_COLOR);

        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label3.setBackground(NO_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label3.setBackground(NO_COLOR.darker());
            }
        });

        panel.add(label3);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
        instance = null;
    }
}