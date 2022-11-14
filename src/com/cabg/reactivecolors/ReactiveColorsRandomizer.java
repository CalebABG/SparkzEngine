package com.cabg.reactivecolors;

import com.cabg.core.EngineSettings;
import com.cabg.gui.ReactiveColorsEditor;
import com.cabg.gui.ReactiveColorsPresets;
import com.cabg.utilities.ColorUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.cabg.core.EngineVariables.engineSettings;
import static com.cabg.core.EngineVariables.random;

public class ReactiveColorsRandomizer {
    private static Timer timer;

    public synchronized static void startCycle() {
        if (EngineSettings.colorsFileExists()) EngineSettings.loadColors();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                cycleColors();
            }
        }, 0, engineSettings.reactiveColorsCycleRateInSeconds * 1000L);
    }

    public static void stopCycle() {
        timer.cancel();
        timer.purge();
    }

    public static void restartCycle() {
        stopCycle();
        startCycle();
    }

    public static void cycleColors() {
        if (EngineSettings.colorsFileExists()) regularCycle();
        else ReactiveColorsEditor.setRandomColors();
    }

    public static void regularCycle() {
        int index = random.nextInt(EngineSettings.savedReactiveColors.size());
        ReactiveColorsPresets.setColors(ColorUtil.deserializeColors(index, EngineSettings.savedReactiveColors), ReactiveColors.getColors());
    }
}
