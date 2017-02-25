package com.engine.GUIWindows;

import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RButton;
import com.engine.JComponents.RLabel;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.*;

public class ParticleSpeedSeed {
    private static ParticleSpeedSeed particleSpeedSeed = null;
    public static JFrame frame;
    public static CTextField speedTextField;

    //public static void main(String[] args) {getInstance(0);}

    public static ParticleSpeedSeed getInstance(int type) {
        if (particleSpeedSeed == null) {particleSpeedSeed = new ParticleSpeedSeed(type);} frame.toFront(); return particleSpeedSeed;
    }

    private ParticleSpeedSeed(int type) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception x) {x.printStackTrace();}
        frame = new JFrame(setTitle(type));
        frame.setIconImage(Settings.getIcon());
        frame.setSize(402, 145);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.setLocationRelativeTo(OptionsMenu.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 102, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        RLabel speedLabel = new RLabel("Speed(Integer or Double)", new Font("Tahoma", Font.BOLD, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, 8, 0, 0);
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedLabel, speedLabel.gridBagConstraints);

        speedTextField = new CTextField("", new Font("Tahoma", Font.PLAIN, 17), 8, GridBagConstraints.BOTH, 0, 1, 0, 10, 0, 0);
        speedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(speedTextField, speedTextField.gridBagConstraints);

        RButton ok = new RButton("Ok", new Font("Tahoma", Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 0, 2, 98, 10, .9, .9);
        ok.addActionListener(e -> handleSeed(type));
        panel.add(ok, ok.gridBagConstraints);

        RButton cancel = new RButton("Cancel", new Font("Tahoma", Font.BOLD, 14), 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.SOUTH, 4, 2, 98, 10, 0, 0);
        cancel.addActionListener(e -> close());
        panel.add(cancel, cancel.gridBagConstraints);
        frame.setVisible(true);
    }

    private static String setTitle(int type) {
        if (type == 0) return "Single Click Speed Seed";
        else if (type == 1) return "Fireworks Speed Seed";
        else if (type == 2) return "Drag Size Speed Seed";
        return null;
    }

    private void handleSeed(int type) {
        try {
            if (type == 0) {
                singleClickSpeedVal = (speedTextField.getText() != null && speedTextField.getText().length() > 0) ? Double.parseDouble(speedTextField.getText()) : singleClickSpeedVal;
            }
            else if (type == 1) {
                fireworksSpeedVal = (speedTextField.getText() != null && speedTextField.getText().length() > 0) ? Double.parseDouble(speedTextField.getText()) : fireworksSpeedVal;
            }
            else if (type == 2) {
                dragSpeedVal = (speedTextField.getText() != null && speedTextField.getText().length() > 0) ? Double.parseDouble(speedTextField.getText()) : dragSpeedVal;
            }
        } catch (Exception e){EException.append(e);}  finally {close();}
    }

    private void close(){particleSpeedSeed = null; frame.dispose();}
}
