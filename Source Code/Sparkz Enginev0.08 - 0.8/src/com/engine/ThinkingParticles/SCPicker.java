package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ColorEditor;
import com.engine.ParticleTypes.Particle;

import javax.swing.*;
import java.awt.*;

import static com.engine.Utilities.ColorConverter.setAlpha;

public class SCPicker {
    private static void pickColor(JFrame parent, int scParticleIndex, String text) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        Color color = JColorChooser.showDialog(parent, text, null);
        Particle.thinkingColors[scParticleIndex] = (color != null) ? color : Particle.thinkingColors[scParticleIndex];
        ColorEditor.labels[scParticleIndex].setBackground(setAlpha(Particle.thinkingColors[scParticleIndex], 255));
    }

    public static void particleColor(int index) {pickColor(ColorEditor.frame, index, "Color " + (index + 1));}
}
