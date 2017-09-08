package com.engine.Utilities;

public class H5Util {
    //public static void main(String[] args) {System.out.println(HStyle("h1", "color: red", "Welcome"));}

    public static String H(int type, String str) {
        if (type == 1) {return "<html><h1>" + str + "</h1></html>";}
        if (type == 2) {return "<html><h2>" + str + "</h2></html>";}
        if (type == 3) {return "<html><h3>" + str + "</h3></html>";}
        if (type == 4) {return "<html><h4>" + str + "</h4></html>";}
        if (type == 5) {return "<html><h5>" + str + "</h5></html>";}
        if (type == 6) {return "<html><h6>" + str + "</h6></html>";}
        else return "<html><h3>" + str + "</h3></html>";
    }

    public static String HTag(String tag, String text) {
        return "<" + tag + ">" + text + "</" + tag + ">";
    }

    public static String HStyle(String tag, String styleText, String text) {
        return "<" + tag + " style=" + "\'" + styleText + "\'" + ">" + text + "</" + tag + ">";
    }

    public static String HCenter(String tag, String text) {
        return "<html>" + "<" + tag + " style=" + "\'text-align: center\'" + ">" + text + "</" + tag + ">" + "</html>";
    }

    public static String HDiv(String text) {
        return "<div>"+ text +"</div>";
    }
}
