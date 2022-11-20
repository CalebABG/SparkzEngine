package com.calebabg.utilities;

import com.calebabg.core.EngineMethods;
import com.calebabg.core.EngineSettings;
import com.calebabg.core.EngineVariables;
import com.calebabg.gui.ExceptionWindow;
import com.calebabg.jcomponents.CMenuBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonUtil {
    private JsonUtil(){}

    public static void writeEngineSettingsJson() {
        String settings = getEngineSettingsJson();

        try (FileOutputStream out = new FileOutputStream(EngineSettings.SETTINGS_FILE_PATH, false);
             Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
            writer.write(settings);
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }

    public static String getEngineSettingsJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(EngineVariables.engineSettings, EngineSettings.class);
    }

    public static void loadEngineSettingsJson() {
        //  Try Parsing and setting engine properties
        try (FileInputStream fin = new FileInputStream(EngineSettings.SETTINGS_FILE_PATH);
             BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            EngineVariables.engineSettings = new Gson().fromJson(br, EngineSettings.class);
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }

        setGlobalEngineVariables();
    }

    public static void setGlobalEngineVariables() {
        EngineMethods.setEngineTitle();
        CMenuBar.updateAllRadios();
    }
}
