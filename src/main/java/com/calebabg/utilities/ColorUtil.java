package com.calebabg.utilities;

import com.calebabg.core.EngineSettings;
import com.calebabg.core.EngineVariables;

import java.awt.*;
import java.util.List;

public class ColorUtil {
    public static Color fromHex(String hex) {
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16));
    }

    public static String toHex(Color color) {
        return "#" +
                String.format("%02X", color.getRed()) +
                String.format("%02X", color.getGreen()) +
                String.format("%02X", color.getBlue());
    }

    public static String serializeColors(Color[] c) {
        return toHex(c[0]) + EngineSettings.colorsSpliceChar +
                toHex(c[1]) + EngineSettings.colorsSpliceChar +
                toHex(c[2]) + EngineSettings.colorsSpliceChar +
                toHex(c[3]) + EngineSettings.colorsSpliceChar +
                toHex(c[4]);
    }

    public static Color[] deserializeColors(int index, List<String> list) {
        String[] split = list.get(index).split(EngineSettings.colorsSpliceChar);
        return new Color[] {
                fromHex(split[0]),
                fromHex(split[1]),
                fromHex(split[2]),
                fromHex(split[3]),
                fromHex(split[4])
        };
    }

    public static Color randomHSLColor() {
        // Saturation between 0.1 and 0.3
        float saturation = (EngineVariables.random.nextFloat() * 2000 + 1000) / 10000f, luminance = 0.9f;
        return Color.getHSBColor(EngineVariables.random.nextFloat(), saturation, luminance);
    }

    public static Color randomHSLColor(int minSat, int maxSat, float luminance) {
        float saturation = (EngineVariables.random.nextFloat() * maxSat + minSat) / 10000f;
        return Color.getHSBColor(EngineVariables.random.nextFloat(), saturation, luminance);
    }

    public static Color randomRGBColor() {
        return new Color(EngineVariables.random.nextInt(0xFFFFFE));
    }

    // https://stackoverflow.com/questions/596216/formula-to-determine-perceived-brightness-of-rgb-color
    public static float brightness(Color c) {
        return (0.2126f * c.getRed()) + (0.7152f * c.getGreen()) + (0.0722f * c.getBlue());
    }
}