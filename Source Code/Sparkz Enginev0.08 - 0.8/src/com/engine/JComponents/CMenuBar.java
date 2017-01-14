package com.engine.JComponents;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.*;
import com.engine.MGrapher.ParticleGraph;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EngineMethods.displayEngineMode;
import static com.engine.EngineHelpers.EngineMethods.displayParticleType;

import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;
import java.awt.event.KeyEvent;

public class CMenuBar extends JMenuBar {
    public static Color bgColor = new Color(20, 23, 25).brighter();
    private static CMenuBar menuBar;
    private static JMenuItem exit, settingsSave, settingsLoad, settingsUI, sliderUI,
            thinkingParticlesUI,particleGraphUI, helpInstructions, helpGraphInstructions, optionsMenu, enginepause;
    private static Font font1 = new Font(Font.SERIF, Font.PLAIN, 21);
    private static final int memThreshold = 75;
    public static JRadioButtonMenuItem[] pModes,pTypes, pGravModes;
    public static ButtonGroup particleModesGroup, particleTypesGroup, particleGravitationGroup;

    public static CMenuBar getMenuBar() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1){e1.printStackTrace();}
        menuBar = new CMenuBar();
        menuBar.setBorder(BorderFactory.createLineBorder(bgColor, 2, false));
        menuBar.add(Box.createHorizontalStrut(11));

        //File Begin
        JMenu mnFile = new JMenu("File");
        mnFile.setFont(font1);
        mnFile.setForeground(Color.white);
        menuBar.add(mnFile);
        menuBar.add(Box.createHorizontalStrut(11));
        //File End

        exit = new JMenuItem("Exit");
        exit.addActionListener(e -> {if (e.getSource() == exit) QuitWindow.getInstance();});
        mnFile.add(exit);

        //Edit Begin
        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setFont(font1);
        mnEdit.setForeground(Color.white);
        menuBar.add(mnEdit);

        JMenuItem trimParticleArrays = new JMenuItem("Trim Particle Arrays");

        trimParticleArrays.addActionListener(e -> EngineMethods.trimParticleArrays());
        mnEdit.add(trimParticleArrays);

        JMenuItem clearParticleArrays = new JMenuItem("Clear All Arrays");

        clearParticleArrays.addActionListener(e -> EngineMethods.clearParticleArrays());
        mnEdit.add(clearParticleArrays);
        //Edit End

        menuBar.add(Box.createHorizontalStrut(11));

        //ShortCuts Begin
        JMenu mnUIWindows = new JMenu("Short-Cuts");
        mnUIWindows.setForeground(Color.white);
        mnUIWindows.setFont(font1);
        menuBar.add(mnUIWindows);

        optionsMenu = new JMenuItem("Options Menu");
        optionsMenu.addActionListener(e -> {if (e.getSource() == optionsMenu) {OptionsMenu.getInstance();}});
        mnUIWindows.add(optionsMenu);

        JMenuItem stats_panel = new JMenuItem("Stats Panel");
        stats_panel.addActionListener(e -> {if (e.getSource() == stats_panel) {StatsPanel.getInstance();}});
        mnUIWindows.add(stats_panel);

        sliderUI = new JMenuItem("Slide Editor");
        sliderUI.addActionListener(e -> {if (e.getSource() == sliderUI) {SlideEditor.getInstance();}});
        mnUIWindows.add(sliderUI);

        JMenuItem exceptionUI = new JMenuItem("Exception Log");

        exceptionUI.addActionListener(e -> {if (e.getSource() == exceptionUI) {EException.getInstance();}});
        mnUIWindows.add(exceptionUI);

        thinkingParticlesUI = new JMenuItem("Particle Color Editor");
        thinkingParticlesUI.addActionListener(e -> {if (e.getSource() == thinkingParticlesUI) {ParticleColor.getInstance();}});
        mnUIWindows.add(thinkingParticlesUI);

        JMenuItem timemachine = new JMenuItem("Color Time Machine");
        timemachine.addActionListener(e -> {if (e.getSource() == timemachine) {ColorTimeMachine.getInstance();}});
        mnUIWindows.add(timemachine);

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
        mnSettings.setForeground(Color.white);
        mnSettings.setFont(font1);
        menuBar.add(mnSettings);

        enginepause = new JMenuItem(isPaused());
        enginepause.addActionListener(e -> {isPaused = EngineMethods.toggle(isPaused); enginepause.setText(isPaused()); EngineMethods.setEngineTitleState();});
        mnSettings.add(enginepause);

        settingsSave = new JMenuItem("Save Settings");
        settingsSave.addActionListener(e -> {if (e.getSource() == settingsSave) {Settings.saveSettings();}});
        mnSettings.add(settingsSave);
        settingsLoad = new JMenuItem("Load Settings");

        settingsLoad.addActionListener(e -> {if (e.getSource() == settingsLoad) {Settings.loadSettings(); updateAllRadios();}});
        mnSettings.add(settingsLoad);
        settingsUI = new JMenuItem("Settings Editor");

        settingsUI.addActionListener(e -> {if (e.getSource() == settingsUI) {SettingsEditor.getInstance();}});
        mnSettings.add(settingsUI);

        JMenuItem memclean = new JMenuItem("Clean Memory");
        memclean.setToolTipText(H5Wrapper.H(3, "Cleans memory if free memory is less than " + memThreshold + " MB"));
        memclean.addActionListener(e -> {
            if (e.getSource() == memclean) {if ((Runtime.getRuntime().freeMemory() / (Math.pow(1024, 2))) <= memThreshold) {System.gc();}}});
        mnSettings.add(memclean);

        menuBar.add(Box.createHorizontalStrut(11));
        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(font1);
        mnHelp.setForeground(Color.white);

        helpInstructions = new JMenuItem("Particle Engine Instructions");
        helpInstructions.addActionListener(e -> {if (e.getSource() == helpInstructions) {EngineInstructions.getInstance();}});
        mnHelp.add(helpInstructions);

        helpGraphInstructions = new JMenuItem("Particle Graph Instructions");
        helpGraphInstructions.addActionListener(e -> {if (e.getSource() == helpGraphInstructions) {GraphInstructions.getInstance(EFrame);}});
        mnHelp.add(helpGraphInstructions);
        menuBar.add(mnHelp);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            JMenuItem accessibility = new JMenuItem("On-Screen Keyboard");
            accessibility.addActionListener(e -> {try {Runtime.getRuntime().exec("cmd /c osk");} catch (Exception ez) {EException.append(ez);}});
            mnHelp.add(accessibility);
        }

        updateState();
        return menuBar;
    }

    private static void setUpModes() {
        JMenu modes = new JMenu("Modes");
        modes.setFont(font1);
        modes.setForeground(Color.white);

        JMenu particleModes = new JMenu("Particle Modes");
        particleModesGroup = new ButtonGroup();

        pModes = new JRadioButtonMenuItem[5];

        pModes[0] = new JRadioButtonMenuItem("Normal Mode");
        pModes[0].setActionCommand("0");
        particleModesGroup.add(pModes[0]);
        particleModes.add(pModes[0]);

        pModes[1] = new JRadioButtonMenuItem("Multi Mode");
        pModes[1].setActionCommand("1");
        particleModesGroup.add(pModes[1]);
        particleModes.add(pModes[1]);

        pModes[2] = new JRadioButtonMenuItem("Fireworks Mode");
        pModes[2].setActionCommand("2");
        particleModesGroup.add(pModes[2]);
        particleModes.add(pModes[2]);

        pModes[3] = new JRadioButtonMenuItem("Graph Mode");
        pModes[3].setActionCommand("3");
        particleModesGroup.add(pModes[3]);
        particleModes.add(pModes[3]);

        pModes[4] = new JRadioButtonMenuItem("Ragdoll Mode");
        pModes[4].setActionCommand("4");
        particleModesGroup.add(pModes[4]);
        particleModes.add(pModes[4]);

        for (JRadioButtonMenuItem b : pModes) {if (Integer.parseInt(b.getActionCommand()) == switchMode) {b.setSelected(true); break;}}
        for (JRadioButtonMenuItem b : pModes) {b.addActionListener(e -> {switchMode = Integer.parseInt(particleModesGroup.getSelection().getActionCommand()); displayEngineMode();});}

        modes.add(particleModes);

        ////
        JMenu particleTypes = new JMenu("Particle Types");
        particleTypesGroup = new ButtonGroup();

        pTypes = new JRadioButtonMenuItem[9];

        pTypes[0] = new JRadioButtonMenuItem("Particle");
        pTypes[0].setActionCommand("0");
        particleTypesGroup.add(pTypes[0]);
        particleTypes.add(pTypes[0]);

        pTypes[1] = new JRadioButtonMenuItem("Gravity Point");
        pTypes[1].setActionCommand("1");
        particleTypesGroup.add(pTypes[1]);
        particleTypes.add(pTypes[1]);

        pTypes[2] = new JRadioButtonMenuItem("Emitter");
        pTypes[2].setActionCommand("2");
        particleTypesGroup.add(pTypes[2]);
        particleTypes.add(pTypes[2]);

        pTypes[3] = new JRadioButtonMenuItem("Flux");
        pTypes[3].setActionCommand("3");
        particleTypesGroup.add(pTypes[3]);
        particleTypes.add(pTypes[3]);

        pTypes[4] = new JRadioButtonMenuItem("Q.E.D");
        pTypes[4].setActionCommand("4");
        particleTypesGroup.add(pTypes[4]);
        particleTypes.add(pTypes[4]);

        pTypes[5] = new JRadioButtonMenuItem("Ion");
        pTypes[5].setActionCommand("5");
        particleTypesGroup.add(pTypes[5]);
        particleTypes.add(pTypes[5]);

        pTypes[6] = new JRadioButtonMenuItem("Black Hole");
        pTypes[6].setActionCommand("6");
        particleTypesGroup.add(pTypes[6]);
        particleTypes.add(pTypes[6]);

        pTypes[7] = new JRadioButtonMenuItem("Duplex");
        pTypes[7].setActionCommand("7");
        particleTypesGroup.add(pTypes[7]);
        particleTypes.add(pTypes[7]);

        pTypes[8] = new JRadioButtonMenuItem("Portal");
        pTypes[8].setActionCommand("8");
        particleTypesGroup.add(pTypes[8]);
        particleTypes.add(pTypes[8]);

        for (JRadioButtonMenuItem b : pTypes) {if (Integer.parseInt(b.getActionCommand()) == particleType) {b.setSelected(true); break;}}
        for (JRadioButtonMenuItem b : pTypes) {
            b.addActionListener(e -> {particleType = Integer.parseInt(particleTypesGroup.getSelection().getActionCommand()); displayParticleType();});
        }

        modes.add(particleTypes);

        ///
        JMenu gravitationModes = new JMenu("Gravitation Modes");
        particleGravitationGroup = new ButtonGroup();

        pGravModes = new JRadioButtonMenuItem[8];

        pGravModes[0] = new JRadioButtonMenuItem("Default Force");
        pGravModes[0].setActionCommand("0");
        particleGravitationGroup.add(pGravModes[0]);
        gravitationModes.add(pGravModes[0]);

        pGravModes[1] = new JRadioButtonMenuItem("Cosine and Sine");
        pGravModes[1].setActionCommand("1");
        particleGravitationGroup.add(pGravModes[1]);
        gravitationModes.add(pGravModes[1]);

        pGravModes[2] = new JRadioButtonMenuItem("Arc Tangent");
        pGravModes[2].setActionCommand("2");
        particleGravitationGroup.add(pGravModes[2]);
        gravitationModes.add(pGravModes[2]);

        pGravModes[3] = new JRadioButtonMenuItem("Horizontal Wave");
        pGravModes[3].setActionCommand("3");
        particleGravitationGroup.add(pGravModes[3]);
        gravitationModes.add(pGravModes[3]);

        pGravModes[4] = new JRadioButtonMenuItem("Vertical Wave");
        pGravModes[4].setActionCommand("4");
        particleGravitationGroup.add(pGravModes[4]);
        gravitationModes.add(pGravModes[4]);

        pGravModes[5] = new JRadioButtonMenuItem("Spirals");
        pGravModes[5].setActionCommand("5");
        particleGravitationGroup.add(pGravModes[5]);
        gravitationModes.add(pGravModes[5]);

        pGravModes[6] = new JRadioButtonMenuItem("Repellent");
        pGravModes[6].setActionCommand("6");
        particleGravitationGroup.add(pGravModes[6]);
        gravitationModes.add(pGravModes[6]);

        pGravModes[7] = new JRadioButtonMenuItem("Organic");
        pGravModes[7].setActionCommand("7");
        particleGravitationGroup.add(pGravModes[7]);
        gravitationModes.add(pGravModes[7]);

        for (JRadioButtonMenuItem b : pGravModes) {if (Integer.parseInt(b.getActionCommand()) == ptGravitationInt) {b.setSelected(true); break;}}
        for (JRadioButtonMenuItem b : pGravModes) {b.addActionListener(e -> ptGravitationInt = Integer.parseInt(particleGravitationGroup.getSelection().getActionCommand()));}

        modes.add(gravitationModes);
        menuBar.add(modes);
    }

    public static void updateParticleModesRadios() {
        for (JRadioButtonMenuItem b : pModes) {if (Integer.parseInt(b.getActionCommand()) == switchMode) {b.setSelected(true); break;}}}
    public static void updateParticleTypesRadios() {
        for (JRadioButtonMenuItem b : pTypes) {if (Integer.parseInt(b.getActionCommand()) == particleType) {b.setSelected(true); break;}}}
    public static void updateGravitationModesRadios() {
        for (JRadioButtonMenuItem b : pGravModes) {if (Integer.parseInt(b.getActionCommand()) == ptGravitationInt) {b.setSelected(true); break;}}}
    public static void updateAllRadios() {
        updateParticleModesRadios(); updateParticleTypesRadios(); updateGravitationModesRadios();}

    private static String isPaused() {if (isPaused) return "Resume Engine"; else return "Pause Engine";}
    public static void updateState() {enginepause.setText(isPaused());}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
    }
}
