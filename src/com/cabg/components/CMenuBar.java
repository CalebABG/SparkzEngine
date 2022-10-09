package com.cabg.components;

import com.cabg.core.BackgroundThread;
import com.cabg.core.EngineMethods;
import com.cabg.core.EngineThemes;
import com.cabg.enums.EngineMode;
import com.cabg.enums.GravitationMode;
import com.cabg.enums.ParticleType;
import com.cabg.gui.*;
import com.cabg.utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.cabg.core.EngineMethods.createEngineInstructionsWindow;
import static com.cabg.core.EngineMethods.createGraphInstructionsWindow;
import static com.cabg.core.EngineVariables.EFrame;
import static com.cabg.core.EngineVariables.engineSettings;

public class CMenuBar extends JMenuBar {
    public Color bgColor = new Color(20, 23, 25).brighter();
    private static JMenuItem enginePauseMenuItem;

    private static final Font font1 = new Font(Font.SERIF, Font.PLAIN, 21);
    public static Font menuItemFont = new Font(Font.SERIF, Font.PLAIN, 18);
    public static ArrayList<JMenu> menus = new ArrayList<>();
    public static ArrayList<JMenuItem> menuItems = new ArrayList<>();

    public static JRadioButtonMenuItem[] pModes, pTypes, pGravModes;
    public static ButtonGroup particleModesGroup, particleTypesGroup, particleGravitationGroup;

    /*Note: Check that accelerators don't affect changing variables*/
    public CMenuBar() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        this.setBorder(BorderFactory.createLineBorder(bgColor, 1, false));
        this.add(Box.createHorizontalStrut(11));

        //File Begin
        JMenu mnFile = new JMenu("File");
        mnFile.setFont(font1);
        mnFile.setForeground(Color.white);
        this.add(mnFile);
        this.add(Box.createHorizontalStrut(11));
        menus.add(mnFile);
        //File End

        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(font1);
        exit.addActionListener(e -> BackgroundThread.run(QuitWindow::getInstance));
        mnFile.add(exit);
        menuItems.add(exit);

        //Edit Begin
        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setFont(font1);
        mnEdit.setForeground(Color.white);
        this.add(mnEdit);
        menus.add(mnEdit);

        JMenuItem trimMoleculeListsMenuItem = new JMenuItem("Trim Molecule Lists");
        trimMoleculeListsMenuItem.addActionListener(e -> EngineMethods.trimMoleculeLists());
        mnEdit.add(trimMoleculeListsMenuItem);
        menuItems.add(trimMoleculeListsMenuItem);

        JMenuItem clearAllMoleculesMenuItem = new JMenuItem("Clear All Molecules");
        clearAllMoleculesMenuItem.addActionListener(e -> EngineMethods.clearAllMoleculeLists());
        mnEdit.add(clearAllMoleculesMenuItem);
        menuItems.add(clearAllMoleculesMenuItem);
        //Edit End

        this.add(Box.createHorizontalStrut(11));

        //Windows Begin
        JMenu mnUIWindows = new JMenu("Windows");
        mnUIWindows.setForeground(Color.white);
        mnUIWindows.setFont(font1);
        this.add(mnUIWindows);
        menus.add(mnUIWindows);

        JMenuItem optionsMenu = new JMenuItem("Options Menu");
        optionsMenu.addActionListener(e -> BackgroundThread.run(OptionsMenu::getInstance));
        mnUIWindows.add(optionsMenu);
        menuItems.add(optionsMenu);

        JMenuItem stats_panel = new JMenuItem("Stats Panel");
        stats_panel.addActionListener(e -> BackgroundThread.run(StatsPanel::getInstance));
        mnUIWindows.add(stats_panel);
        menuItems.add(stats_panel);

        JMenuItem sliderUI = new JMenuItem("Slide Editor");
        sliderUI.addActionListener(e -> BackgroundThread.run(ParticleSlideEditor::getInstance));
        mnUIWindows.add(sliderUI);
        menuItems.add(sliderUI);

        JMenuItem exceptionUI = new JMenuItem("Exception Log");
        exceptionUI.addActionListener(e -> BackgroundThread.run(ExceptionLogger::getInstance));
        mnUIWindows.add(exceptionUI);
        menuItems.add(exceptionUI);

        JMenuItem colorEditor = new JMenuItem("Color Editor");
        colorEditor.addActionListener(e -> BackgroundThread.run(ReactiveColorsEditor::getInstance));
        mnUIWindows.add(colorEditor);
        menuItems.add(colorEditor);

