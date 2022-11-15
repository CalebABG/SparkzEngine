package com.calebabg.utilities;

import com.calebabg.gui.ExceptionWindow;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HTMLUtil {
    public static final String EngineInstructions = getHtml("/html/EngineInstructions.html");
    public static final String GeneralOptions = getHtml("/html/GeneralOptions.html");
    public static final String MoleculeRenderOptions = getHtml("/html/MoleculeRenderOptions.html");
    public static final String ParticleGraphInstructions = getHtml("/html/GraphInstructions.html");
    public static final String ParticleGravitationOptions = getHtml("/html/GravitationOptions.html");
    public static final String ParticleSizeOptions = getHtml("/html/ParticleSizeOptions.html");
    public static final String ParticleSpeedOptions = getHtml("/html/ParticleSpeedOptions.html");
    public static final String FireworksOptions = getHtml("/html/FireworksOptions.html");

    public static String getHtml(String file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(HTMLUtil.class.getResourceAsStream(file)))) {
            StringBuilder lines = new StringBuilder(1024);
            String line;
            while ((line = br.readLine()) != null) lines.append(line);
            return lines.toString();
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
        return null;
    }

    public static String HeadingTag(int type, String str) {
        String content = type > 0 && type < 7 ? HeadingTag("h"+type, str) : HeadingTag("h3", str);
        return HTMLTag(content);
    }

    public static String HTMLTag(String content) {
        return "<html>" + content + "</html>";
    }

    public static String HeadingTag(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String HeadingWithStyleTag(String tag, String styleText, String text) {
        return "<" + tag + " style=" + "'" + styleText + "'" + ">" + text + "</" + tag + ">";
    }

    public static String HeadingWithStyleCenteredTag(String tag, String text) {
        return "<html>" + "<" + tag + " style=" + "'text-align: center'" + ">" + text + "</" + tag + ">" + "</html>";
    }

    public static String DivTag(String text) {
        return "<div>"+ text +"</div>";
    }
}
