package com.cabg.utilities;

import com.cabg.core.*;
import com.cabg.enums.*;
import com.cabg.gui.ExceptionLogger;
import com.cabg.components.CMenuBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;

import static com.cabg.core.EngineVariables.engineSettings;

public class JsonUtil {
    public static final String
            SETTINGS_JSON_OBJECT_KEY = "Settings",
            SETTINGS_JSON_INTS_KEY = "Ints",
            SETTINGS_JSON_DOUBLES_KEY = "Doubles",
            SETTINGS_JSON_BOOLEANS_KEY = "Booleans",
            SETTINGS_JSON_COLORS_KEY = "Colors";

    public static final String
            // Add Properties as needed
            SETTINGS_ENGINE_MODE_PROPERTY = "ENGINE_MODE",
            SETTINGS_PARTICLE_TYPE_PROPERTY = "PARTICLE_TYPE",
            SETTINGS_PARTICLE_GRAVITATION_MODE_PROPERTY = "PARTICLE_GRAVITATION_MODE",
            SETTINGS_PARTICLE_RENDER_MODE_PROPERTY = "PARTICLE_RENDER_MODE",
            SETTINGS_FIREWORKS_RENDER_MODE_PROPERTY = "FIREWORKS_RENDER_MODE";


    public static void writeEngineSettingsJson() {
        String settings = getEngineSettingsJson();

        try (FileOutputStream out = new FileOutputStream(Settings.settingsFilePath, false);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            writer.write(settings);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    public static String getEngineSettingsJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(engineSettings, EngineSettings.class);
    }

    public static void loadEngineSettingsJson(){
        //  Try Parsing and setting engine properties
        try(FileInputStream fin = new FileInputStream(Settings.settingsFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            engineSettings = new Gson().fromJson(br, EngineSettings.class);

        } catch (Exception e){
            ExceptionLogger.append(e);}

        //  Set Global Variables
        setGlobalEngineVariables();
    }

    public static void setGlobalEngineVariables(){
        EngineMethods.setEngineTitle();
        CMenuBar.updateAllRadios();
    }

    public static int constrain(int default_, int value, int min, int max){
        if (value >= min && value <= max) return value;
        else return default_;
    }
}
