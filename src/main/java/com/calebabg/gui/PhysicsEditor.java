package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.enums.PhysicsEditorMode;
import com.calebabg.enums.PhysicsItemType;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CLabel;
import com.calebabg.utilities.EnumUtil;
import com.calebabg.utilities.FontUtil;
import com.calebabg.utilities.InputUtil;
import com.calebabg.physics.Edge;
import com.calebabg.physics.Physics;
import com.calebabg.physics.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.calebabg.core.EngineVariables.eFrame;
import static com.calebabg.core.EngineVariables.engineSettings;

public class PhysicsEditor {
    private static PhysicsEditor instance = null;

    private static JFrame frame;

    public static PhysicsEditorMode EDITOR_MODE = PhysicsEditorMode.ADD;
    public static PhysicsItemType ITEM_TYPE = PhysicsItemType.POINT;

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
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane simEditorScrollPane = new JScrollPane();
        frame.getContentPane().add(simEditorScrollPane, BorderLayout.CENTER);

        JToolBar simEditorToolbar = new JToolBar();
        simEditorScrollPane.setColumnHeaderView(simEditorToolbar);

        JPanel toolbarPanel = new JPanel();
        simEditorToolbar.add(toolbarPanel);
        GridBagLayout gblToolbarpanel = new GridBagLayout();
        gblToolbarpanel.columnWidths = new int[]{69, 70, 63, 0, 72, 0, 0};
        gblToolbarpanel.rowHeights = new int[]{0, 0};
        gblToolbarpanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gblToolbarpanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        toolbarPanel.setLayout(gblToolbarpanel);

        JLabel editormodeLabel = new JLabel("Editor Mode");
        GridBagConstraints gbcEditormodeLabel = new GridBagConstraints();
        gbcEditormodeLabel.anchor = GridBagConstraints.WEST;
        gbcEditormodeLabel.insets = new Insets(0, 0, 0, 5);
        gbcEditormodeLabel.gridx = 0;
        gbcEditormodeLabel.gridy = 0;
        toolbarPanel.add(editormodeLabel, gbcEditormodeLabel);
        editormodeLabel.setFont(FontUtil.PLAIN_14);
        editormodeLabel.setIconTextGap(5);

        editorModesJComboBox.setModel(new DefaultComboBoxModel<>(PhysicsEditorMode.values()));
        editorModesJComboBox.setSelectedItem(EDITOR_MODE);
        editorModesJComboBox.setFont(FontUtil.PLAIN_14);
        editorModesJComboBox.addActionListener(e -> EDITOR_MODE = (PhysicsEditorMode) editorModesJComboBox.getSelectedItem());
        GridBagConstraints gbcEditormodeCombobox = new GridBagConstraints();
        gbcEditormodeCombobox.fill = GridBagConstraints.HORIZONTAL;
        gbcEditormodeCombobox.insets = new Insets(0, 0, 0, 5);
        gbcEditormodeCombobox.gridx = 1;
        gbcEditormodeCombobox.gridy = 0;
        toolbarPanel.add(editorModesJComboBox, gbcEditormodeCombobox);

        JCheckBox gravityCheckbox = new JCheckBox("Zero Gravity");
        gravityCheckbox.setSelected(Physics.ZERO_GRAVITY);
        gravityCheckbox.addActionListener(e -> Physics.toggleGravity());
        gravityCheckbox.setToolTipText("Toggles Gravity");
        gravityCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcGravityCheckbox = new GridBagConstraints();
        gbcGravityCheckbox.insets = new Insets(0, 0, 0, 5);
        gbcGravityCheckbox.gridx = 2;
        gbcGravityCheckbox.gridy = 0;
        toolbarPanel.add(gravityCheckbox, gbcGravityCheckbox);

