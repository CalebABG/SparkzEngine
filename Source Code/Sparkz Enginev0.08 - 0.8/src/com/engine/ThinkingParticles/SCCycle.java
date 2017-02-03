package com.engine.ThinkingParticles;

import static com.engine.EngineHelpers.EConstants.*;

import com.engine.GUIWindows.ColorTimeMachine;
import com.engine.ParticleTypes.Particle;
import com.engine.Utilities.Settings;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SCCycle {
    public static Timer time;
    public synchronized static void startCycle() {
        if (Settings.doesColorsFileExist()) {if (Settings.presetColors == null) {Settings.loadColors();}} // Needs Exception handling
        time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!Settings.doesColorsFileExist()) {
                    Color[] randColors = SCChoices.randomColor();
                    SCChoices.setPresetColors(randColors);
                    ColorTimeMachine.addColor(randColors);
                }
                else {
                    regularCycle();
                }
            }}, 0, (cycleRate * 1000));
    }
    public static void stopCycle() {
        time.cancel(); time.purge();
    }

    public static void regularCycle() {
        int randColor = (int) (Math.random() * Settings.presetColors.size());
        SCChoices.setPresetColors(Settings.convertColors(randColor), Particle.thinkingColors);
    }
}
