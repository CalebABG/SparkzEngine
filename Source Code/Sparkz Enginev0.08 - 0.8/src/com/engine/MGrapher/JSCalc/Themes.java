package com.engine.MGrapher.JSCalc;

import com.engine.JComponents.CLabel;
import com.engine.Utilities.ColorUtility;
import javax.swing.*;
import java.awt.*;
import static com.engine.MGrapher.JSCalc.JSCalc.*;

public class Themes {
    public static void defaultTheme(){setTheme(new Color(20, 23, 25).brighter(), Color.white);}
    public static void midnightBlues(){setTheme(ColorUtility.HEXtoRGB("#0D1E2E"), Color.white);}
    public static void darkMocha(){setTheme(ColorUtility.HEXtoRGB("#231F1C"), Color.white);}
    public static void darkTangerine(){setTheme(ColorUtility.HEXtoRGB("#722916"), Color.white);}
    public static void sienna(){setTheme(ColorUtility.HEXtoRGB("#3B151E"), Color.white);}
    public static void winterGreenDream(){setTheme(ColorUtility.HEXtoRGB("#1C312C"), Color.white);}
    public static void vegasGold(){setTheme(ColorUtility.HEXtoRGB("#594500"), Color.white);}
    public static void roseWood(){setTheme(ColorUtility.HEXtoRGB("#5A0001"), Color.white);}
    public static void antiquePink(){setTheme(ColorUtility.HEXtoRGB("#962D35"), Color.white);}
    public static void nightViolet(){setTheme(ColorUtility.HEXtoRGB("#1A0F30"), Color.white);}

    public static void setTheme(Color keyColorbg, Color keyColorfg) {
        UIManager.put("SplitPane.background", keyColorbg.darker());
        UIManager.put("SplitPaneDivider.border", BorderFactory.createLineBorder(keyColorbg, 1));
        textPane.setBackground(keyColorbg);
        resultsPane.setBackground(keyColorbg);
        panel.setBackground(keyColorbg.darker().darker());
        primary_splitpane.setBorder(BorderFactory.createLineBorder(keyColorbg.darker(), 3));
        edits_results.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        results_scrollpane.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        jstexteditor_scrollpane.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        for (CLabel button : buttons) {
            button.setBackground(keyColorbg);
            button.setForeground(keyColorfg);
        }
        menuBar.bgColor = keyColorbg;
        for (int i = 0; i < menuBar.menus.size(); i++) {
            menuBar.menus.get(i).getPopupMenu().setBorder(BorderFactory.createLineBorder(keyColorbg.darker()));
        }
        for (int i = 0; i < menuBar.menuItems.size(); i++) {
            JMenuItem menuItem = menuBar.menuItems.get(i);
            menuItem.setBackground(keyColorbg);
            menuItem.setForeground(keyColorfg);
        }
        menuBar.setBorder(BorderFactory.createLineBorder(keyColorbg, 1, false));
        SwingUtilities.updateComponentTreeUI(frame);
        SwingUtilities.updateComponentTreeUI(menuBar);
    }
}
