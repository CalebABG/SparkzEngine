package com.cabg.utilities;

import com.cabg.components.CMenuBar;
import com.cabg.core.EngineMethods;
import com.cabg.core.EngineSettings;
import com.cabg.gui.ExceptionLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.cabg.core.EngineVariables.engineSettings;

public class JsonUtil {
    public static void writeEngineSettingsJson() {
        String settings = getEngineSettingsJson();

        try (FileOutputStream out = new FileOutputStream(Settings.settingsFilePath, false);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            writer.write(settings);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    public static String getEngineSettingsJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(engineSettings, EngineSettings.class);
    }

    public static void loadEngineSettingsJson() {
        //  Try Parsing and setting engine properties
        try (FileInputStream fin = new FileInputStream(Settings.settingsFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            engineSettings = new Gson().fromJson(br, EngineSettings.class);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }

        //  Set Global Variables
        setGlobalEngineVariables();
    }

    public static void setGlobalEngineVariables() {
        EngineMethods.setEngineTitle();
        CMenuBar.updateAllRadios();
    }
}
