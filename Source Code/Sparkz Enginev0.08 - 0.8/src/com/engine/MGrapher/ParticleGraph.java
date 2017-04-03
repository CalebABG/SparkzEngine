package com.engine.MGrapher;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.SampleFunctions;
import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.MAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.Autocomplete.AutoCompleteBehaviour;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RButton;
import com.engine.JComponents.RLabel;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.JComponents.TextSuggestor.makeUndoable;
import com.engine.ParticleTypes.Particle;
import com.engine.GUIWindows.EException;
import com.engine.Utilities.Settings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticleGraph {
    // Best x-scale for all functions = 0.02
    private static ParticleGraph particleGraph = null;
    public static JFrame frame;
    public static CTextField[] textFields = new CTextField[5];

    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
    private static String mathExpression = "sin(x)";
    private static double scaleY = 40; //Best at 40
    private static double scaleX = 0.02;
    private static Thread graphThread1;

    public static String[][] mathFunctions = {
            {"var math = Java.type(\"java.lang.Math\")", ""},
            {"var sin = math.sin", "sin(x)"},
            {"var cos = math.cos", "cos(x)"},
            {"var tan = math.tan", "tan(x)"},
            {"var asin = math.asin", "asin(x)"},
            {"var acos = math.acos", "acos(x)"},
            {"var atan = math.atan", "atan(x)"},
            {"var sinh = math.sinh", "sinh(x)"},
            {"var cosh = math.cosh", "cosh(x)"},
            {"var tanh = math.tanh", "tanh(x)"},
            {"var atan2 = math.atan2", "atan2(x, y)"},
            {"var hypot = math.hypot", "hypot(x, y)"},
            {"var log = math.log", "log(x)"},
            {"var log10 = math.log10", "log10(x)"},
            {"var sqrt = math.sqrt", "sqrt(x)"},
            {"var pi = math.PI", ""}, //1
            {"var e = math.E", ""},   //2
            {"var abs = math.abs", "abs(x)"},
            {"var exp = math.exp", "exp(x)"},
            {"var pow = math.pow", "pow(x,2)"},
            {"var cbrt = math.cbrt", "cbrt(x)"},
            {"var ceil = math.ceil", "ceil(x)"},
            {"var round = math.round", "round(x)"},
            {"var floor = math.floor", "floor(x)"},
            {"var toRadians = math.toRadians", "toRadians(x)"},
            {"var toDegrees = math.toDegrees", "toDegrees(x)"},
            {"var rand = function(max, min){return (math.random()*max)+min};", "rand(10, 20)"},
            {"var signum = math.signum", "signum(x)"},
            {"var mod = function(x, y){return (x - y * math.floor(x / y))};", "mod(x, y)"},
            {"var sec = function(x){return 1/math.cos(x);}", "sec(x)"},
            {"var csc = function(x){return 1/math.sin(x);}", "csc(x)"},
            {"var cot = function(x){return 1/math.tan(x);}", "cot(x)"},
            {"var lerp = function(start, stop, amt) {return start + (stop - start) * amt;}", "lerp(0, sin(x), .5)"},
            {"var norm = function(value, start, stop) {return (value - start) / (stop - start);}", "norm(x, 10, 20)"},
            {"var clamp = function(val, min, max){return math.min(math.min(val, min), max);}", "clamp(x, 20, 40)"},
            {"var map = function(value, sMin, sMax, dMin, dMax) {return dMin + (dMax - dMin) * ((value - sMin) / (sMax - sMin));}", "map(sin(x), -1, 1, -10, 20)"}
    };

    public static List<String> suggestions = new ArrayList<>();

    static {
        try{engine.eval("y = 0");}catch (ScriptException e){EException.append(e);}
        for (int i = 0; i < mathFunctions.length; i++) suggestions.add(mathFunctions[i][1]);
    }

//    public static void main(String[] args){}

    public static ParticleGraph getInstance() {
        if (particleGraph == null) {particleGraph = new ParticleGraph();} frame.toFront(); return particleGraph;
    }

    private ParticleGraph() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Particle Graph Editor");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(465, 277);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{138, 64, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JMenuBar menuBar = new JMenuBar();
        scrollPane.setColumnHeaderView(menuBar);

        //Add Helper Math functions to JavaScript Engine at window creation
        try {
            for (String[] mathFunction : mathFunctions) {
                //At 0 are the functions; at 1 are the suggestions
                engine.eval(mathFunction[0]);
            }
        }catch (ScriptException e){EException.append(e);}

        JButton mnHelp = new JButton("Help");
        mnHelp.setFont(new Font("Times", Font.PLAIN, 18));
        mnHelp.addActionListener(e -> EngineMethods.createGraphInstructionsWindow(frame));
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
        textFields[0].addKeyListener(new KAdapter(
                e -> {if (e.getKeyCode() != KeyEvent.VK_ENTER) {textFields[0].setForeground(Color.black);}},
                e -> {if (e.getKeyCode() == KeyEvent.VK_ENTER){threadGraph(0, "");}}
        ));

        AutoCompleteBehaviour<String> autoComplete = new AutoCompleteBehaviour<>();
        autoComplete.setCallback(new AutoCompleteBehaviour.DefaultAutoCompleteCallback<String>() {
            public List<String> getProposals(String input) {
                if (input.length() < 1) return Collections.emptyList();

                final String lower = input.toLowerCase();

                final List<String> result = new ArrayList<>();
                for (String c : suggestions) {
                    if (c.contains(lower)) {
                        result.add(c);
                    }
                }
                return result;
            }

            public String getStringToInsert(String value) {
                return value;
            }
        });

        // set a custom renderer for our proposals
        final DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                final Component result = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final String str = (String) value;
                setText(str);
                return result;
            }
        };
        autoComplete.setListCellRenderer(renderer);

        // setup initial size
        autoComplete.setInitialPopupSize(new Dimension(200,650));

        // how many proposals to display before showing a scroll bar
        autoComplete.setVisibleRowCount(5);

        // attach behaviour to editor
        autoComplete.attachTo(textFields[0]);
        makeUndoable(textFields[0]);
        panel.add(textFields[0], textFields[0].gridBagConstraints);

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem cut = new JMenuItem("Cut");
        cut.addActionListener(e -> textFields[0].cut());
        popupMenu.add(cut);
        JMenuItem copy = new JMenuItem("Copy");
        copy.addActionListener(e -> textFields[0].copy());
        popupMenu.add(copy);
        JMenuItem paste = new JMenuItem("Paste");
        paste.addActionListener(e -> textFields[0].paste());
        popupMenu.add(paste);
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(e -> textFields[0].setText(""));
        popupMenu.add(clear);
        addPopup(textFields[0], popupMenu);

        RLabel yscale = new RLabel("Y Scale", new Font("Times", Font.BOLD, 18), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 11);
        yscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 40</h5></html?");
        panel.add(yscale, yscale.gridBagConstraints);

        textFields[1] = new CTextField("" + scaleY, new Font("Times", Font.PLAIN, 19), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 11});
        textFields[1].addKeyListener(new KAdapter(
                e -> textFields[1].setForeground(Color.black),
                e -> {if (e.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(0, "");}
        ));
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel xscale = new RLabel("X Scale", new Font("Times", Font.BOLD, 18), GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 12);
        xscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 0.02</h5></html?");
        panel.add(xscale, xscale.gridBagConstraints);

        textFields[2] = new CTextField("" + scaleX, new Font("Times", Font.PLAIN, 19), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        textFields[2].addKeyListener(new KAdapter(
                e -> textFields[2].setForeground(Color.black),
                e -> {if (e.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(0, "");}
        ));
        panel.add(textFields[2], textFields[2].gridBagConstraints);

        RButton graphButton = new RButton("<html><span style='color:#008DCB'>Graph Function</span></html>",
                new Font("Times New Roman", Font.PLAIN, 23), 2, GridBagConstraints.HORIZONTAL, new int[]{0, 13}, new int[]{20, 15});
        graphButton.addActionListener(e -> threadGraph(0, ""));
        graphButton.gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        graphButton.gridBagConstraints.fill      = GridBagConstraints.HORIZONTAL;
        graphButton.gridBagConstraints.anchor    = GridBagConstraints.SOUTHWEST;
        graphButton.gridBagConstraints.weightx   = 0.5;
        graphButton.gridBagConstraints.weighty   = 0.5;
        panel.add(graphButton, graphButton.gridBagConstraints);
        frame.setVisible(true);
    }

    private static void graph() {
        ParticlesArray.clear();
        double res = 0.04, positive_width = canvas.getWidth() / 2, negative_width = -positive_width;
        for (double i = negative_width; i < positive_width; i += res) {
            engine.put("x", i * scaleX);
            try {setGraph(i, .95);} catch (Exception e) {throwError(textFields[0]); EException.append(e); break;}
        }
    }

    private static void setGraph(double x, double r) throws Exception {ParticlesArray.add(new Particle(x, (-(evaluateExpr(mathExpression) * scaleY)), r));}

    private static void evalInput(int mode, String express) {
        scaleY = guardDouble(textFields[1].getText(), textFields[1]);
        scaleX = guardDouble(textFields[2].getText(), textFields[2]);
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
            graphThread1 = new Thread(() -> {if (mode == 0) {graphFunction();} else graphFunction(express);});
            graphThread1.start();
        }
        else{
            graphThread1 = new Thread(() -> {if (mode == 0) {graphFunction();} else graphFunction(express);});
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

    private void addPopup(Component c, JPopupMenu p) {
        c.addMouseListener(new MAdapter(e -> {if (e.isPopupTrigger()) {showMenu(e, p);}}, e -> {if (e.isPopupTrigger()) {showMenu(e, p);}}));
    }

    private void showMenu(MouseEvent e, JPopupMenu p) {p.show(e.getComponent(), e.getX(), e.getY());}
    private static void throwError(CTextField textField) {textField.setForeground(Color.red);}
    public void close(){particleGraph = null; frame.dispose();}
}
