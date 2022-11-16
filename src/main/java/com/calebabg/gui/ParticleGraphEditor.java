package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.molecules.Particle;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.jcomponents.CTextField;
import com.calebabg.jcomponents.RButton;
import com.calebabg.jcomponents.RLabel;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.calebabg.core.EngineVariables.*;

public class ParticleGraphEditor {
    private static ParticleGraphEditor instance = null;

    private static JFrame frame;
    private static RButton graphButton;
    private static final CTextField[] textFields = new CTextField[5];

    private static boolean isOkToGraph = true;
    private static boolean isGraphing = false;
    private static float scaleY = 40f; // Best at 40
    private static float scaleX = 0.02f; // Best at 0.02
    private static String mathExpression = "2*sin(x)";

    private static final String[] suggestions = {
            "sin(x)", "cos(x)", "tan(x)", "asin(x)", "acos(x)", "atan(x)",
            "sinh(x)", "cosh(x)", "tanh(x)", "atan2(x)", "hypot(x)", "log(x)",
            "log10(x)", "sqrt(x)", "abs(x)", "exp(x)", "pow(x,2)", "cbrt(x)", "ceil(x)",
            "round(x)", "floor(x)", "toRadians(x)", "toDegrees(x)", "rand(10, 20)", "signum(x)",
            "mod(x, 2)", "sec(x)", "csc(x)", "cot(x)", "lerp(0, sin(x), .5)", "norm(x, 10, 20)",
            "clamp(x, 20, 40)", "map(sin(x), -1, 1, -10, 20)", "e", "pi"
    };

    private static final Font font = new Font(Font.SERIF, Font.PLAIN, 18);
    public static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
    public static final CCellRenderer cellRenderer = new CCellRenderer(font);
    public static final CompletionProvider autoCompleteProvider = createAutoCompletionProvider();

    static {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ParticleGraphEditor.class.getResourceAsStream("/js/GraphFunctions.js")))) {
            scriptEngine.eval(br);
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }
    }

    public static void getInstance() {
        if (instance == null) instance = new ParticleGraphEditor();
        frame.toFront();
    }

    private ParticleGraphEditor() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Particle Graph Editor");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(460, 280);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(e -> close()));
        frame.setLocationRelativeTo(eFrame);

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
        mnHelp.addActionListener(e -> InstructionsWindow.createGraphInstructionsWindow(frame));
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
        textFields[0].addKeyListener(new ExtendedKeyAdapter(e -> textFields[0].setForeground(Color.black), ParticleGraphEditor::isEnter));

        AutoCompletion ac = new AutoCompletion(autoCompleteProvider);
        ac.setListCellRenderer(cellRenderer);
        ac.install(textFields[0]);

        addUndoFunctionality(textFields[0]);
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
        textFields[1].addKeyListener(new ExtendedKeyAdapter(e -> textFields[1].setForeground(Color.black), ParticleGraphEditor::isEnter));
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel xscale = new RLabel("X Scale", font, GridBagConstraints.WEST, new Insets(0, 3, 5, 5), 0, 12);
        xscale.setToolTipText("<html><h5 style='font-size:11px'>Best at 0.02</h5></html?");
        panel.add(xscale, xscale.gridBagConstraints);

        textFields[2] = new CTextField("" + scaleX, font, new Insets(0, 0, 5, 0), GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        textFields[2].addKeyListener(new ExtendedKeyAdapter(e -> textFields[2].setForeground(Color.black), ParticleGraphEditor::isEnter));
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

    public void close() {
        frame.dispose();
        instance = null;
    }

    private static void graph() {
        if (Particles.size() > 0) Particles.clear();

        float spacing = 0.125f;
        float positiveWidth = eCanvas.getWidth() / 2f;
        float negativeWidth = -positiveWidth;

        for (float i = negativeWidth; i < positiveWidth && isOkToGraph; i += spacing) {
            float x = i * scaleX;
            float y = -(guardFloat(mathExpression, textFields[0]) * scaleY);
            scriptEngine.put("x", x);
            Particles.add(new Particle(i, y, 0.2f));
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
        scaleY = guardFloat(textFields[1].getText(), textFields[1]);
        scaleX = guardFloat(textFields[2].getText(), textFields[2]);
        mathExpression = express == null ? textFields[0].getText() : express;
    }

    public static void setInputTextField(String sampleFunction) {
        textFields[0].setText(sampleFunction);
    }

    public static void setParameter(String key, Object value) {
        scriptEngine.put(key, value);
    }

    public static Float evalExpression(String express) {
        Float result = null;
        boolean isOk = true;

        try {
            result = ((Double) scriptEngine.eval(express)).floatValue();
        } catch (Exception e) {
            isOk = false;
            throwError(e);
        }

        isOkToGraph = isOk;

        return result;
    }

    public static float guardFloat(String expr, Object textField) {
        Float result = evalExpression(expr);

        if (result == null) {
            result = 0f;
            if (textField != null) throwError(textField);
        }

        return result;
    }

    private static void isEnter(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ENTER) threadGraph(null);
    }

    private void addPopup(Component component, JPopupMenu popupMenu) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e, popupMenu);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e, popupMenu);
            }
        });
    }

    // https://stackoverflow.com/questions/10532286/how-to-use-ctrlz-and-ctrly-with-all-text-components
    private static void addUndoFunctionality(JTextComponent pTextComponent) {
        final UndoManager undoMgr = new UndoManager();

        // Add listener for undoable events
        pTextComponent.getDocument().addUndoableEditListener(evt -> undoMgr.addEdit(evt.getEdit()));

        // Add undo/redo actions
        pTextComponent.getActionMap().put("undoKeystroke", new AbstractAction("undoKeystroke") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoMgr.canUndo()) undoMgr.undo();
                } catch (Exception e) {
                    ExceptionWindow.append(e);}
            }
        });

        pTextComponent.getActionMap().put("redoKeystroke", new AbstractAction("redoKeystroke") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoMgr.canRedo()) undoMgr.redo();
                } catch (Exception e) {
                    ExceptionWindow.append(e);}
            }
        });

        // Create keyboard accelerators for undo/redo actions (Ctrl+Z/Ctrl+Y)
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undoKeystroke");
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redoKeystroke");
    }

    private static CompletionProvider createAutoCompletionProvider() {
        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        for (String suggestion : suggestions)
            provider.addCompletion(new BasicCompletion(provider, suggestion));

        return provider;
    }

    private void showMenu(MouseEvent event, JPopupMenu popupMenu) {
        popupMenu.show(event.getComponent(), event.getX(), event.getY());
    }

    private static void throwError(Object... objects) {
        if (objects != null && objects.length > 0) {
            for (Object o : objects) {
                if (o != null) {
                    if (o instanceof JTextField) ((JTextField) o).setForeground(Color.red);
                    else if (o instanceof Exception) ExceptionWindow.append(((Exception) o));
                    else ExceptionWindow.append(o.toString());
                } else ExceptionWindow.append(new NullPointerException("Object passed was null"));
            }
        }
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