        JCheckBox debugCheckbox = new JCheckBox("Debug");
        debugCheckbox.setSelected(Physics.DEBUG_MODE);
        debugCheckbox.addActionListener(e -> Physics.toggleDebug());
        debugCheckbox.setToolTipText("Toggles Debug Mode");
        debugCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcDebugcheckbox = new GridBagConstraints();
        gbcDebugcheckbox.insets = new Insets(0, 0, 0, 5);
        gbcDebugcheckbox.gridx = 3;
        gbcDebugcheckbox.gridy = 0;
        toolbarPanel.add(debugCheckbox, gbcDebugcheckbox);

        JCheckBox smoothingCheckbox = new JCheckBox("Smoothing");
        smoothingCheckbox.setSelected(engineSettings.smoothRenderingEnabled);
        smoothingCheckbox.addActionListener(e -> engineSettings.toggleGraphicsSmoothing());
        smoothingCheckbox.setToolTipText("Toggles Graphics Smoothing");
        smoothingCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcSmoothingCheckbox = new GridBagConstraints();
        gbcSmoothingCheckbox.gridx = 4;
        gbcSmoothingCheckbox.gridy = 0;
        toolbarPanel.add(smoothingCheckbox, gbcSmoothingCheckbox);

        JTabbedPane simEditorPane = new JTabbedPane(SwingConstants.TOP);
        simEditorPane.setFont(FontUtil.PLAIN_14);
        simEditorScrollPane.setViewportView(simEditorPane);

