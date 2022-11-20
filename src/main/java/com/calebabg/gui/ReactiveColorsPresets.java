package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.reactivity.ReactiveColors;
import com.calebabg.utilities.ColorUtil;

import javax.swing.*;
import java.awt.*;

public class ReactiveColorsPresets {
    private static ReactiveColorsPresets instance = null;

    public static final Color[] PROPANE = propaneColor();
    public static final Color[] MARITIME = maritime();
    public static final Color[] DAY_AND_NIGHT = dayAndNight();
    public static final Color[] PLANT_LIFE = plantLife();
    public static final Color[] COFFEE = coffee();
    public static final Color[] CINNAMON = cinnamon();
    public static final Color[] PASTELS = pastels();
    public static final Color[] EXOTIC_BERRY = exoticBerry();
    public static final Color[] CITRUS = citrus();
    public static final Color[] CANDY = candy();
    public static final Color[] OCEAN = ocean();
    public static final Color[] HAZY_DAY = hazyDay();
    public static final Color[] RETRO = retro();
    public static final Color[] SHADOW = shadow();
    public static final Color[] ONYX = onyx();

    private final JFrame frame;

    public static void getInstance() {
        if (instance == null) instance = new ReactiveColorsPresets();
        instance.frame.toFront();
    }

    private ReactiveColorsPresets() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("ReactiveColor Presets");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(323, 290);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ReactiveColorsEditor.frame);

        JButton[] presetButtons = new JButton[15];
        presetButtons[0] = new JButton("Propane");
        presetButtons[0].setBounds(10, 11, 89, 39);
        presetButtons[0].addActionListener(e -> setColors(PROPANE));
        frame.add(presetButtons[0]);

        presetButtons[1] = new JButton("Maritime");
        presetButtons[1].setBounds(219, 11, 89, 39);
        presetButtons[1].addActionListener(e -> setColors(MARITIME));
        frame.add(presetButtons[1]);

        presetButtons[2] = new JButton("Day & Night");
        presetButtons[2].setBounds(109, 11, 100, 39);
        presetButtons[2].addActionListener(e -> setColors(DAY_AND_NIGHT));
        frame.add(presetButtons[2]);

        presetButtons[3] = new JButton("Plant Life");
        presetButtons[3].setBounds(10, 61, 89, 39);
        presetButtons[3].addActionListener(e -> setColors(PLANT_LIFE));
        frame.add(presetButtons[3]);

        presetButtons[4] = new JButton("Coffee");
        presetButtons[4].setBounds(109, 61, 100, 39);
        presetButtons[4].addActionListener(e -> setColors(COFFEE));
        frame.add(presetButtons[4]);

        presetButtons[5] = new JButton("Cinnamon");
        presetButtons[5].setBounds(219, 61, 89, 39);
        presetButtons[5].addActionListener(e -> setColors(CINNAMON));
        frame.add(presetButtons[5]);

        presetButtons[6] = new JButton("Pastels");
        presetButtons[6].setBounds(10, 111, 89, 39);
        presetButtons[6].addActionListener(e -> setColors(PASTELS));
        frame.add(presetButtons[6]);

        presetButtons[7] = new JButton("Exotic Berry");
        presetButtons[7].setBounds(109, 111, 100, 39);
        presetButtons[7].addActionListener(e -> setColors(EXOTIC_BERRY));
        frame.add(presetButtons[7]);

        presetButtons[8] = new JButton("Citrus");
        presetButtons[8].setBounds(219, 111, 89, 39);
        presetButtons[8].addActionListener(e -> setColors(CITRUS));
        frame.add(presetButtons[8]);

        presetButtons[9] = new JButton("Candy");
        presetButtons[9].setBounds(10, 161, 89, 39);
        presetButtons[9].addActionListener(e -> setColors(CANDY));
        frame.add(presetButtons[9]);

        presetButtons[10] = new JButton("Ocean");
        presetButtons[10].setBounds(109, 161, 100, 39);
        presetButtons[10].addActionListener(e -> setColors(OCEAN));
        frame.add(presetButtons[10]);

        presetButtons[11] = new JButton("Hazy Day");
        presetButtons[11].setBounds(219, 161, 89, 39);
        presetButtons[11].addActionListener(e -> setColors(HAZY_DAY));
        frame.add(presetButtons[11]);

        presetButtons[12] = new JButton("Retro");
        presetButtons[12].setBounds(10, 211, 89, 39);
        presetButtons[12].addActionListener(e -> setColors(RETRO));
        frame.add(presetButtons[12]);

        presetButtons[13] = new JButton("Shadow");
        presetButtons[13].setBounds(109, 211, 100, 39);
        presetButtons[13].addActionListener(e -> setColors(SHADOW));
        frame.add(presetButtons[13]);

        presetButtons[14] = new JButton("Onyx");
        presetButtons[14].setBounds(219, 211, 89, 39);
        presetButtons[14].addActionListener(e -> setColors(ONYX));
        frame.add(presetButtons[14]);

        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
        instance = null;
    }

    public static void setColors(Color[] colors) {
        ReactiveColors.setColors(colors);
        ReactiveColorsEditor.setColors(colors);
    }

    public static void setColors(Color[] particleColors, Color[] editorColors) {
        ReactiveColors.setColors(particleColors);
        ReactiveColorsEditor.setColors(editorColors);
    }

    public static Color[] defaultColor() {
        return candy();
    }

    public static Color[] randomColor() {
        Color f = ColorUtil.randomRGBColor();
        Color s = ColorUtil.randomRGBColor();
        Color thr = ColorUtil.randomRGBColor();
        Color fr = ColorUtil.randomRGBColor();
        Color l = ColorUtil.randomRGBColor();
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

    public static Color[] ocean() {
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

    public static Color[] coffee() {
        Color f = Color.white;
        Color s = ColorUtil.fromHex("#F1F3CE");
        Color thr = ColorUtil.fromHex("#DDBC95");
        Color fourth = ColorUtil.fromHex("#B38867");
        Color l = ColorUtil.fromHex("#CDCDC0");
        return new Color[]{f, s, thr, fourth, l};
    }

    public static Color[] cinnamon() {
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

    public static Color[] exoticBerry() {
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

    public static Color[] onyx() {
        Color f = ColorUtil.fromHex("#0F1F38");
        Color s = ColorUtil.fromHex("#8E7970");
        Color thr = new Color(15, 0, 0);
        Color fourth = ColorUtil.fromHex("#42313A");
        return new Color[]{f, s, thr, fourth, fourth.darker()};
    }

    public static Color[] shadow() {
        Color f = ColorUtil.fromHex("#262F34");
        Color fourth = new Color(51, 51, 51);
        return new Color[]{f, f.darker(), f.darker().darker(), fourth, fourth.darker()};
    }
}
