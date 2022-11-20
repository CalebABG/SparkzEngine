package com.calebabg.reactivity;

import com.calebabg.core.EngineSettings;
import com.calebabg.core.EngineVariables;
import com.calebabg.gui.ReactiveColorsEditor;
import com.calebabg.gui.ReactiveColorsPresets;
import com.calebabg.utilities.ColorUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ReactiveColorsRandomizer {
    private ReactiveColorsRandomizer(){}

    private static Timer timer;

    public static void startCycle() {
        if (EngineSettings.colorsFileExists()) EngineSettings.loadColors();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                cycleColors();
            }
        }, 0, EngineVariables.engineSettings.reactiveColorsCycleInterval * 1000L);
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
        int index = EngineVariables.random.nextInt(EngineSettings.savedReactiveColors.size());
        ReactiveColorsPresets.setColors(ColorUtil.deserializeColors(index, EngineSettings.savedReactiveColors), ReactiveColors.getColors());
    }
}
