package com.cabg.reactivecolors;

import com.cabg.gui.ReactiveColorsEditor;
import com.cabg.utilities.ColorUtility;

import java.awt.*;

import static com.cabg.verlet.Vect2.clamp;

public class ReactiveColors {
    private static Color[] components = defaultColor();

    private ReactiveColors() {}

    public static Color getReactiveComponent(float velocity) {
        return components[(int) clamp(velocity / 1.5f, 0, components.length - 1)];
    }

    public static Color[] getComponents() {
        return components;
    }

    public static void setComponents(Color[] comps) {
        components = comps;
    }

    public static Color getComponent(int index) {
        return components[index];
    }

    public static void setComponent(int index, Color color) {
        components[index] = color;
    }

    /* ------------------------------------------------------------------------------------ */

    public static void setPresetColors(Color[] colors) {
        setComponents(colors);
        ReactiveColorsEditor.setLabelColors(colors);
    }

    public static void setPresetColors(Color[] particleColors, Color[] editorColors) {
        setComponents(particleColors);
        ReactiveColorsEditor.setLabelColors(editorColors);
    }

    public static Color[] randomColor() {
        Color f = ColorUtility.randRGBColor();
        Color s = ColorUtility.randRGBColor();
        Color thr = ColorUtility.randRGBColor();
        Color fr = ColorUtility.randRGBColor();
        Color l = ColorUtility.randRGBColor();
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

    public static Color[] defaultColor() {
        return furiousFire();
    }

    public static Color[] oceanBlues() {
        Color f = ColorUtility.fromHex("#C4DFE6");
        Color s = ColorUtility.fromHex("#66A5AD");
        Color thr = ColorUtility.fromHex("#07575b");
        Color fourth = ColorUtility.fromHex("#003B46");
        Color l = ColorUtility.fromHex("#021C1E");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] dayAndNight() {
        Color f = ColorUtility.fromHex("#E6DF44");
        Color s = ColorUtility.fromHex("#F0810F");
        Color thr = ColorUtility.fromHex("#2D4262");
        Color fourth = ColorUtility.fromHex("#063852");
        Color l = ColorUtility.fromHex("#011A27");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] plantLife() {
        Color f = ColorUtility.fromHex("#C6D166");
        Color s = ColorUtility.fromHex("#739F3D");
        Color thr = ColorUtility.fromHex("#5C821A");
        Color fourth = ColorUtility.fromHex("#339900");
        Color l = ColorUtility.fromHex("#203513");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] fancyCoffee() {
        Color f = Color.white;
        Color s = ColorUtility.fromHex("#F1F3CE");
        Color thr = ColorUtility.fromHex("#DDBC95");
        Color fourth = ColorUtility.fromHex("#B38867");
        Color l = ColorUtility.fromHex("#CDCDC0");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] cinnamonSpice() {
        Color f = ColorUtility.fromHex("#EBDCB2");
        Color s = ColorUtility.fromHex("#C9A66B");
        Color thr = ColorUtility.fromHex("#805A3B");
        Color fourth = ColorUtility.fromHex("#662E1C");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] citrus() {
        Color f = ColorUtility.fromHex("#FEF3E2");
        Color s = ColorUtility.fromHex("#FA4032");
        Color thr = ColorUtility.fromHex("#FA812F");
        Color fourth = ColorUtility.fromHex("#FAAF08");
        Color l = ColorUtility.fromHex("#E73F0B");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] pastels() {
        Color f = ColorUtility.fromHex("#C1E1DC");
        Color s = ColorUtility.fromHex("#FFCCAC");
        Color thr = ColorUtility.fromHex("#FFEB94");
        Color fourth = ColorUtility.fromHex("#FDD475");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] exoticBerries() {
        Color f = ColorUtility.fromHex("#C0B2B5");
        Color s = ColorUtility.fromHex("#F0EFEA");
        Color thr = ColorUtility.fromHex("#D72C16");
        Color fourth = ColorUtility.fromHex("#A10115");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] candy() {
        Color f = ColorUtility.fromHex("#008DCB");
        Color s = ColorUtility.fromHex("#FFEC5C");
        Color thr = ColorUtility.fromHex("#E1315B");
        Color fourth = ColorUtility.fromHex("#F47D4A");
        Color l = ColorUtility.fromHex("#D24135");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] maritime() {
        Color f = ColorUtility.fromHex("#FF4447").darker();
        Color s = ColorUtility.fromHex("#FFFFFF");
        Color thr = ColorUtility.fromHex("#5EA8A7");
        Color fourth = ColorUtility.fromHex("#257985");
        Color l = ColorUtility.fromHex("#113743");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] hazyDay() {
        Color f = ColorUtility.fromHex("#F4EBDb").darker();
        Color s = ColorUtility.fromHex("#8E9B97");
        Color thr = ColorUtility.fromHex("#97B8C2");
        Color fourth = ColorUtility.fromHex("#537072");
        Color l = ColorUtility.fromHex("#2C4A52");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] retro() {
        Color f = ColorUtility.fromHex("#B3DBC0");
        Color s = ColorUtility.fromHex("#FE0000");
        Color thr = ColorUtility.fromHex("#FDF6F6");
        Color fourth = ColorUtility.fromHex("#67BACA");
        return new Color[]{f, s, thr, fourth, fourth.darker().darker()};
    }

    public static Color[] furiousFire() {
        Color f = ColorUtility.fromHex("#e6df44");
        Color s = ColorUtility.fromHex("#fcaa00");
        Color thr = ColorUtility.fromHex("#fa6001");
        Color fourth = ColorUtility.fromHex("#ff3300");
        Color l = ColorUtility.fromHex("#cc0000");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] onyxImpact() {
        Color f = ColorUtility.fromHex("#0F1F38");
        Color s = ColorUtility.fromHex("#8E7970");
        Color thr = new Color(15, 0, 0);
        Color fourth = ColorUtility.fromHex("#42313A");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] shadowSlate() {
        Color f = ColorUtility.fromHex("#262F34");
        Color fourth = new Color(51, 51, 51);
        return new Color[]{f, f.darker(), f.darker().darker(), fourth, fourth.darker()};
    }
}
