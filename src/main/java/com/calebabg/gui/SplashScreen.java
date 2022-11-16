package com.calebabg.gui;

import com.calebabg.core.EngineVariables;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {
    public void display() {
        Dimension screenSize = EngineVariables.toolkit.getScreenSize();

        int width = 434;
        int height = 536;
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;

        setContentPane(new SplashPanel());
        setBounds(x, y, width, height);
        setVisible(true);

        try {
            Thread.sleep(1500);
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
        dispose();
    }

    static class SplashPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(EngineVariables.splashImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
