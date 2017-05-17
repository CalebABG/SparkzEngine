package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ColorEditor;
import com.engine.ParticleTypes.Particle;
import com.engine.Utilities.ColorUtility;
import java.awt.*;

public class SCChoices {
    public static void setPresetColors(Color[] colors) {
        Particle.setThinkingParticlesColor(colors);
        ColorEditor.setBackgroundColor(colors);
    }

    public static void setPresetColors(Color[] array1, Color[] array2) {
        Particle.setThinkingParticlesColor(array1);
        ColorEditor.setBackgroundColor(array2);
    }

    public static Color[] randomColor() {
        Color f = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        Color s = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        Color thr = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        Color fr = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        Color l = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
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
        Color f = ColorUtility.HEXtoRGB("#C4DFE6");
        Color s = ColorUtility.HEXtoRGB("#66A5AD");
        Color thr = ColorUtility.HEXtoRGB("#07575b");
        Color fourth = ColorUtility.HEXtoRGB("#003B46");
        Color l = ColorUtility.HEXtoRGB("#021C1E");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] dayAndNight() {
        Color f = ColorUtility.HEXtoRGB("#E6DF44");
        Color s = ColorUtility.HEXtoRGB("#F0810F");
        Color thr = ColorUtility.HEXtoRGB("#2D4262");
        Color fourth = ColorUtility.HEXtoRGB("#063852");
        Color l = ColorUtility.HEXtoRGB("#011A27");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] plantLife() {
        Color f = ColorUtility.HEXtoRGB("#C6D166");
        Color s = ColorUtility.HEXtoRGB("#739F3D");
        Color thr = ColorUtility.HEXtoRGB("#5C821A");
        Color fourth = ColorUtility.HEXtoRGB("#ffffff");
        Color l = ColorUtility.HEXtoRGB("#0F1B07");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] fancyCoffee() {
        Color f = Color.white;
        Color s = ColorUtility.HEXtoRGB("#F1F3CE");
        Color thr = ColorUtility.HEXtoRGB("#DDBC95");
        Color fourth = ColorUtility.HEXtoRGB("#B38867");
        Color l = ColorUtility.HEXtoRGB("#CDCDC0");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] cinnamonSpice() {
        Color f = ColorUtility.HEXtoRGB("#EBDCB2");
        Color s = ColorUtility.HEXtoRGB("#C9A66B");
        Color thr = ColorUtility.HEXtoRGB("#805A3B");
        Color fourth = ColorUtility.HEXtoRGB("#662E1C");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] citrus() {
        Color f = ColorUtility.HEXtoRGB("#FEF3E2");
        Color s = ColorUtility.HEXtoRGB("#FA4032");
        Color thr = ColorUtility.HEXtoRGB("#FA812F");
        Color fourth = ColorUtility.HEXtoRGB("#FAAF08");
        Color l = ColorUtility.HEXtoRGB("#E73F0B");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] pastels() {
        Color f = ColorUtility.HEXtoRGB("#C1E1DC");
        Color s = ColorUtility.HEXtoRGB("#FFCCAC");
        Color thr = ColorUtility.HEXtoRGB("#FFEB94");
        Color fourth = ColorUtility.HEXtoRGB("#FDD475");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] exoticBerries() {
        Color f = ColorUtility.HEXtoRGB("#C0B2B5");
        Color s = ColorUtility.HEXtoRGB("#F0EFEA");
        Color thr = ColorUtility.HEXtoRGB("#D72C16");
        Color fourth = ColorUtility.HEXtoRGB("#A10115");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] candy() {
        Color f = ColorUtility.HEXtoRGB("#008DCB");
        Color s = ColorUtility.HEXtoRGB("#FFEC5C");
        Color thr = ColorUtility.HEXtoRGB("#E1315B");
        Color fourth = ColorUtility.HEXtoRGB("#F47D4A");
        Color l = ColorUtility.HEXtoRGB("#D24135");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] maritime() {
        Color f = ColorUtility.HEXtoRGB("#FF4447").darker();
        Color s = ColorUtility.HEXtoRGB("#FFFFFF");
        Color thr = ColorUtility.HEXtoRGB("#5EA8A7");
        Color fourth = ColorUtility.HEXtoRGB("#257985");
        Color l = ColorUtility.HEXtoRGB("#113743");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] hazyday() {
        Color f = ColorUtility.HEXtoRGB("#F4EBDb").darker();
        Color s = ColorUtility.HEXtoRGB("#8E9B97");
        Color thr = ColorUtility.HEXtoRGB("#97B8C2");
        Color fourth = ColorUtility.HEXtoRGB("#537072");
        Color l = ColorUtility.HEXtoRGB("#2C4A52");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] retro() {
        Color f = ColorUtility.HEXtoRGB("#B3DBC0");
        Color s = ColorUtility.HEXtoRGB("#FE0000");
        Color thr = ColorUtility.HEXtoRGB("#FDF6F6");
        Color fourth = ColorUtility.HEXtoRGB("#67BACA");
        return new Color[]{f, s, thr, fourth, fourth.darker().darker()};
    }

    public static Color[] furiousfire(){
        Color f = ColorUtility.HEXtoRGB("#e6df44");
        Color s = ColorUtility.HEXtoRGB("#fcaa00");
        Color thr = ColorUtility.HEXtoRGB("#fa6001");
        Color fourth = ColorUtility.HEXtoRGB("#ff3300");
        Color l = ColorUtility.HEXtoRGB("#cc0000");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] onyximpact() {
        Color f = ColorUtility.HEXtoRGB("#0F1F38");
        Color s = ColorUtility.HEXtoRGB("#8E7970");
        Color thr = new Color(15, 0, 0);
        Color fourth = ColorUtility.HEXtoRGB("#42313A");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] shadowslate() {
        Color f = ColorUtility.HEXtoRGB("#262F34");
        Color fourth = new Color(51, 51, 51);
        return new Color[]{f, f.darker(), f.darker().darker(), fourth, fourth.darker()};
    }
}