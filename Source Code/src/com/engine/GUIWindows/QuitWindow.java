package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;

public class QuitWindow {
    private static QuitWindow exitScreen = null;
    private static JFrame frame;
    private static CLabel label, label2, label3;
    public int width = 350, height = 80;
    private Color optionno = new Color(72, 0, 18, 255);
    private Color optionyes = new Color(21, 50, 21, 255);

    //public static void main(String[] args) {new QuitWindow();}

    public static QuitWindow getInstance() {
        if (exitScreen == null) {exitScreen = new QuitWindow();}frame.toFront(); return exitScreen;
    }

    private QuitWindow() {
        frame = new JFrame("Exit");
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        //frame.setOpacity(0.9f);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent)
        {exitScreen = null; frame.dispose();}});
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(null);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        label = new CLabel(new Rectangle(frame.getWidth()/2-100, frame.getHeight()/2-45, 200, 90), "Exit?",
                new Font("Times", Font.PLAIN, 45), Color.white, new Color(0.0f, 0.0f, 0.0f, 0.0f));
        panel.add(label);

        label2 = new CLabel(new Rectangle(20, frame.getHeight()/2-(26), 90, 50), "Yes", new
                Font("Times", Font.PLAIN, 45), Color.white, optionyes);
        label2.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {System.exit(0);}
            public void mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {label2.setBackground(optionyes.brighter());}
            public void mouseExited(MouseEvent e) {
                label2.setBackground(optionyes.darker());
            }
        });
        panel.add(label2);

        label3 = new CLabel(new Rectangle((frame.getWidth() - 109), frame.getHeight()/2-(26), 90, 50), "No", new
                Font("Times", Font.PLAIN, 45), Color.white, optionno);
        label3.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {exitScreen = null; frame.dispose();}
            public void mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {label3.setBackground(optionno.brighter());}
            public void mouseExited(MouseEvent e) {label3.setBackground(optionno.darker());}
        });
        panel.add(label3);
        frame.setVisible(true);
    }
}
