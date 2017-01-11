package com.engine.GUIWindows;

import com.engine.JComponents.CLabel;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.PresetHolder;
import com.engine.Utilities.ColorConverter;
import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;
import static com.engine.Utilities.ColorConverter.setAlpha;
import static com.engine.Utilities.H5Wrapper.HCenter;
import static com.engine.Utilities.H5Wrapper.HStyle;
import static java.awt.SystemColor.text;

public class ColorTimeMachine {
    private static ColorTimeMachine timeMachine = null;
    public static JFrame frame;
    public static int index = 0, counter = 0;
    public static String title = "Color Time Machine";
    public static Font font = new Font("Tahoma", Font.BOLD, 13);
    public static CLabel[] labels = new CLabel[5];
    public static List<PresetHolder> colorList = Collections.synchronizedList(new ArrayList<>());

    //public static void main(String[] args) {getInstance();}

    public static ColorTimeMachine getInstance() {
        if (timeMachine == null) {timeMachine = new ColorTimeMachine();} frame.toFront(); return timeMachine;
    }

    private ColorTimeMachine() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame(title + " - Colors Seen: " + colorList.size());
        frame.setIconImage(Settings.getIcon());
        frame.setSize(575, 206);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(ParticleColor.frame);

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

        //Uncomment if desire to get information about colors
        /*JButton time_machine = new JButton("Colors Info");
        time_machine.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttons_panel.add(time_machine);*/

        JButton last = new JButton("Last Color");
        last.addActionListener(e -> {
            if (colorList.size() > 0) {
                index--;
                if (index <= 0) index = 0;
                setBackgroundColor(colorList.get(index).colors);
            }
        });
        last.setFont(new Font("Tahoma", Font.BOLD, 13));
        buttons_panel.add(last);

        JButton next = new JButton("Next Color");
        next.setFont(new Font("Tahoma", Font.BOLD, 13));
        next.addActionListener(e -> {
            if (colorList.size() > 0) {
                index++;
                if (index >= colorList.size()) index = colorList.size() - 1;
                setBackgroundColor(colorList.get(index).colors);
            }
        });
        buttons_panel.add(next);

        JButton clear = new JButton("Clear Colors");
        clear.setFont(new Font("Tahoma", Font.BOLD, 13));
        clear.addActionListener(e -> {
            String msg = HCenter("h4", "Confirm Clear?");
            Object[] options = {"Yes, please", "No, cancel!"};
            int n = JOptionPane.showOptionDialog(frame, msg, "Clear Colors", 0, 3, null, options, options[1]);
            if (n == JOptionPane.YES_OPTION) {
                colorList.clear(); index = 0; setColors();
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
                                .addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
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

    public static void addColor(Color[] colors) {
        colorList.add(new PresetHolder(colors));
        index = colorList.size() - 1;
        if (timeMachine != null && labels != null) {
            setColors();
            frame.setTitle(title + " - Colors Seen: " + colorList.size());
        }
    }

    private void close(){timeMachine = null; frame.dispose();}

    public static void setBackgroundColor(Color[] colors) {
        labels[0].setBackground(setAlpha(colors[0], 255));
        labels[1].setBackground(setAlpha(colors[1], 255));
        labels[2].setBackground(setAlpha(colors[2], 255));
        labels[3].setBackground(setAlpha(colors[3], 255));
        labels[4].setBackground(setAlpha(colors[4], 255));
    }

    private static void setColors() {
        if (colorList.size() > 0) {
            setBackgroundColor(colorList.get(index).colors);
        }
        else setBackgroundColor(Particle.thinkingColors);
    }
}
