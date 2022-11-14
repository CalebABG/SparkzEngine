package com.cabg.gui;

import com.cabg.components.CLabel;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.enums.PhysicsEditorMode;
import com.cabg.enums.PhysicsItemType;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.utilities.EnumUtil;
import com.cabg.utilities.InputUtil;
import com.cabg.verlet.Edge;
import com.cabg.verlet.Physics;
import com.cabg.verlet.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.cabg.core.EngineVariables.eFrame;
import static com.cabg.core.EngineVariables.engineSettings;

public class PhysicsEditor {
    public static PhysicsEditor instance = null;

    private static JFrame frame;

    public static PhysicsEditorMode EDITOR_MODE = PhysicsEditorMode.Add;
    public static PhysicsItemType ITEM_TYPE = PhysicsItemType.Point;

    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 14);
    private static CLabel pointColorPanel, linkColorPanel, lblSelectionColor, lblConstraintLink;

    private static final DefaultListModel<Integer> constraintsListModel = new DefaultListModel<>();
    public static JCheckBox showSelectionConstraintCheckbox = new JCheckBox("Show Constraint"),
            constraintDrawLinkCheckbox = new JCheckBox("Draw Link"),
            constraintSeverableCheckbox = new JCheckBox("Severable"),
            selectionShowPointCheckbox = new JCheckBox("Show Point"),
            selectionCollidableCheckbox = new JCheckBox("Collidable");

    public static JList<Integer> constraintsList = new JList<>(constraintsListModel);
    public static JComboBox<PhysicsEditorMode> editorModesJComboBox = new JComboBox<>();
    public static JComboBox<PhysicsItemType> creationModesJComboBox = new JComboBox<>();
    public static JTextField simAccField = new JTextField(10),
            dragForceField = new JTextField(10),
            gravityField = new JTextField(10),
            numPointsField = new JTextField("4", 10),
            objectSizeField = new JTextField(10),
            dampeningField = new JTextField(10),
            objectMassField = new JTextField(10),
            spacingField = new JTextField(10),
            stiffnessField = new JTextField(10),
            airViscosityField = new JTextField(10),
            selectionRadiusField = new JTextField(10),
            selectionMassField = new JTextField(10),
            selectionDampeningField = new JTextField(10),
            selectionStiffnessField = new JTextField(10),
            tearDistanceField = new JTextField(10),
            constraintStiffnessField = new JTextField(10),
            constraintTearDistanceField = new JTextField(10);

    public static void getInstance() {
        if (instance == null) instance = new PhysicsEditor(eFrame);
        frame.toFront();
    }

    private PhysicsEditor(JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Physics Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(568, 610);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane simEditorScrollpane = new JScrollPane();
        frame.getContentPane().add(simEditorScrollpane, BorderLayout.CENTER);

        JToolBar simEditorToolbar = new JToolBar();
        simEditorScrollpane.setColumnHeaderView(simEditorToolbar);

        JPanel toolbarPanel = new JPanel();
        simEditorToolbar.add(toolbarPanel);
        GridBagLayout gbl_toolbarpanel = new GridBagLayout();
        gbl_toolbarpanel.columnWidths = new int[]{69, 70, 63, 0, 72, 0, 0};
        gbl_toolbarpanel.rowHeights = new int[]{0, 0};
        gbl_toolbarpanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_toolbarpanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        toolbarPanel.setLayout(gbl_toolbarpanel);

        JLabel editormode_label = new JLabel("Editor Mode");
        GridBagConstraints gbc_editormode_label = new GridBagConstraints();
        gbc_editormode_label.anchor = GridBagConstraints.WEST;
        gbc_editormode_label.insets = new Insets(0, 0, 0, 5);
        gbc_editormode_label.gridx = 0;
        gbc_editormode_label.gridy = 0;
        toolbarPanel.add(editormode_label, gbc_editormode_label);
        editormode_label.setFont(font);
        editormode_label.setIconTextGap(5);

        editorModesJComboBox.setModel(new DefaultComboBoxModel<>(PhysicsEditorMode.values()));
        editorModesJComboBox.setSelectedItem(EDITOR_MODE);
        editorModesJComboBox.setFont(font);
        editorModesJComboBox.addActionListener(e -> {
            PhysicsEditorMode selectedMode = (PhysicsEditorMode) editorModesJComboBox.getSelectedItem();
            if (selectedMode != EDITOR_MODE) EDITOR_MODE = selectedMode;
        });
        GridBagConstraints gbc_editormode_combobox = new GridBagConstraints();
        gbc_editormode_combobox.fill = GridBagConstraints.HORIZONTAL;
        gbc_editormode_combobox.insets = new Insets(0, 0, 0, 5);
        gbc_editormode_combobox.gridx = 1;
        gbc_editormode_combobox.gridy = 0;
        toolbarPanel.add(editorModesJComboBox, gbc_editormode_combobox);

        JCheckBox gravitycheckbox = new JCheckBox("Zero Gravity");
        gravitycheckbox.setSelected(Physics.ZERO_GRAVITY);
        gravitycheckbox.addActionListener(e -> Physics.toggleGravity());
        gravitycheckbox.setToolTipText("Toggles Gravity");
        gravitycheckbox.setFont(font);
        GridBagConstraints gbc_gravitycheckbox = new GridBagConstraints();
        gbc_gravitycheckbox.insets = new Insets(0, 0, 0, 5);
        gbc_gravitycheckbox.gridx = 2;
        gbc_gravitycheckbox.gridy = 0;
        toolbarPanel.add(gravitycheckbox, gbc_gravitycheckbox);

        JCheckBox debugcheckbox = new JCheckBox("Debug");
        debugcheckbox.setSelected(Physics.DEBUG_MODE);
        debugcheckbox.addActionListener(e -> Physics.toggleDebug());
        debugcheckbox.setToolTipText("Toggles Debug Mode");
        debugcheckbox.setFont(font);
        GridBagConstraints gbc_debugcheckbox = new GridBagConstraints();
        gbc_debugcheckbox.insets = new Insets(0, 0, 0, 5);
        gbc_debugcheckbox.gridx = 3;
        gbc_debugcheckbox.gridy = 0;
        toolbarPanel.add(debugcheckbox, gbc_debugcheckbox);

        JCheckBox chckbxSmoothing = new JCheckBox("Smoothing");
        chckbxSmoothing.setSelected(engineSettings.smoothRenderingEnabled);
        chckbxSmoothing.addActionListener(e -> engineSettings.toggleGraphicsSmoothing());
        chckbxSmoothing.setToolTipText("Toggles Graphics Smoothing");
        chckbxSmoothing.setFont(font);
        GridBagConstraints gbc_chckbxSmoothing = new GridBagConstraints();
        gbc_chckbxSmoothing.gridx = 4;
        gbc_chckbxSmoothing.gridy = 0;
        toolbarPanel.add(chckbxSmoothing, gbc_chckbxSmoothing);

        JTabbedPane simeditorpane = new JTabbedPane(JTabbedPane.TOP);
        simeditorpane.setFont(font);
        simEditorScrollpane.setViewportView(simeditorpane);

        JPanel simproperties_panel = new JPanel();
        simproperties_panel.setFont(font);
        simeditorpane.addTab("Simulation Properties", null, simproperties_panel, "You can change simulation properties here");
        simeditorpane.setEnabledAt(0, true);
        GridBagLayout gbl_simproperties_panel = new GridBagLayout();
        gbl_simproperties_panel.columnWidths = new int[]{164, -44};
        gbl_simproperties_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 37, 0, 0};
        gbl_simproperties_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_simproperties_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        simproperties_panel.setLayout(gbl_simproperties_panel);

        JLabel simacc_label = new JLabel("Simulation Accuracy (higher value = performance hit)");
        simacc_label.setFont(font);
        GridBagConstraints gbc_simacc_label = new GridBagConstraints();
        gbc_simacc_label.fill = GridBagConstraints.VERTICAL;
        gbc_simacc_label.insets = new Insets(5, 0, 5, 0);
        gbc_simacc_label.gridwidth = 2;
        gbc_simacc_label.gridx = 0;
        gbc_simacc_label.gridy = 0;
        simproperties_panel.add(simacc_label, gbc_simacc_label);

        simAccField.setText("" + Physics.SIM_ACCURACY);
        simAccField.setHorizontalAlignment(SwingConstants.CENTER);
        simAccField.setFont(font);
        GridBagConstraints gbc_simacc_field = new GridBagConstraints();
        gbc_simacc_field.insets = new Insets(0, 0, 5, 0);
        gbc_simacc_field.gridwidth = 2;
        gbc_simacc_field.fill = GridBagConstraints.BOTH;
        gbc_simacc_field.gridx = 0;
        gbc_simacc_field.gridy = 1;
        simproperties_panel.add(simAccField, gbc_simacc_field);


        JLabel dragforce_label = new JLabel("Drag Force (smaller value = stronger force)");
        dragforce_label.setFont(font);
        GridBagConstraints gbc_dragforce_label = new GridBagConstraints();
        gbc_dragforce_label.fill = GridBagConstraints.VERTICAL;
        gbc_dragforce_label.insets = new Insets(0, 0, 5, 0);
        gbc_dragforce_label.gridwidth = 2;
        gbc_dragforce_label.gridx = 0;
        gbc_dragforce_label.gridy = 2;
        simproperties_panel.add(dragforce_label, gbc_dragforce_label);

        dragForceField.setText("" + Physics.DRAG_FORCE);
        dragForceField.setHorizontalAlignment(SwingConstants.CENTER);
        dragForceField.setFont(font);

        GridBagConstraints gbc_dragforce_field = new GridBagConstraints();
        gbc_dragforce_field.insets = new Insets(0, 0, 5, 0);
        gbc_dragforce_field.gridwidth = 2;
        gbc_dragforce_field.fill = GridBagConstraints.BOTH;
        gbc_dragforce_field.gridx = 0;
        gbc_dragforce_field.gridy = 3;
        simproperties_panel.add(dragForceField, gbc_dragforce_field);

        JLabel gravity_label = new JLabel("Gravity");
        gravity_label.setFont(font);
        GridBagConstraints gbc_gravity_label = new GridBagConstraints();
        gbc_gravity_label.fill = GridBagConstraints.VERTICAL;
        gbc_gravity_label.insets = new Insets(0, 0, 5, 0);
        gbc_gravity_label.gridwidth = 2;
        gbc_gravity_label.gridx = 0;
        gbc_gravity_label.gridy = 4;
        simproperties_panel.add(gravity_label, gbc_gravity_label);

        gravityField.setText("" + Physics.GRAVITY);
        gravityField.setHorizontalAlignment(SwingConstants.CENTER);
        gravityField.setFont(font);

        GridBagConstraints gbc_gravity_field = new GridBagConstraints();
        gbc_gravity_field.insets = new Insets(0, 0, 5, 0);
        gbc_gravity_field.gridwidth = 2;
        gbc_gravity_field.fill = GridBagConstraints.BOTH;
        gbc_gravity_field.gridx = 0;
        gbc_gravity_field.gridy = 5;
        simproperties_panel.add(gravityField, gbc_gravity_field);

        JLabel airviscosity_label = new JLabel("Air Viscosity");
        airviscosity_label.setFont(font);
        GridBagConstraints gbc_airviscosity_label = new GridBagConstraints();
        gbc_airviscosity_label.fill = GridBagConstraints.VERTICAL;
        gbc_airviscosity_label.gridwidth = 2;
        gbc_airviscosity_label.insets = new Insets(0, 0, 5, 0);
        gbc_airviscosity_label.gridx = 0;
        gbc_airviscosity_label.gridy = 6;
        simproperties_panel.add(airviscosity_label, gbc_airviscosity_label);

        airViscosityField.setText("" + Physics.AIR_VISCOSITY);
        airViscosityField.setHorizontalAlignment(SwingConstants.CENTER);
        airViscosityField.setFont(font);

        GridBagConstraints gbc_airviscosity_field = new GridBagConstraints();
        gbc_airviscosity_field.gridwidth = 2;
        gbc_airviscosity_field.insets = new Insets(0, 0, 5, 0);
        gbc_airviscosity_field.fill = GridBagConstraints.BOTH;
        gbc_airviscosity_field.gridx = 0;
        gbc_airviscosity_field.gridy = 7;
        simproperties_panel.add(airViscosityField, gbc_airviscosity_field);

        JButton btnSetSimulationProperties = new JButton("Set Simulation Properties");
        btnSetSimulationProperties.addActionListener(e -> setSimulationProperties());
        btnSetSimulationProperties.setFont(font);
        GridBagConstraints gbc_btnSetSimulationProperties = new GridBagConstraints();
        gbc_btnSetSimulationProperties.gridwidth = 2;
        gbc_btnSetSimulationProperties.fill = GridBagConstraints.BOTH;
        gbc_btnSetSimulationProperties.insets = new Insets(0, 0, 5, 0);
        gbc_btnSetSimulationProperties.gridx = 0;
        gbc_btnSetSimulationProperties.gridy = 8;
        simproperties_panel.add(btnSetSimulationProperties, gbc_btnSetSimulationProperties);

        JPanel addmode_panel = new JPanel();
        GridBagConstraints gbc_addmode_panel = new GridBagConstraints();
        gbc_addmode_panel.gridwidth = 2;
        gbc_addmode_panel.fill = GridBagConstraints.BOTH;
        gbc_addmode_panel.gridx = 0;
        gbc_addmode_panel.gridy = 9;
        simproperties_panel.add(addmode_panel, gbc_addmode_panel);
        GridBagLayout gbl_addmode_panel = new GridBagLayout();
        gbl_addmode_panel.columnWidths = new int[]{103, 110, 74, 112};
        gbl_addmode_panel.rowHeights = new int[]{30, 30, 33, 32, 29, 30, 31, 0};
        gbl_addmode_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
        gbl_addmode_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        addmode_panel.setLayout(gbl_addmode_panel);

        JLabel creationModeLabel = new JLabel("Item Type");
        creationModeLabel.setFont(font);
        GridBagConstraints gbc_addmode_label = new GridBagConstraints();
        gbc_addmode_label.fill = GridBagConstraints.VERTICAL;
        gbc_addmode_label.insets = new Insets(5, 0, 5, 0);
        gbc_addmode_label.gridwidth = 4;
        gbc_addmode_label.gridx = 0;
        gbc_addmode_label.gridy = 0;
        addmode_panel.add(creationModeLabel, gbc_addmode_label);

        creationModesJComboBox.setModel(new DefaultComboBoxModel<>(PhysicsItemType.values()));
        creationModesJComboBox.setSelectedItem(ITEM_TYPE);
        creationModesJComboBox.setFont(font);
        creationModesJComboBox.addActionListener(e -> {
            PhysicsItemType selectedMode = (PhysicsItemType) creationModesJComboBox.getSelectedItem();
            if (selectedMode != ITEM_TYPE) ITEM_TYPE = selectedMode;

        });
        GridBagConstraints gbc_addmode_combobox = new GridBagConstraints();
        gbc_addmode_combobox.fill = GridBagConstraints.BOTH;
        gbc_addmode_combobox.insets = new Insets(0, 0, 5, 0);
        gbc_addmode_combobox.gridwidth = 4;
        gbc_addmode_combobox.gridx = 0;
        gbc_addmode_combobox.gridy = 1;
        addmode_panel.add(creationModesJComboBox, gbc_addmode_combobox);

        JLabel numpoints_label = new JLabel("Num Points");
        numpoints_label.setFont(font);
        GridBagConstraints gbc_numpoints_label = new GridBagConstraints();
        gbc_numpoints_label.fill = GridBagConstraints.VERTICAL;
        gbc_numpoints_label.anchor = GridBagConstraints.WEST;
        gbc_numpoints_label.insets = new Insets(0, 5, 5, 5);
        gbc_numpoints_label.gridx = 0;
        gbc_numpoints_label.gridy = 2;
        addmode_panel.add(numpoints_label, gbc_numpoints_label);

        numPointsField.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_numpoints_field = new GridBagConstraints();
        gbc_numpoints_field.fill = GridBagConstraints.BOTH;
        gbc_numpoints_field.insets = new Insets(0, 0, 5, 5);
        gbc_numpoints_field.gridx = 1;
        gbc_numpoints_field.gridy = 2;
        addmode_panel.add(numPointsField, gbc_numpoints_field);


        JCheckBox drawlinks_checkbox = new JCheckBox("Draw Links");
        drawlinks_checkbox.setFont(font);
        drawlinks_checkbox.setSelected(true);
        GridBagConstraints gbc_drawlinks_checkbox = new GridBagConstraints();
        gbc_drawlinks_checkbox.anchor = GridBagConstraints.WEST;
        gbc_drawlinks_checkbox.fill = GridBagConstraints.VERTICAL;
        gbc_drawlinks_checkbox.insets = new Insets(0, 0, 5, 5);
        gbc_drawlinks_checkbox.gridx = 2;
        gbc_drawlinks_checkbox.gridy = 2;
        addmode_panel.add(drawlinks_checkbox, gbc_drawlinks_checkbox);

        JCheckBox isSeverableCheckbox = new JCheckBox("Is Severable");
        isSeverableCheckbox.setSelected(true);
        isSeverableCheckbox.setFont(font);
        GridBagConstraints gbc_isSeverableCheckbox = new GridBagConstraints();
        gbc_isSeverableCheckbox.fill = GridBagConstraints.VERTICAL;
        gbc_isSeverableCheckbox.insets = new Insets(0, 0, 5, 0);
        gbc_isSeverableCheckbox.gridx = 3;
        gbc_isSeverableCheckbox.gridy = 2;
        addmode_panel.add(isSeverableCheckbox, gbc_isSeverableCheckbox);

        JLabel objectsize_label = new JLabel("Size");
        objectsize_label.setFont(font);
        GridBagConstraints gbc_objectsize_label = new GridBagConstraints();
        gbc_objectsize_label.anchor = GridBagConstraints.WEST;
        gbc_objectsize_label.fill = GridBagConstraints.VERTICAL;
        gbc_objectsize_label.insets = new Insets(0, 5, 5, 5);
        gbc_objectsize_label.gridx = 0;
        gbc_objectsize_label.gridy = 3;
        addmode_panel.add(objectsize_label, gbc_objectsize_label);

        objectSizeField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectsize_field = new GridBagConstraints();
        gbc_objectsize_field.insets = new Insets(0, 0, 5, 5);
        gbc_objectsize_field.fill = GridBagConstraints.BOTH;
        gbc_objectsize_field.gridx = 1;
        gbc_objectsize_field.gridy = 3;
        addmode_panel.add(objectSizeField, gbc_objectsize_field);

        JLabel teardistance_label = new JLabel("Tear Distance");
        teardistance_label.setFont(font);
        GridBagConstraints gbc_teardistance_label = new GridBagConstraints();
        gbc_teardistance_label.fill = GridBagConstraints.VERTICAL;
        gbc_teardistance_label.anchor = GridBagConstraints.WEST;
        gbc_teardistance_label.insets = new Insets(0, 0, 5, 5);
        gbc_teardistance_label.gridx = 2;
        gbc_teardistance_label.gridy = 3;
        addmode_panel.add(teardistance_label, gbc_teardistance_label);

        tearDistanceField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_teardistance_field = new GridBagConstraints();
        gbc_teardistance_field.insets = new Insets(0, 0, 5, 0);
        gbc_teardistance_field.fill = GridBagConstraints.BOTH;
        gbc_teardistance_field.gridx = 3;
        gbc_teardistance_field.gridy = 3;
        addmode_panel.add(tearDistanceField, gbc_teardistance_field);

        JLabel dampening_label = new JLabel("Dampening");
        dampening_label.setFont(font);
        GridBagConstraints gbc_dampening_label = new GridBagConstraints();
        gbc_dampening_label.fill = GridBagConstraints.VERTICAL;
        gbc_dampening_label.anchor = GridBagConstraints.WEST;
        gbc_dampening_label.insets = new Insets(0, 5, 5, 5);
        gbc_dampening_label.gridx = 0;
        gbc_dampening_label.gridy = 4;
        addmode_panel.add(dampening_label, gbc_dampening_label);

        dampeningField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_dampening_field = new GridBagConstraints();
        gbc_dampening_field.insets = new Insets(0, 0, 5, 5);
        gbc_dampening_field.fill = GridBagConstraints.BOTH;
        gbc_dampening_field.gridx = 1;
        gbc_dampening_field.gridy = 4;
        addmode_panel.add(dampeningField, gbc_dampening_field);

        JLabel objectmass_label = new JLabel("Mass");
        objectmass_label.setFont(font);
        GridBagConstraints gbc_objectmass_label = new GridBagConstraints();
        gbc_objectmass_label.fill = GridBagConstraints.VERTICAL;
        gbc_objectmass_label.anchor = GridBagConstraints.WEST;
        gbc_objectmass_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectmass_label.gridx = 2;
        gbc_objectmass_label.gridy = 4;
        addmode_panel.add(objectmass_label, gbc_objectmass_label);

        objectMassField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectmass_field = new GridBagConstraints();
        gbc_objectmass_field.insets = new Insets(0, 0, 5, 0);
        gbc_objectmass_field.fill = GridBagConstraints.BOTH;
        gbc_objectmass_field.gridx = 3;
        gbc_objectmass_field.gridy = 4;
        addmode_panel.add(objectMassField, gbc_objectmass_field);

        JLabel spacing_label = new JLabel("Spacing");
        spacing_label.setFont(font);
        GridBagConstraints gbc_spacing_label = new GridBagConstraints();
        gbc_spacing_label.fill = GridBagConstraints.VERTICAL;
        gbc_spacing_label.anchor = GridBagConstraints.WEST;
        gbc_spacing_label.insets = new Insets(0, 5, 5, 5);
        gbc_spacing_label.gridx = 0;
        gbc_spacing_label.gridy = 5;
        addmode_panel.add(spacing_label, gbc_spacing_label);

        spacingField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_spacing_field = new GridBagConstraints();
        gbc_spacing_field.insets = new Insets(0, 0, 5, 5);
        gbc_spacing_field.fill = GridBagConstraints.BOTH;
        gbc_spacing_field.gridx = 1;
        gbc_spacing_field.gridy = 5;
        addmode_panel.add(spacingField, gbc_spacing_field);

        JLabel stiffness_label = new JLabel("Stiffness");
        stiffness_label.setFont(font);
        GridBagConstraints gbc_stiffness_label = new GridBagConstraints();
        gbc_stiffness_label.fill = GridBagConstraints.VERTICAL;
        gbc_stiffness_label.insets = new Insets(0, 0, 5, 5);
        gbc_stiffness_label.anchor = GridBagConstraints.WEST;
        gbc_stiffness_label.gridx = 2;
        gbc_stiffness_label.gridy = 5;
        addmode_panel.add(stiffness_label, gbc_stiffness_label);

        stiffnessField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_stiffness_field = new GridBagConstraints();
        gbc_stiffness_field.insets = new Insets(0, 0, 5, 0);
        gbc_stiffness_field.fill = GridBagConstraints.BOTH;
        gbc_stiffness_field.gridx = 3;
        gbc_stiffness_field.gridy = 5;
        addmode_panel.add(stiffnessField, gbc_stiffness_field);

        JLabel pointcolor_label = new JLabel("Point Color");
        pointcolor_label.setFont(font);
        GridBagConstraints gbc_pointcolor_label = new GridBagConstraints();
        gbc_pointcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_pointcolor_label.anchor = GridBagConstraints.WEST;
        gbc_pointcolor_label.insets = new Insets(0, 5, 0, 5);
        gbc_pointcolor_label.gridx = 0;
        gbc_pointcolor_label.gridy = 6;
        addmode_panel.add(pointcolor_label, gbc_pointcolor_label);

        pointColorPanel = new CLabel(Color.GREEN, false);
        pointColorPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = pointColorPanel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Point Color", default_c);
                pointColorPanel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_pointcolor_panel = new GridBagConstraints();
        gbc_pointcolor_panel.insets = new Insets(0, 0, 0, 5);
        gbc_pointcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_pointcolor_panel.gridx = 1;
        gbc_pointcolor_panel.gridy = 6;
        addmode_panel.add(pointColorPanel, gbc_pointcolor_panel);

        JLabel linkcolor_label = new JLabel("Link Color");
        linkcolor_label.setFont(font);
        GridBagConstraints gbc_linkcolor_label = new GridBagConstraints();
        gbc_linkcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_linkcolor_label.anchor = GridBagConstraints.WEST;
        gbc_linkcolor_label.insets = new Insets(0, 0, 0, 5);
        gbc_linkcolor_label.gridx = 2;
        gbc_linkcolor_label.gridy = 6;
        addmode_panel.add(linkcolor_label, gbc_linkcolor_label);

        linkColorPanel = new CLabel(Color.PINK, false);
        linkColorPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = linkColorPanel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", default_c);
                linkColorPanel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_linkcolor_panel = new GridBagConstraints();
        gbc_linkcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_linkcolor_panel.gridx = 3;
        gbc_linkcolor_panel.gridy = 6;
        addmode_panel.add(linkColorPanel, gbc_linkcolor_panel);

        JPanel selection_panel = new JPanel();
        simeditorpane.addTab("Selection Properties", null, selection_panel, "You can change the selected objects properties here");
        GridBagLayout gbl_selection_panel = new GridBagLayout();
        gbl_selection_panel.columnWidths = new int[]{103, 110, 74, 112, 0};
        gbl_selection_panel.rowHeights = new int[]{0, 31, 30, 27, 0, 29, 0, 0};
        gbl_selection_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_selection_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        selection_panel.setLayout(gbl_selection_panel);

        JLabel selectionobjectproperties_label = new JLabel("Selection Object Properties");
        selectionobjectproperties_label.setFont(font);
        GridBagConstraints gbc_selectionobjectproperties_label = new GridBagConstraints();
        gbc_selectionobjectproperties_label.gridwidth = 4;
        gbc_selectionobjectproperties_label.insets = new Insets(5, 0, 5, 0);
        gbc_selectionobjectproperties_label.gridx = 0;
        gbc_selectionobjectproperties_label.gridy = 0;
        selection_panel.add(selectionobjectproperties_label, gbc_selectionobjectproperties_label);

        selectionShowPointCheckbox.setSelected(false);
        selectionShowPointCheckbox.setFont(font);
        GridBagConstraints gbc_pinned_checkbox = new GridBagConstraints();
        gbc_pinned_checkbox.anchor = GridBagConstraints.WEST;
        gbc_pinned_checkbox.insets = new Insets(0, 5, 5, 5);
        gbc_pinned_checkbox.gridx = 0;
        gbc_pinned_checkbox.gridy = 1;
        selection_panel.add(selectionShowPointCheckbox, gbc_pinned_checkbox);

        selectionCollidableCheckbox.setSelected(true);
        selectionCollidableCheckbox.setFont(font);
        GridBagConstraints gbc_collidable_checkbox = new GridBagConstraints();
        gbc_collidable_checkbox.anchor = GridBagConstraints.WEST;
        gbc_collidable_checkbox.insets = new Insets(0, 0, 5, 5);
        gbc_collidable_checkbox.gridx = 1;
        gbc_collidable_checkbox.gridy = 1;
        selection_panel.add(selectionCollidableCheckbox, gbc_collidable_checkbox);

        JLabel objectcolor_label = new JLabel("Selection Color(Click to Change)");
        objectcolor_label.setFont(font);
        GridBagConstraints gbc_objectcolor_label = new GridBagConstraints();
        gbc_objectcolor_label.anchor = GridBagConstraints.WEST;
        gbc_objectcolor_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectcolor_label.gridx = 2;
        gbc_objectcolor_label.gridy = 1;
        selection_panel.add(objectcolor_label, gbc_objectcolor_label);

        lblSelectionColor = new CLabel(Color.CYAN, false);
        lblSelectionColor.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = lblSelectionColor.getBackground();
                Color c = JColorChooser.showDialog(frame, "Color", default_c);
                lblSelectionColor.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_objectcolor_panel = new GridBagConstraints();
        gbc_objectcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_objectcolor_panel.insets = new Insets(0, 0, 5, 0);
        gbc_objectcolor_panel.gridx = 3;
        gbc_objectcolor_panel.gridy = 1;
        selection_panel.add(lblSelectionColor, gbc_objectcolor_panel);

        JLabel radius_label = new JLabel("Radius");
        radius_label.setFont(font);
        GridBagConstraints gbc_radius_label = new GridBagConstraints();
        gbc_radius_label.anchor = GridBagConstraints.WEST;
        gbc_radius_label.insets = new Insets(0, 5, 5, 5);
        gbc_radius_label.gridx = 0;
        gbc_radius_label.gridy = 2;
        selection_panel.add(radius_label, gbc_radius_label);

        selectionRadiusField.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_radius_field = new GridBagConstraints();
        gbc_radius_field.fill = GridBagConstraints.BOTH;
        gbc_radius_field.insets = new Insets(0, 0, 5, 5);
        gbc_radius_field.gridx = 1;
        gbc_radius_field.gridy = 2;
        selection_panel.add(selectionRadiusField, gbc_radius_field);

        JLabel mass_label = new JLabel("Mass");
        mass_label.setFont(font);
        GridBagConstraints gbc_mass_label = new GridBagConstraints();
        gbc_mass_label.fill = GridBagConstraints.VERTICAL;
        gbc_mass_label.anchor = GridBagConstraints.WEST;
        gbc_mass_label.insets = new Insets(0, 0, 5, 5);
        gbc_mass_label.gridx = 2;
        gbc_mass_label.gridy = 2;
        selection_panel.add(mass_label, gbc_mass_label);

        selectionMassField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_mass_field = new GridBagConstraints();
        gbc_mass_field.fill = GridBagConstraints.BOTH;
        gbc_mass_field.insets = new Insets(0, 0, 5, 0);
        gbc_mass_field.gridx = 3;
        gbc_mass_field.gridy = 2;
        selection_panel.add(selectionMassField, gbc_mass_field);

        JLabel objectdampening_label = new JLabel("Dampening");
        objectdampening_label.setFont(font);
        GridBagConstraints gbc_objectdampening_label = new GridBagConstraints();
        gbc_objectdampening_label.anchor = GridBagConstraints.WEST;
        gbc_objectdampening_label.insets = new Insets(0, 5, 5, 5);
        gbc_objectdampening_label.gridx = 0;
        gbc_objectdampening_label.gridy = 3;
        selection_panel.add(objectdampening_label, gbc_objectdampening_label);

        selectionDampeningField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectdampening_field = new GridBagConstraints();
        gbc_objectdampening_field.fill = GridBagConstraints.BOTH;
        gbc_objectdampening_field.insets = new Insets(0, 0, 5, 5);
        gbc_objectdampening_field.gridx = 1;
        gbc_objectdampening_field.gridy = 3;
        selection_panel.add(selectionDampeningField, gbc_objectdampening_field);

        JLabel objectstiffness_label = new JLabel("Stiffness");
        objectstiffness_label.setFont(font);
        GridBagConstraints gbc_objectstiffness_label = new GridBagConstraints();
        gbc_objectstiffness_label.anchor = GridBagConstraints.WEST;
        gbc_objectstiffness_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectstiffness_label.gridx = 2;
        gbc_objectstiffness_label.gridy = 3;
        selection_panel.add(objectstiffness_label, gbc_objectstiffness_label);

        selectionStiffnessField.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectstiffness_field = new GridBagConstraints();
        gbc_objectstiffness_field.fill = GridBagConstraints.BOTH;
        gbc_objectstiffness_field.insets = new Insets(0, 0, 5, 0);
        gbc_objectstiffness_field.gridx = 3;
        gbc_objectstiffness_field.gridy = 3;
        selection_panel.add(selectionStiffnessField, gbc_objectstiffness_field);

        JButton btnSetProperties = new JButton("Set Object Properties");
        btnSetProperties.setFont(font);
        btnSetProperties.addActionListener(e -> setPhysicsItemProperties());
        GridBagConstraints gbc_objectsetproperties_button = new GridBagConstraints();
        gbc_objectsetproperties_button.fill = GridBagConstraints.BOTH;
        gbc_objectsetproperties_button.gridwidth = 4;
        gbc_objectsetproperties_button.insets = new Insets(0, 0, 5, 0);
        gbc_objectsetproperties_button.gridx = 0;
        gbc_objectsetproperties_button.gridy = 4;
        selection_panel.add(btnSetProperties, gbc_objectsetproperties_button);

        JLabel lblConstraintProperties = new JLabel("<html><h3>Selection Constraint Properties <br> <span style='color: green'>Green = Selected Point</span>" +
                " <br> <span style='color: red'>Red = Selected Constraint</span></h3></html>");
        lblConstraintProperties.setFont(font);
        GridBagConstraints gbc_lblConstraintProperties = new GridBagConstraints();
        gbc_lblConstraintProperties.insets = new Insets(0, 0, 5, 0);
        gbc_lblConstraintProperties.gridwidth = 4;
        gbc_lblConstraintProperties.gridx = 0;
        gbc_lblConstraintProperties.gridy = 5;
        selection_panel.add(lblConstraintProperties, gbc_lblConstraintProperties);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 6;
        selection_panel.add(scrollPane, gbc_scrollPane);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{120, 150, 66, 72, 0};
        gbl_panel.rowHeights = new int[]{36, 50, 43, 41, 44, 47, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridheight = 6;
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 0;
        panel.add(scrollPane_1, gbc_scrollPane_1);

        JLabel lblConstraintList = new JLabel("Constraint List");
        lblConstraintList.setHorizontalAlignment(SwingConstants.CENTER);
        lblConstraintList.setFont(font);
        scrollPane_1.setColumnHeaderView(lblConstraintList);

        JPanel panel_2 = new JPanel();
        panel_2.setFont(font);
        scrollPane_1.setViewportView(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2, BorderLayout.CENTER);

        constraintsList.setSelectedIndex(0);
        constraintsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        constraintsList.setFont(font);
        scrollPane_2.setViewportView(constraintsList);

        JLabel label = new JLabel("Constraint Properties");
        label.setFont(font);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.gridwidth = 3;
        gbc_label.insets = new Insets(5, 0, 5, 0);
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel.add(label, gbc_label);

        showSelectionConstraintCheckbox.setSelected(false);
        showSelectionConstraintCheckbox.setFont(font);
        GridBagConstraints gbc_checkBox = new GridBagConstraints();
        gbc_checkBox.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox.anchor = GridBagConstraints.WEST;
        gbc_checkBox.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox.gridx = 1;
        gbc_checkBox.gridy = 1;
        panel.add(showSelectionConstraintCheckbox, gbc_checkBox);

        constraintDrawLinkCheckbox.setSelected(true);
        constraintDrawLinkCheckbox.setFont(font);
        GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
        gbc_checkBox_1.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_1.anchor = GridBagConstraints.WEST;
        gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox_1.gridx = 2;
        gbc_checkBox_1.gridy = 1;
        panel.add(constraintDrawLinkCheckbox, gbc_checkBox_1);

        constraintSeverableCheckbox.setSelected(true);
        constraintSeverableCheckbox.setFont(font);
        GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
        gbc_checkBox_2.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_2.anchor = GridBagConstraints.WEST;
        gbc_checkBox_2.gridwidth = 2;
        gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
        gbc_checkBox_2.gridx = 3;
        gbc_checkBox_2.gridy = 1;
        panel.add(constraintSeverableCheckbox, gbc_checkBox_2);

        JLabel constraint_stiffness_label = new JLabel("Stiffness");
        constraint_stiffness_label.setFont(font);
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.fill = GridBagConstraints.VERTICAL;
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.insets = new Insets(0, 5, 5, 5);
        gbc_label_1.gridx = 1;
        gbc_label_1.gridy = 2;
        panel.add(constraint_stiffness_label, gbc_label_1);

        constraintStiffnessField.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 2;
        panel.add(constraintStiffnessField, gbc_textField);

        JLabel label_2 = new JLabel("Tear Distance");
        label_2.setFont(font);
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.fill = GridBagConstraints.VERTICAL;
        gbc_label_2.anchor = GridBagConstraints.WEST;
        gbc_label_2.insets = new Insets(0, 5, 5, 5);
        gbc_label_2.gridx = 1;
        gbc_label_2.gridy = 3;
        panel.add(label_2, gbc_label_2);

        constraintTearDistanceField.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.fill = GridBagConstraints.BOTH;
        gbc_textField_1.gridwidth = 2;
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.gridx = 2;
        gbc_textField_1.gridy = 3;
        panel.add(constraintTearDistanceField, gbc_textField_1);

        JLabel constraint_link_color_label = new JLabel("Link Color (Click to Change)");
        constraint_link_color_label.setFont(font);
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.fill = GridBagConstraints.VERTICAL;
        gbc_label_3.anchor = GridBagConstraints.WEST;
        gbc_label_3.insets = new Insets(0, 5, 5, 5);
        gbc_label_3.gridx = 1;
        gbc_label_3.gridy = 4;
        panel.add(constraint_link_color_label, gbc_label_3);

        lblConstraintLink = new CLabel(Color.BLUE, false);
        lblConstraintLink.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = lblConstraintLink.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", default_c);
                lblConstraintLink.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 4;
        panel.add(lblConstraintLink, gbc_panel_1);

        JButton btnSetConstraints = new JButton("Set Constraint Properties");
        btnSetConstraints.addActionListener(e -> setPhysicsItemConstraints());
        btnSetConstraints.setFont(font);
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.gridwidth = 3;
        gbc_button.gridx = 1;
        gbc_button.gridy = 5;
        panel.add(btnSetConstraints, gbc_button);

        frame.setVisible(true);
    }

    public static void changeItemType(boolean advance) {
        PhysicsEditor.ITEM_TYPE = EnumUtil.transition(PhysicsEditor.ITEM_TYPE, advance);
        PhysicsEditor.creationModesJComboBox.setSelectedItem(PhysicsEditor.ITEM_TYPE);
    }

    public static void setEditorMode(PhysicsEditorMode editorMode) {
        PhysicsEditor.EDITOR_MODE = editorMode;
        PhysicsEditor.editorModesJComboBox.setSelectedItem(editorMode);
    }

    private void setPhysicsItemProperties() {
        if (Physics.selectedVertex == null) return;

        String rad = selectionRadiusField.getText();
        String mass = selectionMassField.getText();
        String dampening = selectionDampeningField.getText();
        String stiffness = selectionStiffnessField.getText();

        Physics.selectedVertex.color = lblSelectionColor.getBackground();
        Physics.selectedVertex.collidable = selectionCollidableCheckbox.isSelected();

        if (InputUtil.canParseFloat(rad)) Physics.selectedVertex.radius = Float.parseFloat(rad);
        if (InputUtil.canParseFloat(mass)) Physics.selectedVertex.mass = Float.parseFloat(mass);
        if (InputUtil.canParseFloat(dampening)) Physics.selectedVertex.damping = Float.parseFloat(dampening);
        if (InputUtil.canParseFloat(stiffness)) Physics.selectedVertex.stiffness = Float.parseFloat(stiffness);
    }

    private void setPhysicsItemConstraints() {
        if (Physics.selectedVertex == null) return;

        String cStiffness = constraintStiffnessField.getText();
        String cTearDist = constraintTearDistanceField.getText();

        List<Integer> constraintList = constraintsList.getSelectedValuesList();
        if (constraintList.isEmpty()) return;

        for (int i = 0; i < constraintList.size(); i++) {
            Edge edge = Physics.selectedVertex.edges.get(constraintList.get(i));
            edge.render = constraintDrawLinkCheckbox.isSelected();
            edge.severable = constraintSeverableCheckbox.isSelected();
            edge.color = lblConstraintLink.getBackground();
            edge.stiffness = InputUtil.floatTextFieldGuardDefault(0.0f, edge.stiffness, cStiffness);
            edge.tearDistance = InputUtil.floatTextFieldGuardDefault(0.0f, edge.tearDistance, cTearDist);
        }
    }

    public static void clearSelectedPhysicsItemUIFields() {
        if (instance == null) return;

        selectionDampeningField.setText("");
        selectionMassField.setText("");
        selectionRadiusField.setText("");
        selectionStiffnessField.setText("");
    }

    public static void setSelectedPhysicsItemUIFields(Vertex v) {
        if (instance == null) return;

        lblSelectionColor.setBackground(v.color);
        selectionCollidableCheckbox.setSelected(v.collidable);
        selectionDampeningField.setText("" + v.damping);
        selectionMassField.setText("" + v.mass);
        selectionRadiusField.setText("" + v.radius);
        selectionStiffnessField.setText("" + v.stiffness);
    }

    private void setSimulationProperties() {
        // Setting Simulation Properties
        String simAcc = simAccField.getText(), dragForce = dragForceField.getText(),
                grav = gravityField.getText(), airVis = airViscosityField.getText();

        Physics.SIM_ACCURACY = InputUtil.intTextFieldGuardDefault(1, Physics.SIM_ACCURACY, simAcc);
        Physics.DRAG_FORCE = InputUtil.floatTextFieldGuardDefault(0.0f, Physics.DRAG_FORCE, dragForce);
        Physics.GRAVITY = InputUtil.floatTextFieldGuardDefault(0.0f, Physics.GRAVITY , grav);
        Physics.AIR_VISCOSITY = InputUtil.floatTextFieldGuardDefault(0.0f, Physics.AIR_VISCOSITY, airVis);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }

    public static void updateConstraintsList(List<Edge> edgeList) {
        if (instance == null) return;

        constraintsListModel.clear();

        if (edgeList == null || edgeList.isEmpty()) return;

        for (int i = 0; i < edgeList.size(); i++)
            constraintsListModel.addElement(i);

        constraintsList.setSelectedIndex(0);
    }
}
