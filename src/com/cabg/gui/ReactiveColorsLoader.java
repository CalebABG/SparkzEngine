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

public class ReactiveColorsLoader {
    private static ReactiveColorsLoader colorsLoaderUI = null;
    public static JFrame frame;
    private static int lastIndex = 0;
    private static JSlider colorSlider;
    private static final CLabel[] labels = new CLabel[5];

    public static void getInstance() {
        if (colorsLoaderUI == null) colorsLoaderUI = new ReactiveColorsLoader();
        frame.toFront();
    }

    private ReactiveColorsLoader() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame();
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(523, 155);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ReactiveColorsEditor.frame);

        EngineSettings.loadColors();
        ReactiveColors.setPresetColors(ColorUtil.convertColors(lastIndex, EngineSettings.savedReactiveColors));
        frame.setTitle("You have " + EngineSettings.savedReactiveColors.size() + " Saved Colors!");

        int offsetX = 69;
        Color fgColor = Color.white;
        Font font = new Font(Font.SERIF, Font.PLAIN, 17);
        labels[0] = new CLabel(new Rectangle(5, 10, 64, 45), font, fgColor, ReactiveColors.getComponent(0));
        labels[1] = new CLabel(new Rectangle(offsetX + 5, 10, 64, 45), font, fgColor, ReactiveColors.getComponent(1));
        labels[2] = new CLabel(new Rectangle(2 * offsetX + 5, 10, 64, 45), font, fgColor, ReactiveColors.getComponent(2));
        labels[3] = new CLabel(new Rectangle(3 * offsetX + 5, 10, 64, 45), font, fgColor, ReactiveColors.getComponent(3));
        labels[4] = new CLabel(new Rectangle(4 * offsetX + 5, 10, 64, 45), font, fgColor, ReactiveColors.getComponent(4));
        addComps(frame, labels[0], labels[1], labels[2], labels[3], labels[4]);

        JButton button = new JButton("<html><body style='font-size: 12px'>Refresh Colors</body></html>");
        button.setBounds(350, 15, 160, 40);
        button.setVisible(true);
        button.addActionListener(e -> refreshButton());
        frame.add(button);

        colorSlider = new JSlider(0, EngineSettings.savedReactiveColors.size() - 1, lastIndex);
        colorSlider.setPaintTicks(true);
        colorSlider.setPaintLabels(true);
        colorSlider.setMinorTickSpacing(setMinorTicks());
        colorSlider.setMajorTickSpacing(setMajorTicks());
        colorSlider.setBounds(10, frame.getHeight() - 85, 500, 43);
        colorSlider.addChangeListener(e -> colorSliderChangeColor());

        frame.add(colorSlider);
        frame.setVisible(true);
    }

    private void colorSliderChangeColor() {
        int sliderIndex = colorSlider.getValue();
        lastIndex = sliderIndex;

        Color[] selectedColors = ColorUtil.convertColors(sliderIndex, EngineSettings.savedReactiveColors);
        ReactiveColors.setPresetColors(selectedColors, selectedColors);
        setLabelColors(selectedColors);
    }

    private void refreshButton() {
        EngineSettings.loadColors();
        colorSlider.setMaximum(EngineSettings.savedReactiveColors.size() - 1);
        frame.setTitle("You have " + EngineSettings.savedReactiveColors.size() + " Saved Colors!");
    }

    private static void setLabelColors(Color[] colors) {
        labels[0].setBackground(colors[0]);
        labels[1].setBackground(colors[1]);
        labels[2].setBackground(colors[2]);
        labels[3].setBackground(colors[3]);
        labels[4].setBackground(colors[4]);
    }

    private int setMinorTicks() {
        if (EngineSettings.savedReactiveColors.size() < 25) return 1;
        else if (EngineSettings.savedReactiveColors.size() % 15 == 0) {
            return EngineSettings.savedReactiveColors.size() / 15;
        } else return EngineSettings.savedReactiveColors.size() / 25;
    }

    private int setMajorTicks() {
        if (EngineSettings.savedReactiveColors.size() >= 25) return EngineSettings.savedReactiveColors.size() / 5;
        return 1;
    }

    private void addComps(JFrame root, JComponent... components) {
        for (JComponent comps : components) root.add(comps);
    }

    private void close() {
        frame.dispose();
        colorsLoaderUI = null;
    }
}
