package com.engine.ParticleHelpers;

import static com.engine.EngineHelpers.EConstants.*;
import javax.swing.*;

public class ParticleSeed {
    public static void singleClickSizeSeed() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception x){x.getCause();}

        JTextField min = new JTextField();
        JTextField max = new JTextField();

        Object[] ops = {
                "<html><h3> Min Size (integer or decimal):  </h3></html>", min,
                "<html><h3> Max Size (integer or decimal):  </h3></html>", max
        };

        int option = JOptionPane.showConfirmDialog(EFrame, ops, "Single Click Particle Seed", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            double minval = (min.getText() != null && min.getText().length() > 0) ? Double.parseDouble(min.getText()) : singleClickSizeMinVal;
            double maxval = (max.getText() != null && max.getText().length() > 0) ? Double.parseDouble(max.getText()) : singleClickSizeMaxVal;

            singleClickSizeMinVal = minval;
            singleClickSizeMaxVal = maxval;
        }
    }

    public static void fireworksSizeSeed() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.getCause();}

        JTextField min = new JTextField();
        JTextField max = new JTextField();

        Object[] ops = {
                "<html><h3> Min Size (integer or decimal):  </h3></html>", min,
                "<html><h3> Max Size (integer or decimal):  </h3></html>", max
        };

        int option = JOptionPane.showConfirmDialog(EFrame, ops, "Fireworks Particle Seed", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            double minval = (min.getText() != null && min.getText().length() > 0) ? Double.parseDouble(min.getText()) : fireworksSizeMinVal;
            double maxval = (max.getText() != null && max.getText().length() > 0) ? Double.parseDouble(max.getText()) : fireworksSizeMaxVal;

            fireworksSizeMinVal = minval;
            fireworksSizeMaxVal = maxval;
        }
    }

    public static void dragSizeSeed() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception x){x.getCause();}

        JTextField min = new JTextField();
        JTextField max = new JTextField();

        Object[] ops = {
                "<html><h3> Min Size (integer or decimal):  </h3></html>", min,
                "<html><h3> Max Size (integer or decimal):  </h3></html>", max
        };

        int option = JOptionPane.showConfirmDialog(EFrame, ops, "Drag Particle Seed", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            double minval = (min.getText() != null && min.getText().length() > 0) ? Double.parseDouble(min.getText()) : dragSizeMinVal;
            double maxval = (max.getText() != null && max.getText().length() > 0) ? Double.parseDouble(max.getText()) : dragSizeMaxVal;

            dragSizeMinVal = minval;
            dragSizeMaxVal = maxval;
        }
    }

    public static void singleClickSpeedSeed() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception x){x.getCause();}

        String option = JOptionPane.showInputDialog(EFrame, "<html><h3> Speed (integer or decimal):  </h3></html>");
        singleClickSpeedVal = (option != null && option.length() > 0) ? Double.parseDouble(option) : singleClickSpeedVal;
    }

    public static void fireworksSpeedSeed() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception x){x.getCause();}

        String option = JOptionPane.showInputDialog(EFrame, "<html><h3> Speed (integer or decimal):  </h3></html>");
        fireworksSpeedVal = (option != null && option.length() > 0) ? Double.parseDouble(option) : fireworksSpeedVal;
    }

    public static void dragSpeedSeed() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception x){x.getCause();}

        String option = JOptionPane.showInputDialog(EFrame, "<html><h3> Speed (integer or decimal):  </h3></html>");
        dragSpeedVal = (option != null && option.length() > 0) ? Double.parseDouble(option) : dragSpeedVal;
    }
}