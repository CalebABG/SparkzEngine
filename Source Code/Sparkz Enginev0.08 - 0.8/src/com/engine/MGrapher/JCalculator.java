package com.engine.MGrapher;

import com.engine.Utilities.Settings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

import static com.engine.EngineHelpers.EConstants.EFrame;
import static com.engine.MGrapher.ParticleGraph.mathFunctions;

public class JCalculator {
    private static JCalculator calculator = null;
    private static JFrame frame;
    private static JTextPane resultfield;
    private static JTextPane expressionfield;
    public static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    private static String mathExpression = "x = 0";
    private static Thread calcThread1;

    //public static void main(String[] args) {getInstance(null);}

    public static JCalculator getInstance(JFrame parent) {
        if (calculator == null) {calculator = new JCalculator(parent);} frame.toFront(); return calculator;
    }

    public JCalculator(JFrame parent) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1) {e1.printStackTrace();}
        frame = new JFrame("JavaScript Calculator :D");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(376, 366);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {calculator = null; frame.dispose();}});
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        //Add Helper Math functions to JavaScript Engine at window creation
        try {for (int i = 0; i < mathFunctions.length; i++) {engine.eval(mathFunctions[i]);}} catch (Exception e) {e.printStackTrace();}

        JPanel panel_1 = new JPanel();
        panel.add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton evaluatebutton = new JButton("Evaluate Expression!");
        evaluatebutton.addActionListener(e -> threadEvaluate());
        evaluatebutton.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel_1.add(evaluatebutton);

        JPanel panel_2 = new JPanel();
        panel.add(panel_2, BorderLayout.CENTER);
        GridBagLayout gbl_panel_2 = new GridBagLayout();
        gbl_panel_2.columnWidths = new int[]{0, 0};
        gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_2.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        panel_2.setLayout(gbl_panel_2);

        JLabel lblEnterAnExpression = new JLabel("<html><p style='text-align:center'>Enter an Expression! <br> <span style='text-align:center'>(Shift+Enter = Evaluate)</span></P></html>");
        lblEnterAnExpression.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEnterAnExpression.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblEnterAnExpression = new GridBagConstraints();
        gbc_lblEnterAnExpression.fill = GridBagConstraints.BOTH;
        gbc_lblEnterAnExpression.gridx = 0;
        gbc_lblEnterAnExpression.gridy = 0;
        panel_2.add(lblEnterAnExpression, gbc_lblEnterAnExpression);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        panel_2.add(scrollPane, gbc_scrollPane);

        JPanel panel_3 = new JPanel();
        scrollPane.setViewportView(panel_3);
        GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 0};
        gbl_panel_3.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet keywordscolor = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.ORANGE.darker());
        final AttributeSet regular = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        String reservedWords = "(\\W)*(break|case|catch|class|const|continue|debugger|default|delete|do|else|export|extends|finally|for|function|if|import|in|instanceof|new|return|super|switch|this|throw|try|typeof|var|void|while|with|yield)";

        DefaultStyledDocument doc = new DefaultStyledDocument() {
            public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offset);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches(reservedWords))
                            setCharacterAttributes(wordL, wordR - wordL, keywordscolor, false);
                        else
                            setCharacterAttributes(wordL, wordR - wordL, regular, false);
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            public void remove (int offs, int len) throws BadLocationException {
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) before = 0;
                int after = findFirstNonWordChar(text, offs);

                if (text.substring(before, after).matches(reservedWords)) {
                    setCharacterAttributes(before, after - before, keywordscolor, false);
                } else {
                    setCharacterAttributes(before, after - before, regular, false);
                }
            }
        };

        expressionfield = new JTextPane(doc);
        expressionfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER) try {graph();
                }catch (Exception x){resultfield.setText(x.getMessage());}
            }
        });
        expressionfield.setFont(new Font("Tahoma", Font.PLAIN, 16));
        expressionfield.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "tab");
        expressionfield.getActionMap().put("tab", new AbstractAction("tab"){
            private static final long serialVersionUID = -2621537943352838927L;
            public void actionPerformed(ActionEvent e){
                try {expressionfield.getDocument().insertString(expressionfield.getCaretPosition(), " ", null);
                } catch (BadLocationException e1) {e1.printStackTrace();}
            }
        });

        GridBagConstraints gbc_expressionfield = new GridBagConstraints();
        gbc_expressionfield.ipady = 75;
        gbc_expressionfield.fill = GridBagConstraints.BOTH;
        gbc_expressionfield.gridx = 0;
        gbc_expressionfield.gridy = 0;
        panel_3.add(expressionfield, gbc_expressionfield);

        JLabel lblResult = new JLabel("Result");
        lblResult.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblResult.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblResult = new GridBagConstraints();
        gbc_lblResult.fill = GridBagConstraints.BOTH;
        gbc_lblResult.insets = new Insets(0, 0, 5, 0);
        gbc_lblResult.gridx = 0;
        gbc_lblResult.gridy = 2;
        panel_2.add(lblResult, gbc_lblResult);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 3;
        panel_2.add(scrollPane_1, gbc_scrollPane_1);

        resultfield = new JTextPane();
        resultfield.setFont(new Font("Tahoma", Font.PLAIN, 18));
        scrollPane_1.setViewportView(resultfield);
        frame.setVisible(true);
    }

    private static void evalInput() {
        mathExpression = expressionfield.getText();
    }

    private static void graph() throws Exception {
        evalInput();
        resultfield.setText(engine.eval(mathExpression).toString());
    }

    public static void threadEvaluate() {
        if (calcThread1 != null && calcThread1.isAlive()) {
            try {calcThread1.interrupt(); calcThread1.join();} catch (InterruptedException e1) {e1.printStackTrace();}

            calcThread1 = new Thread() {public void run() {try {graph();}catch (Exception e) {resultfield.setText(e.getMessage());}}};
            calcThread1.start();
        }
        else {
            calcThread1 = new Thread() {public void run() {try {graph();} catch (Exception e) {resultfield.setText(e.getMessage());}}};
            calcThread1.start();
        }
    }

    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) if (String.valueOf(text.charAt(index)).matches("\\W")) break;
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) break;
            index++;
        }
        return index;
    }
}
