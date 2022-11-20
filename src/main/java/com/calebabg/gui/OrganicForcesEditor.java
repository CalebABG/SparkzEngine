package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.utilities.FontUtil;
import org.fife.ui.autocomplete.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.calebabg.core.EngineVariables.eFrame;

public class OrganicForcesEditor {
    private static OrganicForcesEditor instance = null;

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
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
        lblPleaseEnterTwo.setFont(FontUtil.PLAIN_18);
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
        lblNewLabel.setFont(FontUtil.PLAIN_18);
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
        gbcLblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbcLblNewLabel.gridwidth = 2;
        gbcLblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbcLblNewLabel.gridx = 0;
        gbcLblNewLabel.gridy = 1;
        panel.add(lblNewLabel, gbcLblNewLabel);

        forceXTextField = new JTextField(expressionForceX, 10);
        forceXTextField.setFont(FontUtil.PLAIN_18);
        forceXTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceXTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceXTextField.setForeground(Color.black);
        }));

        AutoCompletion ac = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac.install(forceXTextField);

        GridBagConstraints gbcTextField = new GridBagConstraints();
        gbcTextField.gridwidth = 2;
        gbcTextField.insets = new Insets(0, 0, 5, 0);
        gbcTextField.fill = GridBagConstraints.HORIZONTAL;
        gbcTextField.gridx = 0;
        gbcTextField.gridy = 2;
        panel.add(forceXTextField, gbcTextField);

        JLabel lblForceYExpression = new JLabel("Force Y Expression");
        lblForceYExpression.setForeground(Color.DARK_GRAY);
        lblForceYExpression.setHorizontalTextPosition(SwingConstants.CENTER);
        lblForceYExpression.setHorizontalAlignment(SwingConstants.CENTER);
        lblForceYExpression.setFont(FontUtil.PLAIN_18);
        lblForceYExpression.setAlignmentX(0.5f);
        GridBagConstraints gbcLblForceYExpression = new GridBagConstraints();
        gbcLblForceYExpression.fill = GridBagConstraints.HORIZONTAL;
        gbcLblForceYExpression.gridwidth = 2;
        gbcLblForceYExpression.insets = new Insets(0, 0, 5, 0);
        gbcLblForceYExpression.gridx = 0;
        gbcLblForceYExpression.gridy = 3;
        panel.add(lblForceYExpression, gbcLblForceYExpression);

        forceYTextField = new JTextField(expressionForceY, 10);
        forceYTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceYTextField.setFont(FontUtil.PLAIN_18);
        forceYTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceYTextField.setForeground(Color.black);
        }));

        AutoCompletion ac2 = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac2.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac2.install(forceYTextField);

        GridBagConstraints gbcTextField1 = new GridBagConstraints();
        gbcTextField1.gridwidth = 2;
        gbcTextField1.insets = new Insets(0, 0, 5, 0);
        gbcTextField1.fill = GridBagConstraints.HORIZONTAL;
        gbcTextField1.gridx = 0;
        gbcTextField1.gridy = 4;
        panel.add(forceYTextField, gbcTextField1);

        JLabel lblAngleIncrement = new JLabel("Angle Increment");
        lblAngleIncrement.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAngleIncrement.setHorizontalAlignment(SwingConstants.CENTER);
        lblAngleIncrement.setForeground(Color.DARK_GRAY);
        lblAngleIncrement.setFont(FontUtil.PLAIN_18);
        lblAngleIncrement.setAlignmentX(0.5f);
        GridBagConstraints gbcLblAngleIncrement = new GridBagConstraints();
        gbcLblAngleIncrement.gridwidth = 2;
        gbcLblAngleIncrement.fill = GridBagConstraints.HORIZONTAL;
        gbcLblAngleIncrement.insets = new Insets(0, 0, 5, 0);
        gbcLblAngleIncrement.gridx = 0;
        gbcLblAngleIncrement.gridy = 5;
        panel.add(lblAngleIncrement, gbcLblAngleIncrement);

        angleIncrementTextField = new JTextField("" + angleIncrement, 10);
        angleIncrementTextField.setHorizontalAlignment(SwingConstants.CENTER);
        angleIncrementTextField.setFont(FontUtil.PLAIN_18);
        angleIncrementTextField.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) angleIncrementTextField.setForeground(Color.black);
        }));

        AutoCompletion ac3 = new AutoCompletion(ParticleGraphEditor.autoCompleteProvider);
        ac3.setListCellRenderer(ParticleGraphEditor.cellRenderer);
        ac3.install(angleIncrementTextField);

        GridBagConstraints gbcTextField2 = new GridBagConstraints();
        gbcTextField2.gridwidth = 2;
        gbcTextField2.insets = new Insets(0, 0, 5, 0);
        gbcTextField2.fill = GridBagConstraints.HORIZONTAL;
        gbcTextField2.gridx = 0;
        gbcTextField2.gridy = 6;
        panel.add(angleIncrementTextField, gbcTextField2);

        JButton button1 = new JButton("Set Forces");
        button1.setFont(FontUtil.PLAIN_18);
        button1.addActionListener(e -> setForces());
        GridBagConstraints gbcBtnNewButton1 = new GridBagConstraints();
        gbcBtnNewButton1.gridwidth = 2;
        gbcBtnNewButton1.fill = GridBagConstraints.HORIZONTAL;
        gbcBtnNewButton1.anchor = GridBagConstraints.EAST;
        gbcBtnNewButton1.insets = new Insets(0, 5, 5, 5);
        gbcBtnNewButton1.gridx = 0;
        gbcBtnNewButton1.gridy = 7;
        panel.add(button1, gbcBtnNewButton1);

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
        expressionForceX = forceXTextField.getText();
        expressionForceY = forceYTextField.getText();
        angleIncrement = ParticleGraphEditor.guardFloat(angleIncrementTextField.getText(), angleIncrementTextField);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
