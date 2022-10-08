package com.cabg.reactivecolors;

import com.cabg.gui.ReactiveColorsTimeMachine;
import com.cabg.utilities.Settings;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static com.cabg.core.EngineVariables.engineSettings;
import static com.cabg.core.EngineVariables.random;

public class ReactiveColorsRandomizer {
    private static Timer time;

    public synchronized static void startCycle() {
        if (Settings.colorsFileExists()) {
            Settings.loadColors();
        } // Needs Exception handling
        time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                cycleColors();
            }
        }, 0, engineSettings.reactiveColorsCycleRateInSeconds * 1000L);
    }

    public static void cycleColors() {
        if (!Settings.colorsFileExists()) {
            Color[] randColors = ReactiveColors.randomColor();
            ReactiveColors.setPresetColors(randColors);
            ReactiveColorsTimeMachine.addColor(randColors);
        } else regularCycle();
    }

    public static void stopCycle() {
        time.cancel();
        time.purge();
    }

    public static void regularCycle() {
        int index = random.nextInt(Settings.userSavedColors.size());
        ReactiveColors.setPresetColors(Settings.convertColors(index, Settings.userSavedColors), ReactiveColors.getComponents());
    }
}
