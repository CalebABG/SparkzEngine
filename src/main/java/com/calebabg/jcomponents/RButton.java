package com.calebabg.jcomponents;


import javax.swing.*;
import java.awt.*;

public class RButton extends JButton {
    public GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public RButton(String text, Font font, int gridWidth, int fill, int[] gridXY, int[] ipadXY) {
        super(text);
        this.setFont(font);
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.fill = fill;
        gridBagConstraints.ipadx = ipadXY[0];
        gridBagConstraints.ipady = ipadXY[1];
        gridBagConstraints.gridx = gridXY[0];
        gridBagConstraints.gridy = gridXY[1];
    }

    public RButton(String text, Font font, int gridWidth, int fill, int anchor,
                   int gridX, int gridY, int ipadX, int ipadY, float weightX, float weightY) {
        super(text);
        this.setFont(font);
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weightX;
        gridBagConstraints.weighty = weightY;
        gridBagConstraints.ipadx = ipadX;
        gridBagConstraints.ipady = ipadY;
        gridBagConstraints.gridx = gridX;
        gridBagConstraints.gridy = gridY;
    }
}
