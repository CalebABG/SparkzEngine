package com.cabg.utilities;

import com.cabg.gui.ExceptionLogger;
import com.cabg.reactivecolors.ReactiveColors;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.cabg.core.EngineVariables.toolkit;
import static com.cabg.utilities.ColorUtility.fromHex;

public class Settings {
    public static Image iconImage = toolkit.getImage(Settings.class.getResource("/EngineLogo.png"));
    public static Image splashImage = toolkit.getImage(Settings.class.getResource("/EngineSplash.png"));

    public static String folder_name = "SparkzEngineSettings";
    public static String settings_file_name = "EngineSettings.json";
    public static String colors_file_name = "SavedColors.txt";
    public static String spliceChar = ";";
    public static String engine_settings_folder_path = "." + Paths.get("/" + folder_name);
    public static String settingsFilePath = engine_settings_folder_path + "/" + settings_file_name;
    public static String colorsFilePath = engine_settings_folder_path + "/" + colors_file_name;

    public static final List<String> userSavedColors = new ArrayList<>(1000);

    public static String getProjectTimespan() {
        return Period.between(LocalDate.now(), LocalDate.of(2015, Month.NOVEMBER, 22)).toString();
    }

    public static boolean settingsFileExists() {
        return Files.exists(Paths.get(settingsFilePath));
    }

    public static boolean colorsFileExists() {
        return Files.exists(Paths.get(colorsFilePath));
    }

    /**
     * This method returns an array of 5 colors at a particular index of another color array
     */
    public static Color[] convertColors(int index, List<String> list) {
        String[] split = list.get(index).split(spliceChar);
        return new Color[]{
                fromHex(split[0]),
                fromHex(split[1]),
                fromHex(split[2]),
                fromHex(split[3]),
                fromHex(split[4])
        };
    }

    /**
     * This method gets the 5 thinking colors for the particles, converts them to RGB format
     * and then appends them into a txt document.
     */
    public static void saveColors(String colorsString) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(colorsFilePath, true), StandardCharsets.UTF_8)) {
            new File("./" + folder_name).mkdir();
            if (colorsString != null) writer.write(colorsString + '\n');
            else writer.write(ColorUtility.serializeReactiveColors(ReactiveColors.getComponents()) + '\n');
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    /**
     * This method reads in the text file with particle colors(RGB)
     * It reads each line and splits the line based on the break char.
     */
    public static void loadColors() {
        userSavedColors.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(colorsFilePath)))) {
            String line;
            while ((line = br.readLine()) != null) userSavedColors.add(line);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    public static void saveSettings() {
        new File("./" + folder_name).mkdir();
        JsonUtil.writeEngineSettingsJson();
    }

    public static void loadSettings() {
        if (settingsFileExists()) JsonUtil.loadEngineSettingsJson();
        else saveSettings();
    }
}