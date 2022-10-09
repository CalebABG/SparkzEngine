package com.cabg.utilities;

import javax.swing.*;
import java.awt.*;

import static com.cabg.core.EngineVariables.*;
import static com.cabg.particletypes.Molecule.PLAIN_COLOR;

public class ColorUtility {
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

    public static void setParticlePlainColor() {
        Color l = JColorChooser.showDialog(EFrame, "Particle Color", PLAIN_COLOR);
        PLAIN_COLOR = (l != null) ? l : PLAIN_COLOR;
    }

    public static void setEngineBackgroundColor() {
        Color f = JColorChooser.showDialog(EFrame, "Background Color", null);
        backgroundColor = (f != null) ? f : backgroundColor;
    }

    public static Color randHSLColor(){
        // Saturation between 0.1 and 0.3
        float saturation = (random.nextFloat() * 2000 + 1000) / 10000f, luminance = 0.9f;
        return Color.getHSBColor(random.nextFloat(), saturation, luminance);
    }

    public static Color randHSLColor(int minSat, int maxSat, float luminance){
        float saturation = (random.nextFloat() * maxSat + minSat) / 10000f;
        return Color.getHSBColor(random.nextFloat(), saturation, luminance);
    }

    public static Color randRGBColor(){return new Color(random.nextInt(0x1000000));}

    public static String serializeReactiveColors(Color[] c) {
        return toHex(c[0]) + Settings.spliceChar +
               toHex(c[1]) + Settings.spliceChar +
               toHex(c[2]) + Settings.spliceChar +
               toHex(c[3]) + Settings.spliceChar +
               toHex(c[4]);
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