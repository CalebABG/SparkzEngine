package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;

import com.engine.J8Helpers.Interfaces.WindowAdapterX;
import com.engine.Utilities.H5Wrapper;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

public class Notifier implements WindowAdapterX {
    private static Notifier notifier = null;
    public static JFrame frame;
    private int timeout = 860;
    private static String s3 = "text-align: center; font-size: 42px; font-family: Times; font-weight: light; color: white";

    public static Notifier getInstance(String t, int w, int h) {if (notifier == null) {
        notifier = new Notifier(t, w, h);} return notifier;}
    public static Notifier getInstance(String t, int timeout, int w, int h) {if (notifier == null) {
        notifier = new Notifier(t, timeout, w, h);} return notifier;}
    public static Notifier getInstance(JFrame parent, String t, int w, int h) {if (notifier == null) {
        notifier = new Notifier(parent, t, w, h);} return notifier;}

    private Notifier(JFrame parent, String text, int width, int height) {
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("<html>" + H5Wrapper.HStyle("h1", s3, text) + "</html>", SwingConstants.CENTER);
        panel.add(label);

        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {try {Thread.sleep(timeout); close();} catch (Exception e){EException.append(e);}});
        //new Thread(() -> {try {Thread.sleep(timeout); close();}catch (Exception e){EException.append(e);}}).start();
    }

    private Notifier(String text, int width, int height) {
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("<html>" + H5Wrapper.HStyle("h1", s3, text) + "</html>", SwingConstants.CENTER);
        panel.add(label);

        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {try {Thread.sleep(timeout); close();} catch (Exception e){EException.append(e);}});
//        new Thread(() -> {try {Thread.sleep(timeout); close();} catch (Exception e){EException.append(e);}}).start();
    }

    private Notifier(String text, final int timeout, int width, int height) {
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        JLabel label = new JLabel("<html>" + H5Wrapper.HStyle("h1", s3, text) + "</html>", SwingConstants.CENTER);
        panel.add(label);

        frame.setVisible(true);
        SwingUtilities.invokeLater(() -> {try {Thread.sleep(timeout); close();} catch (Exception e){EException.append(e);}});
        //new Thread(() -> {try {Thread.sleep(timeout); close();} catch (Exception e){EException.append(e);}}).start();
    }

    private void close() {notifier = null; frame.dispose();}
    public void windowClosing(WindowEvent e) {close();}
}
