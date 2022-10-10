package com.cabg.utilities;

import com.cabg.core.EngineSettings;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.moleculetypes.Molecule.PLAIN_COLOR;

public class ColorUtil {
    public static Color fromHex(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16));
    }

    public static String toHex(Color color) {
        return "#" +
                String.format("%02X", color.getRed()).toLowerCase() +
                String.format("%02X", color.getGreen()).toLowerCase() +
                String.format("%02X", color.getBlue()).toLowerCase();
    }

    public static Color[] convertColors(int index, List<String> list) {
        String[] split = list.get(index).split(EngineSettings.colorsSpliceChar);
        return new Color[]{
                fromHex(split[0]),
                fromHex(split[1]),
                fromHex(split[2]),
                fromHex(split[3]),
                fromHex(split[4])
        };
    }

    public static void setParticlePlainColor() {
        Color l = JColorChooser.showDialog(EFrame, "Particle Color", PLAIN_COLOR);
        PLAIN_COLOR = (l != null) ? l : PLAIN_COLOR;
    }

    public static void setEngineBackgroundColor() {
        Color f = JColorChooser.showDialog(EFrame, "Background Color", null);
        backgroundColor = (f != null) ? f : backgroundColor;
    }

    public static Color randHSLColor() {
        // Saturation between 0.1 and 0.3
        float saturation = (random.nextFloat() * 2000 + 1000) / 10000f, luminance = 0.9f;
        return Color.getHSBColor(random.nextFloat(), saturation, luminance);
    }

    public static Color randHSLColor(int minSat, int maxSat, float luminance) {
        float saturation = (random.nextFloat() * maxSat + minSat) / 10000f;
        return Color.getHSBColor(random.nextFloat(), saturation, luminance);
    }

    public static Color randRGBColor() {
        return new Color(random.nextInt(0x1000000));
    }

    // Resource: http://www.nbdtech.com/Blog/archive/2008/04/27/Calculating-the-Perceived-Brightness-of-a-Color.aspx
    public static int isDark(Color c) {
        return ((c.getRed() << 5) + (c.getGreen() << 6) + (c.getBlue() << 2)) / 100;
    }

    /**
     * Blend two colors.
     * Cred: Jacob Dreyer - mailto:jacob.dreyer@geosoft.no
     *
     * @param color1 First color to blend.
     * @param color2 Second color to blend.
     * @param ratio  Blend ratio. 0.5 will give even blend, 1.0 will return
     *               color1, 0.0 will return color2 and so on.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2, float ratio) {
        float ir = (float) 1.0 - ratio;

        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        return new Color(rgb1[0] * ratio + rgb2[0] * ir,
                rgb1[1] * ratio + rgb2[1] * ir,
                rgb1[2] * ratio + rgb2[2] * ir);
    }
}