package com.cabg.utilities;

import com.cabg.gui.OptionsMenu;

import javax.swing.*;
import java.awt.*;

import static com.cabg.utilities.HTMLUtil.HeadingTag;

public class InputUtil {
    public static boolean canParseStringInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean canParseStringFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static float minValueGuard(float min, float defaultVal, String promptText) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(OptionsMenu.frame, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : defaultVal;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(OptionsMenu.frame, HeadingTag(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : defaultVal;
            }
        }
        return newAmount;
    }

    public static float minValueGuard(float min, float defaultVal, String promptText, JFrame parent) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : defaultVal;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(parent, HeadingTag(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : defaultVal;
            }
        }
        return newAmount;
    }

    public static String valueGuardString(int mode, JFrame frame, String defaultVal, String promptText) {
        if (mode == 0) {
            return defaultVal;
        } else {
            String amount = JOptionPane.showInputDialog(frame, promptText, null, JOptionPane.PLAIN_MESSAGE);
            return (amount != null && amount.length() > 0) ? amount : defaultVal;
        }
    }

    public static int intTextFieldGuardDefault(int min, int defaultVal, String input) {
        if (input == null || input.isEmpty()) {
            return defaultVal;
        } else {
            if (canParseStringInt(input)) {
                if (Integer.parseInt(input) >= min) return Integer.parseInt(input);
                else return defaultVal;
            } else return defaultVal;
        }
    }

    public static float floatTextFieldGuardDefault(float min, float defaultVal, String input) {
        if (input == null || input.isEmpty()) {
            return defaultVal;
        } else {
            if (canParseStringFloat(input)) {
                return (Float.parseFloat(input) >= min) ? Float.parseFloat(input) : defaultVal;
            } else {
                return defaultVal;
            }
        }
    }

    public static boolean stringNotNullOrEmpty(String str) {
        return (str != null && str.length() > 0);
    }
}
