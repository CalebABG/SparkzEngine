package com.engine.Utilities;

import com.engine.GUIWindows.OptionsMenu;
import com.engine.GUIWindows.ParticleTypeUI;

import javax.swing.*;

import static com.engine.Utilities.H5Wrapper.H;

public class InputWrapper {
    public static boolean canParseStringInt(String input) {try {int n = Integer.parseInt(input); return true;}catch (NumberFormatException e){return false;}}
    public static boolean canParseStringDouble(String input) {try {float n = ((float) Double.parseDouble(input)); return true;}catch (NumberFormatException e){return false;}}

    public static double minValueGuard(double min, double default_val, String promptText) {
        String amount = JOptionPane.showInputDialog(OptionsMenu.frame, H(3, promptText),null,JOptionPane.PLAIN_MESSAGE);
        double newAmount = (amount != null && amount.length() > 0 && canParseStringInt(amount)) ? Double.parseDouble(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {amount = JOptionPane.showInputDialog(OptionsMenu.frame,
                H(3, "Not Allowed, Enter an Amount > " + min));
            newAmount = (amount != null && amount.length() > 0) ? Double.parseDouble(amount) : default_val;}}
        return newAmount;
    }

    public static double minValueGuard(double min, double default_val, String promptText, JFrame parent) {
        String amount = JOptionPane.showInputDialog(parent, H(3, promptText),null,JOptionPane.PLAIN_MESSAGE);
        double newAmount = (amount != null && amount.length() > 0 && canParseStringInt(amount)) ? Double.parseDouble(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {amount = JOptionPane.showInputDialog(parent,
                    H(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Double.parseDouble(amount) : default_val;}}
        return newAmount;
    }

    public static String valueGuardString(int mode, ParticleTypeUI ui, String default_val, String promptText) {
        if (mode == 0) {return default_val;}
        else {String amount = JOptionPane.showInputDialog(ui.frame, H(3, promptText),null,JOptionPane.PLAIN_MESSAGE);
            return (amount != null && amount.length() > 0) ? amount : default_val;}
    }

    public static int intTextfieldGuard(int min, int max, int default_val, String input) {
        if (input == null || input.isEmpty()) {return default_val;}
        else {
            if (canParseStringInt(input)) {if (Integer.parseInt(input) <= max && Integer.parseInt(input) >= min) {return Integer.parseInt(input);} else {return default_val;}}
            else {return default_val;}
        }
    }

    public static int intTextfieldGuardDefault(int min, int default_val, String input) {
        if (input == null || input.isEmpty()) {
            return default_val;
        }
        else {
            if (canParseStringInt(input)) {if (Integer.parseInt(input) >= min) {return Integer.parseInt(input);} else {return default_val;}}
            else {return default_val;}
        }
    }

    public static double doubleTextfieldGuardDefault(double min, double default_val, String input) {
        if (input == null || input.isEmpty()) {return default_val;}
        else {
            if (canParseStringDouble(input)) {if (Double.parseDouble(input) >= min) {return Double.parseDouble(input);} else {return default_val;}}
            else {return default_val;}
        }
    }

    public static boolean boolTextfieldGuard(boolean default_val, String input) {
        if (input == null || input.isEmpty()) {return default_val;}
        else {return Settings.StoBool(input, default_val);}
    }
}
