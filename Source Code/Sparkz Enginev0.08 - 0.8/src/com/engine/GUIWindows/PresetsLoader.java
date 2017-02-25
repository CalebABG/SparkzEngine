package com.engine.GUIWindows;
import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

import static com.engine.Utilities.ColorConverter.setAlpha;

public class PresetsLoader {
    private static PresetsLoader presetsLoaderUI = null;
    public static JFrame frame;
    private static JSlider colorSlider;
    private static JButton button;
    private static CLabel[] labels = new CLabel[5];
    public static Font font = new Font("Arial", Font.PLAIN, 17);
    private static Color fgColor = Color.white;
    private static int lastIndex = 0;

    //public static void main(String[] args) {getInstance();}

    public static PresetsLoader getInstance() {
        if (presetsLoaderUI == null) {presetsLoaderUI = new PresetsLoader();} frame.toFront(); return presetsLoaderUI;
    }

    private PresetsLoader() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setSize(523, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ColorEditor.frame);
        Settings.loadColors();
        SCChoices.setPresetColors(Settings.convertColors(lastIndex));
        frame.setTitle("You have: " + Settings.presetColors.size() + " Presets :D");

        int offsetX = (64) + 5;
        labels[0] = new CLabel(new Rectangle(              5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[0], 255));
        labels[1] = new CLabel(new Rectangle(    offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[1], 255));
        labels[2] = new CLabel(new Rectangle(2 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[2], 255));
        labels[3] = new CLabel(new Rectangle(3 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[3], 255));
        labels[4] = new CLabel(new Rectangle(4 * offsetX + 5, 10, 64, 45), font, fgColor, setAlpha(Particle.thinkingColors[4], 255));
        addComps(frame, labels[0], labels[1], labels[2], labels[3], labels[4]);

        button = new JButton("<html><body style='color:blue; font-size: 12px'>Refresh Colors</body></html>");
        button.setBounds(350, 15, 160, 40);
        button.setVisible(true);
        button.addActionListener(e -> refreshButton());
        frame.add(button);

        colorSlider = new JSlider(0, ((Settings.PRESET_INDEXES - 1) == 0) ? 0 : (Settings.PRESET_INDEXES - 1), lastIndex);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.setMinorTickSpacing(setMinorTicks());
        colorSlider.setMajorTickSpacing(setMajorTicks());
        colorSlider.setBounds(10, frame.getHeight() - 85, 500, 43);
        colorSlider.addChangeListener(e -> {
            int sliderIndex = colorSlider.getValue();
            lastIndex = sliderIndex;
            SCChoices.setPresetColors(Settings.convertColors(sliderIndex), Particle.thinkingColors);
            setLabelColors(Settings.convertColors(sliderIndex));
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
        labels[0].setBackground(setAlpha(colors[0], 255));
        labels[1].setBackground(setAlpha(colors[1], 255));
        labels[2].setBackground(setAlpha(colors[2], 255));
        labels[3].setBackground(setAlpha(colors[3], 255));
        labels[4].setBackground(setAlpha(colors[4], 255));
    }

    private int setMinorTicks(){
        if (Settings.presetColors == null || Settings.presetColors.size() < 25) return 1;
        else if (Settings.presetColors.size() % 15 == 0) {return Settings.presetColors.size() / 15;}
        else return Settings.presetColors.size() / 25;
    }

    private int setMajorTicks() {
        if (Settings.presetColors == null) return 1;
        else if (Settings.presetColors.size() >= 25) {return Settings.presetColors.size() / 5;}
        return 1;
    }

    private void addComps(JFrame root, JComponent... components) {for (JComponent comps : components) {root.add(comps);}}
    private void close() {presetsLoaderUI = null; frame.dispose();}
}
