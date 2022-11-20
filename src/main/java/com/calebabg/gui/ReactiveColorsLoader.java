package com.calebabg.gui;

import com.calebabg.core.EngineSettings;
import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CLabel;
import com.calebabg.reactivity.ReactiveColors;
import com.calebabg.utilities.ColorUtil;
import com.calebabg.utilities.FontUtil;

import javax.swing.*;
import java.awt.*;

public class ReactiveColorsLoader {
    private static ReactiveColorsLoader instance = null;

    private static JFrame frame;
    private static int index = 0;
    private static JSlider colorSlider;
    private static final CLabel[] labels = new CLabel[5];

    public static void getInstance() {
        if (instance == null) instance = new ReactiveColorsLoader();
        frame.toFront();
    }

    private ReactiveColorsLoader() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame();
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(523, 155);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(ReactiveColorsEditor.frame);

        EngineSettings.loadColors();
        ReactiveColorsPresets.setColors(ColorUtil.deserializeColors(index, EngineSettings.savedReactiveColors));
        frame.setTitle("You have " + EngineSettings.savedReactiveColors.size() + " Saved Colors!");

        int offsetX = 69;
        Color fgColor = Color.white;
        labels[0] = new CLabel(new Rectangle(5, 10, 64, 45), FontUtil.BOLD_16, fgColor, ReactiveColors.getColor(0));
        labels[1] = new CLabel(new Rectangle(offsetX + 5, 10, 64, 45), FontUtil.BOLD_16, fgColor, ReactiveColors.getColor(1));
        labels[2] = new CLabel(new Rectangle(2 * offsetX + 5, 10, 64, 45), FontUtil.BOLD_16, fgColor, ReactiveColors.getColor(2));
        labels[3] = new CLabel(new Rectangle(3 * offsetX + 5, 10, 64, 45), FontUtil.BOLD_16, fgColor, ReactiveColors.getColor(3));
        labels[4] = new CLabel(new Rectangle(4 * offsetX + 5, 10, 64, 45), FontUtil.BOLD_16, fgColor, ReactiveColors.getColor(4));

        addComps(frame, labels);

        JButton button = new JButton("<html><body style='font-size: 12px'>Refresh Colors</body></html>");
        button.setBounds(350, 15, 160, 40);
        button.setVisible(true);
        button.addActionListener(e -> refreshButton());
        frame.add(button);

        colorSlider = new JSlider(0, EngineSettings.savedReactiveColors.size() - 1, index);
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
        index = sliderIndex;

        Color[] selectedColors = ColorUtil.deserializeColors(sliderIndex, EngineSettings.savedReactiveColors);
        ReactiveColorsPresets.setColors(selectedColors, selectedColors);
        setLabelColors(selectedColors);
    }

    private void refreshButton() {
        EngineSettings.loadColors();
        colorSlider.setMaximum(EngineSettings.savedReactiveColors.size() - 1);
        frame.setTitle("You have " + EngineSettings.savedReactiveColors.size() + " Saved Colors!");
    }

    private static void setLabelColors(Color[] colors) {
        for (int i = 0; i < labels.length; i++)
            labels[i].setBackground(colors[i]);
    }

    private int setMinorTicks() {
        if (EngineSettings.savedReactiveColors.size() < 25)
            return 1;
        else if (EngineSettings.savedReactiveColors.size() % 15 == 0)
            return EngineSettings.savedReactiveColors.size() / 15;
        else
            return EngineSettings.savedReactiveColors.size() / 25;
    }

    private int setMajorTicks() {
        if (EngineSettings.savedReactiveColors.size() >= 25) return EngineSettings.savedReactiveColors.size() / 5;
        return 1;
    }

    private void addComps(JFrame root, JComponent[] components) {
        for (JComponent comps : components)
            root.add(comps);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
