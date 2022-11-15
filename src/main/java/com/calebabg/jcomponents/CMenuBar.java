package com.calebabg.jcomponents;

import com.calebabg.core.BackgroundThread;
import com.calebabg.core.EngineMethods;
import com.calebabg.core.EngineSettings;
import com.calebabg.core.EngineThemes;
import com.calebabg.enums.EngineMode;
import com.calebabg.enums.GravitationMode;
import com.calebabg.enums.MoleculeType;
import com.calebabg.gui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static com.calebabg.core.EngineVariables.eFrame;
import static com.calebabg.core.EngineVariables.engineSettings;

public class CMenuBar extends JMenuBar {
    public static Color bgColor = new Color(20, 23, 25).brighter();
    private static JMenuItem enginePauseMenuItem;
    private static final Font font1 = new Font(Font.SERIF, Font.PLAIN, 21);
    public static Font menuItemFont = new Font(Font.SERIF, Font.PLAIN, 18);
    public static ArrayList<JMenu> menus = new ArrayList<>();
    public static ArrayList<JMenuItem> menuItems = new ArrayList<>();
    public static JRadioButtonMenuItem[] engineModesMenuItems, moleculeTypesMenuItems, gravitationModesMenuItems;
    public static ButtonGroup engineModesGroup, moleculeTypesGroup, particleGravitationGroup;

    public CMenuBar() {
        EngineThemes.setLookAndFeel();

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

        JMenuItem trimMoleculeListsMenuItem = new JMenuItem("Trim All");
        trimMoleculeListsMenuItem.addActionListener(e -> EngineMethods.trimAllEntityLists());
        mnEdit.add(trimMoleculeListsMenuItem);
        menuItems.add(trimMoleculeListsMenuItem);

        JMenuItem clearAllMoleculesMenuItem = new JMenuItem("Clear All");
        clearAllMoleculesMenuItem.addActionListener(e -> EngineMethods.clearAllEntityLists());
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

        JMenuItem exceptionUI = new JMenuItem("Exception Window");
        exceptionUI.addActionListener(e -> BackgroundThread.run(ExceptionWindow::getInstance));
        mnUIWindows.add(exceptionUI);
        menuItems.add(exceptionUI);

        JMenuItem colorEditor = new JMenuItem("Colors Editor");
        colorEditor.addActionListener(e -> BackgroundThread.run(ReactiveColorsEditor::getInstance));
        mnUIWindows.add(colorEditor);
        menuItems.add(colorEditor);

        JMenuItem particleGraphUI = new JMenuItem("Graph Editor");
        particleGraphUI.addActionListener(e -> BackgroundThread.run(ParticleGraphEditor::getInstance));
        mnUIWindows.add(particleGraphUI);
        menuItems.add(particleGraphUI);

        JMenuItem vphysicseditor = new JMenuItem("Physics Editor");
        vphysicseditor.addActionListener(e -> BackgroundThread.run(PhysicsEditor::getInstance));
        mnUIWindows.add(vphysicseditor);
        menuItems.add(vphysicseditor);

        JMenuItem timemachine = new JMenuItem("Colors Time Machine");
        timemachine.addActionListener(e -> BackgroundThread.run(ReactiveColorsTimeMachine::getInstance));
        mnUIWindows.add(timemachine);
        menuItems.add(timemachine);

        this.add(Box.createHorizontalStrut(11));

        JMenuItem customForces = new JMenuItem("Organic Forces Editor");
        customForces.addActionListener(e -> BackgroundThread.run(OrganicForcesEditor::getInstance));
        mnUIWindows.add(customForces);
        menuItems.add(customForces);

        JMenuItem flowfieldUI = new JMenuItem("Flow Field Editor");
        flowfieldUI.addActionListener(e -> BackgroundThread.run(FlowFieldEditor::getInstance));
        mnUIWindows.add(flowfieldUI);
        menuItems.add(flowfieldUI);
        //Windows End

        setUpModes();
        menuItems.addAll(Arrays.asList(gravitationModesMenuItems));
        menuItems.addAll(Arrays.asList(engineModesMenuItems));
        menuItems.addAll(Arrays.asList(moleculeTypesMenuItems));

        this.add(Box.createHorizontalStrut(11));

        JMenu mnSettings = new JMenu("Settings");
        mnSettings.setForeground(Color.white);
        mnSettings.setFont(font1);
        this.add(mnSettings);
        menus.add(mnSettings);

        enginePauseMenuItem = new JMenuItem(enginePauseStatus());
        enginePauseMenuItem.addActionListener(e -> {
            engineSettings.togglePause();
            enginePauseMenuItem.setText(enginePauseStatus());
            EngineMethods.setEngineTitle();
        });
        mnSettings.add(enginePauseMenuItem);
        menuItems.add(enginePauseMenuItem);

        JMenuItem settingsSave = new JMenuItem("Save Settings");
        settingsSave.addActionListener(e -> EngineSettings.saveSettings());
        mnSettings.add(settingsSave);
        menuItems.add(settingsSave);

        JMenuItem settingsLoad = new JMenuItem("Load Settings");
        settingsLoad.addActionListener(e -> EngineSettings.loadSettings());
        mnSettings.add(settingsLoad);
        menuItems.add(settingsLoad);

        //Add themes
        JMenu themeMenu = new JMenu("Themes");
        themeMenu.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        themeMenu.setOpaque(true); //Have to set opaque inside another menu
        mnSettings.add(themeMenu);
        menuItems.add(themeMenu);

        JMenuItem theme1 = new JMenuItem("Default");
        theme1.addActionListener(e -> EngineThemes.defaultTheme());
        theme1.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme1);
        menuItems.add(theme1);