        JMenuItem particleGraphUI = new JMenuItem("Graph Editor");
        particleGraphUI.addActionListener(e -> BackgroundThread.run(ParticleGrapher::getInstance));
        mnUIWindows.add(particleGraphUI);
        menuItems.add(particleGraphUI);

        JMenuItem vphysicseditor = new JMenuItem("Physics Editor");
        vphysicseditor.addActionListener(e -> BackgroundThread.run(() -> PhysicsEditor.getInstance(EFrame)));
        mnUIWindows.add(vphysicseditor);
        menuItems.add(vphysicseditor);

        JMenuItem timemachine = new JMenuItem("Color Time Machine");
        timemachine.addActionListener(e -> BackgroundThread.run(ReactiveColorsTimeMachine::getInstance));
        mnUIWindows.add(timemachine);
        menuItems.add(timemachine);

        this.add(Box.createHorizontalStrut(11));

        JMenuItem customForces = new JMenuItem("Organic Forces Editor");
        customForces.addActionListener(e -> BackgroundThread.run(() -> OrganicForcesEditor.getInstance(EFrame)));
        mnUIWindows.add(customForces);
        menuItems.add(customForces);

        JMenuItem flowfieldUI = new JMenuItem("Flow Field Editor");
        flowfieldUI.addActionListener(e -> BackgroundThread.run(() -> FlowFieldEditor.getInstance(EFrame)));
        mnUIWindows.add(flowfieldUI);
        menuItems.add(flowfieldUI);
        //Windows End

        setUpModes();
        menuItems.addAll(Arrays.asList(pGravModes));
        menuItems.addAll(Arrays.asList(pModes));
        menuItems.addAll(Arrays.asList(pTypes));

        this.add(Box.createHorizontalStrut(11));

        JMenu mnSettings = new JMenu("Settings");
        mnSettings.setForeground(Color.white);
        mnSettings.setFont(font1);
        this.add(mnSettings);
        menus.add(mnSettings);

        enginePauseMenuItem = new JMenuItem(isPaused());
        enginePauseMenuItem.addActionListener(e -> {
            engineSettings.togglePause();
            enginePauseMenuItem.setText(isPaused());
            EngineMethods.setEngineTitle();
        });
        mnSettings.add(enginePauseMenuItem);
        menuItems.add(enginePauseMenuItem);

        JMenuItem settingsSave = new JMenuItem("Save Settings");
        settingsSave.addActionListener(e -> Settings.saveSettings());
        mnSettings.add(settingsSave);
        menuItems.add(settingsSave);

        JMenuItem settingsLoad = new JMenuItem("Load Settings");
        settingsLoad.addActionListener(e -> Settings.loadSettings());
        mnSettings.add(settingsLoad);
        menuItems.add(settingsLoad);

        //Add themes
        JMenu theme_menu = new JMenu("Themes");
        theme_menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        theme_menu.setOpaque(true); //Have to set opaque inside another menu
        mnSettings.add(theme_menu);
        menuItems.add(theme_menu);

