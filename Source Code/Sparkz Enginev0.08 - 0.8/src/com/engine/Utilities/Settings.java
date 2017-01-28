package com.engine.Utilities;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.EException;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.ParticleTypes.Particle;

import static com.engine.Utilities.ColorConverter.HEXAtoRGBA;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Settings {
    public static DecimalFormat dcFormat = new DecimalFormat("0.000");
    @SuppressWarnings("deprecation")
    public static final double PROJECT_LOG = Double.parseDouble((dcFormat.format(((new Date().getTime() - new Date("November 22, 2015").getTime()) / (1000.0 * 60 * 60 * (24 * 7 * 4))))));
    private static Image iconImage = Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/enginelogo.png"));
    private static Image splashImage = Toolkit.getDefaultToolkit().getImage(Settings.class.getResource("/enginesplash.png"));
    public static int PRESET_INDEXES = 1;
    public static String folder_name = "Settings", settings_file_name = "EngineSettings.txt", colors_file_name = "SavedColors.txt", spliceChar = ";";
    public static String settings_file_path = ("." + Paths.get("/" + folder_name) + "/" + settings_file_name),
                         colors_file_path = ("." + Paths.get("/" + folder_name) + "/" + colors_file_name);
    public static ArrayList<String[]> presetColors;

    //public static void main(String[] args) { //saveSettings(); //loadSettings();}

    public static boolean doesSettingsFileExist(){return Files.exists(Paths.get(settings_file_path));}
    public static boolean doesColorsFileExist(){return Files.exists(Paths.get(colors_file_path));}
    public static boolean StoBool(String s, boolean def_val){if (s.equalsIgnoreCase("TRUE")) {return true;}
    else if (s.equalsIgnoreCase("FALSE")) {return false;} else {return def_val;}}
    public static Image getIcon(){return iconImage;}
    public static Image getSplashImage(){return splashImage;}

    public static String getOS() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {return "linux";} 
        else if (os.contains("windows") || os.contains("win")) {return "windows";} 
        else if (os.contains("solaris") || os.contains("sunos")) {return "solaris";} 
        else if (os.contains("mac os") || os.contains("macos") || os.contains("darwin")) {return "mac";}
        else return "Cannot determine Operating System";
    }

    /**
     * This method returns an array of 5 colors at a particular index of another color array
     *
     */
    public static Color[] convertColors(int index) {
        return new Color[]{
                HEXAtoRGBA(presetColors.get(index)[0]), HEXAtoRGBA(presetColors.get(index)[1]),
                HEXAtoRGBA(presetColors.get(index)[2]), HEXAtoRGBA(presetColors.get(index)[3]),
                HEXAtoRGBA(presetColors.get(index)[4])};
    }

    /**
     * This method gets the 5 thinking colors for the particles, converts them to RGBA format
     * and then appends them into a text document.
     */
    public static void saveColors(int mode, String[] strings) {
        try {
            new File("./" + folder_name).mkdir();
            String[] getStrings;
            ArrayList<String> splitStrings = new ArrayList<>();
            FileOutputStream out = new FileOutputStream(colors_file_path, true);
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            getStrings = (mode == 0) ? Particle.getThinkingParticlesStrings() : strings;
            Collections.addAll(splitStrings, getStrings);
            for (int i = 0; i < splitStrings.size(); i++) {writer.write((splitStrings.get(i) + spliceChar));}
            writer.write("\n");
            writer.close();
            out.close();
        } catch (Exception e) {EException.append(e);}
    }

    /**
     * This method reads in the text file with particle colors(RGBA)
     * It reads each line and splits the line based on the break char.
     */
    public static void loadColors() {
        try {
            FileInputStream fin = new FileInputStream(colors_file_path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            ArrayList<String[]> splitArray = new ArrayList<>();
            ArrayList<String> text = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {text.add(line);}
            for (int i = 0; i < text.size(); i++) {splitArray.add(text.get(i).split(spliceChar));}
            br.close();
            fin.close();
            PRESET_INDEXES = (splitArray.size());
            presetColors = splitArray;
        }catch (Exception e){EException.append(e);}
    }

    public static void saveSettings() {
        try{
            new File("./" + folder_name).mkdir();
            FileOutputStream out = new FileOutputStream(settings_file_path, false);
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            /*-------------------------------------------------------*/
            writer.write("# switchMode -- set: 0 - 4 <- MAX \n");
            writer.write("switchMode: " + switchMode + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleType -- set: 0 - 8 <- MAX \n");
            writer.write("particleType: " + particleType + "\n");
            /*-------------------------------------------------------*/
            writer.write("# ptGravitationInt -- set: 0 - 6 <- MAX \n");
            writer.write("ptGravitationInt: " + ptGravitationInt + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksAmount -- set: 0 - specify number \n");
            writer.write("fireworksAmount: " + fireworksAmount + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleMode -- set: 1 - 19 <- MAX \n");
            writer.write("particleMode: " + particleMode + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragAmount -- set: 1 - specify number \n");
            writer.write("dragAmount: " + dragAmount + "\n");
            /*-------------------------------------------------------*/
            writer.write("# baseLife -- set: 0 - specify number \n");
            writer.write("baseLife: " + baseLife + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfParticleMode -- set: 1 - 19 <- MAX \n");
            writer.write("rfParticleMode: " + rfParticleMode + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfLife -- set: 0 - specify number \n");
            writer.write("rfLife: " + rfLife + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfWind -- set: 0 - specify number \n");
            writer.write("rfWind: " + rfWind + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfJitter -- set: 0 - specify number \n");
            writer.write("rfJitter: " + rfJitter + "\n");
            /*-------------------------------------------------------*/
            writer.write("# safetyAmount -- set: 0 - specify number \n");
            writer.write("safetyAmount: " + safetyAmount + "\n");
            /*-------------------------------------------------------*/
            writer.write("# cycleRate -- set: 1 - specify number \n");
            writer.write("cycleRate: " + cycleRate + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSizeMaxVal -- set: 0 - specify number \n");
            writer.write("singleClickSizeMaxVal: " + singleClickSizeMaxVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSizeMinVal -- set: 0 - specify number \n");
            writer.write("singleClickSizeMinVal: " + singleClickSizeMinVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSizeMaxVal -- set: 0 - specify number \n");
            writer.write("multiClickSizeMaxVal: " + multiClickSizeMaxVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSizeMinVal -- set: 0 - specify number \n");
            writer.write("multiClickSizeMinVal: " + multiClickSizeMinVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSizeMaxVal -- set: 0 - specify number \n");
            writer.write("fireworksSizeMaxVal: " + fireworksSizeMaxVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSizeMinVal -- set: 0 - specify number \n");
            writer.write("fireworksSizeMinVal: " + fireworksSizeMinVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSizeMaxVal -- set: 0 - specify number \n");
            writer.write("dragSizeMaxVal: " + dragSizeMaxVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSizeMinVal -- set: 0 - specify number \n");
            writer.write("dragSizeMinVal: " + dragSizeMinVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSpeedVal -- set: 0 - specify number \n");
            writer.write("singleClickSpeedVal: " + singleClickSpeedVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSpeedVal -- set: 0 - specify number \n");
            writer.write("multiClickSpeedVal: " + multiClickSpeedVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSpeedVal -- set: 0 - specify number \n");
            writer.write("fireworksSpeedVal: " + fireworksSpeedVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSpeedVal -- set: 0 - specify number \n");
            writer.write("dragSpeedVal: " + dragSpeedVal + "\n");
            /*-------------------------------------------------------*/
            writer.write("# thinkingParticles -- set: true - false \n");
            writer.write("thinkingParticles: " + thinkingParticles + "\n");
            /*-------------------------------------------------------*/
            writer.write("# connectParticles -- set: true - false \n");
            writer.write("connectParticles: " + connectParticles + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleFriction -- set: true - false \n");
            writer.write("particleFriction: " + particleFriction + "\n");
            /*-------------------------------------------------------*/
            writer.write("# mouseGravitation -- set: true - false \n");
            writer.write("mouseGravitation: " + mouseGravitation + "\n");
            /*-------------------------------------------------------*/
            writer.write("# isPaused -- set: true - false \n");
            writer.write("isPaused: " + isPaused + "\n");
            /*-------------------------------------------------------*/
            writer.write("# GDMODEBOOL -- set: true - false \n");
            writer.write("GDMODEBOOL: " + GDMODEBOOL + "\n");
            /*-------------------------------------------------------*/
            writer.write("# DUPLEXMODE -- set: true - false \n");
            writer.write("DUPLEXMODE: " + DUPLEXMODE + "\n");
            /*-------------------------------------------------------*/
            writer.close(); out.close();
        }catch (Exception e){EException.append(e);}
    }

    private static void setEngineVariables(ArrayList<String> s) {
        //Set Variables
        switchMode = Integer.parseInt(s.get(0)); particleType = Integer.parseInt(s.get(1));
        ptGravitationInt = Integer.parseInt(s.get(2)); fireworksAmount = Integer.parseInt(s.get(3));
        particleMode = Integer.parseInt(s.get(4)); dragAmount = Integer.parseInt(s.get(5));
        baseLife = Integer.parseInt(s.get(6)); rfParticleMode = Integer.parseInt(s.get(7));
        rfLife = Integer.parseInt(s.get(8)); rfWind = Integer.parseInt(s.get(9));
        rfJitter = Integer.parseInt(s.get(10)); safetyAmount = Integer.parseInt(s.get(11));
        cycleRate = Integer.parseInt(s.get(12));
        /*---------------------------------------------------------------------------------------------------------------------*/
        singleClickSizeMaxVal = Double.parseDouble(s.get(13)); singleClickSizeMinVal = Double.parseDouble(s.get(14));
        multiClickSizeMaxVal = Double.parseDouble(s.get(15)); multiClickSizeMinVal = Double.parseDouble(s.get(16));
        fireworksSizeMaxVal = Double.parseDouble(s.get(17)); fireworksSizeMinVal = Double.parseDouble(s.get(18));
        dragSizeMaxVal = Double.parseDouble(s.get(19)); dragSizeMinVal = Double.parseDouble(s.get(20));
        singleClickSpeedVal = Double.parseDouble(s.get(21)); multiClickSpeedVal = Double.parseDouble(s.get(22));
        fireworksSpeedVal = Double.parseDouble(s.get(23)); dragSpeedVal = Double.parseDouble(s.get(24));
        /*---------------------------------------------------------------------------------------------------------------------*/
        thinkingParticles = StoBool(s.get(25), thinkingParticles); connectParticles = StoBool(s.get(26), connectParticles);
        particleFriction = StoBool(s.get(27), particleFriction); mouseGravitation = StoBool(s.get(28), mouseGravitation);
        isPaused = StoBool(s.get(29), isPaused); GDMODEBOOL = StoBool(s.get(30), GDMODEBOOL);
        DUPLEXMODE = StoBool(s.get(31), DUPLEXMODE);
        //Check Engine State: Paused or Running
        EngineMethods.setEngineTitleState();
    }

    public static void loadSettings() {
        if (doesSettingsFileExist()) {
            try {
                String line;
                FileInputStream fin = new FileInputStream(settings_file_path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fin));
                ArrayList<String> text = new ArrayList<>(), textKeys = new ArrayList<>(), textValues = new ArrayList<>(), trimValues = new ArrayList<>();
                while ((line = br.readLine()) != null) {text.add(line);}
                for (int i = 0; i < text.size() - 1; i++) {if (i % 2 == 0) {textKeys.add(text.get(i + 1));}}
                br.close(); fin.close();
                for (int i = 0; i < textKeys.size(); i++) {textValues.add(textKeys.get(i).substring(textKeys.get(i).indexOf(':') + 1));}
                for (String s : textValues) {trimValues.add(s.trim());}
                setEngineVariables(trimValues);
            } catch (Exception e) {EException.append(e);}
        }
        else {saveSettings();}
    }
}