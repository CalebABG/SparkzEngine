package com.engine.JComponents;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.*;
import com.engine.MGrapher.ParticleGraph;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;

public class CMenuBar {
    private static JMenuBar menuBar;
    private static JMenuItem exit = new JMenuItem("Exit"), mnAbout, settingsSave, settingsLoad, settingsUI, sliderUI,
            thinkingParticlesUI,statsUI,particleGraphUI, helpInstructions, helpGraphInstructions, accessibility, optionsMenu,
            enginepause;
    private static Font font1 = new Font("Times", Font.PLAIN, 15);
    private static final int memThreshold = 75;

    public static void setUpMenuBar(JFrame root) {
        menuBar = new JMenuBar();
        root.setJMenuBar(menuBar);
        root.getContentPane().setLayout(new BorderLayout(0, 0));

        //File Begin
        JMenu mnFile = new JMenu("File");
        mnFile.setFont(font1);
        menuBar.add(mnFile);
        menuBar.add(Box.createHorizontalStrut(11));
        //File End

        //Edit Begin
        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setFont(font1);
        menuBar.add(mnEdit);

        JMenuItem trimParticleArrays = new JMenuItem("Trim Particle Arrays");
        trimParticleArrays.addActionListener(e -> EngineMethods.trimParticleArrays());
        mnEdit.add(trimParticleArrays);

        JMenuItem clearParticleArrays = new JMenuItem("Clear All Arrays");
        clearParticleArrays.addActionListener(e -> EngineMethods.clearParticleArrays());
        mnEdit.add(clearParticleArrays);
        //Edit End

        //Exit Begin
        exit.addActionListener(e -> {if (e.getSource() == exit) QuitWindow.getInstance();});
        mnFile.add(exit);
        menuBar.add(Box.createHorizontalStrut(11));
        //Exit End

        //ShortCuts Begin
        JMenu mnUIWindows = new JMenu("Short-Cuts");
        mnUIWindows.setFont(font1);
        menuBar.add(mnUIWindows);

        optionsMenu = new JMenuItem("Options Menu");
        optionsMenu.addActionListener(e -> {if (e.getSource() == optionsMenu) {OptionsMenu.getInstance();}});
        mnUIWindows.add(optionsMenu);
        statsUI = new JMenuItem("Stats Panel");
        statsUI.addActionListener(e -> {if (e.getSource() == statsUI) {
            StatsPanel.getInstance();}});
        mnUIWindows.add(statsUI);
        sliderUI = new JMenuItem("Slide Editor");
        sliderUI.addActionListener(e -> {if (e.getSource() == sliderUI) {
            SlideEditor.getInstance();}});
        mnUIWindows.add(sliderUI);

        thinkingParticlesUI = new JMenuItem("Particle Color Editor");
        thinkingParticlesUI.addActionListener(e -> {if (e.getSource() == thinkingParticlesUI) {
            ParticleColor.getInstance();}});
        mnUIWindows.add(thinkingParticlesUI);
        particleGraphUI = new JMenuItem("Particle Graph Editor");
        particleGraphUI.addActionListener(e -> {if (e.getSource() == particleGraphUI) {ParticleGraph.getInstance();}});
        mnUIWindows.add(particleGraphUI);

        menuBar.add(Box.createHorizontalStrut(11));
        JMenuItem customForces = new JMenuItem("Organic Forces Editor");
        customForces.addActionListener(e -> OrganicForces.getInstance(EFrame));
        mnUIWindows.add(customForces);
        //ShortCuts End

        setUpModes();

        menuBar.add(Box.createHorizontalStrut(11));
        JMenu mnSettings = new JMenu("Settings");
        mnSettings.setFont(font1);
        menuBar.add(mnSettings);

        enginepause = new JMenuItem(isPaused());
        enginepause.addActionListener(e -> {isPaused = EngineMethods.toggle(isPaused); enginepause.setText(isPaused()); EngineMethods.setEngineTitleState();});
        mnSettings.add(enginepause);

        settingsSave = new JMenuItem("Save Settings");
        settingsSave.addActionListener(e -> {if (e.getSource() == settingsSave) {Settings.saveSettings();}});
        mnSettings.add(settingsSave);
        settingsLoad = new JMenuItem("Load Settings");
        settingsLoad.addActionListener(e -> {if (e.getSource() == settingsLoad) {Settings.loadSettings();}});
        mnSettings.add(settingsLoad);
        settingsUI = new JMenuItem("Settings Editor");
        settingsUI.addActionListener(e -> {if (e.getSource() == settingsUI) {SettingsEditor.getInstance();}});
        mnSettings.add(settingsUI);

        JMenuItem memclean = new JMenuItem("Clean Memory");
        memclean.setToolTipText(H5Wrapper.H(3, "Cleans memory if free memory is less than " + memThreshold + " MB"));
        memclean.addActionListener(e -> {
            if (e.getSource() == memclean) {if ((Runtime.getRuntime().freeMemory() / (Math.pow(1024,2))) <= memThreshold) {System.gc();}}});
        mnSettings.add(memclean);

        menuBar.add(Box.createHorizontalStrut(11));
        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(font1);
        mnAbout = new JMenuItem("About Sparkz Engine");
        mnHelp.add(mnAbout);
        helpInstructions = new JMenuItem("Particle Engine Instructions");
        helpInstructions.addActionListener(e -> {if (e.getSource() == helpInstructions) {EngineInstructions.getInstance();}});
        mnHelp.add(helpInstructions);
        helpGraphInstructions = new JMenuItem("Particle Graph Instructions");
        helpGraphInstructions.addActionListener(e -> {if (e.getSource() == helpGraphInstructions) {GraphInstructions.getInstance(EFrame);}});
        mnHelp.add(helpGraphInstructions);
        menuBar.add(mnHelp);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            accessibility = new JMenuItem("On-Screen Keyboard");
            accessibility.addActionListener(e -> {
                try {Runtime.getRuntime().exec("cmd /c osk");} catch (Exception ez) {
                    EException.append(ez);}
            });
            mnHelp.add(accessibility);
        }
        updateState();
    }

    private static void setUpModes() {
        JMenu modes = new JMenu("Modes");
        modes.setFont(font1);
        JMenu particleModes = new JMenu("Particle Modes");
        JMenuItem normal = new JMenuItem("Normal Mode");
        normal.addActionListener(e -> {switchMode = 0; EngineMethods.displayMode();});
        particleModes.add(normal);
        JMenuItem multi = new JMenuItem("Multi Mode");
        multi.addActionListener(e -> {switchMode = 1; EngineMethods.displayMode();});
        particleModes.add(multi);
        JMenuItem fireworks = new JMenuItem("Fireworks Mode");
        fireworks.addActionListener(e -> {switchMode = 2; EngineMethods.displayMode();});
        particleModes.add(fireworks);
        JMenuItem graph = new JMenuItem("Graph Mode");
        graph.addActionListener(e -> {switchMode = 3; EngineMethods.displayMode();});
        particleModes.add(graph);
        modes.add(particleModes);
        JMenuItem ragdoll = new JMenuItem("Ragdoll Mode");
        ragdoll.addActionListener(e -> {switchMode = 4; EngineMethods.displayMode();});
        particleModes.add(ragdoll);
        JMenu particleTypes = new JMenu("Particle Types");
        JMenuItem particle = new JMenuItem("Particle");
        particle.addActionListener(e -> {particleType = 0; EngineMethods.displayParticleType();});
        particleTypes.add(particle);
        JMenuItem gravitypoint = new JMenuItem("Gravity Point");
        gravitypoint.addActionListener(e -> {particleType = 1; EngineMethods.displayParticleType();});
        particleTypes.add(gravitypoint);
        JMenuItem emitter = new JMenuItem("Emitter");
        emitter.addActionListener(e -> {particleType = 2; EngineMethods.displayParticleType();});
        particleTypes.add(emitter);
        JMenuItem flux = new JMenuItem("Flux");
        flux.addActionListener(e -> {particleType = 3; EngineMethods.displayParticleType();});
        particleTypes.add(flux);
        JMenuItem qed = new JMenuItem("Q.E.D");
        qed.addActionListener(e -> {particleType = 4; EngineMethods.displayParticleType();});
        particleTypes.add(qed);
        JMenuItem ions = new JMenuItem("Ion");
        ions.addActionListener(e -> {particleType = 5; EngineMethods.displayParticleType();});
        particleTypes.add(ions);
        JMenuItem blackhole = new JMenuItem("Black Hole");
        blackhole.addActionListener(e -> {particleType = 6; EngineMethods.displayParticleType();});
        particleTypes.add(blackhole);
        JMenuItem duplex = new JMenuItem("Duplex");
        duplex.addActionListener(e -> {particleType = 7; EngineMethods.displayParticleType();});
        particleTypes.add(duplex);
        JMenuItem portal = new JMenuItem("Portal");
        portal.addActionListener(e -> {particleType = 8; EngineMethods.displayParticleType();});
        particleTypes.add(portal);

        JMenu gravitationModes = new JMenu("Gravitation Modes");
        JMenuItem default_force = new JMenuItem("Default Force");
        default_force.addActionListener(e -> ptGravitationInt = 0);
        gravitationModes.add(default_force);
        JMenuItem cos_sin = new JMenuItem("Cosine and Sine");
        cos_sin.addActionListener(e -> ptGravitationInt = 1);
        gravitationModes.add(cos_sin);
        JMenuItem arc_tan = new JMenuItem("Arc Tangent");
        arc_tan.addActionListener(e -> ptGravitationInt = 2);
        gravitationModes.add(arc_tan);
        JMenuItem h_wave = new JMenuItem("H-Wave");
        h_wave.addActionListener(e -> ptGravitationInt = 3);
        gravitationModes.add(h_wave);
        JMenuItem v_wave = new JMenuItem("V-Wave");
        v_wave.addActionListener(e -> ptGravitationInt = 4);
        gravitationModes.add(v_wave);
        JMenuItem spirals = new JMenuItem("Spirals");
        spirals.addActionListener(e -> ptGravitationInt = 5);
        gravitationModes.add(spirals);
        JMenuItem repellent = new JMenuItem("Repellent");
        repellent.addActionListener(e -> ptGravitationInt = 6);
        gravitationModes.add(repellent);
        JMenuItem organic = new JMenuItem("Organic");
        organic.addActionListener(e -> ptGravitationInt = 7);
        gravitationModes.add(organic);

        modes.add(particleTypes);
        modes.add(gravitationModes);
        menuBar.add(modes);
    }

    private static String isPaused() {
        if (isPaused) return "Resume Engine"; else return "Pause Engine";
    }
    public static void updateState() {enginepause.setText(isPaused());}
    private static String getState(boolean b) {if (b) return "On"; else return "Off";}
}
