package com.engine.MGrapher;

import com.engine.GUIWindows.GraphInstructions;
import com.engine.GUIWindows.SampleFunctions;
import com.engine.JComponents.AutoComplete;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RButton;
import com.engine.JComponents.RLabel;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.JComponents.AutoComplete.makeUndoable;
import com.engine.ParticleTypes.Particle;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class ParticleGraph {
    // Best x-scale for all functions = 0.02
    private static ParticleGraph particleGraph = null;
    public static JFrame frame;
    public static CTextField[] textFields = new CTextField[5];
    public static String[] mathFunctions = {
            "var sin = Math.sin", "var cos = Math.cos", "var tan = Math.tan",
            "var asin = Math.asin", "var acos = Math.acos", "var atan = Math.atan",
            "var log = Math.log", "var sqrt = Math.sqrt", "var pi = Math.PI",
            "var e = Math.E", "var abs = Math.abs", "var exp = Math.exp", "var pow = Math.pow",
            "var rand = function(max, min){return (Math.random()*max)+min};",
            "var signum = function(xpr){return (xpr / Math.abs(xpr))};",
            "var mod = function(x, y){return (x - y * Math.floor(x / y))};",
            "var sec = function(x){return 1/Math.cos(x);}",
            "var csc = function(x){return 1/Math.sin(x);}",
            "var cot = function(x){return 1/Math.tan(x);}",
            "var lerp = function(start, stop, amt) {return start + (stop - start) * amt;}",
            "var norm = function(value, start, stop) {return (value - start) / (stop - start);}",
            "var clamp = function(val, min, max){return Math.min(Math.min(val, min), max);}",
            "var map = function(value, sMin, sMax, dMin, dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}"
    };

    public static String[] suggestions = {
            "sin(x)", "cos(x)", "tan(x)",
            "asin(x)", "acos(x)", "atan(x)",
            "log(x)", "sqrt(x)", "abs(x)",
            "exp(x)", "pow(x, 2)", "rand(sin(x),0)",
            "signum(x)", "mod(x,sin(x))", "lerp(sin(x),x/3,.5)",
            "norm(x,0,0)", "clamp(x,0,1)", "map(sin(x),-1,1,-1,1)",
            "sec(x)", "csc(x)", "cot(x)"
    };

    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private static String mathExpression = "sin(x)";
    private static double scaleY = 40; //Best at 40
    private static double scaleX = 0.02;
    private static Thread graphThread1;

    //public static void main(String[] args){}

    public static ParticleGraph getInstance() {
        if (particleGraph == null) {particleGraph = new ParticleGraph();} frame.toFront(); return particleGraph;
    }

    private ParticleGraph() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Particle Graph Editor");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(465, 277);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {particleGraph = null; frame.dispose();}});
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{138, 64, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JMenuBar menuBar = new JMenuBar();
        scrollPane.setColumnHeaderView(menuBar);

        //Add Helper Math functions to JavaScript Engine at window creation
        try {for (int i = 0; i < mathFunctions.length; i++) {engine.eval(mathFunctions[i]);}} catch (Exception e){EException.append(e);}

        JButton mnHelp = new JButton("Help");
        mnHelp.setFont(new Font("Times", Font.PLAIN, 18));
        mnHelp.addActionListener(e -> {if (e.getSource() == mnHelp) {GraphInstructions.getInstance(frame);}});
        menuBar.add(mnHelp);

        JButton sampleFunctions = new JButton("Samples");
        sampleFunctions.setFont(new Font("Times", Font.PLAIN, 18));
        sampleFunctions.addActionListener(e -> SampleFunctions.getInstance(frame));
        menuBar.add(sampleFunctions);

        JLabel syntaxErr = new JLabel("<html><span style='color:red;font-style:italic'>Red Text</span> = Syntax Error</html>", SwingConstants.CENTER);
        syntaxErr.setFont(new Font("Times", Font.PLAIN, 18));
        menuBar.add(syntaxErr);

        RLabel enterFunction = new RLabel("Enter a Function :D (Hit: Enter to graph)", new Font("Times", Font.BOLD, 18), 2, new Insets(3, 0, 5, 0), new int[]{0, 0});
        panel.add(enterFunction, enterFunction.gridBagConstraints);

        textFields[0] = new CTextField(mathExpression, new Font("Times", Font.PLAIN, 20), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{0, 1});
        textFields[0].gridBagConstraints.gridwidth = 2;
        textFields[0].setFocusTraversalKeysEnabled(false);
        textFields[0].addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {if (e.getKeyCode() != KeyEvent.VK_ENTER) {textFields[0].setForeground(Color.black);}}
            public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ENTER){threadGraph(0, "");}}
        });

        //Textfield AutoSuggest Functions
        String COMMIT_ACTION = "commit";
        AutoComplete autoComplete = new AutoComplete(textFields[0], Arrays.asList(suggestions));
        textFields[0].getDocument().addDocumentListener(autoComplete);
        textFields[0].getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        textFields[0].getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
        makeUndoable(textFields[0]);
        panel.add(textFields[0], textFields[0].gridBagConstraints);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(e -> {if (e.getSource() == cut) {textFields[0].cut();}});
        popupMenu.add(cut);
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(e -> {if (e.getSource() == copy) {textFields[0].copy();}});
        popupMenu.add(copy);
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(e -> {if (e.getSource() == paste) {textFields[0].paste();}});
        popupMenu.add(paste);
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(e -> {if (e.getSource() == clear) {textFields[0].setText("");}});
        popupMenu.add(clear);
        addPopup(textFields[0], popupMenu);

        RLabel yscale = new RLabel("Y Scale", new Font("Times", Font.BOLD, 18), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 11);
        yscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 40</h5></html?");
        panel.add(yscale, yscale.gridBagConstraints);

        textFields[1] = new CTextField("" + scaleY, new Font("Times", Font.PLAIN, 19), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 11});
        textFields[1].addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) {textFields[1].setForeground(Color.black);}});
        textFields[1].addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(0, "");}
        });
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel xscale = new RLabel("X Scale", new Font("Times", Font.BOLD, 18), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 12);
        xscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 0.02</h5></html?");
        panel.add(xscale, xscale.gridBagConstraints);

        textFields[2] = new CTextField("" + scaleX, new Font("Times", Font.PLAIN, 19), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        textFields[2].addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) {textFields[2].setForeground(Color.black);}});
        textFields[2].addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(0, "");}
        });
        panel.add(textFields[2], textFields[2].gridBagConstraints);

        RButton graphButton = new RButton("<html><span style='color:#008DCB'>Graph Function</span></html>",
                new Font("Times New Roman", Font.PLAIN, 23), 2, GridBagConstraints.HORIZONTAL, new int[]{0, 13}, new int[]{20, 15});
        graphButton.addActionListener(e -> {if (e.getSource() == graphButton) {threadGraph(0, "");}});
        graphButton.gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        graphButton.gridBagConstraints.fill =      GridBagConstraints.HORIZONTAL;
        graphButton.gridBagConstraints.anchor =    GridBagConstraints.SOUTHWEST;
        graphButton.gridBagConstraints.weightx =   0.5;
        graphButton.gridBagConstraints.weighty =   0.5;
        panel.add(graphButton, graphButton.gridBagConstraints);
        frame.setVisible(true);
    }

    private static void graph() {
        ParticlesArray.clear();
        double res = 0.04, positive_width = canvas.getWidth() / 2, negative_width = -positive_width;
        for (double i = negative_width; i < positive_width; i += res) {
            engine.put("x", i * scaleX);
            try {setGraph(i, .98);} catch (Exception e) {throwError(textFields[0]); EException.append(e); break;}
        }
    }

    private static void setGraph(double x, double r) throws Exception {ParticlesArray.add(new Particle(x, (-(evaluateExpr(mathExpression) * scaleY)), r));}
    private static void setGraph(double x, double y, double r) throws Exception {ParticlesArray.add(new Particle(x, y, r));}

    private static void evalInput(int mode, String express) {
        scaleY = guardDouble(textFields[1].getText(),textFields[1]);
        scaleX = guardDouble(textFields[2].getText(),textFields[2]);
        mathExpression = (mode == 0) ? textFields[0].getText() : express;
    }

    private static double evaluateExpr(String express) throws Exception {
        if (engine.eval(express) instanceof Integer) return  ((Integer) engine.eval(express)).doubleValue();
        else if (engine.eval(express) instanceof Double) return (double) engine.eval(express);
        else return 0;
    }

    public static void threadGraph(int mode, String express) {
        if (graphThread1 != null && graphThread1.isAlive()) {
            try {graphThread1.interrupt(); graphThread1.join();} catch (InterruptedException e1) {EException.append(e1);}
            graphThread1 = new Thread(){public void run() {if (mode == 0) {graphFunction();} else graphFunction(express);}};
            graphThread1.start();
        }
        else{
            graphThread1 = new Thread(){public void run() {if (mode == 0) {graphFunction();} else graphFunction(express);}};
            graphThread1.start();
        }
    }

    private static void graphFunction() {evalInput(0, ""); graph();}
    public static void graphFunction(String express) {evalInput(1, express); graph();}

    private static double guardDouble(String expr, CTextField textField){
        double result = 0;
        try{result = evaluateExpr(expr);} catch (Exception e){throwError(textField);}
        return result;
    }

    private static void addPopup(Component component, JPopupMenu popup) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {if (e.isPopupTrigger()) {showMenu(e);}}
            public void mouseReleased(MouseEvent e) {if (e.isPopupTrigger()) {showMenu(e);}}
            private void showMenu(MouseEvent e) {popup.show(e.getComponent(), e.getX(), e.getY());}});
    }

    private static void throwError(CTextField textField) {textField.setForeground(Color.red);}
}
