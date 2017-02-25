package com.engine.GUIWindows;

import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RButton;
import com.engine.JComponents.RLabel;
import com.engine.Utilities.Settings;
import static com.engine.EngineHelpers.EConstants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ParticleSizeSeed {
    private static ParticleSizeSeed particleSizeSeed = null;
    public static JFrame frame;
    public static CTextField minTextField, maxTextField;

    //public static void main(String[] args) {getInstance(0);}

    public static ParticleSizeSeed getInstance(int type) {
        if (particleSizeSeed == null) {
            particleSizeSeed = new ParticleSizeSeed(type);} frame.toFront(); return particleSizeSeed;
    }

    // Type == 0 : singleClickSize; Type == 1 : fireworksSize; Type == 2 : dragSize
    private ParticleSizeSeed(int type) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception x) {x.printStackTrace();}
        frame = new JFrame(setTitle(type));
        frame.setIconImage(Settings.getIcon());
        frame.setSize(402, 215);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.setLocationRelativeTo(OptionsMenu.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        RLabel minSize = new RLabel("Min Size(Integer or Double)", new Font("Tahoma", Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        minSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minSize, minSize.gridBagConstraints);

        minTextField = new CTextField("", new Font("Times", Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        minTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(minTextField, minTextField.gridBagConstraints);

        RLabel maxSize = new RLabel("Max Size(Integer or Double)", new Font("Tahoma", Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 2);
        maxSize.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxSize, maxSize.gridBagConstraints);

        maxTextField = new CTextField("", new Font("Times", Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 3, 0, 10, 0, 0);
        maxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(maxTextField, maxTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font("Tahoma", Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 4, 98, 10, 0, 0);
        ok.addActionListener(e -> handleSeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font("Tahoma", Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 4, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        if (type == 0) return "Single Click Size Seed";
        else if (type == 1) return "Fireworks Size Seed";
        else if (type == 2) return "Drag Size Seed";
        return null;
    }

    private void handleSeed(int type) {
        try {
            if (type == 0) {
                singleClickSizeMinVal = (minTextField.getText() != null && minTextField.getText().length() > 0) ? Double.parseDouble(minTextField.getText()) : singleClickSizeMinVal;
                singleClickSizeMaxVal = (maxTextField.getText() != null && maxTextField.getText().length() > 0) ? Double.parseDouble(maxTextField.getText()) : singleClickSizeMaxVal;
            }
            else if (type == 1) {
                fireworksSizeMinVal = (minTextField.getText() != null && minTextField.getText().length() > 0) ? Double.parseDouble(minTextField.getText()) : fireworksSizeMinVal;
                fireworksSizeMaxVal = (maxTextField.getText() != null && maxTextField.getText().length() > 0) ? Double.parseDouble(maxTextField.getText()) : fireworksSizeMaxVal;
            }
            else if (type == 2) {
                dragSizeMinVal = (minTextField.getText() != null && minTextField.getText().length() > 0) ? Double.parseDouble(minTextField.getText()) : dragSizeMinVal;
                dragSizeMaxVal = (maxTextField.getText() != null && maxTextField.getText().length() > 0) ? Double.parseDouble(maxTextField.getText()) : dragSizeMaxVal;
            }
        } catch (Exception e){EException.append(e);}  finally {close();}
    }

    private void close(){particleSizeSeed = null; frame.dispose();}
}