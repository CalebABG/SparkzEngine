package com.engine.Utilities;

public class HTMLUtil {

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
