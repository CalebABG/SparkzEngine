package com.engine.MGrapher.JSCalc;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.MAdapter;
import com.engine.J8Helpers.Extensions.WAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import static com.engine.JComponents.TextSuggestor.makeUndoable;
import static com.engine.MGrapher.JSCalc.JSOperations.*;

public class JSCalc {
    private static JSCalc jsCalc = null;

    public static JFrame frame;
    public static JPanel panel;
    public static JSMenuBar menuBar;
    public static JTextArea textPane;
    public static JTextArea resultsPane;
    public static JSplitPane primary_splitpane, edits_results;
    public static JScrollPane jstexteditor_scrollpane, results_scrollpane;
    public static ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("Nashorn");
    public static CLabel[] buttons = new CLabel[30];
    public static Font font1 = new Font(Font.SERIF, Font.PLAIN, 20);
    public static Font textFont = new Font(Font.SERIF, Font.PLAIN, 20);
    public static Color keyColorbg = new Color(20, 23, 25).brighter();
    public static Color keyColorfg = Color.WHITE;
    public static Robot robot;
    public static String for_state = "for(var i = 0; i < 5; i++){\n" + "    \n" + "}";
    public static String if_state = "if(){\n" + "    \n" + "}";
    public static String if_else_state = "if(){\n" + "\t\n" + "} else{\n" + "\t\n" + "}";
    public static String while_state = "while(){\n" + "    \n" + "}";
    public static String switch_state = "switch() {\n" +
                                        "    case :\n" +
                                        "        break;\n" +
                                        "    default:\n" +
                                        "        break;\n" +
                                        "}";

    static {
        try {
            robot = new Robot();
            scriptEngine.put("π", Math.PI); // Have to add here, will not put from mfunctions file
            scriptEngine.eval(new InputStreamReader(JSCalc.class.getClass().getResourceAsStream("/com/engine/MGrapher/JSCalc/mfunctions.js")));
        } catch (Exception e) {e.printStackTrace();}
    }

    //public static void main(String[] args) {new JSCalc(null);}

    public static JSCalc getInstance(JFrame parent) {
        if (jsCalc == null) {jsCalc = new JSCalc(parent);} frame.toFront(); return jsCalc;
    }

