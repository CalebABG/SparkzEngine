package com.calebabg.utilities;

import javax.swing.*;

import static com.calebabg.utilities.HTMLUtil.headingTag;

public class InputUtil {
    private InputUtil(){}

    public static boolean canParseInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean canParseFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static float minValueGuard(float min, float defaultVal, String promptText) {
        JLabel label = new JLabel(promptText);
        label.setFont(FontUtil.PLAIN_18);

        String amount = JOptionPane.showInputDialog(null, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseFloat(amount)) ? Float.parseFloat(amount) : defaultVal;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(null, HTMLUtil.headingTag(3, "Not Allowed, Enter an Amount >= " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : defaultVal;
            }
        }
        return newAmount;
    }

    public static float minValueGuard(float min, float defaultVal, String promptText, JFrame parent) {
        JLabel label = new JLabel(promptText);
        label.setFont(FontUtil.PLAIN_18);

        String amount = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseFloat(amount)) ? Float.parseFloat(amount) : defaultVal;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(parent, HTMLUtil.headingTag(3, "Not Allowed, Enter an Amount >= " + min));
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

    public static int intGuardDefault(int min, int defaultVal, String input) {
        if (input == null || input.isEmpty()) {
            return defaultVal;
        } else {
            if (canParseInt(input)) {
                if (Integer.parseInt(input) >= min) return Integer.parseInt(input);
                else return defaultVal;
            } else return defaultVal;
        }
    }

    public static float floatGuardDefault(float min, float defaultVal, String input) {
        if (input == null || input.isEmpty()) {
            return defaultVal;
        } else {
            if (canParseFloat(input)) {
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
