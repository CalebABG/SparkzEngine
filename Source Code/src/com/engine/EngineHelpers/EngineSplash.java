package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;

public class EngineSplash extends JWindow {
    private int duration;
    private int imgSize = 256;

    public EngineSplash(int d) {duration = d;}

    public void display() {
        SplashPanel content = new SplashPanel();
        this.setContentPane(content);

        Dimension screen =  Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - imgSize) / 2;
        int y = (screen.height - imgSize) / 2;
        setBounds(x, y, imgSize, imgSize);
        setVisible(true);
        try {Thread.sleep(duration);} catch (Exception e) {EException.append(e);}
        setVisible(false);
    }

    private class SplashPanel extends JPanel {
        Image image;
        public SplashPanel() {try {image = Settings.getIcon();} catch (Exception e) {EException.append(e);}}
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        }
    }
}