    private JSCalc(JFrame parent) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}
        UIManager.put("SplitPane.background", keyColorbg.darker());
        UIManager.put("SplitPaneDivider.border", BorderFactory.createLineBorder(keyColorbg, 1));
        frame = new JFrame("JSCalc <(^.^)> -- Shift+Enter to Calculate");
        frame.setSize(430, 570);
        frame.setIconImage(Settings.getIcon());
        frame.setLocationRelativeTo(parent);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));

        menuBar = new JSMenuBar();
        frame.setJMenuBar(menuBar);

        primary_splitpane = new JSplitPane();
        primary_splitpane.setBorder(BorderFactory.createLineBorder(keyColorbg.darker(), 3));
        primary_splitpane.setOneTouchExpandable(true);
        primary_splitpane.setDividerSize(8);
        primary_splitpane.setResizeWeight(1.0);
        primary_splitpane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        primary_splitpane.setDoubleBuffered(true);
        primary_splitpane.setContinuousLayout(true);
        frame.getContentPane().add(primary_splitpane, BorderLayout.CENTER);

        edits_results = new JSplitPane();
        edits_results.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        edits_results.setDividerSize(8);
        edits_results.setContinuousLayout(true);
        edits_results.setDoubleBuffered(true);
        edits_results.setResizeWeight(0.2);
        edits_results.setOneTouchExpandable(true);
        edits_results.setOrientation(JSplitPane.VERTICAL_SPLIT);
        primary_splitpane.setLeftComponent(edits_results);

        jstexteditor_scrollpane = new JScrollPane();
        jstexteditor_scrollpane.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        edits_results.setRightComponent(jstexteditor_scrollpane);

        textPane = new JTextArea();
        textPane.setLineWrap(true);
        textPane.setTabSize(2);
        textPane.setFont(textFont);
        textPane.setBackground(keyColorbg);
        textPane.setForeground(Color.white);
        textPane.setCaretColor(Color.white);
        textPane.addKeyListener(new KAdapter(e -> {
        }, e -> {
            if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER) calculate();
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) shortcut_switch(textPane.getSelectedText());
        }));
        makeUndoable(textPane);
        jstexteditor_scrollpane.setViewportView(textPane);

        resultsPane = new JTextArea();
        resultsPane.setTabSize(2);
        resultsPane.setLineWrap(true);
        resultsPane.setEditable(false);
        resultsPane.setForeground(Color.WHITE);
        resultsPane.setFont(textFont);
        resultsPane.setCaretColor(Color.WHITE);
        resultsPane.setBackground(keyColorbg);

        results_scrollpane = new JScrollPane();
        results_scrollpane.setBorder(BorderFactory.createLineBorder(keyColorbg, 1));
        results_scrollpane.setViewportView(resultsPane);
        edits_results.setLeftComponent(results_scrollpane);

        panel = new JPanel();
        panel.setBackground(keyColorbg.darker().darker());
        primary_splitpane.setRightComponent(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{65, 65, 95, 95, 95, 90, 0};
        gbl_panel.rowHeights = new int[]{55, 55, 55, 55, 60, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);


        // x^2
        buttons[0] = new CLabel("<html>x<sup>2</sup></html>", font1, keyColorfg, keyColorbg);
        buttons[0].addMouseListener(new MAdapter.MouseClicked(e -> x_squared()));
        buttons[0].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_x_squared = new GridBagConstraints();
        gbc_x_squared.fill = GridBagConstraints.BOTH;
        gbc_x_squared.insets = new Insets(0, 0, 5, 5);
        gbc_x_squared.ipady = 15;
        gbc_x_squared.ipadx = 10;
        gbc_x_squared.gridx = 0;
        gbc_x_squared.gridy = 0;
        panel.add(buttons[0], gbc_x_squared);

        //x^3
        buttons[1] = new CLabel("<html>x<sup>3</sup></html>", font1, keyColorfg, keyColorbg);
        //buttons[1].getMouseListeners()[0].mouseClicked();
        buttons[1].addMouseListener(new MAdapter.MouseClicked(e -> x_cubed()));
        GridBagConstraints gbc_x_cubed = new GridBagConstraints();
        gbc_x_cubed.fill = GridBagConstraints.BOTH;
        gbc_x_cubed.insets = new Insets(0, 0, 5, 5);
        gbc_x_cubed.gridx = 1;
        gbc_x_cubed.gridy = 0;
        panel.add(buttons[1], gbc_x_cubed);

        // 7
        buttons[2] = new CLabel("7", font1, keyColorfg, keyColorbg);
        buttons[2].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[2].getText())));
        buttons[2].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_seven = new GridBagConstraints();
        gbc_seven.fill = GridBagConstraints.BOTH;
        gbc_seven.insets = new Insets(0, 0, 5, 5);
        gbc_seven.gridx = 2;
        gbc_seven.gridy = 0;
        panel.add(buttons[2], gbc_seven);

        // 8
        buttons[3] = new CLabel("8", font1, keyColorfg, keyColorbg);
        buttons[3].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[3].getText())));
        buttons[3].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_eight = new GridBagConstraints();
        gbc_eight.fill = GridBagConstraints.BOTH;
        gbc_eight.insets = new Insets(0, 0, 5, 5);
        gbc_eight.gridx = 3;
        gbc_eight.gridy = 0;
        panel.add(buttons[3], gbc_eight);

        // 9
        buttons[4] = new CLabel("9", font1, keyColorfg, keyColorbg);
        buttons[4].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[4].getText())));
        buttons[4].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_nine = new GridBagConstraints();
        gbc_nine.fill = GridBagConstraints.BOTH;
        gbc_nine.insets = new Insets(0, 0, 5, 5);
        gbc_nine.gridx = 4;
        gbc_nine.gridy = 0;
        panel.add(buttons[4], gbc_nine);

        // divide: /
        buttons[5] = new CLabel("/", font1, keyColorfg, keyColorbg);
        buttons[5].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[5].getText())));
        buttons[5].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_divide = new GridBagConstraints();
        gbc_divide.fill = GridBagConstraints.BOTH;
        gbc_divide.insets = new Insets(0, 0, 5, 0);
        gbc_divide.gridx = 5;
        gbc_divide.gridy = 0;
        panel.add(buttons[5], gbc_divide);

        // square root
        buttons[6] = new CLabel("\u221A", font1, keyColorfg, keyColorbg);
        buttons[6].addMouseListener(new MAdapter.MouseClicked(e -> square_root()));
        buttons[6].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_square_root = new GridBagConstraints();
        gbc_square_root.fill = GridBagConstraints.BOTH;
        gbc_square_root.insets = new Insets(0, 0, 5, 5);
        gbc_square_root.gridx = 0;
        gbc_square_root.gridy = 1;
        panel.add(buttons[6], gbc_square_root);

        // e^x
        buttons[7] = new CLabel("<html>e<sup>x</sup></html>", font1, keyColorfg, keyColorbg);
        buttons[7].addMouseListener(new MAdapter.MouseClicked(e -> e_to_x()));
        buttons[7].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_one_over_x = new GridBagConstraints();
        gbc_one_over_x.fill = GridBagConstraints.BOTH;
        gbc_one_over_x.insets = new Insets(0, 0, 5, 5);
        gbc_one_over_x.gridx = 1;
        gbc_one_over_x.gridy = 1;
        panel.add(buttons[7], gbc_one_over_x);

        // 4
        buttons[8] = new CLabel("4", font1, keyColorfg, keyColorbg);
        buttons[8].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[8].getText())));
        buttons[8].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_four = new GridBagConstraints();
        gbc_four.fill = GridBagConstraints.BOTH;
        gbc_four.insets = new Insets(0, 0, 5, 5);
        gbc_four.gridx = 2;
        gbc_four.gridy = 1;
        panel.add(buttons[8], gbc_four);

        // 5
        buttons[9] = new CLabel("5", font1, keyColorfg, keyColorbg);
        buttons[9].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[9].getText())));
        buttons[9].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_five = new GridBagConstraints();
        gbc_five.fill = GridBagConstraints.BOTH;
        gbc_five.insets = new Insets(0, 0, 5, 5);
        gbc_five.gridx = 3;
        gbc_five.gridy = 1;
        panel.add(buttons[9], gbc_five);

        // 6
        buttons[10] = new CLabel("6", font1, keyColorfg, keyColorbg);
        buttons[10].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[10].getText())));
        buttons[10].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_six = new GridBagConstraints();
        gbc_six.fill = GridBagConstraints.BOTH;
        gbc_six.insets = new Insets(0, 0, 5, 5);
        gbc_six.gridx = 4;
        gbc_six.gridy = 1;
        panel.add(buttons[10], gbc_six);

        // times: *
        buttons[11] = new CLabel("*", font1, keyColorfg, keyColorbg);
        buttons[11].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[11].getText())));
        buttons[11].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_times = new GridBagConstraints();
        gbc_times.fill = GridBagConstraints.BOTH;
        gbc_times.insets = new Insets(0, 0, 5, 0);
        gbc_times.gridx = 5;
        gbc_times.gridy = 1;
        panel.add(buttons[11], gbc_times);

        // ln(x)
        buttons[12] = new CLabel("ln", font1, keyColorfg, keyColorbg);
        buttons[12].addMouseListener(new MAdapter.MouseClicked(e -> ln_x()));
        buttons[12].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_ln = new GridBagConstraints();
        gbc_ln.fill = GridBagConstraints.BOTH;
        gbc_ln.insets = new Insets(0, 0, 5, 5);
        gbc_ln.gridx = 0;
        gbc_ln.gridy = 2;
        panel.add(buttons[12], gbc_ln);

        // log(x)
        buttons[13] = new CLabel("log", font1, keyColorfg, keyColorbg);
        buttons[13].addMouseListener(new MAdapter.MouseClicked(e -> log_x()));
        buttons[13].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_log = new GridBagConstraints();
        gbc_log.fill = GridBagConstraints.BOTH;
        gbc_log.insets = new Insets(0, 0, 5, 5);
        gbc_log.gridx = 1;
        gbc_log.gridy = 2;
        panel.add(buttons[13], gbc_log);

        // 1
        buttons[14] = new CLabel("1", font1, keyColorfg, keyColorbg);
        buttons[14].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[14].getText())));
        buttons[14].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_one = new GridBagConstraints();
        gbc_one.fill = GridBagConstraints.BOTH;
        gbc_one.insets = new Insets(0, 0, 5, 5);
        gbc_one.gridx = 2;
        gbc_one.gridy = 2;
        panel.add(buttons[14], gbc_one);

        // 2
        buttons[15] = new CLabel("2", font1, keyColorfg, keyColorbg);
        buttons[15].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[15].getText())));
        buttons[15].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_two = new GridBagConstraints();
        gbc_two.fill = GridBagConstraints.BOTH;
        gbc_two.insets = new Insets(0, 0, 5, 5);
        gbc_two.gridx = 3;
        gbc_two.gridy = 2;
        panel.add(buttons[15], gbc_two);

        // 3
        buttons[16] = new CLabel("3", font1, keyColorfg, keyColorbg);
        buttons[16].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[16].getText())));
        buttons[16].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_three = new GridBagConstraints();
        gbc_three.fill = GridBagConstraints.BOTH;
        gbc_three.insets = new Insets(0, 0, 5, 5);
        gbc_three.gridx = 4;
        gbc_three.gridy = 2;
        panel.add(buttons[16], gbc_three);

        // minus: -
        buttons[17] = new CLabel("-", font1, keyColorfg, keyColorbg);
        buttons[17].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[17].getText())));
        buttons[17].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_minus = new GridBagConstraints();
        gbc_minus.fill = GridBagConstraints.BOTH;
        gbc_minus.insets = new Insets(0, 0, 5, 0);
        gbc_minus.gridx = 5;
        gbc_minus.gridy = 2;
        panel.add(buttons[17], gbc_minus);

        // pi
        buttons[18] = new CLabel("π", font1, keyColorfg, keyColorbg);
        buttons[18].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[18].getText())));
        buttons[18].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_pi = new GridBagConstraints();
        gbc_pi.fill = GridBagConstraints.BOTH;
        gbc_pi.insets = new Insets(0, 0, 5, 5);
        gbc_pi.gridx = 0;
        gbc_pi.gridy = 3;
        panel.add(buttons[18], gbc_pi);

        // eulers constant: e
        buttons[19] = new CLabel("e", font1, keyColorfg, keyColorbg);
        buttons[19].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[19].getText())));
        buttons[19].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_eulersConstant = new GridBagConstraints();
        gbc_eulersConstant.fill = GridBagConstraints.BOTH;
        gbc_eulersConstant.insets = new Insets(0, 0, 5, 5);
        gbc_eulersConstant.gridx = 1;
        gbc_eulersConstant.gridy = 3;
        panel.add(buttons[19], gbc_eulersConstant);

        // left parentheses: (
        buttons[20] = new CLabel("(", font1, keyColorfg, keyColorbg);
        buttons[20].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[20].getText())));
        buttons[20].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_left_parens = new GridBagConstraints();
        gbc_left_parens.fill = GridBagConstraints.BOTH;
        gbc_left_parens.insets = new Insets(0, 0, 5, 5);
        gbc_left_parens.gridx = 2;
        gbc_left_parens.gridy = 3;
        panel.add(buttons[20], gbc_left_parens);

        // 0
        buttons[21] = new CLabel("0", font1, keyColorfg, keyColorbg);
        buttons[21].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[21].getText())));
        buttons[21].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_zero = new GridBagConstraints();
        gbc_zero.fill = GridBagConstraints.BOTH;
        gbc_zero.insets = new Insets(0, 0, 5, 5);
        gbc_zero.gridx = 3;
        gbc_zero.gridy = 3;
        panel.add(buttons[21], gbc_zero);

        // right parentheses: (
        buttons[22] = new CLabel(")", font1, keyColorfg, keyColorbg);
        buttons[22].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[22].getText())));
        buttons[22].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_right_parens = new GridBagConstraints();
        gbc_right_parens.fill = GridBagConstraints.BOTH;
        gbc_right_parens.insets = new Insets(0, 0, 5, 5);
        gbc_right_parens.gridx = 4;
        gbc_right_parens.gridy = 3;
        panel.add(buttons[22], gbc_right_parens);

        // plus: +
        buttons[23] = new CLabel("+", font1, keyColorfg, keyColorbg);
        buttons[23].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[23].getText())));
        buttons[23].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_plus = new GridBagConstraints();
        gbc_plus.fill = GridBagConstraints.BOTH;
        gbc_plus.insets = new Insets(0, 0, 5, 0);
        gbc_plus.gridx = 5;
        gbc_plus.gridy = 3;
        panel.add(buttons[23], gbc_plus);

        // factorial: n!
        buttons[24] = new CLabel("n!", font1, keyColorfg, keyColorbg);
        buttons[24].addMouseListener(new MAdapter.MouseClicked(e -> nfactorial()));
        buttons[24].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_nfactorial = new GridBagConstraints();
        gbc_nfactorial.fill = GridBagConstraints.BOTH;
        gbc_nfactorial.insets = new Insets(0, 0, 0, 5);
        gbc_nfactorial.gridx = 0;
        gbc_nfactorial.gridy = 4;
        panel.add(buttons[24], gbc_nfactorial);

        // 1/x
        buttons[25] = new CLabel("1/x", font1, keyColorfg, keyColorbg);
        buttons[25].addMouseListener(new MAdapter.MouseClicked(e -> one_over_x()));
        buttons[25].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_ten_to_x = new GridBagConstraints();
        gbc_ten_to_x.fill = GridBagConstraints.BOTH;
        gbc_ten_to_x.insets = new Insets(0, 0, 0, 5);
        gbc_ten_to_x.gridx = 1;
        gbc_ten_to_x.gridy = 4;
        panel.add(buttons[25], gbc_ten_to_x);

        // clear everything: CE
        buttons[26] = new CLabel("CE", font1, keyColorfg, keyColorbg);
        buttons[26].addMouseListener(new MAdapter.MouseClicked(e -> textPane.setText("")));
        buttons[26].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_ce = new GridBagConstraints();
        gbc_ce.fill = GridBagConstraints.BOTH;
        gbc_ce.insets = new Insets(0, 0, 0, 5);
        gbc_ce.gridx = 2;
        gbc_ce.gridy = 4;
        panel.add(buttons[26], gbc_ce);

        // Backspace button
        buttons[27] = new CLabel("\u00AB", font1, keyColorfg, keyColorbg);
        buttons[27].setHorizontalAlignment(SwingConstants.CENTER);
        buttons[27].addMouseListener(new MAdapter.MouseClicked(e -> backspace()));
        GridBagConstraints gbc_backspace = new GridBagConstraints();
        gbc_backspace.fill = GridBagConstraints.BOTH;
        gbc_backspace.insets = new Insets(0, 0, 0, 5);
        gbc_backspace.gridx = 3;
        gbc_backspace.gridy = 4;
        panel.add(buttons[27], gbc_backspace);

        // dot: .
        buttons[28] = new CLabel(".", font1, keyColorfg, keyColorbg);
        buttons[28].addMouseListener(new MAdapter.MouseClicked(e -> addText(buttons[28].getText())));
        buttons[28].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_dot = new GridBagConstraints();
        gbc_dot.fill = GridBagConstraints.BOTH;
        gbc_dot.insets = new Insets(0, 0, 0, 5);
        gbc_dot.gridx = 4;
        gbc_dot.gridy = 4;
        panel.add(buttons[28], gbc_dot);

        // equals: =
        buttons[29] = new CLabel("=", font1, keyColorfg, keyColorbg);
        buttons[29].addMouseListener(new MAdapter.MouseClicked(e -> calculate()));
        buttons[29].setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_equals = new GridBagConstraints();
        gbc_equals.fill = GridBagConstraints.BOTH;
        gbc_equals.gridx = 5;
        gbc_equals.gridy = 4;
        panel.add(buttons[29], gbc_equals);

        for (CLabel button : buttons) {
            button.addMouseListener(new MAdapter(e -> {
                button.pressed_bgcolor = button.getBackground().darker().darker();
                button.pressed_fgcolor = button.getForeground();
                button.released_bgcolor = button.getBackground();
                button.released_fgcolor = button.getForeground();

                button.setBackground(button.pressed_bgcolor);
                button.setForeground(button.pressed_fgcolor);
            }, e -> {
                button.setBackground(button.released_bgcolor);
                button.setForeground(button.released_fgcolor);
            }));
        }

        frame.addWindowListener(new WAdapter.WindowOpened(e -> textPane.requestFocus()));
        frame.setVisible(true);
    }

    public static void reset(){
        try {
            scriptEngine = new ScriptEngineManager().getEngineByName("Nashorn");
            scriptEngine.put("π", Math.PI); // Have to add here, will not put from mfunctions file
            scriptEngine.eval(new InputStreamReader(JSCalc.class.getClass().getResourceAsStream("/mfunctions.js")));
        } catch (Exception e) {e.printStackTrace();}
    }

    private void shortcut_switch(String state) {
        if (state != null && !state.isEmpty()){
            switch (state) {
                case "for":
                    textPane.replaceSelection(for_state);
                    break;
                case "if":
                    textPane.replaceSelection(if_state);
                    break;
                case "ifel":
                    textPane.replaceSelection(if_else_state);
                    break;
                case "while":
                    textPane.replaceSelection(while_state);
                    break;
                case "switch":
                    textPane.replaceSelection(switch_state);
                    break;
                default:
                    break;
            }
        }
    }

    public void calculate() {
        try {resultsPane.setText(scriptEngine.eval(textPane.getText()).toString());} catch (Exception e) {resultsPane.setText(e.getMessage());}
    }

    public static double evaluateExpr(String express) throws Exception {
        if (scriptEngine.eval(express) instanceof Integer) return ((Integer) scriptEngine.eval(express)).doubleValue();
        else if (scriptEngine.eval(express) instanceof Double) return (double) scriptEngine.eval(express);
        else return 0;
    }

    public static void addText(String text) {
        try {
            textPane.getDocument().insertString(textPane.getCaretPosition(), text, null);
        } catch (BadLocationException e1) {e1.printStackTrace();}
    }

    public static void backspace() {
        try {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_BACK_SPACE);
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void close(){jsCalc = null; frame.dispose();}
}