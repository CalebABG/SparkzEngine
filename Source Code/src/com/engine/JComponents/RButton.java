package com.engine.JComponents;


import javax.swing.*;
import java.awt.*;

public class RButton extends JButton{
    public String text = "";
    public Font font;
    public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public RButton(String text, Font font, int gridWidth, int fill, int[] gridXY, int[] ipadXY) {
        super(text);
        this.text = super.getText();
        this.font = font;
        this.setFont(this.font);
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.fill = fill;
        gridBagConstraints.ipadx = ipadXY[0];
        gridBagConstraints.ipady = ipadXY[1];
        gridBagConstraints.gridx = gridXY[0];
        gridBagConstraints.gridy = gridXY[1];
    }
}
