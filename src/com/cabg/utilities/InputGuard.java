package com.cabg.utilities;

import com.cabg.gui.OptionsMenu;
import com.cabg.gui.ParticleTypePicker;

import javax.swing.*;
import java.awt.*;

import static com.cabg.utilities.HTMLUtil.HeadingTag;

public class InputGuard {
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

    public static float minValueGuard(float min, float default_val, String promptText) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(OptionsMenu.frame, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(OptionsMenu.frame, HeadingTag(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : default_val;
            }
        }
        return newAmount;
    }

    public static float minValueGuard(float min, float default_val, String promptText, JFrame parent) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(parent, HeadingTag(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : default_val;
            }
        }
        return newAmount;
    }

    public static String valueGuardString(int mode, ParticleTypePicker ui, String default_val, String promptText) {
        if (mode == 0) {
            return default_val;
        } else {
            String amount = JOptionPane.showInputDialog(ui.frame, promptText, null, JOptionPane.PLAIN_MESSAGE);
            return (amount != null && amount.length() > 0) ? amount : default_val;
        }
    }

    public static int intTextFieldGuardDefault(int min, int default_val, String input) {
        if (input == null || input.isEmpty()) return default_val;
        else {
            if (canParseStringInt(input)) {
                if (Integer.parseInt(input) >= min) return Integer.parseInt(input);
                else return default_val;
            } else return default_val;
        }
    }

    public static float floatTextFieldGuardDefault(float min, float default_val, String input) {
        if (input == null || input.isEmpty()) return default_val;
        else {
            if (canParseStringFloat(input)) {
                if (Float.parseFloat(input) >= min) return Float.parseFloat(input);
                else return default_val;
            } else return default_val;
        }
    }

    public static boolean stringNotNullOrEmpty(String str) {
        return (str != null && str.length() > 0);
    }
}
