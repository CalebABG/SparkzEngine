package com.cabg.reactivecolors;

import com.cabg.gui.ReactiveColorsEditor;
import com.cabg.utilities.ColorUtil;

import java.awt.*;

import static com.cabg.verlet.Vec2.clamp;

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

    public static void setPresetColors(Color[] colors) {
        setComponents(colors);
        ReactiveColorsEditor.setLabelColors(colors);
    }

    public static void setPresetColors(Color[] particleColors, Color[] editorColors) {
        setComponents(particleColors);
        ReactiveColorsEditor.setLabelColors(editorColors);
    }

    public static Color[] randomColor() {
        Color f = ColorUtil.randRGBColor();
        Color s = ColorUtil.randRGBColor();
        Color thr = ColorUtil.randRGBColor();
        Color fr = ColorUtil.randRGBColor();
        Color l = ColorUtil.randRGBColor();
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
        Color f = ColorUtil.fromHex("#C4DFE6");
        Color s = ColorUtil.fromHex("#66A5AD");
        Color thr = ColorUtil.fromHex("#07575b");
        Color fourth = ColorUtil.fromHex("#003B46");
        Color l = ColorUtil.fromHex("#021C1E");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] dayAndNight() {
        Color f = ColorUtil.fromHex("#E6DF44");
        Color s = ColorUtil.fromHex("#F0810F");
        Color thr = ColorUtil.fromHex("#2D4262");
        Color fourth = ColorUtil.fromHex("#063852");
        Color l = ColorUtil.fromHex("#011A27");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] plantLife() {
        Color f = ColorUtil.fromHex("#C6D166");
        Color s = ColorUtil.fromHex("#739F3D");
        Color thr = ColorUtil.fromHex("#5C821A");
        Color fourth = ColorUtil.fromHex("#339900");
        Color l = ColorUtil.fromHex("#203513");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] fancyCoffee() {
        Color f = Color.white;
        Color s = ColorUtil.fromHex("#F1F3CE");
        Color thr = ColorUtil.fromHex("#DDBC95");
        Color fourth = ColorUtil.fromHex("#B38867");
        Color l = ColorUtil.fromHex("#CDCDC0");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] cinnamonSpice() {
        Color f = ColorUtil.fromHex("#EBDCB2");
        Color s = ColorUtil.fromHex("#C9A66B");
        Color thr = ColorUtil.fromHex("#805A3B");
        Color fourth = ColorUtil.fromHex("#662E1C");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] citrus() {
        Color f = ColorUtil.fromHex("#FEF3E2");
        Color s = ColorUtil.fromHex("#FA4032");
        Color thr = ColorUtil.fromHex("#FA812F");
        Color fourth = ColorUtil.fromHex("#FAAF08");
        Color l = ColorUtil.fromHex("#E73F0B");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] pastels() {
        Color f = ColorUtil.fromHex("#C1E1DC");
        Color s = ColorUtil.fromHex("#FFCCAC");
        Color thr = ColorUtil.fromHex("#FFEB94");
        Color fourth = ColorUtil.fromHex("#FDD475");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] exoticBerries() {
        Color f = ColorUtil.fromHex("#C0B2B5");
        Color s = ColorUtil.fromHex("#F0EFEA");
        Color thr = ColorUtil.fromHex("#D72C16");
        Color fourth = ColorUtil.fromHex("#A10115");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] candy() {
        Color f = ColorUtil.fromHex("#008DCB");
        Color s = ColorUtil.fromHex("#FFEC5C");
        Color thr = ColorUtil.fromHex("#E1315B");
        Color fourth = ColorUtil.fromHex("#F47D4A");
        Color l = ColorUtil.fromHex("#D24135");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] maritime() {
        Color f = ColorUtil.fromHex("#FF4447").darker();
        Color s = ColorUtil.fromHex("#FFFFFF");
        Color thr = ColorUtil.fromHex("#5EA8A7");
        Color fourth = ColorUtil.fromHex("#257985");
        Color l = ColorUtil.fromHex("#113743");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] hazyDay() {
        Color f = ColorUtil.fromHex("#F4EBDb").darker();
        Color s = ColorUtil.fromHex("#8E9B97");
        Color thr = ColorUtil.fromHex("#97B8C2");
        Color fourth = ColorUtil.fromHex("#537072");
        Color l = ColorUtil.fromHex("#2C4A52");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] retro() {
        Color f = ColorUtil.fromHex("#B3DBC0");
        Color s = ColorUtil.fromHex("#FE0000");
        Color thr = ColorUtil.fromHex("#FDF6F6");
        Color fourth = ColorUtil.fromHex("#67BACA");
        return new Color[]{f, s, thr, fourth, fourth.darker().darker()};
    }

    public static Color[] furiousFire() {
        Color f = ColorUtil.fromHex("#e6df44");
        Color s = ColorUtil.fromHex("#fcaa00");
        Color thr = ColorUtil.fromHex("#fa6001");
        Color fourth = ColorUtil.fromHex("#ff3300");
        Color l = ColorUtil.fromHex("#cc0000");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] onyxImpact() {
        Color f = ColorUtil.fromHex("#0F1F38");
        Color s = ColorUtil.fromHex("#8E7970");
        Color thr = new Color(15, 0, 0);
        Color fourth = ColorUtil.fromHex("#42313A");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] shadowSlate() {
        Color f = ColorUtil.fromHex("#262F34");
        Color fourth = new Color(51, 51, 51);
        return new Color[]{f, f.darker(), f.darker().darker(), fourth, fourth.darker()};
    }
}
