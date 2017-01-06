package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

public class SWindow {
    private static SWindow sWindow = null;
    public static JFrame frame;
    private int timeout = 860;
    private static String s3 = "text-align: center; font-size: 42px; font-family: Times; font-weight: light; color: white";

    public static SWindow getInstance(String t, int w, int h) {
        if (sWindow == null) {sWindow = new SWindow(t,w,h);} return sWindow;}
    public static SWindow getInstance(String t, int timeout, int w, int h) {
        if (sWindow == null) {sWindow = new SWindow(t,timeout,w,h);} return sWindow;}

    private SWindow(String text, int width, int height) {
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("<html>" + H5Wrapper.HStyle("h1", s3, text) + "</html>", SwingConstants.CENTER);
        panel.add(label);

        frame.setVisible(true);
        new Thread(){public void run() {try {Thread.sleep(timeout); close();}catch (Exception e){
            EException.append(e);}}}.start();
    }

    private SWindow(String text, final int timeout, int width, int height) {
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
//        frame.setOpacity(0.8f);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("<html>" + H5Wrapper.HStyle("h1", s3, text) + "</html>", SwingConstants.CENTER);
        panel.add(label);

        frame.setVisible(true);
        new Thread(){public void run() {try {Thread.sleep(timeout); close();}catch (Exception e){
            EException.append(e);}}}.start();
    }

    private static void close() {sWindow = null; frame.dispose();}
}
