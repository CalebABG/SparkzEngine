package com.cabg.core;

import com.cabg.enums.EngineMode;
import com.cabg.enums.GravitationMode;
import com.cabg.enums.MoleculeRenderMode;
import com.cabg.enums.MoleculeType;
import com.cabg.gui.ExceptionLogger;
import com.cabg.reactivecolors.ReactiveColors;
import com.cabg.utilities.ColorUtil;
import com.cabg.utilities.JsonUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.cabg.enums.EngineMode.NORMAL;
import static com.cabg.enums.GravitationMode.DEFAULT;
import static com.cabg.enums.MoleculeRenderMode.RECTANGLE_NO_FILL;
import static com.cabg.enums.MoleculeType.PARTICLE;

public class EngineSettings {
    public static String folderName = "SparkzEngineSettings";
    public static String settingsFileName = "EngineSettings.json";
    public static String colorsFileName = "SavedColors.txt";
    public static String colorsSpliceChar = ";";
    public static String engineSettingsFolderPath = "." + Paths.get("/" + folderName);
    public static String settingsFilePath = engineSettingsFolderPath + "/" + settingsFileName;
    public static String colorsFilePath = engineSettingsFolderPath + "/" + colorsFileName;
    public static final List<String> savedReactiveColors = new ArrayList<>(125);

    public transient boolean
            running,
            controlKeyIsDown,
            leftMouseButtonIsDown,
            rightMouseButtonIsDown;

    public boolean
            paused,
            reactiveColorsEnabled = true,
            connectParticles,
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
            fireworksAmount = 30,
            fireworksLife = 90,
            fireworksWind = 2,
            fireworksJitter = 5,
            fireworksParticleSafetyAmount = 120,
            desiredFramesPerSecond = 60,
            reactiveColorsCycleRateInSeconds = 5;

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
        connectParticles = !connectParticles;
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
        engineMode = EngineMethods.getMode(engineMode, advance);
    }

    public void changeParticleType(boolean advance) {
        moleculeType = EngineMethods.getMode(moleculeType, advance);
    }

    public static String getProjectTimeSpan() {
        return Period.between(LocalDate.now(), LocalDate.of(2015, Month.NOVEMBER, 22)).toString();
    }

    public static boolean settingsFileExists() {
        return Files.exists(Paths.get(settingsFilePath));
    }

    public static boolean colorsFileExists() {
        return Files.exists(Paths.get(colorsFilePath));
    }

    public static void saveColors(String colorsString) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(colorsFilePath, true), StandardCharsets.UTF_8)) {
            new File("./" + folderName).mkdir();
            if (colorsString != null) writer.write(colorsString + '\n');
            else writer.write(ColorUtil.serializeColors(ReactiveColors.getComponents()) + '\n');
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    public static void loadColors() {
        savedReactiveColors.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(colorsFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) savedReactiveColors.add(line);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    public static void saveSettings() {
        new File("./" + folderName).mkdir();
        JsonUtil.writeEngineSettingsJson();
    }

    public static void loadSettings() {
        if (settingsFileExists()) JsonUtil.loadEngineSettingsJson();
        else saveSettings();
    }

}
