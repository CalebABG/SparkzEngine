package com.engine.GUIWindows;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.engine.Utilities.ColorConverter.setAlpha;

public class LoadPresets {
    private static LoadPresets loadPresetsUI = null;
    public static JFrame frame;
    private static JSlider colorSlider;
    private static JButton button;
    private static int offsetX = (64) + 5;
    private static CLabel[] labels = new CLabel[5];
    public static Font font = new Font("Arial", Font.PLAIN, 17);
    private static Color fgColor = Color.white;

    //public static void main(String[] args) {}

    public static LoadPresets getInstance() {
        if (loadPresetsUI == null) {loadPresetsUI = new LoadPresets();}frame.toFront(); return loadPresetsUI;
    }

    private LoadPresets() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setSize(523, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent)
        {loadPresetsUI = null; frame.dispose();}});
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ParticleColor.frame);
        Settings.loadColors();
        SCChoices.setPresetColors(Settings.convertColors(0));
        frame.setTitle("You have: " + Settings.presetColors.size() + " Presets :D");

        labels[0] = new CLabel(new Rectangle(5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[0],255));
        labels[1] = new CLabel(new Rectangle(    offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[1],255));
        labels[2] = new CLabel(new Rectangle(2 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[2],255));
        labels[3] = new CLabel(new Rectangle(3 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[3],255));
        labels[4] = new CLabel(new Rectangle(4 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[4],255));
        addComps(frame, labels[0], labels[1], labels[2], labels[3], labels[4]);

        button = new JButton("<html><body style='color:blue; font-size: 12px'>Refresh Colors</body></html>");
        button.setBounds(350, 15, 160, 40);
        button.setVisible(true);
        button.addActionListener(e -> {if (e.getSource() == button) {refreshButton();}});
        frame.add(button);

        colorSlider = new JSlider(0, ((Settings.PRESET_INDEXES - 1) == 0) ? 0 : (Settings.PRESET_INDEXES - 1), 0);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.setMinorTickSpacing(1);
        colorSlider.setMajorTickSpacing(5);
        colorSlider.setBounds(10, frame.getHeight() - 85, 500, 43);
        colorSlider.addChangeListener(e -> {
            SCChoices.setPresetColors(Settings.convertColors(colorSlider.getValue()), Particle.thinkingColors);
            setLabelColors(Settings.convertColors(colorSlider.getValue()));
        });

        frame.add(colorSlider);
        frame.setVisible(true);
    }

    private void refreshButton() {
        Settings.loadColors();
        colorSlider.setMaximum(Settings.PRESET_INDEXES - 1);
        frame.setTitle("You have: " + Settings.presetColors.size() + " Presets :D");
    }

    private static void setLabelColors(Color[] colors) {
        labels[0].setBackground(setAlpha(colors[0],255)); labels[1].setBackground(setAlpha(colors[1],255));
        labels[2].setBackground(setAlpha(colors[2],255)); labels[3].setBackground(setAlpha(colors[3],255));
        labels[4].setBackground(setAlpha(colors[4],255));
    }

    private void addComps(JFrame root, JComponent... components) {for (JComponent comps : components) {root.add(comps);}}
}
