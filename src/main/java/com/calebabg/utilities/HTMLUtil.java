package com.calebabg.utilities;

import com.calebabg.gui.ExceptionWindow;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HTMLUtil {
    private HTMLUtil(){}

    public static final String ENGINE_INSTRUCTIONS = getHtml("/html/EngineInstructions.html");
    public static final String GENERAL_OPTIONS = getHtml("/html/GeneralOptions.html");
    public static final String MOLECULE_RENDER_OPTIONS = getHtml("/html/MoleculeRenderOptions.html");
    public static final String GRAPH_INSTRUCTIONS = getHtml("/html/GraphInstructions.html");
    public static final String GRAVITATION_OPTIONS = getHtml("/html/GravitationOptions.html");
    public static final String PARTICLE_SIZE_OPTIONS = getHtml("/html/ParticleSizeOptions.html");
    public static final String PARTICLE_SPEED_OPTIONS = getHtml("/html/ParticleSpeedOptions.html");
    public static final String FIREWORKS_OPTIONS = getHtml("/html/FireworksOptions.html");

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

    public static String headingTag(int type, String str) {
        String content = type > 0 && type < 7 ? headingTag("h"+type, str) : headingTag("h3", str);
        return htmlTag(content);
    }

    public static String htmlTag(String content) {
        return "<html>" + content + "</html>";
    }

    public static String headingTag(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String headingWithStyleTag(String tag, String styleText, String text) {
        return "<" + tag + " style=" + "'" + styleText + "'" + ">" + text + "</" + tag + ">";
    }

    public static String headingWithStyleCenteredTag(String tag, String text) {
        return "<html>" + "<" + tag + " style=" + "'text-align: center'" + ">" + text + "</" + tag + ">" + "</html>";
    }

    public static String divTag(String text) {
        return "<div>"+ text +"</div>";
    }
}
