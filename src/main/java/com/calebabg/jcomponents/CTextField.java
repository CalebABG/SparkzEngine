package com.calebabg.jcomponents;

import javax.swing.*;
import java.awt.*;

public class CTextField extends JTextField {
    public final GridBagConstraints gridBagConstraints = new GridBagConstraints();

    public CTextField(String text, Font font, int gridWidth, int fill,
                      int gridX, int gridY, int ipadX, int ipadY, float weightX, float weightY) {
        super(text);
        this.setFont(font);
        this.setColumns(10);
        gridBagConstraints.gridwidth = gridWidth;
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weightX;
        gridBagConstraints.weighty = weightY;
        gridBagConstraints.ipadx = ipadX;
        gridBagConstraints.ipady = ipadY;
        gridBagConstraints.gridx = gridX;
        gridBagConstraints.gridy = gridY;
    }

    public CTextField(String text, Font font, Insets insets, int fill, int[] gridXY) {
        super(text);
        this.setFont(font);
        this.setColumns(10);
        gridBagConstraints.fill = fill;
        gridBagConstraints.insets = insets;
        gridBagConstraints.gridx = gridXY[0];
        gridBagConstraints.gridy = gridXY[1];
    }
}
