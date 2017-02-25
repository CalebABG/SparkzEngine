package com.engine.GUIWindows;

import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.PresetHolder;
import com.engine.ThinkingParticles.SCChoices;
import com.engine.Utilities.ColorConverter;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import static com.engine.Utilities.ColorConverter.setAlpha;
import static com.engine.Utilities.H5Wrapper.HCenter;

public class ColorTimeMachine {
    private static ColorTimeMachine timeMachine = null;
    public static JFrame frame;
    public static int index = 0;
    public static String title = "Color Time Machine";
    public static Font font = new Font("Tahoma", Font.BOLD, 13);
    public static CLabel[] labels = new CLabel[5];
    public static JToggleButton colors_info;
    public static List<PresetHolder> colorList = Collections.synchronizedList(new ArrayList<>());

    //public static void main(String[] args) {getInstance();}

    public static ColorTimeMachine getInstance() {
        if (timeMachine == null) {timeMachine = new ColorTimeMachine();} frame.toFront(); return timeMachine;
    }

    public static boolean isNull(){return timeMachine == null;}

    private ColorTimeMachine() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame(title + " - Colors Seen: " + colorList.size());
        frame.setIconImage(Settings.getIcon());
        frame.setSize(688, 206);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(ColorEditor.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel container = new JPanel();
        scrollPane.setViewportView(container);
        container.setLayout(new BorderLayout(0, 0));

        JPanel buttons_panel = new JPanel();
        container.add(buttons_panel, BorderLayout.SOUTH);

        JButton presets = new JButton("Save Colors");
        presets.addActionListener(e -> {
            if (colorList == null || colorList.size() <= 0) {
                Settings.saveColors(0, null);
            }
            else Settings.saveColors(1, ColorConverter.getThinkingParticlesStrings(colorList.get(index).colors));
        });
        presets.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttons_panel.add(presets);

        JButton set_colors = new JButton("Set Colors");
        set_colors.setFont(new Font("Tahoma", Font.BOLD, 13));
        set_colors.addActionListener(e -> {
            if (colorList.size() > 0) SCChoices.setPresetColors(colorList.get(index).colors);
        });
        buttons_panel.add(set_colors);

        colors_info = new JToggleButton("Show Values");
        colors_info.setFont(new Font("Tahoma", Font.BOLD, 13));
        colors_info.addItemListener(e -> {
            if (colors_info.isSelected()) {
                updateColorValues();
            }
            else{for (int i = 0; i < labels.length; i++) {labels[i].setText("");}}
        });
        buttons_panel.add(colors_info);

        JButton last = new JButton("Last Color");
        last.addActionListener(e -> {
            handleButtons(0);
            if (colors_info.isSelected()) updateColorValues();
        });
        last.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttons_panel.add(last);

        JButton next = new JButton("Next Color");
        next.setFont(new Font("Tahoma", Font.BOLD, 13));
        next.addActionListener(e -> {
            handleButtons(1);
            if (colors_info.isSelected()) updateColorValues();
        });
        buttons_panel.add(next);

        JButton clear = new JButton("Clear Colors");
        clear.setFont(new Font("Tahoma", Font.BOLD, 13));
        clear.addActionListener(e -> {
            String msg = HCenter("h4", "Clear All Seen Colors?");
            Object[] options = {"Yes, please", "No, cancel!"};
            int n = JOptionPane.showOptionDialog(frame, msg, "Clear Colors", 0, 3, null, options, options[1]);
            if (n == JOptionPane.YES_OPTION) {
                colorList.clear(); index = 0; setColors();
                if (colors_info.isSelected()) updateColorValues();
                frame.setTitle(title + " - Colors Seen: " + colorList.size());
            }
        });
        buttons_panel.add(clear);

        JPanel panel = new JPanel();
        container.add(panel, BorderLayout.CENTER);

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        JPanel panel_4 = new JPanel();
        JPanel panel_5 = new JPanel();

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(GroupLayout.Alignment.LEADING, gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(panel_5, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel_1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                                .addContainerGap())
        );
        panel_5.setLayout(new BorderLayout(0, 0));

        labels[4] = new CLabel(font, null, null);
        labels[4].setHorizontalAlignment(SwingConstants.CENTER);
        panel_5.add(labels[4], BorderLayout.CENTER);
        panel_4.setLayout(new BorderLayout(0, 0));

        labels[3] = new CLabel(font, null, null);
        labels[3].setHorizontalAlignment(SwingConstants.CENTER);
        panel_4.add(labels[3], BorderLayout.CENTER);
        panel_3.setLayout(new BorderLayout(0, 0));

        labels[2] = new CLabel(font, null, null);
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(labels[2], BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        labels[1] = new CLabel(font, null, null);
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(labels[1], BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        labels[0] = new CLabel(font, null, null);
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(labels[0], BorderLayout.CENTER);
        panel.setLayout(gl_panel);

        setColors();

        frame.setVisible(true);
    }

    public static void updateColorValues() {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].getBackground().equals(Color.black)){
                labels[i].setText(labels[i].getBGColor());
                labels[i].setForeground(Color.white);
            }
            else if (labels[i].getBackground().equals(Color.white)){
                labels[i].setText(labels[i].getBGColor());
                labels[i].setForeground(Color.black);
            }
            else{
                labels[i].setText(labels[i].getBGColor());
                labels[i].setForeground(Color.black);
            }
        }
    }

    public static void handleButtons(int mode) {
        //Last color
        if (mode == 0) {
            if (colorList.size() > 0) {
                index--;
                if (index <= 0) index = 0;
                frame.setTitle(title + " - Colors Seen: " + colorList.size() + " - Index: " + index);
                setBackgroundColor(colorList.get(index).colors);
            }
        }
        //Next color
        else {
            if (colorList.size() > 0) {
                index++;
                if (index >= colorList.size()) index = colorList.size() - 1;
                frame.setTitle(title + " - Colors Seen: " + colorList.size() + " - Index: " + index);
                setBackgroundColor(colorList.get(index).colors);
            }
        }
    }

    public static void addColor(Color[] colors) {
        colorList.add(new PresetHolder(colors));
        index = colorList.size() - 1;
        if (timeMachine != null && labels != null) {setColors(); frame.setTitle(title + " - Colors Seen: " + colorList.size());}
    }

    public static void setBackgroundColor(Color[] colors) {
        labels[0].setBackground(setAlpha(colors[0], 255)); labels[1].setBackground(setAlpha(colors[1], 255));
        labels[2].setBackground(setAlpha(colors[2], 255)); labels[3].setBackground(setAlpha(colors[3], 255));
        labels[4].setBackground(setAlpha(colors[4], 255));
    }

    private static void setColors() {
        if (colorList.size() > 0) setBackgroundColor(colorList.get(index).colors);
        else setBackgroundColor(Particle.thinkingColors);
    }

    private void close(){timeMachine = null; frame.dispose();}
}
