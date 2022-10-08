package com.cabg.gui;

import com.cabg.core.EngineMethods;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;
import com.cabg.components.CTextField;
import com.cabg.components.RButton;
import com.cabg.components.RLabel;
import com.cabg.particletypes.Particle;
import com.cabg.utilities.InputGuard;
import com.cabg.utilities.Settings;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.cabg.core.EngineVariables.*;
import static org.apache.commons.math3.util.FastMath.pow;

public class ParticleGrapher {
    // Best x-scale for all functions = 0.02
    private static ParticleGrapher particleGrapher = null;
    public static JFrame frame;
    public static final CTextField[] textFields = new CTextField[5];
    private static RButton graphButton;
    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 18);
    public static boolean isOkToGraph = true;

    public static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    static {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ParticleGrapher.class.getResourceAsStream("/GraphFunc.js")))) {
            engine.eval(br);
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
    }

    private static final String[] suggestions = {
            "sin(x)", "cos(x)", "tan(x)", "asin(x)", "acos(x)", "atan(x)",
            "sinh(x)", "cosh(x)", "tanh(x)", "atan2(x)", "hypot(x)", "log(x)",
            "log10(x)", "sqrt(x)", "abs(x)", "exp(x)", "pow(x,2)", "cbrt(x)", "ceil(x)",
            "round(x)", "floor(x)", "toRadians(x)", "toDegrees(x)", "rand(10, 20)", "signum(x)",
            "mod(x, 2)", "sec(x)", "csc(x)", "cot(x)", "lerp(0, sin(x), .5)", "norm(x, 10, 20)",
            "clamp(x, 20, 40)", "map(sin(x), -1, 1, -10, 20)", "e", "pi"
    };

    private static String mathExpression = "2*sin(x)";
    private static float scaleY = 40f; //Best at 40
    private static float scaleX = 0.02f;
    private static boolean isGraphing = false;

    public static final CCellRenderer cellRenderer = new CCellRenderer(new Font(Font.SERIF, Font.PLAIN, 18));
    public static final CompletionProvider autoCompleteProvider = createCompletionProvider();

    public static void getInstance() {
        if (particleGrapher == null) particleGrapher = new ParticleGrapher();
        frame.toFront();
    }

    private ParticleGrapher() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }
        frame = new JFrame("Particle Graph Editor");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(460, 280);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
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

        JButton mnHelp = new JButton("Help");
        mnHelp.setFont(font);
        mnHelp.addActionListener(e -> EngineMethods.createGraphInstructionsWindow(frame));
        menuBar.add(mnHelp);

        JButton sampleFunctions = new JButton("Samples");
        sampleFunctions.setFont(font);
        sampleFunctions.addActionListener(e -> SampleFunctions.getInstance(frame));
        menuBar.add(sampleFunctions);

        JLabel syntaxErr = new JLabel("<html><span style='color:red;font-style:italic'>Red Text</span> = Syntax Error</html>", SwingConstants.CENTER);
        syntaxErr.setFont(font);
        menuBar.add(syntaxErr);

        RLabel enterFunction = new RLabel("Enter an Expression (Enter = Graph)", new Font(Font.SERIF, Font.BOLD, 18), 2, new Insets(3, 0, 5, 0), new int[]{0, 0});
        panel.add(enterFunction, enterFunction.gridBagConstraints);

        textFields[0] = new CTextField(mathExpression, new Font(Font.SERIF, Font.PLAIN, 20), new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{0, 1});
        textFields[0].gridBagConstraints.gridwidth = 2;
        textFields[0].setFocusTraversalKeysEnabled(false);
        textFields[0].addKeyListener(new ExtendedKeyAdapter(e -> textFields[0].setForeground(Color.black), ParticleGrapher::isEnter));

        AutoCompletion ac = new AutoCompletion(autoCompleteProvider);
        ac.setListCellRenderer(cellRenderer);
        ac.install(textFields[0]);

        InputGuard.addUndoFunctionality(textFields[0]);
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

        RLabel yscale = new RLabel("Y Scale", font, GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 11);
        yscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 40</h5></html?");
        panel.add(yscale, yscale.gridBagConstraints);

        textFields[1] = new CTextField("" + scaleY, font, new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 11});
        textFields[1].addKeyListener(new ExtendedKeyAdapter(e -> textFields[1].setForeground(Color.black), ParticleGrapher::isEnter));
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel xscale = new RLabel("X Scale", font, GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 12);
        xscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 0.02</h5></html?");
        panel.add(xscale, xscale.gridBagConstraints);

        textFields[2] = new CTextField("" + scaleX, font, new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        textFields[2].addKeyListener(new ExtendedKeyAdapter(e -> textFields[2].setForeground(Color.black), ParticleGrapher::isEnter));
        panel.add(textFields[2], textFields[2].gridBagConstraints);

        graphButton = new RButton("<html><span style='color:#008DCB'>Graph</span></html>",
                new Font(Font.SERIF, Font.PLAIN, 23), 2, GridBagConstraints.HORIZONTAL, new int[]{0, 13}, new int[]{20, 15});
        graphButton.addActionListener(e -> threadGraph(null));
        graphButton.gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        graphButton.gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        graphButton.gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        graphButton.gridBagConstraints.weightx = 0.5;
        graphButton.gridBagConstraints.weighty = 0.5;
        panel.add(graphButton, graphButton.gridBagConstraints);
        frame.setVisible(true);
    }

    private static void graph() {
        if (Particles.size() > 0) Particles.clear();

        float inc = (float) pow(2, -3);
        float positive_width = canvas.getWidth() / 2f;
        float negative_width = -positive_width;

        for (float i = negative_width; i < positive_width && isOkToGraph; i += inc) {
            engine.put("x", i * scaleX);

            float y = -(guardDouble(mathExpression, textFields[0]) * scaleY);

            Particles.add(new Particle(i, y, .45f));
        }
    }

    public static void threadGraph(String express) {
        if (!isGraphing) {
            isGraphing = true;
            graphButton.setEnabled(false);

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                protected Void doInBackground() {
                    graphFunction(express);
                    return null;
                }

                protected void done() {
                    isGraphing = false;
                    graphButton.setEnabled(true);
                }
            };
            worker.execute();
        }
    }

    private static void graphFunction(String expression) {
        evalInput(expression);
        graph();
    }

    private static void evalInput(String express) {
        scaleY = guardDouble(textFields[1].getText(), textFields[1]);
        scaleX = guardDouble(textFields[2].getText(), textFields[2]);
        mathExpression = express == null ? textFields[0].getText() : express;
    }

    public static Float evaluateExpr(String express, boolean useIntendedForGraphing) {
        Float result = null;
        boolean isOk = true;

        try {
            result = ((Double) engine.eval(express)).floatValue();
        } catch (Exception e) {
            if (useIntendedForGraphing) {
                isOk = false;
                throwError(e);
            }
        }

        isOkToGraph = isOk;

        return result;
    }

    public static float guardDouble(String expr, Object textfield) {
        Float result = 0f;

        //Null pointer handling
        if (engine != null) {
            result = evaluateExpr(expr, true);

            if (result == null) {
                result = 0f;
                if (textfield != null) throwError(textfield);
            }
        }

        return result;
    }

    private static void isEnter(KeyEvent k) {
        if (k.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(null);
    }

    private void addPopup(Component c, JPopupMenu p) {
        c.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e, p);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e, p);
            }
        });
    }

    /**
     * Create a simple provider that adds some Java-related completions.
     */
    private static CompletionProvider createCompletionProvider() {
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        for (String suggestion : suggestions)
            provider.addCompletion(new BasicCompletion(provider, suggestion));

        return provider;
    }

    private void showMenu(MouseEvent e, JPopupMenu p) {
        p.show(e.getComponent(), e.getX(), e.getY());
    }

    private static void throwError(Object... objects) {
        if (objects != null && objects.length > 0) {
            for (Object o : objects) {
                if (o != null) {
                    if (o instanceof JTextField) ((JTextField) o).setForeground(Color.red);
                    else if (o instanceof Exception) ExceptionLogger.append(((Exception) o));
                    else ExceptionLogger.append(o.toString());
                } else ExceptionLogger.append(new NullPointerException("Object passed was null"));
            }
        }
    }

    public void close() {
        frame.dispose();
        particleGrapher = null;
    }

    static class CCellRenderer implements ListCellRenderer<Object> {
        private final Font font;
        private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public CCellRenderer(Font font) {
            this.font = font;
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            renderer.setFont(font);
            return renderer;
        }
    }
}