        JPanel simPropertiesPanel = new JPanel();
        simPropertiesPanel.setFont(FontUtil.PLAIN_14);
        simEditorPane.addTab("Simulation Properties", null, simPropertiesPanel, "You can change simulation properties here");
        simEditorPane.setEnabledAt(0, true);
        GridBagLayout gblSimPropertiesPanel = new GridBagLayout();
        gblSimPropertiesPanel.columnWidths = new int[]{164, -44};
        gblSimPropertiesPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 37, 0, 0};
        gblSimPropertiesPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblSimPropertiesPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        simPropertiesPanel.setLayout(gblSimPropertiesPanel);

        JLabel simAccuracyLabel = new JLabel("Simulation Accuracy (higher value = performance hit)");
        simAccuracyLabel.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcSimAccuracyLabel = new GridBagConstraints();
        gbcSimAccuracyLabel.fill = GridBagConstraints.VERTICAL;
        gbcSimAccuracyLabel.insets = new Insets(5, 0, 5, 0);
        gbcSimAccuracyLabel.gridwidth = 2;
        gbcSimAccuracyLabel.gridx = 0;
        gbcSimAccuracyLabel.gridy = 0;
        simPropertiesPanel.add(simAccuracyLabel, gbcSimAccuracyLabel);

        simAccField.setText("" + Physics.SIM_ACCURACY);
        simAccField.setHorizontalAlignment(SwingConstants.CENTER);
        simAccField.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcSimAccuracyField = new GridBagConstraints();
        gbcSimAccuracyField.insets = new Insets(0, 0, 5, 0);
        gbcSimAccuracyField.gridwidth = 2;
        gbcSimAccuracyField.fill = GridBagConstraints.BOTH;
        gbcSimAccuracyField.gridx = 0;
        gbcSimAccuracyField.gridy = 1;
        simPropertiesPanel.add(simAccField, gbcSimAccuracyField);


        JLabel dragForceLabel = new JLabel("Drag Force (smaller value = stronger force)");
        dragForceLabel.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcDragForceLabel = new GridBagConstraints();
        gbcDragForceLabel.fill = GridBagConstraints.VERTICAL;
        gbcDragForceLabel.insets = new Insets(0, 0, 5, 0);
        gbcDragForceLabel.gridwidth = 2;
        gbcDragForceLabel.gridx = 0;
        gbcDragForceLabel.gridy = 2;
        simPropertiesPanel.add(dragForceLabel, gbcDragForceLabel);

        dragForceField.setText("" + Physics.DRAG_FORCE);
        dragForceField.setHorizontalAlignment(SwingConstants.CENTER);
        dragForceField.setFont(FontUtil.PLAIN_14);

        GridBagConstraints gbcDragForceField = new GridBagConstraints();
        gbcDragForceField.insets = new Insets(0, 0, 5, 0);
        gbcDragForceField.gridwidth = 2;
        gbcDragForceField.fill = GridBagConstraints.BOTH;
        gbcDragForceField.gridx = 0;
        gbcDragForceField.gridy = 3;
        simPropertiesPanel.add(dragForceField, gbcDragForceField);

        JLabel gravityLabel = new JLabel("Gravity");
        gravityLabel.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcGravityLabel = new GridBagConstraints();
        gbcGravityLabel.fill = GridBagConstraints.VERTICAL;
        gbcGravityLabel.insets = new Insets(0, 0, 5, 0);
        gbcGravityLabel.gridwidth = 2;
        gbcGravityLabel.gridx = 0;
        gbcGravityLabel.gridy = 4;
        simPropertiesPanel.add(gravityLabel, gbcGravityLabel);

        gravityField.setText("" + Physics.GRAVITY);
        gravityField.setHorizontalAlignment(SwingConstants.CENTER);
        gravityField.setFont(FontUtil.PLAIN_14);

        GridBagConstraints gbcGravityField = new GridBagConstraints();
        gbcGravityField.insets = new Insets(0, 0, 5, 0);
        gbcGravityField.gridwidth = 2;
        gbcGravityField.fill = GridBagConstraints.BOTH;
        gbcGravityField.gridx = 0;
        gbcGravityField.gridy = 5;
        simPropertiesPanel.add(gravityField, gbcGravityField);

        JLabel airViscosityLabel = new JLabel("Air Viscosity");
        airViscosityLabel.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbcAirViscosityLabel = new GridBagConstraints();
        gbcAirViscosityLabel.fill = GridBagConstraints.VERTICAL;
        gbcAirViscosityLabel.gridwidth = 2;
        gbcAirViscosityLabel.insets = new Insets(0, 0, 5, 0);
        gbcAirViscosityLabel.gridx = 0;
        gbcAirViscosityLabel.gridy = 6;
        simPropertiesPanel.add(airViscosityLabel, gbcAirViscosityLabel);

        airViscosityField.setText("" + Physics.AIR_VISCOSITY);
        airViscosityField.setHorizontalAlignment(SwingConstants.CENTER);
        airViscosityField.setFont(FontUtil.PLAIN_14);

        GridBagConstraints gbc_airviscosity_field = new GridBagConstraints();
        gbc_airviscosity_field.gridwidth = 2;
        gbc_airviscosity_field.insets = new Insets(0, 0, 5, 0);
        gbc_airviscosity_field.fill = GridBagConstraints.BOTH;
        gbc_airviscosity_field.gridx = 0;
        gbc_airviscosity_field.gridy = 7;
        simPropertiesPanel.add(airViscosityField, gbc_airviscosity_field);

        JButton btnSetSimulationProperties = new JButton("Set Simulation Properties");
        btnSetSimulationProperties.addActionListener(e -> setSimulationProperties());
        btnSetSimulationProperties.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_btnSetSimulationProperties = new GridBagConstraints();
        gbc_btnSetSimulationProperties.gridwidth = 2;
        gbc_btnSetSimulationProperties.fill = GridBagConstraints.BOTH;
        gbc_btnSetSimulationProperties.insets = new Insets(0, 0, 5, 0);
        gbc_btnSetSimulationProperties.gridx = 0;
        gbc_btnSetSimulationProperties.gridy = 8;
        simPropertiesPanel.add(btnSetSimulationProperties, gbc_btnSetSimulationProperties);

        JPanel addmode_panel = new JPanel();
        GridBagConstraints gbc_addmode_panel = new GridBagConstraints();
        gbc_addmode_panel.gridwidth = 2;
        gbc_addmode_panel.fill = GridBagConstraints.BOTH;
        gbc_addmode_panel.gridx = 0;
        gbc_addmode_panel.gridy = 9;
        simPropertiesPanel.add(addmode_panel, gbc_addmode_panel);
        GridBagLayout gbl_addmode_panel = new GridBagLayout();
        gbl_addmode_panel.columnWidths = new int[]{103, 110, 74, 112};
        gbl_addmode_panel.rowHeights = new int[]{30, 30, 33, 32, 29, 30, 31, 0};
        gbl_addmode_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
        gbl_addmode_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        addmode_panel.setLayout(gbl_addmode_panel);

        JLabel creationModeLabel = new JLabel("Item Type");
        creationModeLabel.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_addmode_label = new GridBagConstraints();
        gbc_addmode_label.fill = GridBagConstraints.VERTICAL;
        gbc_addmode_label.insets = new Insets(5, 0, 5, 0);
        gbc_addmode_label.gridwidth = 4;
        gbc_addmode_label.gridx = 0;
        gbc_addmode_label.gridy = 0;
        addmode_panel.add(creationModeLabel, gbc_addmode_label);

        creationModesJComboBox.setModel(new DefaultComboBoxModel<>(PhysicsItemType.values()));
        creationModesJComboBox.setSelectedItem(ITEM_TYPE);
        creationModesJComboBox.setFont(FontUtil.PLAIN_14);
        creationModesJComboBox.addActionListener(e -> ITEM_TYPE = (PhysicsItemType) creationModesJComboBox.getSelectedItem());
        GridBagConstraints gbc_addmode_combobox = new GridBagConstraints();
        gbc_addmode_combobox.fill = GridBagConstraints.BOTH;
        gbc_addmode_combobox.insets = new Insets(0, 0, 5, 0);
        gbc_addmode_combobox.gridwidth = 4;
        gbc_addmode_combobox.gridx = 0;
        gbc_addmode_combobox.gridy = 1;
        addmode_panel.add(creationModesJComboBox, gbc_addmode_combobox);

        JLabel numpoints_label = new JLabel("Num Points");
        numpoints_label.setFont(FontUtil.PLAIN_14);
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
        drawlinks_checkbox.setFont(FontUtil.PLAIN_14);
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
        isSeverableCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_isSeverableCheckbox = new GridBagConstraints();
        gbc_isSeverableCheckbox.fill = GridBagConstraints.VERTICAL;
        gbc_isSeverableCheckbox.insets = new Insets(0, 0, 5, 0);
        gbc_isSeverableCheckbox.gridx = 3;
        gbc_isSeverableCheckbox.gridy = 2;
        addmode_panel.add(isSeverableCheckbox, gbc_isSeverableCheckbox);

        JLabel objectsize_label = new JLabel("Size");
        objectsize_label.setFont(FontUtil.PLAIN_14);
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
        teardistance_label.setFont(FontUtil.PLAIN_14);
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
        dampening_label.setFont(FontUtil.PLAIN_14);
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
        objectmass_label.setFont(FontUtil.PLAIN_14);
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
        spacing_label.setFont(FontUtil.PLAIN_14);
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
        stiffness_label.setFont(FontUtil.PLAIN_14);
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
        pointcolor_label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_pointcolor_label = new GridBagConstraints();
        gbc_pointcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_pointcolor_label.anchor = GridBagConstraints.WEST;
        gbc_pointcolor_label.insets = new Insets(0, 5, 0, 5);
        gbc_pointcolor_label.gridx = 0;
        gbc_pointcolor_label.gridy = 6;
        addmode_panel.add(pointcolor_label, gbc_pointcolor_label);

        pointColorPanel = new CLabel(Color.GREEN, false);
        pointColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color panelBackground = pointColorPanel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Point Color", panelBackground);
                pointColorPanel.setBackground((c != null) ? c : panelBackground);
            }
        });
        GridBagConstraints gbc_pointcolor_panel = new GridBagConstraints();
        gbc_pointcolor_panel.insets = new Insets(0, 0, 0, 5);
        gbc_pointcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_pointcolor_panel.gridx = 1;
        gbc_pointcolor_panel.gridy = 6;
        addmode_panel.add(pointColorPanel, gbc_pointcolor_panel);

        JLabel linkcolor_label = new JLabel("Link Color");
        linkcolor_label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_linkcolor_label = new GridBagConstraints();
        gbc_linkcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_linkcolor_label.anchor = GridBagConstraints.WEST;
        gbc_linkcolor_label.insets = new Insets(0, 0, 0, 5);
        gbc_linkcolor_label.gridx = 2;
        gbc_linkcolor_label.gridy = 6;
        addmode_panel.add(linkcolor_label, gbc_linkcolor_label);

        linkColorPanel = new CLabel(Color.PINK, false);
        linkColorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color panelBackground = linkColorPanel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", panelBackground);
                linkColorPanel.setBackground((c != null) ? c : panelBackground);
            }
        });
        GridBagConstraints gbc_linkcolor_panel = new GridBagConstraints();
        gbc_linkcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_linkcolor_panel.gridx = 3;
        gbc_linkcolor_panel.gridy = 6;
        addmode_panel.add(linkColorPanel, gbc_linkcolor_panel);

        JPanel selection_panel = new JPanel();
        simEditorPane.addTab("Selection Properties", null, selection_panel, "You can change the selected objects properties here");
        GridBagLayout gbl_selection_panel = new GridBagLayout();
        gbl_selection_panel.columnWidths = new int[]{103, 110, 74, 112, 0};
        gbl_selection_panel.rowHeights = new int[]{0, 31, 30, 27, 0, 29, 0, 0};
        gbl_selection_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_selection_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        selection_panel.setLayout(gbl_selection_panel);

        JLabel selectionobjectproperties_label = new JLabel("Selection Object Properties");
        selectionobjectproperties_label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_selectionobjectproperties_label = new GridBagConstraints();
        gbc_selectionobjectproperties_label.gridwidth = 4;
        gbc_selectionobjectproperties_label.insets = new Insets(5, 0, 5, 0);
        gbc_selectionobjectproperties_label.gridx = 0;
        gbc_selectionobjectproperties_label.gridy = 0;
        selection_panel.add(selectionobjectproperties_label, gbc_selectionobjectproperties_label);

        selectionShowPointCheckbox.setSelected(false);
        selectionShowPointCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_pinned_checkbox = new GridBagConstraints();
        gbc_pinned_checkbox.anchor = GridBagConstraints.WEST;
        gbc_pinned_checkbox.insets = new Insets(0, 5, 5, 5);
        gbc_pinned_checkbox.gridx = 0;
        gbc_pinned_checkbox.gridy = 1;
        selection_panel.add(selectionShowPointCheckbox, gbc_pinned_checkbox);

        selectionCollidableCheckbox.setSelected(true);
        selectionCollidableCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_collidable_checkbox = new GridBagConstraints();
        gbc_collidable_checkbox.anchor = GridBagConstraints.WEST;
        gbc_collidable_checkbox.insets = new Insets(0, 0, 5, 5);
        gbc_collidable_checkbox.gridx = 1;
        gbc_collidable_checkbox.gridy = 1;
        selection_panel.add(selectionCollidableCheckbox, gbc_collidable_checkbox);

        JLabel objectcolor_label = new JLabel("Selection Color(Click to Change)");
        objectcolor_label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_objectcolor_label = new GridBagConstraints();
        gbc_objectcolor_label.anchor = GridBagConstraints.WEST;
        gbc_objectcolor_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectcolor_label.gridx = 2;
        gbc_objectcolor_label.gridy = 1;
        selection_panel.add(objectcolor_label, gbc_objectcolor_label);

        lblSelectionColor = new CLabel(Color.CYAN, false);
        lblSelectionColor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color selectionColorBackground = lblSelectionColor.getBackground();
                Color c = JColorChooser.showDialog(frame, "Color", selectionColorBackground);
                lblSelectionColor.setBackground((c != null) ? c : selectionColorBackground);
            }
        });
        GridBagConstraints gbc_objectcolor_panel = new GridBagConstraints();
        gbc_objectcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_objectcolor_panel.insets = new Insets(0, 0, 5, 0);
        gbc_objectcolor_panel.gridx = 3;
        gbc_objectcolor_panel.gridy = 1;
        selection_panel.add(lblSelectionColor, gbc_objectcolor_panel);

        JLabel radius_label = new JLabel("Radius");
        radius_label.setFont(FontUtil.PLAIN_14);
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
        mass_label.setFont(FontUtil.PLAIN_14);
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
        objectdampening_label.setFont(FontUtil.PLAIN_14);
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
        objectstiffness_label.setFont(FontUtil.PLAIN_14);
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
        btnSetProperties.setFont(FontUtil.PLAIN_14);
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
        lblConstraintProperties.setFont(FontUtil.PLAIN_14);
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
        lblConstraintList.setFont(FontUtil.PLAIN_14);
        scrollPane_1.setColumnHeaderView(lblConstraintList);

        JPanel panel_2 = new JPanel();
        panel_2.setFont(FontUtil.PLAIN_14);
        scrollPane_1.setViewportView(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2, BorderLayout.CENTER);

        constraintsList.setSelectedIndex(0);
        constraintsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        constraintsList.setFont(FontUtil.PLAIN_14);
        scrollPane_2.setViewportView(constraintsList);

        JLabel label = new JLabel("Constraint Properties");
        label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.gridwidth = 3;
        gbc_label.insets = new Insets(5, 0, 5, 0);
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel.add(label, gbc_label);

        showSelectionConstraintCheckbox.setSelected(false);
        showSelectionConstraintCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_checkBox = new GridBagConstraints();
        gbc_checkBox.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox.anchor = GridBagConstraints.WEST;
        gbc_checkBox.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox.gridx = 1;
        gbc_checkBox.gridy = 1;
        panel.add(showSelectionConstraintCheckbox, gbc_checkBox);

        constraintDrawLinkCheckbox.setSelected(true);
        constraintDrawLinkCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
        gbc_checkBox_1.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_1.anchor = GridBagConstraints.WEST;
        gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox_1.gridx = 2;
        gbc_checkBox_1.gridy = 1;
        panel.add(constraintDrawLinkCheckbox, gbc_checkBox_1);

        constraintSeverableCheckbox.setSelected(true);
        constraintSeverableCheckbox.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
        gbc_checkBox_2.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_2.anchor = GridBagConstraints.WEST;
        gbc_checkBox_2.gridwidth = 2;
        gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
        gbc_checkBox_2.gridx = 3;
        gbc_checkBox_2.gridy = 1;
        panel.add(constraintSeverableCheckbox, gbc_checkBox_2);

        JLabel constraint_stiffness_label = new JLabel("Stiffness");
        constraint_stiffness_label.setFont(FontUtil.PLAIN_14);
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
        label_2.setFont(FontUtil.PLAIN_14);
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
        constraint_link_color_label.setFont(FontUtil.PLAIN_14);
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.fill = GridBagConstraints.VERTICAL;
        gbc_label_3.anchor = GridBagConstraints.WEST;
        gbc_label_3.insets = new Insets(0, 5, 5, 5);
        gbc_label_3.gridx = 1;
        gbc_label_3.gridy = 4;
        panel.add(constraint_link_color_label, gbc_label_3);

        lblConstraintLink = new CLabel(Color.BLUE, false);
        lblConstraintLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Color linkBackground = lblConstraintLink.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", linkBackground);
                lblConstraintLink.setBackground((c != null) ? c : linkBackground);
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
        btnSetConstraints.setFont(FontUtil.PLAIN_14);
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
            edge.stiffness = InputUtil.floatGuardDefault(0.0f, edge.stiffness, cStiffness);
            edge.tearDistance = InputUtil.floatGuardDefault(0.0f, edge.tearDistance, cTearDist);
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

        Physics.SIM_ACCURACY = InputUtil.intGuardDefault(1, Physics.SIM_ACCURACY, simAcc);
        Physics.DRAG_FORCE = InputUtil.floatGuardDefault(0.0f, Physics.DRAG_FORCE, dragForce);
        Physics.GRAVITY = InputUtil.floatGuardDefault(0.0f, Physics.GRAVITY , grav);
        Physics.AIR_VISCOSITY = InputUtil.floatGuardDefault(0.0f, Physics.AIR_VISCOSITY, airVis);
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
