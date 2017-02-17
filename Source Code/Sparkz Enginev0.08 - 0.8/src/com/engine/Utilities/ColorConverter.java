package com.engine.Utilities;

import static com.engine.EngineHelpers.EConstants.*;
import javax.swing.*;
import java.awt.*;

public class ColorConverter {
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
        return new String[]{ColorConverter.RGBAtoHEXA(getRGBA(c, 0)), ColorConverter.RGBAtoHEXA(getRGBA(c, 1)),
                ColorConverter.RGBAtoHEXA(getRGBA(c, 2)), ColorConverter.RGBAtoHEXA(getRGBA(c, 3)),
                ColorConverter.RGBAtoHEXA(getRGBA(c, 4))
        };
    }
}