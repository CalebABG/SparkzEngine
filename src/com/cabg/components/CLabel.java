package com.cabg.components;

import javax.swing.*;
import java.awt.*;

public class CLabel extends JLabel {
    public CLabel(Rectangle bounds, String text, Font font, Color foreground, Color background) {
        super(text);
        setUp(bounds, font);
        setBackground(background);
        setForeground(foreground);
    }

    public CLabel(Rectangle bounds, Font font, Color foreground, Color background) {
        super("");
        setUp(bounds, font);
        setBackground(background);
        setForeground(foreground);
    }

    public CLabel(String text, Font font, Color foreground, Color background) {
        super(text);
        setFont(font);
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public CLabel(Font font, Color foreground, Color background) {
        setFont(font);
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public CLabel(Color color, boolean useAsForeground) {
        if (useAsForeground) setForeground(color);
        else setBackground(color);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setUp(Rectangle bounds, Font font) {
        setOpaque(true);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBounds((int) (bounds.getX()), (int) (bounds.getY()), (int) (bounds.getWidth()), (int) (bounds.getHeight()));
    }

    public String getBackgroundRGBText() {
        Color bg = getBackground();
        return "<html><h2> R: " + bg.getRed() + "<br> G: " + bg.getGreen() + "<br> B: " + bg.getBlue() + "</h2></html>";
    }
}


