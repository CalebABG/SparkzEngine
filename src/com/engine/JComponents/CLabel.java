package com.engine.JComponents;

import javax.swing.*;
import java.awt.*;

public class CLabel extends JLabel {
    public String text;
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

    public CLabel(String text, Font font, Color foreground, Color background) {
        super(text);
        this.text = super.getText();
        this.font = font;
        setBackground(background);
        setForeground(foreground);
        setOpaque(true);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public CLabel(Font font, Color foreground, Color background) {
        this.text = super.getText();
        this.font = font;
        setBackground(background);
        setForeground(foreground);
        setOpaque(true);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public CLabel(Color foreground, Color background) {
        this.text = super.getText();
        setBackground(background);
        setForeground(foreground);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * @param useAsForeground used to determine if the color should be used as the background or foreground color.
     *                              If set to true = foreground color; set to false = background color.
     * @param color                 color to be used as either foreground or background color
     **/
    public CLabel(Color color, boolean useAsForeground) {
        this.text = super.getText();
        if (useAsForeground) setForeground(color);
        else setBackground(color);
        setOpaque(true);
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public void setUp() {
        setOpaque(true);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBounds((int) (bounds.getX()), (int) (bounds.getY()), (int) (bounds.getWidth()), (int) (bounds.getHeight()));
    }

    public String getBGColorText() {
        Color bg = getBackground();
        return "<html><h2> R: " + bg.getRed() + "<br> G: " + bg.getGreen() + "<br> B: " + bg.getBlue() + "</h2></html>";
    }
}


