package com.engine.ThinkingParticles;

import com.engine.GUIWindows.ParticleColor;
import com.engine.ParticleTypes.Particle;

import javax.swing.*;
import java.awt.*;

import static com.engine.Utilities.ColorConverter.setAlpha;

public class SCPicker {
    private static void pickColor(JFrame parent, int scParticleIndex, String text) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        Color color = JColorChooser.showDialog(parent, text, null);
        Particle.thinkingColors[scParticleIndex] = (color != null) ? color : Particle.thinkingColors[scParticleIndex];
        ParticleColor.labels[scParticleIndex].setBackground(setAlpha(Particle.thinkingColors[scParticleIndex], 255));
    }
    public static void particleColor1() {pickColor(ParticleColor.frame, 0, "Color 1");}
    public static void particleColor2() {pickColor(ParticleColor.frame, 1, "Color 2");}
    public static void particleColor3() {pickColor(ParticleColor.frame, 2, "Color 3");}
    public static void particleColor4() {pickColor(ParticleColor.frame, 3, "Color 4");}
    public static void particleColor5() {pickColor(ParticleColor.frame, 4, "Color 5");}
}
