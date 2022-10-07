package com.engine.Utilities;

import com.engine.ThinkingParticles.ReactiveColors;

import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.ParticleTypes.Molecule.PLAIN_COLOR;
import static org.apache.commons.math3.util.FastMath.round;

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

    public static void setPlainColor() {
        Color l = JColorChooser.showDialog(EFrame, "Particle Color", PLAIN_COLOR);
        PLAIN_COLOR = (l != null) ? l : PLAIN_COLOR;
    }

    //-------------------------- Helper Functions ------------------------------------//
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

    public static void setReactiveColors(Color[] colors) {
        for (int i = 0; i < ReactiveColors.getComponents().length; i++)
            ReactiveColors.setComponent(i, colors[i]);
    }

    public static String serializeReactiveColors(Color[] c) {
        return toHex(c[0]) + Settings.spliceChar +
               toHex(c[1]) + Settings.spliceChar +
               toHex(c[2]) + Settings.spliceChar +
               toHex(c[3]) + Settings.spliceChar +
               toHex(c[4]);
    }

    ///////////////////////////////////////////////////////
    //  Helper Color Functions
    //  Cred: Jacob Dreyer - mailto:jacob.dreyer@geosoft.no
    /**
     * Blend two colors.
     *
     * @param color1 First color to blend.
     * @param color2 Second color to blend.
     * @param ratio  Blend ratio. 0.5 will give even blend, 1.0 will return
     *               color1, 0.0 will return color2 and so on.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2, float ratio) {
        float ir = (float) 1.0 - ratio;

        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        return new Color(rgb1[0] * ratio + rgb2[0] * ir,
                rgb1[1] * ratio + rgb2[1] * ir,
                rgb1[2] * ratio + rgb2[2] * ir);
    }

    /**
     * Make an even blend between two colors.
     *
     * @param color1 First color to blend.
     * @param color2 Second color to blend.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2) {
        return blend(color1, color2, 0.5f);
    }

    /**
     * Make a color darker.
     *
     * @param color    Color to make darker.
     * @param fraction Darkness fraction.
     * @return Darker color.
     */
    public static Color darker(Color color, float fraction) {
        int red = (int) round(color.getRed() * (1.0 - fraction));
        int green = (int) round(color.getGreen() * (1.0 - fraction));
        int blue = (int) round(color.getBlue() * (1.0 - fraction));

        if (red < 0) red = 0;
        else if (red > 255) red = 255;
        if (green < 0) green = 0;
        else if (green > 255) green = 255;
        if (blue < 0) blue = 0;
        else if (blue > 255) blue = 255;

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }

    /**
     * Make a color lighter.
     *
     * @param color    Color to make lighter.
     * @param fraction Darkness fraction.
     * @return Lighter color.
     */
    public static Color lighter(Color color, float fraction) {
        int red = (int) round(color.getRed() * (1.0 + fraction));
        int green = (int) round(color.getGreen() * (1.0 + fraction));
        int blue = (int) round(color.getBlue() * (1.0 + fraction));

        if (red < 0) red = 0;
        else if (red > 255) red = 255;
        if (green < 0) green = 0;
        else if (green > 255) green = 255;
        if (blue < 0) blue = 0;
        else if (blue > 255) blue = 255;

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);
    }
}