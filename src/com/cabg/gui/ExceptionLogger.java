package com.cabg.gui;

import com.cabg.core.EngineSettings;
import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cabg.core.EngineVariables.*;

public class ExceptionLogger {
    private static ExceptionLogger instance = null;

    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm:ss a");

    private final JFrame frame;
    private static JTextArea textArea;

    public static void getInstance() {
        if (instance == null) instance = new ExceptionLogger();
        instance.frame.toFront();
    }

    private ExceptionLogger() {
        EngineThemes.setLookAndFeel();

        frame = new JFrame("Exception Log");
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(430, 260);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(eFrame);
        frame.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) close();
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) textArea.setText("");
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P)
                textArea.append("Project Development: " + EngineSettings.getProjectTimeSpan() + "\n");
        }));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setDragEnabled(false);
        textArea.setEnabled(false);
        textArea.setFont(new Font(Font.SERIF, Font.ITALIC, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().add(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static String logException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void append(Exception e) {
        textArea.append(dateFormatter.format(new Date()) + " - " + logException(e) + "\n");
    }

    public static void append(String text) {
        textArea.append(text + '\n');
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
