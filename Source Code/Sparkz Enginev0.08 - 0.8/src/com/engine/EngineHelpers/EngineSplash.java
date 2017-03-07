package com.engine.EngineHelpers;

import com.engine.GUIWindows.EException;
import com.engine.J8Helpers.Interfaces.MouseAdapterX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class EngineSplash extends JWindow implements MouseAdapterX {
    private int duration;
    private Point mpt = new Point();

    public EngineSplash(int d) {
        duration = d;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void display() {
        SplashPanel content = new SplashPanel();
        setContentPane(content);
        int w = 434, h = 536;
        Dimension screen =  Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - w) / 2;
        int y = (screen.height - h) / 2;
        setBounds(x, y, w, h);
        setVisible(true);
        try {Thread.sleep(duration);} catch (Exception e) {EException.append(e);}
        setVisible(false);
        dispose();
    }

    public void mousePressed(MouseEvent e) {mpt.x = e.getX(); mpt.y = e.getY();}
    public void mouseDragged(MouseEvent e) {int xDrag = e.getXOnScreen(), yDrag = e.getYOnScreen(); setLocation((xDrag - mpt.x), (yDrag - mpt.y));}
}
