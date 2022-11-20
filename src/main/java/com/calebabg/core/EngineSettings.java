package com.calebabg.core;

import com.calebabg.enums.EngineMode;
import com.calebabg.enums.GravitationMode;
import com.calebabg.enums.MoleculeRenderMode;
import com.calebabg.enums.MoleculeType;
import com.calebabg.gui.ExceptionWindow;
import com.calebabg.reactivity.ReactiveColors;
import com.calebabg.utilities.ColorUtil;
import com.calebabg.utilities.EnumUtil;
import com.calebabg.utilities.JsonUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.calebabg.enums.EngineMode.NORMAL;
import static com.calebabg.enums.GravitationMode.DEFAULT;
import static com.calebabg.enums.MoleculeRenderMode.RECTANGLE_NO_FILL;
import static com.calebabg.enums.MoleculeType.PARTICLE;

public class EngineSettings {
    public static final String SETTINGS_FOLDER_NAME = "SparkzEngineSettings";
    public static final String SETTINGS_FILE_NAME = "EngineSettings.json";
    public static final String COLORS_FILE_NAME = "SavedColors.txt";
    public static final String COLORS_SPLIT_DELIMITER = ";";
    public static final String SETTINGS_FOLDER_PATH = "." + Paths.get("/" + SETTINGS_FOLDER_NAME);
    public static final String SETTINGS_FILE_PATH = SETTINGS_FOLDER_PATH + "/" + SETTINGS_FILE_NAME;
    public static final String COLORS_FILE_PATH = SETTINGS_FOLDER_PATH + "/" + COLORS_FILE_NAME;
    public static final List<String> savedReactiveColors = new ArrayList<>(125);

    public transient boolean
            running,
            controlKeyIsDown,
            leftMouseButtonIsDown,
            rightMouseButtonIsDown;

    public boolean
            paused,
            reactiveColorsEnabled = true,
            linkMolecules,
            particleFriction = true,
            particleBoundless,
            particlesGravitateToMouse = true,
            cycleReactiveColors,
            showGravityPointsLink,
            showParticlesLink,
            duplexContain,
            smoothRenderingEnabled;

    public int
            particleDragAmount = 1,
            particleLife = 65,
            multiAmount = 5,
            fireworksAmount = 30,
            fireworksLife = 90,
            fireworksWind = 2,
            fireworksJitter = 5,
            fireworksParticleSafetyAmount = 120,
            desiredFramesPerSecond = 60,
            reactiveColorsCycleInterval = 5;

    public float
            singleClickSizeMin = 0.8f,
            singleClickSizeMax = 0.9f,
            multiClickSizeMin = 0.8f,
            multiClickSizeMax = 0.9f,
            fireworksSizeMin = 0.35f,
            fireworksSizeMax = 0.45f,
            particleDragSizeMin = 0.25f,
            particleDragSizeMax = 0.45f,
            singleClickSpeed = 5.85f,
            multiClickSpeed = 5.95f,
            fireworksSpeed = 5.19f,
            particleDragSpeed = 1.25f;

    public EngineMode engineMode = NORMAL;
    public MoleculeType moleculeType = PARTICLE;
    public GravitationMode gravitationMode = DEFAULT;
    public MoleculeRenderMode particleRenderMode = RECTANGLE_NO_FILL;
    public MoleculeRenderMode fireworksRenderMode = RECTANGLE_NO_FILL;

    public void toggleGraphicsSmoothing() {
        smoothRenderingEnabled = !smoothRenderingEnabled;
    }

    public void toggleParticleFriction() {
        particleFriction = !particleFriction;
    }

    public void toggleMouseGravitation() {
        particlesGravitateToMouse = !particlesGravitateToMouse;
    }

    public void toggleConnectedParticlesMode() {
        linkMolecules = !linkMolecules;
    }

    public void toggleReactiveColors() {
        reactiveColorsEnabled = !reactiveColorsEnabled;
    }

    public void toggleCycleReactiveColors() {
        cycleReactiveColors = !cycleReactiveColors;
    }

    public void toggleParticleBoundsChecking() {
        particleBoundless = !particleBoundless;
    }

    public void togglePause() {
        paused = !paused;
    }

    public void toggleDuplexMode() {
        duplexContain = !duplexContain;
    }

    public void toggleParticlesLinkVisibility() {
        showParticlesLink = !showParticlesLink;
    }

    public void toggleGravityPointsLinkVisibility() {
        showGravityPointsLink = !showGravityPointsLink;
    }

    public void changeEngineMode(boolean advance) {
        engineMode = EnumUtil.transition(engineMode, advance);
    }

    public void changeMoleculeType(boolean advance) {
        moleculeType = EnumUtil.transition(moleculeType, advance);
    }

    public static String getProjectTimeSpan() {
        return Period.between(LocalDate.now(), LocalDate.of(2015, Month.NOVEMBER, 22)).toString();
    }

    public static boolean settingsFileExists() {
        return Files.exists(Paths.get(SETTINGS_FILE_PATH));
    }

    public static boolean colorsFileExists() {
        return Files.exists(Paths.get(COLORS_FILE_PATH));
    }

    public static void saveColors(String colorsString) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(COLORS_FILE_PATH, true), StandardCharsets.UTF_8)) {
            new File("./" + SETTINGS_FOLDER_NAME).mkdir();
            if (colorsString != null) writer.write(colorsString + '\n');
            else writer.write(ColorUtil.serializeColors(ReactiveColors.getColors()) + '\n');
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }

    public static void loadColors() {
        savedReactiveColors.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(COLORS_FILE_PATH)))) {
            String line;
            while ((line = br.readLine()) != null) savedReactiveColors.add(line);
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }

    public static void saveSettings() {
        new File("./" + SETTINGS_FOLDER_NAME).mkdir();
        JsonUtil.writeEngineSettingsJson();
    }

    public static void loadSettings() {
        if (settingsFileExists()) JsonUtil.loadEngineSettingsJson();
        else saveSettings();
    }

}
