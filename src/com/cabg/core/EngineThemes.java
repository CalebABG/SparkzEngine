package com.cabg.core;

import com.cabg.components.CMenuBar;
import com.cabg.utilities.ColorUtility;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

import static com.cabg.core.EngineVariables.menuBar;

public class EngineThemes {
    public static void defaultTheme() {
        setTheme(new Color(20, 23, 25).brighter(), Color.white);
    }

    public static void midnightBlues() {
        setTheme(ColorUtility.fromHex("#0D1E2E"), Color.white);
    }

    public static void darkMocha() {
        setTheme(ColorUtility.fromHex("#231F1C"), Color.white);
    }

    public static void darkTangerine() {
        setTheme(ColorUtility.fromHex("#722916"), Color.white);
    }

    public static void sienna() {
        setTheme(ColorUtility.fromHex("#3B151E"), Color.white);
    }

    public static void winterGreenDream() {
        setTheme(ColorUtility.fromHex("#1C312C"), Color.white);
    }

    public static void vegasGold() {
        setTheme(ColorUtility.fromHex("#594500"), Color.white);
    }

    public static void roseWood() {
        setTheme(ColorUtility.fromHex("#5A0001"), Color.white);
    }

    public static void antiquePink() {
        setTheme(ColorUtility.fromHex("#962D35"), Color.white);
    }

    public static void nightViolet() {
        setTheme(ColorUtility.fromHex("#1A0F30"), Color.white);
    }

    public static void setTheme(Color backgroundColor, Color foregroundColor) {
        menuBar.bgColor = backgroundColor;
        menuBar.setBorder(BorderFactory.createLineBorder(backgroundColor, 1, false));

        Color borderColor = backgroundColor.darker();
        Border border = BorderFactory.createLineBorder(borderColor);

        for (JMenu menu : CMenuBar.menus) {
            menu.getPopupMenu().setBorder(border);
        }

        for (JMenuItem menuItem : CMenuBar.menuItems) {
            menuItem.setBackground(backgroundColor);
            menuItem.setForeground(foregroundColor);
        }
    }
}
