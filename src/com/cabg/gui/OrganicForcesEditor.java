package com.cabg.gui;

import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import org.fife.ui.autocomplete.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.cabg.core.EngineVariables.eFrame;

public class OrganicForcesEditor {
    private static OrganicForcesEditor instance = null;

    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 18);
    public static float angleIncrement = 0.05f;
    public static String expressionForceX = "cos(x)", expressionForceY = "sin(x)";

    private final JFrame frame;
    private final JTextField forceXTextField, forceYTextField, angleIncrementTextField;

    public static void getInstance() {
        if (instance == null) instance = new OrganicForcesEditor();
        instance.frame.toFront();
    }

    private OrganicForcesEditor() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Organic Forces");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(335, 354);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(eFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{47, 116, 0};
        gblPanel.rowHeights = new int[]{73, 0, 0, 0, 0, 25, 0};
        gblPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);

        JLabel lblPleaseEnterTwo = new JLabel("Please Enter Two Force Expressions");
        lblPleaseEnterTwo.setForeground(Color.BLACK);
        lblPleaseEnterTwo.setFont(font);
        lblPleaseEnterTwo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbcLblPleaseEnterTwo = new GridBagConstraints();
        gbcLblPleaseEnterTwo.fill = GridBagConstraints.HORIZONTAL;
        gbcLblPleaseEnterTwo.gridwidth = 2;
        gbcLblPleaseEnterTwo.insets = new Insets(0, 0, 0, 0);
        gbcLblPleaseEnterTwo.gridx = 0;
        gbcLblPleaseEnterTwo.gridy = 0;
        panel.add(lblPleaseEnterTwo, gbcLblPleaseEnterTwo);

        JLabel lblNewLabel = new JLabel("Force X Expression");
        lblNewLabel.setForeground(Color.DARK_GRAY);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel.setFont(font);
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.gridwidth = 2;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        forceXTextField = new JTextField(expressionForceX, 10);
        forceXTextField.setFont(font);
        forceXTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceXTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceXTextField.setForeground(Color.black);
        }));

        AutoCompletion ac = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac.install(forceXTextField);

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 2;
        panel.add(forceXTextField, gbc_textField);

        JLabel lblForceYExpression = new JLabel("Force Y Expression");
        lblForceYExpression.setForeground(Color.DARK_GRAY);
        lblForceYExpression.setHorizontalTextPosition(SwingConstants.CENTER);
        lblForceYExpression.setHorizontalAlignment(SwingConstants.CENTER);
        lblForceYExpression.setFont(font);
        lblForceYExpression.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblForceYExpression = new GridBagConstraints();
        gbc_lblForceYExpression.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblForceYExpression.gridwidth = 2;
        gbc_lblForceYExpression.insets = new Insets(0, 0, 5, 0);
        gbc_lblForceYExpression.gridx = 0;
        gbc_lblForceYExpression.gridy = 3;
        panel.add(lblForceYExpression, gbc_lblForceYExpression);

        forceYTextField = new JTextField(expressionForceY, 10);
        forceYTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceYTextField.setFont(font);
        forceYTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceYTextField.setForeground(Color.black);
        }));

        AutoCompletion ac2 = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac2.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac2.install(forceYTextField);

        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.gridwidth = 2;
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 0;
        gbc_textField_1.gridy = 4;
        panel.add(forceYTextField, gbc_textField_1);

        JLabel lblAngleIncrement = new JLabel("Angle Increment");
        lblAngleIncrement.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAngleIncrement.setHorizontalAlignment(SwingConstants.CENTER);
        lblAngleIncrement.setForeground(Color.DARK_GRAY);
        lblAngleIncrement.setFont(font);
        lblAngleIncrement.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblAngleIncrement = new GridBagConstraints();
        gbc_lblAngleIncrement.gridwidth = 2;
        gbc_lblAngleIncrement.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAngleIncrement.insets = new Insets(0, 0, 5, 0);
        gbc_lblAngleIncrement.gridx = 0;
        gbc_lblAngleIncrement.gridy = 5;
        panel.add(lblAngleIncrement, gbc_lblAngleIncrement);

        angleIncrementTextField = new JTextField("" + angleIncrement, 10);
        angleIncrementTextField.setHorizontalAlignment(SwingConstants.CENTER);
        angleIncrementTextField.setFont(font);
        angleIncrementTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) angleIncrementTextField.setForeground(Color.black);
        }));

        AutoCompletion ac3 = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac3.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac3.install(angleIncrementTextField);

        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.gridwidth = 2;
        gbc_textField_2.insets = new Insets(0, 0, 5, 0);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 0;
        gbc_textField_2.gridy = 6;
        panel.add(angleIncrementTextField, gbc_textField_2);

        JButton button1 = new JButton("Set Forces");
        button1.setFont(font);
        button1.addActionListener(e -> setForces());
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.gridwidth = 2;
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_1.insets = new Insets(0, 5, 5, 5);
        gbc_btnNewButton_1.gridx = 0;
        gbc_btnNewButton_1.gridy = 7;
        panel.add(button1, gbc_btnNewButton_1);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    public static Float evalForceXExpression() {
        return ParticleGraphEditor.evalExpression(expressionForceX);
    }

    public static Float evalForceYExpression() {
        return ParticleGraphEditor.evalExpression(expressionForceY);
    }

    private void setForces() {
        String fxText = forceXTextField.getText();
        String fyText = forceYTextField.getText();
        String axText = angleIncrementTextField.getText();

        float angleInc = ParticleGraphEditor.guardFloat(axText, angleIncrementTextField);

        expressionForceX = fxText;
        expressionForceY = fyText;
        angleIncrement = angleInc;
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
