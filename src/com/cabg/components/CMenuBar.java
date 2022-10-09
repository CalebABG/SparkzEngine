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
    public static JRadioButtonMenuItem[] engineModesMenuItems, particleTypesMenuItems, gravitationModesMenuItems;
    public static ButtonGroup particleModesGroup, particleTypesGroup, particleGravitationGroup;

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
        menuItems.addAll(Arrays.asList(gravitationModesMenuItems));
        menuItems.addAll(Arrays.asList(engineModesMenuItems));
        menuItems.addAll(Arrays.asList(particleTypesMenuItems));

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

        engineModesMenuItems = new JRadioButtonMenuItem[5];

        engineModesMenuItems[0] = new JRadioButtonMenuItem("Normal Mode");
        engineModesMenuItems[0].setActionCommand("0");
        particleModesGroup.add(engineModesMenuItems[0]);
        particleModes.add(engineModesMenuItems[0]);

        engineModesMenuItems[1] = new JRadioButtonMenuItem("Multi Mode");
        engineModesMenuItems[1].setActionCommand("1");
        particleModesGroup.add(engineModesMenuItems[1]);
        particleModes.add(engineModesMenuItems[1]);

        engineModesMenuItems[2] = new JRadioButtonMenuItem("Fireworks Mode");
        engineModesMenuItems[2].setActionCommand("2");
        particleModesGroup.add(engineModesMenuItems[2]);
        particleModes.add(engineModesMenuItems[2]);

        engineModesMenuItems[3] = new JRadioButtonMenuItem("Graph Mode");
        engineModesMenuItems[3].setActionCommand("3");
        particleModesGroup.add(engineModesMenuItems[3]);
        particleModes.add(engineModesMenuItems[3]);

        engineModesMenuItems[4] = new JRadioButtonMenuItem("Ragdoll Mode");
        engineModesMenuItems[4].setActionCommand("4");
        particleModesGroup.add(engineModesMenuItems[4]);
        particleModes.add(engineModesMenuItems[4]);

        for (JRadioButtonMenuItem b : engineModesMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.engineMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : engineModesMenuItems) {
            b.addActionListener(e -> engineSettings.engineMode = EngineMode.values()[Integer.parseInt(particleModesGroup.getSelection().getActionCommand())]);
        }

        modes.add(particleModes);

        JMenu particleTypes = new JMenu("Particle Types");
        particleTypes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        particleTypes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(particleTypes);

        particleTypesGroup = new ButtonGroup();

        particleTypesMenuItems = new JRadioButtonMenuItem[9];

        particleTypesMenuItems[0] = new JRadioButtonMenuItem("Particle");
        particleTypesMenuItems[0].setActionCommand("0");
        particleTypesGroup.add(particleTypesMenuItems[0]);
        particleTypes.add(particleTypesMenuItems[0]);

        particleTypesMenuItems[1] = new JRadioButtonMenuItem("Gravity Point");
        particleTypesMenuItems[1].setActionCommand("1");
        particleTypesGroup.add(particleTypesMenuItems[1]);
        particleTypes.add(particleTypesMenuItems[1]);

        particleTypesMenuItems[2] = new JRadioButtonMenuItem("Emitter");
        particleTypesMenuItems[2].setActionCommand("2");
        particleTypesGroup.add(particleTypesMenuItems[2]);
        particleTypes.add(particleTypesMenuItems[2]);

        particleTypesMenuItems[3] = new JRadioButtonMenuItem("Flux");
        particleTypesMenuItems[3].setActionCommand("3");
        particleTypesGroup.add(particleTypesMenuItems[3]);
        particleTypes.add(particleTypesMenuItems[3]);

        particleTypesMenuItems[4] = new JRadioButtonMenuItem("Q.E.D");
        particleTypesMenuItems[4].setActionCommand("4");
        particleTypesGroup.add(particleTypesMenuItems[4]);
        particleTypes.add(particleTypesMenuItems[4]);

        particleTypesMenuItems[5] = new JRadioButtonMenuItem("Ion");
        particleTypesMenuItems[5].setActionCommand("5");
        particleTypesGroup.add(particleTypesMenuItems[5]);
        particleTypes.add(particleTypesMenuItems[5]);

        particleTypesMenuItems[6] = new JRadioButtonMenuItem("Black Hole");
        particleTypesMenuItems[6].setActionCommand("6");
        particleTypesGroup.add(particleTypesMenuItems[6]);
        particleTypes.add(particleTypesMenuItems[6]);

        particleTypesMenuItems[7] = new JRadioButtonMenuItem("Duplex");
        particleTypesMenuItems[7].setActionCommand("7");
        particleTypesGroup.add(particleTypesMenuItems[7]);
        particleTypes.add(particleTypesMenuItems[7]);

        particleTypesMenuItems[8] = new JRadioButtonMenuItem("Portal");
        particleTypesMenuItems[8].setActionCommand("8");
        particleTypesGroup.add(particleTypesMenuItems[8]);
        particleTypes.add(particleTypesMenuItems[8]);

        for (JRadioButtonMenuItem b : particleTypesMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.particleType.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : particleTypesMenuItems) {
            b.addActionListener(e -> engineSettings.particleType = ParticleType.values()[Integer.parseInt(particleTypesGroup.getSelection().getActionCommand())]);
        }

        modes.add(particleTypes);

        JMenu gravitationModes = new JMenu("Gravitation Modes");
        gravitationModes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        gravitationModes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(gravitationModes);

        particleGravitationGroup = new ButtonGroup();

        gravitationModesMenuItems = new JRadioButtonMenuItem[9];

        gravitationModesMenuItems[0] = new JRadioButtonMenuItem("Default Force");
        gravitationModesMenuItems[0].setActionCommand("0");
        particleGravitationGroup.add(gravitationModesMenuItems[0]);
        gravitationModes.add(gravitationModesMenuItems[0]);

        gravitationModesMenuItems[1] = new JRadioButtonMenuItem("Cosine and Sine");
        gravitationModesMenuItems[1].setActionCommand("1");
        particleGravitationGroup.add(gravitationModesMenuItems[1]);
        gravitationModes.add(gravitationModesMenuItems[1]);

        gravitationModesMenuItems[2] = new JRadioButtonMenuItem("Arc Tangent");
        gravitationModesMenuItems[2].setActionCommand("2");
        particleGravitationGroup.add(gravitationModesMenuItems[2]);
        gravitationModes.add(gravitationModesMenuItems[2]);

        gravitationModesMenuItems[3] = new JRadioButtonMenuItem("Horizontal Wave");
        gravitationModesMenuItems[3].setActionCommand("3");
        particleGravitationGroup.add(gravitationModesMenuItems[3]);
        gravitationModes.add(gravitationModesMenuItems[3]);

        gravitationModesMenuItems[4] = new JRadioButtonMenuItem("Vertical Wave");
        gravitationModesMenuItems[4].setActionCommand("4");
        particleGravitationGroup.add(gravitationModesMenuItems[4]);
        gravitationModes.add(gravitationModesMenuItems[4]);

        gravitationModesMenuItems[5] = new JRadioButtonMenuItem("Spirals");
        gravitationModesMenuItems[5].setActionCommand("5");
        particleGravitationGroup.add(gravitationModesMenuItems[5]);
        gravitationModes.add(gravitationModesMenuItems[5]);

        gravitationModesMenuItems[6] = new JRadioButtonMenuItem("Repellent");
        gravitationModesMenuItems[6].setActionCommand("6");
        particleGravitationGroup.add(gravitationModesMenuItems[6]);
        gravitationModes.add(gravitationModesMenuItems[6]);

        gravitationModesMenuItems[7] = new JRadioButtonMenuItem("Organic");
        gravitationModesMenuItems[7].setActionCommand("7");
        particleGravitationGroup.add(gravitationModesMenuItems[7]);
        gravitationModes.add(gravitationModesMenuItems[7]);

        gravitationModesMenuItems[8] = new JRadioButtonMenuItem("Flow Field");
        gravitationModesMenuItems[8].setActionCommand("8");
        particleGravitationGroup.add(gravitationModesMenuItems[8]);
        gravitationModes.add(gravitationModesMenuItems[8]);

        for (JRadioButtonMenuItem b : gravitationModesMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.gravitationMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : gravitationModesMenuItems) {
            b.addActionListener(e -> engineSettings.gravitationMode = GravitationMode.values()[Integer.parseInt(particleGravitationGroup.getSelection().getActionCommand())]);
        }

        modes.add(gravitationModes);
        this.add(modes);
    }

    public static void updateEngineModeRadios() {
        updateRadios(engineModesMenuItems, engineSettings.engineMode.ordinal());
    }

    public static void updateParticleTypeRadios() {
        updateRadios(particleTypesMenuItems, engineSettings.particleType.ordinal());
    }

    public static void updateGravitationModeRadios() {
        updateRadios(gravitationModesMenuItems, engineSettings.gravitationMode.ordinal());
    }

    public static void updateRadios(JRadioButtonMenuItem[] radioButtonMenuItems, int enumPos) {
        for (JRadioButtonMenuItem b : radioButtonMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == enumPos) {
                b.setSelected(true);
                break;
            }
        }
    }

    public static void updateAllRadios() {
        updateState();
        updateEngineModeRadios();
        updateParticleTypeRadios();
        updateGravitationModeRadios();
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
