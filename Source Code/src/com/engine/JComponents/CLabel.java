package com.engine.JComponents;

import javax.swing.*;
import java.awt.*;

public class CLabel extends JLabel {
    public String text = "";
    public Font font;
    public Rectangle bounds;

    public CLabel(Rectangle bounds, String text, Font font, Color foreground, Color background) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.bounds = bounds;
        setBackground(background);
        setForeground(foreground);
        setUp();
    }

    public CLabel(Rectangle bounds, Font font, Color foreground, Color background) {
        super("");
        this.text = super.getText();
        this.font = font;
        this.bounds = bounds;
        setBackground(background);
        setForeground(foreground);
        setUp();
    }

    public void setUp() {
        setOpaque(true);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBounds((int) (bounds.getX()), (int) (bounds.getY()), (int) (bounds.getWidth()), (int) (bounds.getHeight()));
    }
}


