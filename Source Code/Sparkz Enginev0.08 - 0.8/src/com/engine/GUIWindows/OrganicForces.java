package com.engine.GUIWindows;

import com.engine.Interfaces_Extensions.KAdapter;
import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.JComponents.TextSuggestor;
import com.engine.ParticleTypes.Particle;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import static com.engine.JComponents.TextSuggestor.makeUndoable;
import static com.engine.MGrapher.ParticleGraph.mathFunctions;
import static com.engine.MGrapher.ParticleGraph.suggestions;
import static com.engine.ParticleTypes.Particle.particleScriptEngine;

public class OrganicForces {
    private static OrganicForces customForces = null;
    public static JFrame frame;
    private static JTextField forceXTextField, forceYTextField, angleIncrementTextField;

    public static OrganicForces getInstance(JFrame parent) {
        if (customForces == null) {customForces = new OrganicForces(parent);} frame.toFront(); return customForces;
    }

    private OrganicForces(JFrame parent){
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Organic Forces");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(335, 354);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{47, 116, 0};
        gbl_panel.rowHeights = new int[]{73, 0, 0, 0, 0, 25, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblPleaseEnterTwo = new JLabel("Please Enter Two Force Expressions");
        lblPleaseEnterTwo.setForeground(Color.BLACK);
        lblPleaseEnterTwo.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblPleaseEnterTwo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblPleaseEnterTwo = new GridBagConstraints();
        gbc_lblPleaseEnterTwo.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblPleaseEnterTwo.gridwidth = 2;
        gbc_lblPleaseEnterTwo.insets = new Insets(0, 0, 5, 0);
        gbc_lblPleaseEnterTwo.gridx = 0;
        gbc_lblPleaseEnterTwo.gridy = 0;
        panel.add(lblPleaseEnterTwo, gbc_lblPleaseEnterTwo);

        JLabel lblNewLabel = new JLabel("Force X Expression");
        lblNewLabel.setForeground(Color.DARK_GRAY);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.gridwidth = 2;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        forceXTextField = new JTextField("cos(x)");
        forceXTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        forceXTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceXTextField.addKeyListener(new KAdapter(e -> {if (e.getKeyCode() != KeyEvent.VK_ENTER) {forceXTextField.setForeground(Color.black);}}, e -> {}));

        String COMMIT_ACTION = "commit";
        TextSuggestor textSuggestor = new TextSuggestor(forceXTextField, Arrays.asList(suggestions));
        forceXTextField.getDocument().addDocumentListener(textSuggestor);
        forceXTextField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        forceXTextField.getActionMap().put(COMMIT_ACTION, textSuggestor.new CommitAction());
        makeUndoable(forceXTextField);

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 2;
        panel.add(forceXTextField, gbc_textField);
        forceXTextField.setColumns(10);

        JLabel lblForceYExpression = new JLabel("Force Y Expression");
        lblForceYExpression.setForeground(Color.DARK_GRAY);
        lblForceYExpression.setHorizontalTextPosition(SwingConstants.CENTER);
        lblForceYExpression.setHorizontalAlignment(SwingConstants.CENTER);
        lblForceYExpression.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblForceYExpression.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblForceYExpression = new GridBagConstraints();
        gbc_lblForceYExpression.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblForceYExpression.gridwidth = 2;
        gbc_lblForceYExpression.insets = new Insets(0, 0, 5, 0);
        gbc_lblForceYExpression.gridx = 0;
        gbc_lblForceYExpression.gridy = 3;
        panel.add(lblForceYExpression, gbc_lblForceYExpression);

        forceYTextField = new JTextField("sin(x)");
        forceYTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceYTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        forceYTextField.setColumns(10);
        forceYTextField.addKeyListener(new KAdapter(e -> {if (e.getKeyCode() != KeyEvent.VK_ENTER) {forceYTextField.setForeground(Color.black);}}, e -> {}));

        TextSuggestor textSuggestor2 = new TextSuggestor(forceYTextField, Arrays.asList(suggestions));
        forceYTextField.getDocument().addDocumentListener(textSuggestor2);
        forceYTextField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        forceYTextField.getActionMap().put(COMMIT_ACTION, textSuggestor2.new CommitAction());
        makeUndoable(forceYTextField);

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
        lblAngleIncrement.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblAngleIncrement.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblAngleIncrement = new GridBagConstraints();
        gbc_lblAngleIncrement.gridwidth = 2;
        gbc_lblAngleIncrement.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAngleIncrement.insets = new Insets(0, 0, 5, 0);
        gbc_lblAngleIncrement.gridx = 0;
        gbc_lblAngleIncrement.gridy = 5;
        panel.add(lblAngleIncrement, gbc_lblAngleIncrement);

        angleIncrementTextField = new JTextField(""+Particle.angleIncrement);
        angleIncrementTextField.setHorizontalAlignment(SwingConstants.CENTER);
        angleIncrementTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        angleIncrementTextField.setColumns(10);

        TextSuggestor textSuggestor3 = new TextSuggestor(angleIncrementTextField, Arrays.asList(suggestions));
        angleIncrementTextField.getDocument().addDocumentListener(textSuggestor3);
        angleIncrementTextField.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        angleIncrementTextField.getActionMap().put(COMMIT_ACTION, textSuggestor3.new CommitAction());
        makeUndoable(angleIncrementTextField);

        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.gridwidth = 2;
        gbc_textField_2.insets = new Insets(0, 0, 5, 0);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 0;
        gbc_textField_2.gridy = 6;
        panel.add(angleIncrementTextField, gbc_textField_2);

        JButton button1 = new JButton("Set Forces");
        button1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button1.addActionListener(e -> setForces());
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_1.gridx = 0;
        gbc_btnNewButton_1.gridy = 7;
        panel.add(button1, gbc_btnNewButton_1);

        JButton button2 = new JButton("Close");
        button2.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button2.addActionListener(e -> close());
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton.weightx = 0.5;
        gbc_btnNewButton.weighty = 0.5;
        gbc_btnNewButton.anchor = GridBagConstraints.WEST;
        gbc_btnNewButton.gridx = 1;
        gbc_btnNewButton.gridy = 7;
        panel.add(button2, gbc_btnNewButton);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
    }

    private static void setForces() {
        try {
            for (String mathFunction : mathFunctions) {
                particleScriptEngine.eval(mathFunction);
            }
        }catch (Exception e){EException.append(e);}
        Particle.expressionForceX = forceXTextField.getText();
        Particle.expressionForceY = forceYTextField.getText();
        Particle.angleIncrement = Particle.evaluateExpr(angleIncrementTextField.getText());
    }

    private static void close(){customForces = null; frame.dispose();}
}