        JMenuItem theme2 = new JMenuItem("Midnight Blues");
        theme2.addActionListener(e -> EngineThemes.midnightBlues());
        theme2.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme2);
        menuItems.add(theme2);

        JMenuItem theme3 = new JMenuItem("Dark Mocha");
        theme3.addActionListener(e -> EngineThemes.darkMocha());
        theme3.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme3);
        menuItems.add(theme3);

        JMenuItem theme4 = new JMenuItem("Mild Tangerine");
        theme4.addActionListener(e -> EngineThemes.darkTangerine());
        theme4.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme4);
        menuItems.add(theme4);

        JMenuItem theme5 = new JMenuItem("Serene Sienna");
        theme5.addActionListener(e -> EngineThemes.sienna());
        theme5.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme5);
        menuItems.add(theme5);

        JMenuItem theme6 = new JMenuItem("Winter Green");
        theme6.addActionListener(e -> EngineThemes.winterGreen());
        theme6.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme6);
        menuItems.add(theme6);

        JMenuItem theme7 = new JMenuItem("Vegas Gold");
        theme7.addActionListener(e -> EngineThemes.vegasGold());
        theme7.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme7);
        menuItems.add(theme7);

        JMenuItem theme8 = new JMenuItem("Rose Wood");
        theme8.addActionListener(e -> EngineThemes.roseWood());
        theme8.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme8);
        menuItems.add(theme8);

        JMenuItem theme9 = new JMenuItem("Antique Pink");
        theme9.addActionListener(e -> EngineThemes.antiquePink());
        theme9.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme9);
        menuItems.add(theme9);

        JMenuItem theme10 = new JMenuItem("Night Violet");
        theme10.addActionListener(e -> EngineThemes.nightViolet());
        theme10.setBorder(BorderFactory.createLineBorder(bgColor));
        themeMenu.add(theme10);
        menuItems.add(theme10);

        this.add(Box.createHorizontalStrut(11));

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(font1);
        mnHelp.setForeground(Color.white);
        menus.add(mnHelp);

        JMenuItem helpInstructions = new JMenuItem("Engine Instructions");
        helpInstructions.addActionListener(e -> InstructionsWindow.createEngineInstructionsWindow(eFrame));
        mnHelp.add(helpInstructions);
        menuItems.add(helpInstructions);

        JMenuItem helpGraphInstructions = new JMenuItem("Graph Instructions");
        helpGraphInstructions.addActionListener(e -> InstructionsWindow.createGraphInstructionsWindow(eFrame));
        mnHelp.add(helpGraphInstructions);
        this.add(mnHelp);
        menuItems.add(helpGraphInstructions);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            JMenuItem accessibility = new JMenuItem("On-Screen Keyboard");
            accessibility.addActionListener(e -> {
                try {
                    Runtime.getRuntime().exec("cmd /c osk");
                } catch (Exception f) {
                    ExceptionWindow.append(f);
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

        JMenu engineModes = new JMenu("Engine Modes");
        engineModes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        engineModes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(engineModes);

        engineModesGroup = new ButtonGroup();

        engineModesMenuItems = new JRadioButtonMenuItem[5];

        engineModesMenuItems[0] = new JRadioButtonMenuItem("Normal Mode");
        engineModesMenuItems[0].setActionCommand("0");
        engineModesGroup.add(engineModesMenuItems[0]);
        engineModes.add(engineModesMenuItems[0]);

        engineModesMenuItems[1] = new JRadioButtonMenuItem("Multi Mode");
        engineModesMenuItems[1].setActionCommand("1");
        engineModesGroup.add(engineModesMenuItems[1]);
        engineModes.add(engineModesMenuItems[1]);

        engineModesMenuItems[2] = new JRadioButtonMenuItem("Fireworks Mode");
        engineModesMenuItems[2].setActionCommand("2");
        engineModesGroup.add(engineModesMenuItems[2]);
        engineModes.add(engineModesMenuItems[2]);

        engineModesMenuItems[3] = new JRadioButtonMenuItem("Graph Mode");
        engineModesMenuItems[3].setActionCommand("3");
        engineModesGroup.add(engineModesMenuItems[3]);
        engineModes.add(engineModesMenuItems[3]);

        engineModesMenuItems[4] = new JRadioButtonMenuItem("Physics Mode");
        engineModesMenuItems[4].setActionCommand("4");
        engineModesGroup.add(engineModesMenuItems[4]);
        engineModes.add(engineModesMenuItems[4]);

        for (JRadioButtonMenuItem b : engineModesMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.engineMode.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : engineModesMenuItems) {
            b.addActionListener(e -> engineSettings.engineMode = EngineMode.values()[Integer.parseInt(engineModesGroup.getSelection().getActionCommand())]);
        }

        modes.add(engineModes);

        JMenu moleculeTypes = new JMenu("Molecule Types");
        moleculeTypes.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        moleculeTypes.setOpaque(true); //Have to set opaque inside another menu
        menuItems.add(moleculeTypes);

        moleculeTypesGroup = new ButtonGroup();

        moleculeTypesMenuItems = new JRadioButtonMenuItem[9];

        moleculeTypesMenuItems[0] = new JRadioButtonMenuItem("Particle");
        moleculeTypesMenuItems[0].setActionCommand("0");
        moleculeTypesGroup.add(moleculeTypesMenuItems[0]);
        moleculeTypes.add(moleculeTypesMenuItems[0]);

        moleculeTypesMenuItems[1] = new JRadioButtonMenuItem("Gravity Point");
        moleculeTypesMenuItems[1].setActionCommand("1");
        moleculeTypesGroup.add(moleculeTypesMenuItems[1]);
        moleculeTypes.add(moleculeTypesMenuItems[1]);

        moleculeTypesMenuItems[2] = new JRadioButtonMenuItem("Emitter");
        moleculeTypesMenuItems[2].setActionCommand("2");
        moleculeTypesGroup.add(moleculeTypesMenuItems[2]);
        moleculeTypes.add(moleculeTypesMenuItems[2]);

        moleculeTypesMenuItems[3] = new JRadioButtonMenuItem("Flux");
        moleculeTypesMenuItems[3].setActionCommand("3");
        moleculeTypesGroup.add(moleculeTypesMenuItems[3]);
        moleculeTypes.add(moleculeTypesMenuItems[3]);

        moleculeTypesMenuItems[4] = new JRadioButtonMenuItem("Q.E.D");
        moleculeTypesMenuItems[4].setActionCommand("4");
        moleculeTypesGroup.add(moleculeTypesMenuItems[4]);
        moleculeTypes.add(moleculeTypesMenuItems[4]);

        moleculeTypesMenuItems[5] = new JRadioButtonMenuItem("Ion");
        moleculeTypesMenuItems[5].setActionCommand("5");
        moleculeTypesGroup.add(moleculeTypesMenuItems[5]);
        moleculeTypes.add(moleculeTypesMenuItems[5]);

        moleculeTypesMenuItems[6] = new JRadioButtonMenuItem("Black Hole");
        moleculeTypesMenuItems[6].setActionCommand("6");
        moleculeTypesGroup.add(moleculeTypesMenuItems[6]);
        moleculeTypes.add(moleculeTypesMenuItems[6]);

        moleculeTypesMenuItems[7] = new JRadioButtonMenuItem("Duplex");
        moleculeTypesMenuItems[7].setActionCommand("7");
        moleculeTypesGroup.add(moleculeTypesMenuItems[7]);
        moleculeTypes.add(moleculeTypesMenuItems[7]);

        moleculeTypesMenuItems[8] = new JRadioButtonMenuItem("Portal");
        moleculeTypesMenuItems[8].setActionCommand("8");
        moleculeTypesGroup.add(moleculeTypesMenuItems[8]);
        moleculeTypes.add(moleculeTypesMenuItems[8]);

        for (JRadioButtonMenuItem b : moleculeTypesMenuItems) {
            if (Integer.parseInt(b.getActionCommand()) == engineSettings.moleculeType.ordinal()) {
                b.setSelected(true);
                break;
            }
        }
        for (JRadioButtonMenuItem b : moleculeTypesMenuItems) {
            b.addActionListener(e -> engineSettings.moleculeType = MoleculeType.values()[Integer.parseInt(moleculeTypesGroup.getSelection().getActionCommand())]);
        }

        modes.add(moleculeTypes);

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

    public static void updateMoleculeTypeRadios() {
        updateRadios(moleculeTypesMenuItems, engineSettings.moleculeType.ordinal());
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
        updateMoleculeTypeRadios();
        updateGravitationModeRadios();
    }

    private static String enginePauseStatus() {
        return engineSettings.paused ? "Resume Engine" : "Pause Engine";
    }

    public static void updateState() {
        enginePauseMenuItem.setText(enginePauseStatus());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(bgColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
