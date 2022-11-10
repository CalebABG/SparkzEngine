package com.cabg.reactivecolors;

import com.cabg.core.EngineSettings;
import com.cabg.gui.ReactiveColorsTimeMachine;
import com.cabg.utilities.ColorUtil;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static com.cabg.core.EngineVariables.engineSettings;
import static com.cabg.core.EngineVariables.random;

public class ReactiveColorsRandomizer {
    private static Timer timer;

    public synchronized static void startCycle() {
        if (EngineSettings.colorsFileExists()) {
            EngineSettings.loadColors();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                cycleColors();
            }
        }, 0, engineSettings.reactiveColorsCycleRateInSeconds * 1000L);
    }

    public static void cycleColors() {
        if (EngineSettings.colorsFileExists()) {
            regularCycle();
        } else {
            Color[] randColors = ReactiveColors.randomColor();
            ReactiveColors.setPresetColors(randColors);
            ReactiveColorsTimeMachine.addColor(randColors);
        }
    }

    public static void stopCycle() {
        timer.cancel();
        timer.purge();
    }

    public static void regularCycle() {
        int index = random.nextInt(EngineSettings.savedReactiveColors.size());
        ReactiveColors.setPresetColors(ColorUtil.convertColors(index, EngineSettings.savedReactiveColors), ReactiveColors.getComponents());
    }

    public static void restartCycle() {
        stopCycle();
        startCycle();
    }
}
