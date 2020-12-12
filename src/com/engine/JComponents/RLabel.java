package com.engine.JComponents;

import javax.swing.*;
import java.awt.*;

public class RLabel extends JLabel {
    public String text;
    public Font font;
    public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public RLabel(String text, Font font, Insets insets, int anchor, int[] gridXY, int[] ipadXY) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.insets = insets;
        gridBagConstraints.ipadx = ipadXY[0];
        gridBagConstraints.ipady = ipadXY[1];
        gridBagConstraints.gridx = gridXY[0];
        gridBagConstraints.gridy = gridXY[1];
    }

    public RLabel(String text, Font font, Insets insets, int fill, int gw, int gx, int gy) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.fill = fill;
        gridBagConstraints.gridwidth = gw;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = gx;
        gridBagConstraints.gridy = gy;
    }

    public RLabel(String text, Font font, int anchor, Insets insets, int x, int y) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
    }

    public RLabel(String text, Font font, int gridwidth, Insets insets, int[] xy) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = xy[0];
        gridBagConstraints.gridy = xy[1];
    }

    public RLabel(String text, Font font, int gridWidth, Insets insets) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.insets = insets;
    }
}
