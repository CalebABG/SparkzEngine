package com.cabg.gui;

import com.cabg.core.EngineVariables;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.cabg.core.EngineVariables.toolkit;

public class SplashScreen extends JWindow {
    private final int duration;
    private final Point mpt = new Point();

    public SplashScreen(int d) {
        duration = d;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mpt.x = e.getX();
                mpt.y = e.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation((e.getXOnScreen() - mpt.x), (e.getYOnScreen() - mpt.y));
            }
        });
    }

    public void display() {
        SplashPanel content = new SplashPanel();
        setContentPane(content);
        int w = 434;
        int h = 536;
        Dimension screen = toolkit.getScreenSize();
        int x = (screen.width - w) / 2;
        int y = (screen.height - h) / 2;
        setBounds(x, y, w, h);
        setVisible(true);
        try {
            Thread.sleep(duration);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        dispose();
    }

    static class SplashPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (EngineVariables.splashImage != null)
                g.drawImage(EngineVariables.splashImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
