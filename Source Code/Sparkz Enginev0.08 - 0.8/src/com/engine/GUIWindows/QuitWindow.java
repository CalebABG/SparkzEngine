package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;

import com.engine.J8Helpers.Extensions.MLAStruct;
import com.engine.J8Helpers.Extensions.MLAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class QuitWindow {
    private static QuitWindow exitScreen = null;
    private static JFrame frame;
    private static CLabel label, label2, label3;
    public int width = 350, height = 80;
    private Color option_no = new Color(72, 0, 18, 255);
    private Color option_yes = new Color(21, 50, 21, 255);

    //public static void main(String[] args) {new QuitWindow();}

    public static QuitWindow getInstance() {
        if (exitScreen == null) {exitScreen = new QuitWindow();} frame.toFront(); return exitScreen;
    }

    private QuitWindow() {
        frame = new JFrame("Exit");
        frame.setIconImage(Settings.getIcon());
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(0, 0, width, height);
        frame.setShape(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(22, 22, 22));
        panel.setLayout(null);
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        label = new CLabel(new Rectangle(frame.getWidth() / 2 - 100, frame.getHeight() / 2 - 45, 200, 90), "Exit?",
                new Font("Times", Font.PLAIN, 45), Color.white, new Color(0.0f, 0.0f, 0.0f, 0.0f));
        panel.add(label);

        label2 = new CLabel(new Rectangle(20, frame.getHeight() / 2 - (26), 90, 50), "Yes", new
                Font("Times", Font.PLAIN, 45), Color.white, option_yes);

        MLAdapter mlAdapter1 = new MLAdapter(
                new MLAStruct("Clicked", e -> System.exit(0)),
                new MLAStruct("Entered", e -> label2.setBackground(option_yes.brighter())),
                new MLAStruct("Exited",  e -> label2.setBackground(option_yes.darker()))
        );
        label2.addMouseListener(mlAdapter1);
        panel.add(label2);

        label3 = new CLabel(new Rectangle((frame.getWidth() - 109), frame.getHeight() / 2 - (26), 90, 50), "No", new
                Font("Times", Font.PLAIN, 45), Color.white, option_no);

        MLAdapter mlAdapter2 = new MLAdapter(
                new MLAStruct("Clicked", e -> close()),
                new MLAStruct("Entered", e -> label3.setBackground(option_no.brighter())),
                new MLAStruct("Exited",  e -> label3.setBackground(option_no.darker()))
        );
        label3.addMouseListener(mlAdapter2);

        panel.add(label3);
        frame.setVisible(true);
    }

    public void close(){exitScreen = null; frame.dispose();}
}