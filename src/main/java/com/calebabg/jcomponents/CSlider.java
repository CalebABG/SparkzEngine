package com.calebabg.jcomponents;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class CSlider extends JSlider {
    public CSlider(int min, int max, int initVal, Rectangle bounds,
                   int minorTickSpacing, int majorTickSpacing,
                   boolean showTickMarks, boolean showLabels, boolean showBorder) {
        super(min, max, initVal);
        setBounds(bounds);
        setMinorTickSpacing(minorTickSpacing);
        setMajorTickSpacing(majorTickSpacing);
        setPaintTicks(showTickMarks);
        setPaintLabels(showLabels);
        if (showBorder) setBorder(new CompoundBorder());
    }
}
