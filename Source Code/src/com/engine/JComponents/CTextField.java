package com.engine.JComponents;

import javax.swing.*;
import java.awt.*;

public class CTextField extends JTextField{
    public String text = "";
    public Font font;
    public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public CTextField(String text, Font font, int fill, Insets insets, int x, int y) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        this.setColumns(10);
        gridBagConstraints.fill = fill;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
    }

    public CTextField(String text, Font font, Insets insets, int fill, int[] gridXY) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        this.setColumns(10);
        gridBagConstraints.fill = fill;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = gridXY[0];
        gridBagConstraints.gridy = gridXY[1];
    }
}
