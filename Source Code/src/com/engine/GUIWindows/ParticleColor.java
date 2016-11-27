package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.ThinkingParticles.SCCycle;
import com.engine.ThinkingParticles.SCPicker;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static com.engine.Utilities.ColorConverter.setAlpha;

public class ParticleColor {
    private static ParticleColor thinkingParticlesUI = null;
    public static JFrame frame;
    private static int WIDTH = 774, HEIGHT = 250, cHeight = 20, btHeight = 110;
    public static Font font = new Font("Arial", Font.PLAIN, 17);
    private static Color fgColor = Color.white;
    public static CLabel[] labels = new CLabel[5];
    private static JButton cycleColor;
    private static String changeColor = "Change Color";
    private static String presets = "Presets";
    private static String save = "Save Colors";
    private static String load = "Load Colors";
    private static String defaultColors = "Default Colors";
    private static String randColor = "Random Colors";

    //public static void main(String[] args) {ParticleColor.getInstance();}

    public static ParticleColor getInstance() {
        if (thinkingParticlesUI == null) {thinkingParticlesUI = new ParticleColor();}frame.toFront(); return thinkingParticlesUI;
    }

    private ParticleColor() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Thinking Particles Color Changer");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(220, 220, 220));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent)
        {thinkingParticlesUI = null; frame.dispose();}});
        frame.setLocationRelativeTo(EFrame);

        labels[0] = new CLabel(new Rectangle(10, cHeight, 128, 75), font, fgColor, setAlpha(Particle.thinkingColors[0],255));
        labels[1] = new CLabel(new Rectangle(170, cHeight, 128, 75), font, fgColor, setAlpha(Particle.thinkingColors[1],255));
        labels[2] = new CLabel(new Rectangle(327, cHeight, 128, 75), font, fgColor, setAlpha(Particle.thinkingColors[2],255));
        labels[3] = new CLabel(new Rectangle(478, cHeight, 128, 75), font, fgColor, setAlpha(Particle.thinkingColors[3],255));
        labels[4] = new CLabel(new Rectangle(632, cHeight, 128, 75), font, fgColor, setAlpha(Particle.thinkingColors[4],255));
        addComps(frame, labels[0], labels[1], labels[2], labels[3], labels[4]);

        JButton presetsButton = new JButton(ParticleColor.presets);
        presetsButton.setOpaque(false);
        presetsButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        presetsButton.setBounds(0, (frame.getHeight() - (34*2)), 94, 34);
        presetsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParticlePresets.getInstance();}});
        frame.getContentPane().add(presetsButton);

        JButton randomColors = new JButton(randColor);
        randomColors.setOpaque(false);
        randomColors.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        randomColors.setBounds(92, (frame.getHeight() - (34*2)), 141, 34);
        randomColors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCChoices.setPresetColors(SCChoices.randomColor());
            }
        });
        frame.getContentPane().add(randomColors);

        JButton defaultColorsButton = new JButton(ParticleColor.defaultColors);
        defaultColorsButton.setOpaque(false);
        defaultColorsButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        defaultColorsButton.setBounds(230, (frame.getHeight() - (34*2)), 132, 34);
        defaultColorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SCChoices.setPresetColors(SCChoices.defaultColor());
            }
        });
        frame.getContentPane().add(defaultColorsButton);

        cycleColor = new JButton("Cycle Colors: " + EngineMethods.getCycle());
        cycleColor.setOpaque(false);
        cycleColor.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        cycleColor.setBounds(360, (frame.getHeight() - (34*2)), 166, 34);
        cycleColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cycleColors = EngineMethods.toggle(cycleColors);
                if (cycleColors) {
                    SCCycle.startCycle(); cycleColor.setText("Cycle Colors: On");}
                else {
                    SCCycle.stopCycle(); cycleColor.setText("Cycle Colors: Off");}
            }
        });
        frame.getContentPane().add(cycleColor);

        JButton saveColors = new JButton(save);
        saveColors.setOpaque(false);
        saveColors.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        saveColors.setBounds(524, (frame.getHeight() - (34*2)), 128, 34);
        saveColors.addActionListener(e -> Settings.saveColors());
        frame.getContentPane().add(saveColors);

        JButton loadColors = new JButton(load);
        loadColors.setOpaque(false);
        loadColors.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        loadColors.setBounds(650, (frame.getHeight() - (34*2)), 119, 34);
        loadColors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Settings.doesFileExist()) {
                    LoadPresets.getInstance();}else{SWindow.getInstance("Save First", 280, 85);}
            }
        });
        frame.getContentPane().add(loadColors);

        ////////////////////////////////////////////
        JButton btn2NewButton = new JButton(changeColor);
        btn2NewButton.setOpaque(false);
        btn2NewButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        btn2NewButton.setBounds(10, btHeight, 128, 37);
        btn2NewButton.addActionListener(e -> SCPicker.particleColor1());
        frame.getContentPane().add(btn2NewButton);


        JButton btn3NewButton = new JButton(changeColor);
        btn3NewButton.setOpaque(false);
        btn3NewButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        btn3NewButton.setBounds(170, btHeight, 128, 37);
        btn3NewButton.addActionListener(e -> SCPicker.particleColor2());
        frame.getContentPane().add(btn3NewButton);


        JButton btn4NewButton = new JButton(changeColor);
        btn4NewButton.setOpaque(false);
        btn4NewButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        btn4NewButton.setBounds(327, btHeight, 128, 37);
        btn4NewButton.addActionListener(e -> SCPicker.particleColor3());
        frame.getContentPane().add(btn4NewButton);


        JButton btn5NewButton = new JButton(changeColor);
        btn5NewButton.setOpaque(false);
        btn5NewButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        btn5NewButton.setBounds(478, btHeight, 128, 37);
        btn5NewButton.addActionListener(e -> SCPicker.particleColor4());
        frame.getContentPane().add(btn5NewButton);


        JButton btn6NewButton = new JButton(changeColor);
        btn6NewButton.setForeground(Color.black);
        btn6NewButton.setOpaque(false);
        btn6NewButton.setFont(new Font(Font.SERIF, Font.BOLD, 15));
        btn6NewButton.setBounds(632, btHeight, 128, 37);
        btn6NewButton.addActionListener(e -> SCPicker.particleColor5());
        frame.getContentPane().add(btn6NewButton);
        frame.setVisible(true);
    }

    public static void setBackgroundColor(Color[] colors) {
        labels[0].setBackground(setAlpha(colors[0],255)); labels[1].setBackground(setAlpha(colors[1],255));
        labels[2].setBackground(setAlpha(colors[2],255)); labels[3].setBackground(setAlpha(colors[3],255));
        labels[4].setBackground(setAlpha(colors[4],255));
    }

    public static void addComps(JFrame root, JComponent... components) {for (JComponent comps : components) {root.add(comps);}}
}
