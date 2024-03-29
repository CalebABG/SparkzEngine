package com.calebabg.reactivity;

import com.calebabg.gui.ReactiveColorsPresets;

import java.awt.*;

import static com.calebabg.utilities.MathUtil.clamp;

public class ReactiveColors {
    private static Color[] colors = ReactiveColorsPresets.defaultColor();

    private ReactiveColors() {}

    public static Color getReactiveColor(float velocity) {
        return colors[(int) clamp(velocity / 2.0f, 0f, colors.length - 1f)];
    }

    public static Color[] getColors() {
        return colors;
    }

    public static void setColors(Color[] colors) {
        ReactiveColors.colors = colors;
    }

    public static Color getColor(int index) {
        return colors[index];
    }

    public static void setColor(int index, Color color) {
        colors[index] = color;
    }
}
