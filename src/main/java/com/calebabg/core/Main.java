package com.calebabg.core;

import com.calebabg.gui.SplashScreen;
import com.calebabg.gui.StatsPanel;

public class Main {
    static {
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.transaccel", "True");
        System.setProperty("sun.java2d.ddforcevram", "True");
        System.setProperty("apple.laf.useScreenMenuBar", "True");
        System.setProperty("com.apple.macos.use-file-dialog-packages", "True");
    }

    public static void main(String[] args) {
        new SplashScreen().display();
        Engine e = new Engine();
        StatsPanel.getInstance();
        e.run();
    }
}
