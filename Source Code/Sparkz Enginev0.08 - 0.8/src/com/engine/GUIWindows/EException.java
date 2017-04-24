package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.TimerTaskX;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;

public class EException {
    private static EException EException = null;
    private static String ELOG = "";
    public static JFrame frame;
    private static JTextArea textArea = new JTextArea(ELOG);
    private static Timer timer;

    //public static void main(String[] args) {}

    public static EException getInstance() {
        if (EException == null) {EException = new EException();} return EException;
    }

    private EException(){
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame("Exception Log");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(430, 260);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);
        frame.addKeyListener(new KAdapter(e -> {}, e -> {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {close();}
            if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_C)) {ELOG = "";}
            if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_P)) {ELOG += "Project Development: " + Settings.PROJECT_LOG + " Months\n";}
        }));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setDoubleBuffered(true);
        textArea.setDragEnabled(false);
        textArea.setEnabled(false);
        textArea.setFont(new Font("Arial", Font.ITALIC, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().add(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        startTimer();
        frame.setVisible(true);
    }

    private static void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTaskX(com.engine.GUIWindows.EException::update), 0, timerFPS);
    }

    private static void stopTimer() {timer.cancel(); timer.purge();}

    private static String logException(Exception e){StringWriter sw = new StringWriter(); e.printStackTrace(new PrintWriter(sw)); return sw.toString();}
    public static void write(String s){ELOG += s+"\n";}
    public static void setText(String s){textArea.setText(s);}
    public static void append(Exception except){ELOG += "" + new SimpleDateFormat("h:mm:ss a").format(new Date()) + " - " + (logException(except)) + "\n";}
    public static void update(){try {if (textArea != null) {textArea.setText(ELOG);}} catch (Exception ex) {append(ex);}}

    private static void close() {EException = null; stopTimer(); frame.dispose();}
}
