package com.engine.Utilities;

import static com.engine.EngineHelpers.EConstants.*;
import javax.swing.*;
import java.awt.*;

public class ColorUtility {
    private static Color c = new Color(63, 138, 242);

    public static Color HEXtoRGB(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16));
    }

    public static String RGBtoHEX(int r, int g, int b) {
        return "#" +
                String.format("%02X", r).toLowerCase() +
                String.format("%02X", g).toLowerCase() +
                String.format("%02X", b).toLowerCase();
    }

    public static Color HEXAtoRGBA(String s) {
        return new Color(
                Integer.parseInt(s.substring(1, 3), 16),
                Integer.parseInt(s.substring(3, 5), 16),
                Integer.parseInt(s.substring(5, 7), 16),
                Integer.parseInt(s.substring(7, 9), 16)
        );
    }

    /* Custom RGBA To HEX-A*/
    public static String RGBAtoHEXA(int r, int g, int b, int a) {
        return "#" +
                String.format("%02X", r).toLowerCase() +
                String.format("%02X", g).toLowerCase() +
                String.format("%02X", b).toLowerCase() +
                String.format("%02X", a).toLowerCase();
    }

    public static String RGBAtoHEXA(int[] indexes) {
        return "#" +
                String.format("%02X", (indexes[0])).toLowerCase() +
                String.format("%02X", (indexes[1])).toLowerCase() +
                String.format("%02X", (indexes[2])).toLowerCase() +
                String.format("%02X", (indexes[3])).toLowerCase();
    }

    //-------------------------- Helper Functions ------------------------------------//
    public static Color setAlpha(Color c, int alpha) {return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);}

    public static Color getColor() {return c;}
    public static void setColor() {
        Color l = JColorChooser.showDialog(EFrame, "Particle Color", null); c = (l != null) ? l : c;
    }
    public static void giveBackgroundColor() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.getCause();}
        Color f = JColorChooser.showDialog(EFrame, "Background Color", null); backgroundColor = (f != null) ? f : backgroundColor;
    }

    public static Color randHSLColor(){
        // Saturation between 0.1 and 0.3
        float saturation = (float) (((Math.random() * 2000) + 1000) / 10000f), luminance = 0.9f;
        return Color.getHSBColor((float) Math.random(), saturation, luminance);
    }

    public static Color randHSLColor(int minSat, int maxSat, double luminance){
        float saturation = (float) (((Math.random() * maxSat) + minSat) / 10000f);
        return Color.getHSBColor((float) Math.random(), saturation, (float) luminance);
    }

    public static Color randRGBColor(){return new Color((int)(Math.random() * 0x1000000));}

    private static int[] getRGBA(Color[] c, int index) {
        return new int[]{c[index].getRed(), c[index].getGreen(),c[index].getBlue(), c[index].getAlpha()};
    }

    public static String[] getThinkingParticlesStrings(Color[] c) {
        return new String[]{ColorUtility.RGBAtoHEXA(getRGBA(c, 0)), ColorUtility.RGBAtoHEXA(getRGBA(c, 1)),
                ColorUtility.RGBAtoHEXA(getRGBA(c, 2)), ColorUtility.RGBAtoHEXA(getRGBA(c, 3)),
                ColorUtility.RGBAtoHEXA(getRGBA(c, 4))
        };
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = (float) 1.0 - r;

        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        Color color = new Color(rgb1[0] * r + rgb2[0] * ir,
                rgb1[1] * r + rgb2[1] * ir,
                rgb1[2] * r + rgb2[2] * ir);

        return color;
    }

    /**
     * Make an even blend between two colors.
     *
     * @param color1 First color to blend.
     * @param color2 Second color to blend.
     * @return Blended color.
     */
    public static Color blend(Color color1, Color color2) {
        return blend(color1, color2, 0.5);
    }

    /**
     * Make a color darker.
     *
     * @param color    Color to make darker.
     * @param fraction Darkness fraction.
     * @return Darker color.
     */
    public static Color darker(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 - fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 - fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 - fraction));

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
    public static Color lighter(Color color, double fraction) {
        int red = (int) Math.round(color.getRed() * (1.0 + fraction));
        int green = (int) Math.round(color.getGreen() * (1.0 + fraction));
        int blue = (int) Math.round(color.getBlue() * (1.0 + fraction));

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
     * Return the hex name of a specified color.
     *
     * @param color Color to get hex name of.
     * @return Hex name of color: "rrggbb".
     */
    public static String getHexName(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        String rHex = Integer.toString(r, 16);
        String gHex = Integer.toString(g, 16);
        String bHex = Integer.toString(b, 16);

        return (rHex.length() == 2 ? "" + rHex : "0" + rHex) +
                (gHex.length() == 2 ? "" + gHex : "0" + gHex) +
                (bHex.length() == 2 ? "" + bHex : "0" + bHex);
    }


    /**
     * Return the "distance" between two colors. The rgb entries are taken
     * to be coordinates in a 3D space [0.0-1.0], and this method returnes
     * the distance between the coordinates for the first and second color.
     *
     * @param r1, g1, b1  First color.
     * @param r2, g2, b2  Second color.
     * @return Distance bwetween colors.
     */
    public static double colorDistance(double r1, double g1, double b1,
                                       double r2, double g2, double b2) {
        double a = r2 - r1;
        double b = g2 - g1;
        double c = b2 - b1;

        return Math.sqrt(a * a + b * b + c * c);
    }


    /**
     * Return the "distance" between two colors.
     *
     * @param color1 First color [r,g,b].
     * @param color2 Second color [r,g,b].
     * @return Distance bwetween colors.
     */
    public static double colorDistance(double[] color1, double[] color2) {
        return colorDistance(color1[0], color1[1], color1[2],
                color2[0], color2[1], color2[2]);
    }


    /**
     * Return the "distance" between two colors.
     *
     * @param color1 First color.
     * @param color2 Second color.
     * @return Distance between colors.
     */
    public static double colorDistance(Color color1, Color color2) {
        float rgb1[] = new float[3];
        float rgb2[] = new float[3];

        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);

        return colorDistance(rgb1[0], rgb1[1], rgb1[2],
                rgb2[0], rgb2[1], rgb2[2]);
    }


    /**
     * Check if a color is more dark than light. Useful if an entity of
     * this color is to be labeled: Use white label on a "dark" color and
     * black label on a "light" color.
     *
     * @param r,g,b Color to check.
     * @return True if this is a "dark" color, false otherwise.
     */
    public static boolean isDark(double r, double g, double b) {
        // Measure distance to white and black respectively
        double dWhite = colorDistance(r, g, b, 1.0, 1.0, 1.0);
        double dBlack = colorDistance(r, g, b, 0.0, 0.0, 0.0);

        return dBlack < dWhite;
    }


    /**
     * Check if a color is more dark than light. Useful if an entity of
     * this color is to be labeled: Use white label on a "dark" color and
     * black label on a "light" color.
     *
     * @param color Color to check.
     * @return True if this is a "dark" color, false otherwise.
     */
    public static boolean isDark(Color color) {
        float r = color.getRed() / 255.0f;
        float g = color.getGreen() / 255.0f;
        float b = color.getBlue() / 255.0f;

        return isDark(r, g, b);
    }
}