        JMenuItem theme1 = new JMenuItem("Default");
        theme1.addActionListener(e -> EngineThemes.defaultTheme());
        theme1.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme1);
        menuItems.add(theme1);

        JMenuItem theme2 = new JMenuItem("Midnight Blues");
        theme2.addActionListener(e -> EngineThemes.midnightBlues());
        theme2.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme2);
        menuItems.add(theme2);

        JMenuItem theme3 = new JMenuItem("Dark Mocha");
        theme3.addActionListener(e -> EngineThemes.darkMocha());
        theme3.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme3);
        menuItems.add(theme3);

        JMenuItem theme4 = new JMenuItem("Mild Tangerine");
        theme4.addActionListener(e -> EngineThemes.darkTangerine());
        theme4.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme4);
        menuItems.add(theme4);

        JMenuItem theme5 = new JMenuItem("Serene Sienna");
        theme5.addActionListener(e -> EngineThemes.sienna());
        theme5.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme5);
        menuItems.add(theme5);

        JMenuItem theme6 = new JMenuItem("WinterGreen Dream");
        theme6.addActionListener(e -> EngineThemes.winterGreenDream());
        theme6.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme6);
        menuItems.add(theme6);

        JMenuItem theme7 = new JMenuItem("Vegas Gold");
        theme7.addActionListener(e -> EngineThemes.vegasGold());
        theme7.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme7);
        menuItems.add(theme7);

        JMenuItem theme8 = new JMenuItem("RoseWood");
        theme8.addActionListener(e -> EngineThemes.roseWood());
        theme8.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme8);
        menuItems.add(theme8);

        JMenuItem theme9 = new JMenuItem("Antique Pink");
        theme9.addActionListener(e -> EngineThemes.antiquePink());
        theme9.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme9);
        menuItems.add(theme9);

        JMenuItem theme10 = new JMenuItem("Night Violet");
        theme10.addActionListener(e -> EngineThemes.nightViolet());
        theme10.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme10);
        menuItems.add(theme10);

        this.add(Box.createHorizontalStrut(11));

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(font1);
        mnHelp.setForeground(Color.white);
        menus.add(mnHelp);

        JMenuItem helpInstructions = new JMenuItem("Engine Instructions");
        helpInstructions.addActionListener(e -> createEngineInstructionsWindow(EFrame));
        mnHelp.add(helpInstructions);
        menuItems.add(helpInstructions);

        JMenuItem helpGraphInstructions = new JMenuItem("Graph Instructions");
        helpGraphInstructions.addActionListener(e -> createGraphInstructionsWindow(EFrame));
        mnHelp.add(helpGraphInstructions);
        this.add(mnHelp);
        menuItems.add(helpGraphInstructions);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            JMenuItem accessibility = new JMenuItem("On-Screen Keyboard");
            accessibility.addActionListener(e -> {
                try {
                    Runtime.getRuntime().exec("cmd /c osk");
                } catch (Exception f) {
                    ExceptionLogger.append(f);
                }
            });
            mnHelp.add(accessibility);
            menuItems.add(accessibility);
        }

        //Setup design
        for (JMenu menu : menus) {
            menu.setFont(font1);
            menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
            menu.setForeground(Color.white);
        }

        for (JMenuItem menuItem : menuItems) {
            menuItem.setOpaque(true);
            menuItem.setBackground(bgColor);
            menuItem.setForeground(Color.white);
            menuItem.setFont(menuItemFont);
        }

        updateState();
        //updateAllRadios();
    }

    private void setUpModes() {
        JMenu modes = new JMenu("Modes");
        modes.setForeground(Color.white);
        modes.setFont(font1);
        this.add(modes);
        menus.add(modes);

        JMenu particleModes = new JMenu("Particle Modes");
        particleModes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        particleModes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(particleModes);

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

        for (JRadioButtonMenuItem b : pModes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.engineMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : pModes) {
            b.addActionListener(e -> engineSettings.engineMode = EngineMode.values()[Integer.parseInt(particleModesGroup.getSelection().getActionCommand())]);
        }

        modes.add(particleModes);

        ////
        JMenu particleTypes = new JMenu("Particle Types");
        particleTypes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        particleTypes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(particleTypes);

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

        for (JRadioButtonMenuItem b : pTypes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.particleType.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : pTypes) {
            b.addActionListener(e -> engineSettings.particleType = ParticleType.values()[Integer.parseInt(particleTypesGroup.getSelection().getActionCommand())]);
        }

        modes.add(particleTypes);

        ///
        JMenu gravitationModes = new JMenu("Gravitation Modes");
        gravitationModes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        gravitationModes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(gravitationModes);

        particleGravitationGroup = new ButtonGroup();

        pGravModes = new JRadioButtonMenuItem[9];

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

        pGravModes[8] = new JRadioButtonMenuItem("Flow Field");
        pGravModes[8].setActionCommand("8");
        particleGravitationGroup.add(pGravModes[8]);
        gravitationModes.add(pGravModes[8]);

        for (JRadioButtonMenuItem b : pGravModes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.gravitationMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : pGravModes) {
            b.addActionListener(e -> engineSettings.gravitationMode = GravitationMode.values()[Integer.parseInt(particleGravitationGroup.getSelection().getActionCommand())]);
        }

        modes.add(gravitationModes);
        this.add(modes);
    }

    public static void updateParticleModesRadios() {
        for (JRadioButtonMenuItem b : pModes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.engineMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
    }

    public static void updateParticleTypesRadios() {
        for (JRadioButtonMenuItem b : pTypes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.particleType.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
    }

    public static void updateGravitationModesRadios() {
        for (JRadioButtonMenuItem b : pGravModes) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.gravitationMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
    }

    public static void updateAllRadios() {
        updateParticleModesRadios();
        updateParticleTypesRadios();
        updateGravitationModesRadios();
        updateState();
    }

    private static String isPaused() {
        return engineSettings.paused ? "Resume Engine" : "Pause Engine";
    }

    public static void updateState() {
        enginePauseMenuItem.setText(isPaused());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
