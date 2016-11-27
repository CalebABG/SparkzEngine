package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ParticleColor;
import com.engine.ParticleTypes.Particle;
import com.engine.Utilities.ColorConverter;
import java.awt.*;

public class SCChoices {
    public static void setPresetColors(Color[] colors) {
        Particle.setThinkingParticlesColor(colors);
        ParticleColor.setBackgroundColor(colors);
    }

    public static void setPresetColors(Color[] array1, Color[] array2) {
        Particle.setThinkingParticlesColor(array1);
        ParticleColor.setBackgroundColor(array2);
    }

    public static Color[] randomColor() {
        Color f = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Color s = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Color thr = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Color fr = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Color l = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        return new Color[]{f, s, thr, fr, l};
    }

    public static Color[] propaneColor() {
        Color f = new Color(204, 204, 255);
        Color s = Color.yellow;
        Color thr = Color.red;
        Color fourth = Color.blue;
        Color l = new Color(0, 0, 51);
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] defaultColor() {return furiousfire();}

    public static Color[] oceanBlues() {
        Color f = ColorConverter.HEXtoRGB("#C4DFE6");
        Color s = ColorConverter.HEXtoRGB("#66A5AD");
        Color thr = ColorConverter.HEXtoRGB("#07575b");
        Color fourth = ColorConverter.HEXtoRGB("#003B46");
        Color l = ColorConverter.HEXtoRGB("#021C1E");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] dayAndNight() {
        Color f = ColorConverter.HEXtoRGB("#E6DF44");
        Color s = ColorConverter.HEXtoRGB("#F0810F");
        Color thr = ColorConverter.HEXtoRGB("#2D4262");
        Color fourth = ColorConverter.HEXtoRGB("#063852");
        Color l = ColorConverter.HEXtoRGB("#011A27");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] plantLife() {
        Color f = ColorConverter.HEXtoRGB("#C6D166");
        Color s = ColorConverter.HEXtoRGB("#739F3D");
        Color thr = ColorConverter.HEXtoRGB("#5C821A");
        Color fourth = ColorConverter.HEXtoRGB("#ffffff");
        Color l = ColorConverter.HEXtoRGB("#0F1B07");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] fancyCoffee() {
        Color f = Color.white;
        Color s = ColorConverter.HEXtoRGB("#F1F3CE");
        Color thr = ColorConverter.HEXtoRGB("#DDBC95");
        Color fourth = ColorConverter.HEXtoRGB("#B38867");
        Color l = ColorConverter.HEXtoRGB("#CDCDC0");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] cinnamonSpice() {
        Color f = ColorConverter.HEXtoRGB("#EBDCB2");
        Color s = ColorConverter.HEXtoRGB("#C9A66B");
        Color thr = ColorConverter.HEXtoRGB("#805A3B");
        Color fourth = ColorConverter.HEXtoRGB("#662E1C");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] citrus() {
        Color f = ColorConverter.HEXtoRGB("#FEF3E2");
        Color s = ColorConverter.HEXtoRGB("#FA4032");
        Color thr = ColorConverter.HEXtoRGB("#FA812F");
        Color fourth = ColorConverter.HEXtoRGB("#FAAF08");
        Color l = ColorConverter.HEXtoRGB("#E73F0B");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] pastels() {
        Color f = ColorConverter.HEXtoRGB("#C1E1DC");
        Color s = ColorConverter.HEXtoRGB("#FFCCAC");
        Color thr = ColorConverter.HEXtoRGB("#FFEB94");
        Color fourth = ColorConverter.HEXtoRGB("#FDD475");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] exoticBerries() {
        Color f = ColorConverter.HEXtoRGB("#C0B2B5");
        Color s = ColorConverter.HEXtoRGB("#F0EFEA");
        Color thr = ColorConverter.HEXtoRGB("#D72C16");
        Color fourth = ColorConverter.HEXtoRGB("#A10115");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] candy() {
        Color f = ColorConverter.HEXtoRGB("#008DCB");
        Color s = ColorConverter.HEXtoRGB("#FFEC5C");
        Color thr = ColorConverter.HEXtoRGB("#E1315B");
        Color fourth = ColorConverter.HEXtoRGB("#F47D4A");
        Color l = ColorConverter.HEXtoRGB("#D24135");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] maritime() {
        Color f = ColorConverter.HEXtoRGB("#FF4447").darker();
        Color s = ColorConverter.HEXtoRGB("#FFFFFF");
        Color thr = ColorConverter.HEXtoRGB("#5EA8A7");
        Color fourth = ColorConverter.HEXtoRGB("#257985");
        Color l = ColorConverter.HEXtoRGB("#113743");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] hazyday() {
        Color f = ColorConverter.HEXtoRGB("#F4EBDb").darker();
        Color s = ColorConverter.HEXtoRGB("#8E9B97");
        Color thr = ColorConverter.HEXtoRGB("#97B8C2");
        Color fourth = ColorConverter.HEXtoRGB("#537072");
        Color l = ColorConverter.HEXtoRGB("#2C4A52");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] retro() {
        Color f = ColorConverter.HEXtoRGB("#B3DBC0");
        Color s = ColorConverter.HEXtoRGB("#FE0000");
        Color thr = ColorConverter.HEXtoRGB("#FDF6F6");
        Color fourth = ColorConverter.HEXtoRGB("#67BACA");
        return new Color[]{f, s, thr, fourth, fourth.darker().darker()};
    }

    public static Color[] furiousfire(){
        Color f = ColorConverter.HEXtoRGB("#e6df44");
        Color s = ColorConverter.HEXtoRGB("#fcaa00");
        Color thr = ColorConverter.HEXtoRGB("#fa6001");
        Color fourth = ColorConverter.HEXtoRGB("#ff3300");
        Color l = ColorConverter.HEXtoRGB("#cc0000");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] onyximpact() {
        Color f = ColorConverter.HEXtoRGB("#0F1F38");
        Color s = ColorConverter.HEXtoRGB("#8E7970");
        Color thr = new Color(15, 0, 0);
        Color fourth = ColorConverter.HEXtoRGB("#42313A");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] shadowslate() {
        Color f = ColorConverter.HEXtoRGB("#262F34");
        Color fourth = new Color(51, 51, 51);
        return new Color[]{f, f.darker(), f.darker().darker(), fourth, fourth.darker()};
    }
}