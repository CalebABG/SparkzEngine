package com.engine.GUIWindows;

import com.engine.JComponents.CTextField;
import com.engine.JComponents.RLabel;
import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Utilities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class SettingsEditor {
    private static SettingsEditor settingsEditor = null;
    public static JFrame frame;
    public static JPanel panel;
    private static JMenuItem mntmSave, currentSettings, refreshUI, clearAll;
    private static JCheckBox loadsave, intelliEdit;
    private static CTextField[] textFields = new CTextField[32];
    private static KeyAdapter[] keyAdapters = new KeyAdapter[32];

    //public static void main(String[] args) {}

    public static SettingsEditor getInstance() {
        if (settingsEditor == null) {settingsEditor = new SettingsEditor();}frame.toFront(); return settingsEditor;
    }

    private SettingsEditor() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Settings Editor");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(465, 442);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent)
        {settingsEditor = null; frame.dispose();}});
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{138, 64, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        keyAdapters[0] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(switchMode, textFields[0].getText(), textFields[0]);}};
        keyAdapters[1] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(particleType, textFields[1].getText(), textFields[1]);}};
        keyAdapters[2] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(ptGravitationInt, textFields[2].getText(), textFields[2]);}};
        keyAdapters[3] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(fireworksAmount, textFields[3].getText(), textFields[3]);}};
        keyAdapters[4] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(particleMode, textFields[4].getText(), textFields[4]);}};
        keyAdapters[5] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(dragAmount, textFields[5].getText(), textFields[5]);}};
        keyAdapters[6] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(baseLife, textFields[6].getText(), textFields[6]);}};
        keyAdapters[7] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(rfParticleMode, textFields[7].getText(), textFields[7]);}};
        keyAdapters[8] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(rfLife, textFields[8].getText(), textFields[8]);}};
        keyAdapters[9] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(rfWind, textFields[9].getText(), textFields[9]);}};
        keyAdapters[10] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(rfJitter, textFields[10].getText(), textFields[10]);}};
        keyAdapters[11] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(safetyAmount, textFields[11].getText(), textFields[11]);}};
        keyAdapters[12] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesInt(cycleRate, textFields[12].getText(), textFields[12]);}};
        keyAdapters[13] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(singleClickSizeMaxVal, textFields[13].getText(), textFields[13]);}};
        keyAdapters[14] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(singleClickSizeMinVal, textFields[14].getText(), textFields[14]);}};
        keyAdapters[15] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(multiClickSizeMaxVal, textFields[15].getText(), textFields[15]);}};
        keyAdapters[16] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(multiClickSizeMinVal, textFields[16].getText(), textFields[16]);}};
        keyAdapters[17] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(fireworksSizeMaxVal, textFields[17].getText(), textFields[17]);}};
        keyAdapters[18] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(fireworksSizeMinVal, textFields[18].getText(), textFields[18]);}};
        keyAdapters[19] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(dragSizeMaxVal, textFields[19].getText(), textFields[19]);}};
        keyAdapters[20] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(dragSizeMinVal, textFields[20].getText(), textFields[20]);}};
        keyAdapters[21] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(singleClickSpeedVal, textFields[21].getText(), textFields[21]);}};
        keyAdapters[22] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(multiClickSpeedVal, textFields[22].getText(), textFields[22]);}};
        keyAdapters[23] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(fireworksSpeedVal, textFields[23].getText(), textFields[23]);}};
        keyAdapters[24] = new KeyAdapter(){public void keyReleased(KeyEvent e){guardValuesDouble(dragSpeedVal, textFields[24].getText(), textFields[24]);}};

        JMenuBar menuBar = new JMenuBar();
        scrollPane.setColumnHeaderView(menuBar);

        JMenu mnFile = new JMenu(" - File - ");
        mnFile.setFont(new Font("Times", Font.PLAIN, 15));
        mnFile.setToolTipText("<html><div style='font-size:10px'> Save Settings <br> Load Settings <br> Refresh Editor <br> Clear All </div></html>");
        menuBar.add(mnFile);

        loadsave = new JCheckBox("Load on Save", true);
        loadsave.setToolTipText("Enabling this will save and then load in your settings.");
        loadsave.setFont(new Font("Times", Font.PLAIN, 15));
        menuBar.add(loadsave);

        intelliEdit = new JCheckBox("Intelli Edit", true);
        intelliEdit.setToolTipText("<html> Enabling this will show changes in values. <br> If values = variables: color is blue. If values are not the same: color is black. <br> " +
                "Only on Ints & Doubles. </html>");
        intelliEdit.setFont(new Font("Times", Font.PLAIN, 15));
        intelliEdit.addActionListener(e -> intelliEditorMode());
        menuBar.add(intelliEdit);

        Component horizontalStrut = Box.createHorizontalStrut(11);
        menuBar.add(horizontalStrut);

        JButton quicksave = new JButton("Quick Save");
        quicksave.setMargin(new Insets(1, 1, 1, 1));
        quicksave.setFont(new Font("Times", Font.PLAIN, 13));
        quicksave.addActionListener(e -> saveButton());
        menuBar.add(quicksave);

        mntmSave = new JMenuItem("Save Settings");
        mntmSave.addActionListener(e -> saveButton());
        mnFile.add(mntmSave);

        currentSettings = new JMenuItem("Load Settings");
        currentSettings.addActionListener(e -> loadButton());
        mnFile.add(currentSettings);

        refreshUI = new JMenuItem("Refresh Editor");
        refreshUI.addActionListener(e -> refreshUI());
        mnFile.add(refreshUI);

        clearAll = new JMenuItem("Clear All");
        clearAll.addActionListener(e -> clearText());
        mnFile.add(clearAll);

        RLabel lblNewLabel = new RLabel("-- Ints --", new Font("Times", Font.BOLD, 18), 2, new Insets(3, 0, 5, 0), new int[]{0, 0});
        panel.add(lblNewLabel, lblNewLabel.gridBagConstraints);

        RLabel lblNewLabel_1 = new RLabel("Switch Mode - set: 0 - 4", new Font("Times", Font.PLAIN, 17), new Insets(0, 3, 5, 5), GridBagConstraints.WEST, new int[]{0, 1}, new int[]{1, 4});
        panel.add(lblNewLabel_1, lblNewLabel_1.gridBagConstraints);

        textFields[0] = new CTextField("" + switchMode, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 1});
        panel.add(textFields[0], textFields[0].gridBagConstraints);

        RLabel lblParticletypeSet = new RLabel("Particle Type - set: 0 - 8", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 2);
        panel.add(lblParticletypeSet, lblParticletypeSet.gridBagConstraints);

        textFields[1] = new CTextField("" + particleType, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 2});
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel lblPtgravitationintSet = new RLabel("Particle Gravitation - set: 0 - 6", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 3);
        panel.add(lblPtgravitationintSet, lblPtgravitationintSet.gridBagConstraints);

        textFields[2] = new CTextField("" + ptGravitationInt, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 3});
        panel.add(textFields[2], textFields[2].gridBagConstraints);

        RLabel lblFireworksamountSet = new RLabel("Fireworks Amount - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 4);
        panel.add(lblFireworksamountSet, lblFireworksamountSet.gridBagConstraints);

        textFields[3] = new CTextField("" + fireworksAmount, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 4});
        panel.add(textFields[3], textFields[3].gridBagConstraints);

        RLabel lblParticlemodeSet = new RLabel("Regular Particle Mode - set: 1 - 19", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 5);
        panel.add(lblParticlemodeSet, lblParticlemodeSet.gridBagConstraints);

        textFields[4] = new CTextField("" + particleMode, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 5});
        panel.add(textFields[4], textFields[4].gridBagConstraints);

        RLabel lblDragamountSet = new RLabel("Drag Amount - set: 1 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 6);
        panel.add(lblDragamountSet, lblDragamountSet.gridBagConstraints);

        textFields[5] = new CTextField("" + dragAmount, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 6});
        panel.add(textFields[5], textFields[5].gridBagConstraints);

        RLabel lblBaselifeSet = new RLabel("Base Life - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 7);
        panel.add(lblBaselifeSet, lblBaselifeSet.gridBagConstraints);

        textFields[6] = new CTextField("" + baseLife, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 7});
        panel.add(textFields[6], textFields[6].gridBagConstraints);

        RLabel lblRfparticlemodeSet = new RLabel("Fireworks Particle Mode - set: 1 - 19", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 8);
        panel.add(lblRfparticlemodeSet, lblRfparticlemodeSet.gridBagConstraints);

        textFields[7] = new CTextField("" + rfParticleMode, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 8});
        panel.add(textFields[7], textFields[7].gridBagConstraints);

        RLabel lblRflifeSet = new RLabel("Fireworks Life - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 9);
        panel.add(lblRflifeSet, lblRflifeSet.gridBagConstraints);

        textFields[8] = new CTextField("" + rfLife, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 9});
        panel.add(textFields[8], textFields[8].gridBagConstraints);

        RLabel lblRfwindSet = new RLabel("Fireworks Wind - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 10);
        panel.add(lblRfwindSet, lblRfwindSet.gridBagConstraints);

        textFields[9] = new CTextField("" + rfWind, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 10});
        panel.add(textFields[9], textFields[9].gridBagConstraints);

        RLabel lblRfjitterSet = new RLabel("Fireworks Jitter - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 11);
        panel.add(lblRfjitterSet, lblRfjitterSet.gridBagConstraints);

        textFields[10] = new CTextField("" + rfJitter, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 11});
        panel.add(textFields[10], textFields[10].gridBagConstraints);

        RLabel lblSafetyamountSet = new RLabel("Safety Amount - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 12);
        panel.add(lblSafetyamountSet, lblSafetyamountSet.gridBagConstraints);

        textFields[11] = new CTextField("" + safetyAmount, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        panel.add(textFields[11], textFields[11].gridBagConstraints);

        RLabel lblCyclerateSet = new RLabel("Cycle Rate - set: 1 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 13);
        panel.add(lblCyclerateSet, lblCyclerateSet.gridBagConstraints);

        textFields[12] = new CTextField("" + cycleRate, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 13});
        panel.add(textFields[12], textFields[12].gridBagConstraints);

        RLabel lblNewLabel_2 = new RLabel("-- Doubles --", new Font("Times", Font.BOLD, 18), 2, new Insets(0, 0, 5, 5), new int[]{0, 14});
        panel.add(lblNewLabel_2, lblNewLabel_2.gridBagConstraints);

        RLabel lblIfinput = new RLabel("SingleClickSizeMaxVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 15);
        panel.add(lblIfinput, lblIfinput.gridBagConstraints);

        textFields[13] = new CTextField("" + singleClickSizeMaxVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 15});
        panel.add(textFields[13], textFields[13].gridBagConstraints);

        RLabel lblSingleclicksizeminvalSet = new RLabel("SingleClickSizeMinVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 16);
        panel.add(lblSingleclicksizeminvalSet, lblSingleclicksizeminvalSet.gridBagConstraints);

        textFields[14] = new CTextField("" + singleClickSizeMinVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 16});
        panel.add(textFields[14], textFields[14].gridBagConstraints);

        RLabel lblMulticlicksizemaxvalSet = new RLabel("MultiClickSizeMaxVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 17);
        panel.add(lblMulticlicksizemaxvalSet, lblMulticlicksizemaxvalSet.gridBagConstraints);

        textFields[15] = new CTextField("" + multiClickSizeMaxVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 17});
        panel.add(textFields[15], textFields[15].gridBagConstraints);

        RLabel lblMulticlicksizeminvalSet = new RLabel("MultiClickSizeMinVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 18);
        panel.add(lblMulticlicksizeminvalSet, lblMulticlicksizeminvalSet.gridBagConstraints);

        textFields[16] = new CTextField("" + multiClickSizeMinVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 18});
        panel.add(textFields[16], textFields[16].gridBagConstraints);

        RLabel lblFireworkssizemaxvalSet = new RLabel("FireworksSizeMaxVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 19);
        panel.add(lblFireworkssizemaxvalSet, lblFireworkssizemaxvalSet.gridBagConstraints);

        textFields[17] = new CTextField("" + fireworksSizeMaxVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 19});
        panel.add(textFields[17], textFields[17].gridBagConstraints);

        RLabel lblFireworkssizeminvalSet = new RLabel("FireworksSizeMinVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 20);
        panel.add(lblFireworkssizeminvalSet, lblFireworkssizeminvalSet.gridBagConstraints);

        textFields[18] = new CTextField("" + fireworksSizeMinVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 20});
        panel.add(textFields[18], textFields[18].gridBagConstraints);

        RLabel lblDragsizemaxvalSet = new RLabel("DragSizeMaxVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 21);
        panel.add(lblDragsizemaxvalSet, lblDragsizemaxvalSet.gridBagConstraints);

        textFields[19] = new CTextField("" + dragSizeMaxVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 21});
        panel.add(textFields[19], textFields[19].gridBagConstraints);

        RLabel lblDragsizeminvalSet = new RLabel("DragSizeMinVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 22);
        panel.add(lblDragsizeminvalSet, lblDragsizeminvalSet.gridBagConstraints);

        textFields[20] = new CTextField("" + dragSizeMinVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 22});
        panel.add(textFields[20], textFields[20].gridBagConstraints);

        RLabel lblsingleClickSpeedValSet = new RLabel("SingleClickSpeedVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 23);
        panel.add(lblsingleClickSpeedValSet, lblsingleClickSpeedValSet.gridBagConstraints);

        textFields[21] = new CTextField("" + singleClickSpeedVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 23});
        panel.add(textFields[21], textFields[21].gridBagConstraints);

        RLabel lblmultiClickSpeedValSet = new RLabel("MultiClickSpeedVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 24);
        panel.add(lblmultiClickSpeedValSet, lblmultiClickSpeedValSet.gridBagConstraints);

        textFields[22] = new CTextField("" + multiClickSpeedVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 24});
        panel.add(textFields[22], textFields[22].gridBagConstraints);

        RLabel lblfireworksSpeedValSet = new RLabel("FireworksSpeedVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 25);
        panel.add(lblfireworksSpeedValSet, lblfireworksSpeedValSet.gridBagConstraints);

        textFields[23] = new CTextField("" + fireworksSpeedVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 25});
        panel.add(textFields[23], textFields[23].gridBagConstraints);

        RLabel lbldragSpeedValSet = new RLabel("DragSpeedVal - set: 0 - x", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 26);
        panel.add(lbldragSpeedValSet, lbldragSpeedValSet.gridBagConstraints);

        textFields[24] = new CTextField("" + dragSpeedVal, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 26});
        panel.add(textFields[24], textFields[24].gridBagConstraints);

        RLabel lblNewLabel_3 = new RLabel("-- Booleans --", new Font("Times", Font.BOLD, 18), 2, new Insets(4, 0, 5, 5), new int[]{0, 27});
        panel.add(lblNewLabel_3, lblNewLabel_3.gridBagConstraints);

        RLabel lblthinkingParticlesSet = new RLabel("Thinking Particles - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 28);
        panel.add(lblthinkingParticlesSet, lblthinkingParticlesSet.gridBagConstraints);

        textFields[25] = new CTextField("" + thinkingParticles, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 28});
        panel.add(textFields[25], textFields[25].gridBagConstraints);

        RLabel lblconnectParticlesSet = new RLabel("Connect Particles - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 29);
        panel.add(lblconnectParticlesSet, lblconnectParticlesSet.gridBagConstraints);

        textFields[26] = new CTextField("" + connectParticles, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 29});
        panel.add(textFields[26], textFields[26].gridBagConstraints);

        RLabel lblparticleFrictionSet = new RLabel("Particle Friction - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 30);
        panel.add(lblparticleFrictionSet, lblparticleFrictionSet.gridBagConstraints);

        textFields[27] = new CTextField("" + particleFriction, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 30});
        panel.add(textFields[27], textFields[27].gridBagConstraints);

        RLabel lblmouseGravitationSet = new RLabel("Mouse Gravitation - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 31);
        panel.add(lblmouseGravitationSet, lblmouseGravitationSet.gridBagConstraints);

        textFields[28] = new CTextField("" + mouseGravitation, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 31});
        panel.add(textFields[28], textFields[28].gridBagConstraints);

        RLabel lblispausedSet = new RLabel("Pause Simulation - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 32);
        panel.add(lblispausedSet, lblispausedSet.gridBagConstraints);

        textFields[29] = new CTextField("" + isPaused, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 32});
        panel.add(textFields[29], textFields[29].gridBagConstraints);

        RLabel lblGDMODEBOOLSet = new RLabel("GDMODEBOOL - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 33);
        panel.add(lblGDMODEBOOLSet, lblGDMODEBOOLSet.gridBagConstraints);

        textFields[30] = new CTextField("" + GDMODEBOOL, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 33});
        panel.add(textFields[30], textFields[30].gridBagConstraints);

        RLabel lblduplexModeSwapSet = new RLabel("DUPLEX MODE - set: true - false", new Font("Times", Font.PLAIN, 17), GridBagConstraints.WEST, new Insets(0, 3, 0, 5), 0, 34);
        panel.add(lblduplexModeSwapSet, lblduplexModeSwapSet.gridBagConstraints);

        textFields[31] = new CTextField("" + DUPLEXMODE, new Font("Times", Font.PLAIN, 17), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 34});
        panel.add(textFields[31], textFields[31].gridBagConstraints);

        for (int i = 25; i < textFields.length; i++) {
            final int _i = i;
            if (textFields[_i].getText().equalsIgnoreCase("TRUE")) textFields[_i].setForeground(Color.GREEN.darker().darker());
            else if (textFields[_i].getText().equalsIgnoreCase("FALSE")) textFields[_i].setForeground(Color.red.darker().darker());
            textFields[i].addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    if (textFields[_i].getText().equalsIgnoreCase("TRUE")) {textFields[_i].setForeground(Color.GREEN.darker().darker());}
                    else if (textFields[_i].getText().equalsIgnoreCase("FALSE")) {textFields[_i].setForeground(Color.red.darker().darker());}
                    else{textFields[_i].setForeground(Color.black);}
                }
            });
        }

        if (intelliEdit.isSelected())intelliEditorMode();
        frame.setVisible(true);
    }

    private static void saveButton() {
        if (loadsave.isSelected() && intelliEdit.isSelected()) {saveSettings(); Settings.loadSettings(); refreshUI(); intelliSense();}
        else {saveSettings();}
    }

    private static void loadButton() {
        if (intelliEdit.isSelected()){Settings.loadSettings(); refreshUI(); intelliSense();}
        else{Settings.loadSettings(); refreshUI();}
    }

    private void intelliEditorMode() {
        if (intelliEdit.isSelected()) {
            intelliSense();
            textFields[0].addKeyListener(keyAdapters[0]); textFields[1].addKeyListener(keyAdapters[1]); textFields[2].addKeyListener(keyAdapters[2]);
            textFields[3].addKeyListener(keyAdapters[3]); textFields[4].addKeyListener(keyAdapters[4]); textFields[5].addKeyListener(keyAdapters[5]);
            textFields[6].addKeyListener(keyAdapters[6]); textFields[7].addKeyListener(keyAdapters[7]); textFields[8].addKeyListener(keyAdapters[8]);
            textFields[9].addKeyListener(keyAdapters[9]); textFields[10].addKeyListener(keyAdapters[10]); textFields[11].addKeyListener(keyAdapters[11]);
            textFields[12].addKeyListener(keyAdapters[12]); textFields[13].addKeyListener(keyAdapters[13]); textFields[14].addKeyListener(keyAdapters[14]);
            textFields[15].addKeyListener(keyAdapters[15]); textFields[16].addKeyListener(keyAdapters[16]); textFields[17].addKeyListener(keyAdapters[17]);
            textFields[18].addKeyListener(keyAdapters[18]); textFields[19].addKeyListener(keyAdapters[19]); textFields[20].addKeyListener(keyAdapters[20]);
            textFields[21].addKeyListener(keyAdapters[21]); textFields[22].addKeyListener(keyAdapters[22]); textFields[23].addKeyListener(keyAdapters[23]);
            textFields[24].addKeyListener(keyAdapters[24]);
        }
        else {for (int i = 0; i < 25; i++) {textFields[i].removeKeyListener(keyAdapters[i]); textFields[i].setForeground(Color.BLACK);}}
    }

    private static void intelliSense() {
        checkValuesInt(switchMode, Integer.parseInt(textFields[0].getText()), textFields[0]);
        checkValuesInt(particleType, Integer.parseInt(textFields[1].getText()), textFields[1]);
        checkValuesInt(ptGravitationInt, Integer.parseInt(textFields[2].getText()), textFields[2]);
        checkValuesInt(fireworksAmount, Integer.parseInt(textFields[3].getText()), textFields[3]);
        checkValuesInt(particleMode, Integer.parseInt(textFields[4].getText()), textFields[4]);
        checkValuesInt(dragAmount, Integer.parseInt(textFields[5].getText()), textFields[5]);
        checkValuesInt(baseLife, Integer.parseInt(textFields[6].getText()), textFields[6]);
        checkValuesInt(rfParticleMode, Integer.parseInt(textFields[7].getText()), textFields[7]);
        checkValuesInt(rfLife, Integer.parseInt(textFields[8].getText()), textFields[8]);
        checkValuesInt(rfWind, Integer.parseInt(textFields[9].getText()), textFields[9]);
        checkValuesInt(rfJitter, Integer.parseInt(textFields[10].getText()), textFields[10]);
        checkValuesInt(safetyAmount, Integer.parseInt(textFields[11].getText()), textFields[11]);
        checkValuesInt(cycleRate, Integer.parseInt(textFields[12].getText()), textFields[12]);
        checkValuesDouble(singleClickSizeMaxVal, Double.parseDouble(textFields[13].getText()), textFields[13]);
        checkValuesDouble(singleClickSizeMinVal, Double.parseDouble(textFields[14].getText()), textFields[14]);
        checkValuesDouble(multiClickSizeMaxVal, Double.parseDouble(textFields[15].getText()), textFields[15]);
        checkValuesDouble(multiClickSizeMinVal, Double.parseDouble(textFields[16].getText()), textFields[16]);
        checkValuesDouble(fireworksSizeMaxVal, Double.parseDouble(textFields[17].getText()), textFields[17]);
        checkValuesDouble(fireworksSizeMinVal, Double.parseDouble(textFields[18].getText()), textFields[18]);
        checkValuesDouble(dragSizeMaxVal, Double.parseDouble(textFields[19].getText()), textFields[19]);
        checkValuesDouble(dragSizeMinVal, Double.parseDouble(textFields[20].getText()), textFields[20]);
        checkValuesDouble(singleClickSpeedVal, Double.parseDouble(textFields[21].getText()), textFields[21]);
        checkValuesDouble(multiClickSpeedVal, Double.parseDouble(textFields[22].getText()), textFields[22]);
        checkValuesDouble(fireworksSpeedVal, Double.parseDouble(textFields[23].getText()), textFields[23]);
        checkValuesDouble(dragSpeedVal, Double.parseDouble(textFields[24].getText()), textFields[24]);
    }

    private static void guardValuesInt(int var, String comparison, JComponent component) {
        if (InputWrapper.canParseStringInt(comparison)) {checkValuesInt(var, Integer.parseInt(comparison), component);}}
    private static void guardValuesDouble(double var, String comparison, JComponent component) {
        if (InputWrapper.canParseStringDouble(comparison)) {checkValuesDouble(var, Double.parseDouble(comparison), component);}}

    private static void checkValuesInt(int default_var, int comparison_val, JComponent component) {
        if (comparison_val == default_var){component.setForeground(new Color(0x2D5FBA));}
        else {component.setForeground(Color.BLACK);}
    }

    private static void checkValuesDouble(double default_var, double comparison_val, JComponent component) {
        if (comparison_val == default_var){component.setForeground(new Color(0x2D5FBA));}
        else {component.setForeground(Color.BLACK);}
    }

    private static void saveSettings() {
        try{
            new File("./" + "Settings").mkdir();
            FileOutputStream out = new FileOutputStream(("." + Paths.get("/" + "Settings") + "/" + "settings.txt"), false);
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            /*-------------------------------------------------------*/
            writer.write("# switchMode -- set: 0 - 4 <- MAX \n");
            writer.write("switchMode: " + InputWrapper.intTextfieldGuard(0, 4, switchMode, textFields[0].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleType -- set: 0 - 8 <- MAX \n");
            writer.write("particleType: " + InputWrapper.intTextfieldGuard(0, 8, particleType,textFields[1].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# ptGravitationInt -- set: 0 - 6 <- MAX \n");
            writer.write("ptGravitationInt: " + InputWrapper.intTextfieldGuard(0, 6, ptGravitationInt,textFields[2].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksAmount -- set: 0 - specify number \n");
            writer.write("fireworksAmount: " + InputWrapper.intTextfieldGuardDefault(0, fireworksAmount,textFields[3].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleMode -- set: 1 - 19 <- MAX \n");
            writer.write("particleMode: " + InputWrapper.intTextfieldGuard(1,19, particleMode,textFields[4].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragAmount -- set: 1 - specify number \n");
            writer.write("dragAmount: " + InputWrapper.intTextfieldGuardDefault(1, dragAmount,textFields[5].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# baseLife -- set: 0 - specify number \n");
            writer.write("baseLife: " + InputWrapper.intTextfieldGuardDefault(0, baseLife, textFields[6].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfParticleMode -- set: 1 - 19 <- MAX \n");
            writer.write("rfParticleMode: " + InputWrapper.intTextfieldGuard(1,19, rfParticleMode,textFields[7].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfLife -- set: 0 - specify number \n");
            writer.write("rfLife: " + InputWrapper.intTextfieldGuardDefault(0, rfLife,textFields[8].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfWind -- set: 0 - specify number \n");
            writer.write("rfWind: " + InputWrapper.intTextfieldGuardDefault(0, rfWind,textFields[9].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# rfJitter -- set: 0 - specify number \n");
            writer.write("rfJitter: " + InputWrapper.intTextfieldGuardDefault(0, rfJitter,textFields[10].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# safetyAmount -- set: 0 - specify number \n");
            writer.write("safetyAmount: " + InputWrapper.intTextfieldGuardDefault(0, safetyAmount,textFields[11].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# cycleRate -- set: 1 - specify number \n");
            writer.write("cycleRate: " + InputWrapper.intTextfieldGuardDefault(1, cycleRate,textFields[12].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSizeMaxVal -- set: 0 - specify number \n");
            writer.write("singleClickSizeMaxVal: " + InputWrapper.doubleTextfieldGuardDefault(0, singleClickSizeMaxVal,textFields[13].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSizeMinVal -- set: 0 - specify number \n");
            writer.write("singleClickSizeMinVal: " + InputWrapper.doubleTextfieldGuardDefault(0, singleClickSizeMinVal,textFields[14].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSizeMaxVal -- set: 0 - specify number \n");
            writer.write("multiClickSizeMaxVal: " + InputWrapper.doubleTextfieldGuardDefault(0, multiClickSizeMaxVal,textFields[15].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSizeMinVal -- set: 0 - specify number \n");
            writer.write("multiClickSizeMinVal: " + InputWrapper.doubleTextfieldGuardDefault(0, multiClickSizeMinVal,textFields[16].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSizeMaxVal -- set: 0 - specify number \n");
            writer.write("fireworksSizeMaxVal: " + InputWrapper.doubleTextfieldGuardDefault(0, fireworksSizeMaxVal,textFields[17].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSizeMinVal -- set: 0 - specify number \n");
            writer.write("fireworksSizeMinVal: " + InputWrapper.doubleTextfieldGuardDefault(0, fireworksSizeMinVal,textFields[18].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSizeMaxVal -- set: 0 - specify number \n");
            writer.write("dragSizeMaxVal: " + InputWrapper.doubleTextfieldGuardDefault(0, dragSizeMaxVal,textFields[19].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSizeMinVal -- set: 0 - specify number \n");
            writer.write("dragSizeMinVal: " + InputWrapper.doubleTextfieldGuardDefault(0, dragSizeMinVal,textFields[20].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# singleClickSpeedVal -- set: 0 - specify number \n");
            writer.write("singleClickSpeedVal: " + InputWrapper.doubleTextfieldGuardDefault(0, singleClickSpeedVal,textFields[21].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# multiClickSpeedVal -- set: 0 - specify number \n");
            writer.write("multiClickSpeedVal: " + InputWrapper.doubleTextfieldGuardDefault(0, multiClickSpeedVal,textFields[22].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# fireworksSpeedVal -- set: 0 - specify number \n");
            writer.write("fireworksSpeedVal: " + InputWrapper.doubleTextfieldGuardDefault(0, fireworksSpeedVal,textFields[23].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# dragSpeedVal -- set: 0 - specify number \n");
            writer.write("dragSpeedVal: " + InputWrapper.doubleTextfieldGuardDefault(0, dragSpeedVal,textFields[24].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# thinkingParticles -- set: true - false \n");
            writer.write("thinkingParticles: " + InputWrapper.boolTextfieldGuard(thinkingParticles,textFields[25].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# connectParticles -- set: true - false \n");
            writer.write("connectParticles: " + InputWrapper.boolTextfieldGuard(connectParticles,textFields[26].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# particleFriction -- set: true - false \n");
            writer.write("particleFriction: " + InputWrapper.boolTextfieldGuard(particleFriction,textFields[27].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# mouseGravitation -- set: true - false \n");
            writer.write("mouseGravitation: " + InputWrapper.boolTextfieldGuard(mouseGravitation,textFields[28].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# isPaused -- set: true - false \n");
            writer.write("isPaused: " + InputWrapper.boolTextfieldGuard(isPaused,textFields[29].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# GDMODEBOOL -- set: true - false \n");
            writer.write("GDMODEBOOL: " + InputWrapper.boolTextfieldGuard(GDMODEBOOL,textFields[30].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.write("# DUPLEXMODE -- set: true - false \n");
            writer.write("DUPLEXMODE: " + InputWrapper.boolTextfieldGuard(DUPLEXMODE, textFields[31].getText()) + "\n");
            /*-------------------------------------------------------*/
            writer.close(); out.close();
        }catch (Exception e){EException.append(e);}
    }

    private static void refreshUI() {
        textFields[0].setText(""+ switchMode); textFields[1].setText(""+ particleType); textFields[2].setText(""+ ptGravitationInt);
        textFields[3].setText(""+ fireworksAmount); textFields[4].setText(""+ particleMode); textFields[5].setText(""+ dragAmount);
        textFields[6].setText(""+ baseLife); textFields[7].setText(""+ rfParticleMode); textFields[8].setText(""+ rfLife);
        textFields[9].setText(""+ rfWind); textFields[10].setText(""+ rfJitter); textFields[11].setText(""+ safetyAmount);
        textFields[12].setText(""+ cycleRate); textFields[13].setText(""+ singleClickSizeMaxVal); textFields[14].setText(""+ singleClickSizeMinVal);
        textFields[15].setText(""+ multiClickSizeMaxVal); textFields[16].setText(""+ multiClickSizeMinVal); textFields[17].setText(""+ fireworksSizeMaxVal);
        textFields[18].setText(""+ fireworksSizeMinVal); textFields[19].setText(""+ dragSizeMaxVal); textFields[20].setText(""+ dragSizeMinVal);
        textFields[21].setText(""+ singleClickSpeedVal); textFields[22].setText(""+ multiClickSpeedVal); textFields[23].setText(""+ fireworksSpeedVal);
        textFields[24].setText(""+ dragSpeedVal); textFields[25].setText(""+ thinkingParticles); textFields[26].setText(""+ connectParticles);
        textFields[27].setText(""+ particleFriction); textFields[28].setText(""+ mouseGravitation); textFields[29].setText(""+ isPaused);
        textFields[30].setText(""+ GDMODEBOOL); textFields[31].setText(""+ DUPLEXMODE);
        for (int i = 25; i < textFields.length; i++) {
            if (textFields[i].getText().equalsIgnoreCase("TRUE")) textFields[i].setForeground(Color.GREEN.darker().darker());
            else if (textFields[i].getText().equalsIgnoreCase("FALSE")) textFields[i].setForeground(Color.red.darker().darker());
        }
    }

    private static void clearText() {for (int i = 0; i < textFields.length; i++) {textFields[i].setText("");}}
}