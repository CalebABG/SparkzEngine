package com.cabg.gui;

import com.cabg.components.CLabel;
import com.cabg.core.EngineSettings;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.reactivecolors.ReactiveColors;
import com.cabg.reactivecolors.ReactiveColorsRandomizer;

import javax.swing.*;
import java.awt.*;

import static com.cabg.core.EngineVariables.EFrame;
import static com.cabg.core.EngineVariables.engineSettings;

public class ReactiveColorsEditor {
    private static ReactiveColorsEditor reactiveColorsEditor = null;
    public static JFrame frame;
    public Font font = new Font(Font.SERIF, Font.PLAIN, 18);
    public Font uiFont = new Font(Font.SERIF, Font.BOLD, 16);
    public static CLabel[] labels = new CLabel[5];

    public static void getInstance() {
        if (reactiveColorsEditor == null) reactiveColorsEditor = new ReactiveColorsEditor();
        frame.toFront();
    }

    private ReactiveColorsEditor() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        frame = new JFrame("Reactive Colors Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(950, 250);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));
        scrollPane.setViewportView(container);

        JPanel buttons_panel = new JPanel();
        container.add(buttons_panel, BorderLayout.SOUTH);

        JButton presets = new JButton("Presets");
        presets.setFont(uiFont);
        presets.addActionListener(e -> ReactiveColorsPresets.getInstance());
        buttons_panel.add(presets);

        JButton random_colors = new JButton("Random");
        random_colors.setFont(uiFont);
        random_colors.addActionListener(e -> setRandomColors());
        buttons_panel.add(random_colors);

        JButton default_colors = new JButton("Default");
        default_colors.setFont(uiFont);
        default_colors.addActionListener(e -> ReactiveColors.setPresetColors(ReactiveColors.defaultColor()));
        buttons_panel.add(default_colors);

        JButton cycle_colors = new JButton("Cycle: " + (engineSettings.cycleReactiveColors ? "On" : "Off"));
        cycle_colors.setFont(uiFont);
        cycle_colors.addActionListener(e -> handleColorCycle(cycle_colors));
        buttons_panel.add(cycle_colors);

        JButton save_colors = new JButton("Save");
        save_colors.setFont(uiFont);
        save_colors.addActionListener(e -> EngineSettings.saveColors(null));
        buttons_panel.add(save_colors);

        JButton load_colors = new JButton("Load");
        load_colors.setFont(uiFont);
        load_colors.addActionListener(e -> loadColors());
        buttons_panel.add(load_colors);

        JButton time_machine = new JButton("Time Machine");
        time_machine.addActionListener(e -> ReactiveColorsTimeMachine.getInstance());
        time_machine.setFont(uiFont);
        buttons_panel.add(time_machine);

        JPanel colors_panel = new JPanel();
        container.add(colors_panel, BorderLayout.CENTER);

        JPanel color_1_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_2_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_3_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_4_panel = new JPanel(new BorderLayout(0, 0));
        JPanel color_5_panel = new JPanel(new BorderLayout(0, 0));

        GroupLayout gl_colors_panel = new GroupLayout(colors_panel);
        gl_colors_panel.setHorizontalGroup(
                gl_colors_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_colors_panel.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(color_1_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_2_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_3_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_4_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED) //.addGap(18)
                                .addComponent(color_5_panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_colors_panel.setVerticalGroup(
                gl_colors_panel.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(gl_colors_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_colors_panel.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(color_5_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_4_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_3_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_2_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(color_1_panel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                                .addContainerGap())
        );

        JButton color_5_button = new JButton("Change Color");
        color_5_button.setFont(uiFont);
        color_5_button.addActionListener(e -> pickColor(4));
        color_5_panel.add(color_5_button, BorderLayout.SOUTH);

        JPanel color_5_inner_panel = new JPanel();
        color_5_panel.add(color_5_inner_panel, BorderLayout.CENTER);
        color_5_inner_panel.setLayout(new BorderLayout(0, 0));

        Color fgColor = Color.white;
        labels[4] = new CLabel(font, fgColor, ReactiveColors.getComponent(4));
        labels[4].setHorizontalAlignment(SwingConstants.CENTER);
        color_5_inner_panel.add(labels[4], BorderLayout.CENTER);

        JButton color_4_button = new JButton("Change Color");
        color_4_button.setFont(uiFont);
        color_4_button.addActionListener(e -> pickColor(3));
        color_4_panel.add(color_4_button, BorderLayout.SOUTH);

        JPanel color_4_inner_panel = new JPanel();
        color_4_panel.add(color_4_inner_panel, BorderLayout.CENTER);
        color_4_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[3] = new CLabel(font, fgColor, ReactiveColors.getComponent(3));
        labels[3].setHorizontalAlignment(SwingConstants.CENTER);
        color_4_inner_panel.add(labels[3], BorderLayout.CENTER);

        JButton color_3_button = new JButton("Change Color");
        color_3_button.setFont(uiFont);
        color_3_button.addActionListener(e -> pickColor(2));
        color_3_panel.add(color_3_button, BorderLayout.SOUTH);

        JPanel color_3_inner_panel = new JPanel();
        color_3_panel.add(color_3_inner_panel, BorderLayout.CENTER);
        color_3_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[2] = new CLabel(font, fgColor, ReactiveColors.getComponent(2));
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        color_3_inner_panel.add(labels[2], BorderLayout.CENTER);

        JButton color_2_button = new JButton("Change Color");
        color_2_button.setFont(uiFont);
        color_2_button.addActionListener(e -> pickColor(1));
        color_2_panel.add(color_2_button, BorderLayout.SOUTH);

        JPanel color_2_inner_panel = new JPanel();
        color_2_panel.add(color_2_inner_panel, BorderLayout.CENTER);
        color_2_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[1] = new CLabel(font, fgColor, ReactiveColors.getComponent(1));
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        color_2_inner_panel.add(labels[1], BorderLayout.CENTER);

        JButton color_1_button = new JButton("Change Color");
        color_1_button.setFont(uiFont);
        color_1_button.addActionListener(e -> pickColor(0));
        color_1_panel.add(color_1_button, BorderLayout.SOUTH);

        JPanel color_1_inner_panel = new JPanel();
        color_1_panel.add(color_1_inner_panel, BorderLayout.CENTER);
        color_1_inner_panel.setLayout(new BorderLayout(0, 0));

        labels[0] = new CLabel(font, fgColor, ReactiveColors.getComponent(0));
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        color_1_inner_panel.add(labels[0], BorderLayout.CENTER);
        colors_panel.setLayout(gl_colors_panel);

        frame.setVisible(true);
    }

    public static void pickColor(int index) {
        pickColor(ReactiveColorsEditor.frame, index, "Color " + (index + 1));
    }

    public static void pickColor(JFrame parent, int index, String text) {
        Color color = JColorChooser.showDialog(parent, text, null);
        if (color != null) ReactiveColors.setComponent(index, color);
        ReactiveColorsEditor.setLabelReactiveBackgroundColor(index);
    }

    private void loadColors() {
        if (EngineSettings.colorsFileExists()) {
            ReactiveColorsLoader.getInstance();
        } else {
            JOptionPane.showConfirmDialog(frame, "<html><h3>Save a Color First</h3></html>", "No Colors Saved", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setRandomColors() {
        Color[] colors = ReactiveColors.randomColor();

        ReactiveColors.setPresetColors(colors);
        ReactiveColorsTimeMachine.addColor(colors);

        if (ReactiveColorsTimeMachine.timeMachine != null && ReactiveColorsTimeMachine.colors_info.isSelected())
            ReactiveColorsTimeMachine.updateColorValues();
    }

    public void handleColorCycle(JButton btnCycleColors) {
        engineSettings.toggleCycleReactiveColors();

        if (engineSettings.cycleReactiveColors) {
            ReactiveColorsRandomizer.startCycle();
            btnCycleColors.setText("Cycle: On");
        } else {
            ReactiveColorsRandomizer.stopCycle();
            btnCycleColors.setText("Cycle: Off");
        }
    }

    public static void setLabelColors(Color[] colors) {
        labels[0].setBackground(colors[0]);
        labels[1].setBackground(colors[1]);
        labels[2].setBackground(colors[2]);
        labels[3].setBackground(colors[3]);
        labels[4].setBackground(colors[4]);
    }

    public static void setLabelReactiveBackgroundColor(int index) {
        labels[index].setBackground(ReactiveColors.getComponents()[index]);
    }

    public void close() {
        frame.dispose();
        reactiveColorsEditor = null;
    }
}
