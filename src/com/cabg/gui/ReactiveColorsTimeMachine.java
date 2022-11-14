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
    public static ReactiveColorsTimeMachine instance = null;

    private static final String title = "Color Time Machine";
    private static final Font font = new Font(Font.SERIF, Font.BOLD, 13);

    private static int index = 0;
    private static JFrame frame;
    public static JToggleButton colorInfoBtn;
    private static final CLabel[] colorLabels = new CLabel[5];
    public static List<String> serializedColorsList = new ArrayList<>();

    public static void getInstance() {
        if (instance == null) instance = new ReactiveColorsTimeMachine();
        frame.toFront();
    }

    private ReactiveColorsTimeMachine() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame(title + " - Colors Seen: " + serializedColorsList.size());
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(795, 205);
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
        set_colors.addActionListener(e -> setReactiveColors());
        buttons_panel.add(set_colors);

        colorInfoBtn = new JToggleButton("Show/Hide Values");
        colorInfoBtn.setFont(font);
        colorInfoBtn.addItemListener(e -> showColorValues());
        buttons_panel.add(colorInfoBtn);

        JButton last = new JButton("Last Color");
        last.addActionListener(e -> changeColors(false));
        last.setFont(font);
        buttons_panel.add(last);

        JButton next = new JButton("Next Color");
        next.setFont(font);
        next.addActionListener(e -> changeColors(true));
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

        colorLabels[4] = new CLabel(font, null, null);
        colorLabels[4].setHorizontalAlignment(SwingConstants.CENTER);
        panel_5.add(colorLabels[4], BorderLayout.CENTER);
        panel_4.setLayout(new BorderLayout(0, 0));

        colorLabels[3] = new CLabel(font, null, null);
        colorLabels[3].setHorizontalAlignment(SwingConstants.CENTER);
        panel_4.add(colorLabels[3], BorderLayout.CENTER);
        panel_3.setLayout(new BorderLayout(0, 0));

        colorLabels[2] = new CLabel(font, null, null);
        colorLabels[2].setHorizontalAlignment(SwingConstants.CENTER);
        panel_3.add(colorLabels[2], BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        colorLabels[1] = new CLabel(font, null, null);
        colorLabels[1].setHorizontalAlignment(SwingConstants.CENTER);
        panel_2.add(colorLabels[1], BorderLayout.CENTER);
        panel_1.setLayout(new BorderLayout(0, 0));

        colorLabels[0] = new CLabel(font, null, null);
        colorLabels[0].setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(colorLabels[0], BorderLayout.CENTER);
        panel.setLayout(gl_panel);

        updateLabelsBackgroundColors();

        frame.setVisible(true);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }

    private void setReactiveColors() {
        if (serializedColorsList.size() > 0)
            ReactiveColorsPresets.setColors(ColorUtil.deserializeColors(index, serializedColorsList));
    }

    private void changeColors(boolean advance) {
        if (advance) {
            if (++index >= serializedColorsList.size()) index = serializedColorsList.size() - 1;
        }
        else {
            if (--index <= 0) index = 0;
        }

        updateComponents();
    }

    private void showColorValues() {
        if (colorInfoBtn.isSelected()) updateLabelsTextColor();
        else for (CLabel label : colorLabels) label.setText("");
    }

    private void clearColors() {
        String msg = HeadingWithStyleCenteredTag("h4", "Clear All Seen Colors?");
        Object[] options = {"Yes, please", "No, cancel!"};

        int opt = JOptionPane.showOptionDialog(frame, msg, "Clear Colors", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (opt != JOptionPane.YES_OPTION) return;

        index = 0;
        serializedColorsList.clear();
        updateComponents();
    }

    private void saveColors() {
        if (serializedColorsList == null || serializedColorsList.size() < 1)
            EngineSettings.saveColors(null);
        else
            EngineSettings.saveColors(ColorUtil.serializeColors(ColorUtil.deserializeColors(index, serializedColorsList)));
    }

    public static void addColors(Color[] colors) {
        serializedColorsList.add(ColorUtil.serializeColors(colors));
        index = serializedColorsList.size() - 1;
        if (instance != null) updateComponents();
    }

    private static void updateComponents() {
        updateTitle();
        updateLabelsBackgroundColors();
        updateLabelsTextColor();
    }

    private static void updateTitle() {
        frame.setTitle(title + " - Colors Seen: " + serializedColorsList.size());
    }

    private static void updateLabelsTextColor() {
        if (!colorInfoBtn.isSelected()) return;

        for (CLabel label : colorLabels) {
            Color labelBGColor = label.getBackground();
            String backgroundRGBText = label.getBackgroundRGBText();

            if (ColorUtil.brightness(labelBGColor) < 130) {
                label.setText(backgroundRGBText);
                label.setForeground(Color.white);
            } else {
                label.setText(backgroundRGBText);
                label.setForeground(Color.black);
            }
        }
    }

    private static void updateLabelsBackgroundColors() {
        if (serializedColorsList.size() > 0)
            setLabelsBackgroundColor(ColorUtil.deserializeColors(index, serializedColorsList));
        else
            setLabelsBackgroundColor(ReactiveColors.getColors());
    }

    private static void setLabelsBackgroundColor(Color[] colors) {
        for (int i = 0; i < colorLabels.length; i++)
            colorLabels[i].setBackground(colors[i]);
    }
}
