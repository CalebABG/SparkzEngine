package com.cabg.gui;

import com.cabg.core.EngineSettings;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.components.CLabel;
import com.cabg.reactivecolors.ReactiveColors;
import com.cabg.utilities.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.cabg.core.EngineVariables.eFrame;
import static com.cabg.utilities.HTMLUtil.HeadingWithStyleCenteredTag;

public class ReactiveColorsTimeMachine {
    public static ReactiveColorsTimeMachine timeMachine = null;
    public static JFrame frame;
    public static int index = 0;
    public static final String title = "Color Time Machine";
    private static final Font font = new Font(Font.SERIF, Font.BOLD, 13);
    public static CLabel[] labels = new CLabel[5];
    public static JToggleButton colors_info;
    public static List<String> colorList = new ArrayList<>();

    public static void getInstance() {
        if (timeMachine == null) timeMachine = new ReactiveColorsTimeMachine();
        frame.toFront();
    }

    private ReactiveColorsTimeMachine() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(title + " - Colors Seen: " + colorList.size());
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(688, 206);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(ReactiveColorsEditor.frame == null ? eFrame : ReactiveColorsEditor.frame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel container = new JPanel();
        scrollPane.setViewportView(container);
        container.setLayout(new BorderLayout(0, 0));

        JPanel buttons_panel = new JPanel();
        container.add(buttons_panel, BorderLayout.SOUTH);

        JButton presets = new JButton("Save Colors");
        presets.addActionListener(e -> saveColors());
        presets.setFont(font);
        buttons_panel.add(presets);

        JButton set_colors = new JButton("Set Colors");
        set_colors.setFont(font);
        set_colors.addActionListener(e -> setColors());
        buttons_panel.add(set_colors);

        colors_info = new JToggleButton("Show Values");
        colors_info.setFont(font);
        colors_info.addItemListener(e -> showColorValues());
        buttons_panel.add(colors_info);

        JButton last = new JButton("Last Color");
        last.addActionListener(e -> colorPager(false));
        last.setFont(font);
        buttons_panel.add(last);

        JButton next = new JButton("Next Color");
        next.setFont(font);
        next.addActionListener(e -> colorPager(true));
        buttons_panel.add(next);

        JButton clear = new JButton("Clear Colors");
        clear.setFont(font);
        clear.addActionListener(e -> clearColors());
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

        updateLabelsBackgroundColors();

        frame.setVisible(true);
    }

    private void setColors() {
        if (colorList.size() > 0) ReactiveColors.setPresetColors(ColorUtil.convertColors(index, colorList));
    }

    private void colorPager(boolean advance) {
        handleButtons(advance);
        if (colors_info.isSelected()) updateColorValues();
    }

    private void showColorValues() {
        if (colors_info.isSelected()) updateColorValues();
        else for (CLabel label : labels) label.setText("");
    }

    private void clearColors() {
        String msg = HeadingWithStyleCenteredTag("h4", "Clear All Seen Colors?");
        Object[] options = {"Yes, please", "No, cancel!"};
        int n = JOptionPane.showOptionDialog(frame, msg, "Clear Colors", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
        if (n == JOptionPane.YES_OPTION) {
            colorList.clear();
            index = 0;
            updateLabelsBackgroundColors();
            if (colors_info.isSelected()) updateColorValues();
            frame.setTitle(title + " - Colors Seen: " + colorList.size());
        }
    }

    private void saveColors() {
        if (colorList == null || colorList.size() < 1) EngineSettings.saveColors(null);
        else EngineSettings.saveColors(ColorUtil.serializeColors(ColorUtil.convertColors(index, colorList)));
    }

    public static void updateColorValues() {
        for (CLabel label : labels) {
            Color labelBGColor = label.getBackground();
            String labelBGText = label.getBGColorText();

            if (ColorUtil.isDark(labelBGColor) >= 50) {
                label.setText(labelBGText);
                label.setForeground(Color.black);
            } else {
                label.setText(labelBGText);
                label.setForeground(Color.white);
            }
        }
    }

    private static void handleButtons(boolean advance) {
        if (colorList.size() > 0) {
            if (advance) {
                if (++index >= colorList.size()) index = colorList.size() - 1;
            }
            else {
                if (--index <= 0) index = 0;
            }

            frame.setTitle(title + " - Colors Seen: " + colorList.size() + " - Index: " + index);
            setLabelsBackgroundColor(ColorUtil.convertColors(index, colorList));
        }
    }

    public static void addColor(Color[] colors) {
        colorList.add(ColorUtil.serializeColors(colors));
        index = colorList.size() - 1;
        if (timeMachine != null && labels != null) {
            updateLabelsBackgroundColors();
            frame.setTitle(title + " - Colors Seen: " + colorList.size());
        }
    }

    private static void setLabelsBackgroundColor(Color[] colors) {
        labels[0].setBackground(colors[0]);
        labels[1].setBackground(colors[1]);
        labels[2].setBackground(colors[2]);
        labels[3].setBackground(colors[3]);
        labels[4].setBackground(colors[4]);
    }

    private static void updateLabelsBackgroundColors() {
        if (colorList.size() > 0) setLabelsBackgroundColor(ColorUtil.convertColors(index, colorList));
        else setLabelsBackgroundColor(ReactiveColors.getComponents());
    }

    private void close() {
        frame.dispose();
        timeMachine = null;
    }
}
