package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class EngineSplash extends JWindow {
    private int duration;
    private Point mpt = new Point();

    public EngineSplash(int d) {
        duration = d;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {mpt.x = e.getX(); mpt.y = e.getY();}});
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) { int xDrag = e.getXOnScreen(), yDrag = e.getYOnScreen(); setLocation((xDrag - mpt.x), (yDrag - mpt.y));}});
    }

    public void display() {
        SplashPanel content = new SplashPanel(); setContentPane(content);
        int w = 434, h = 536;
        Dimension screen =  Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - w) / 2;
        int y = (screen.height - h) / 2;
        setBounds(x, y, w, h);
        setVisible(true);
        try {Thread.sleep(duration);} catch (Exception e) {EException.append(e);}
        setVisible(false);
    }

    private class SplashPanel extends JPanel {
        Image image;
        public SplashPanel() {try {image = Settings.getSplashImage();} catch (Exception e) {EException.append(e);}}
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) { g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);}
        }
    }
}
