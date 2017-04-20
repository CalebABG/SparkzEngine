package com.engine.MGrapher.JSCalc;

import com.engine.JComponents.CLabel;

import javax.swing.*;
import java.awt.*;

public class JSMenuBar extends JMenuBar {
    public static Color bgColor = JSCalc.keyColorbg;
    private static JSMenuBar menuBar;
    private static Font font1 = new Font(Font.SERIF, Font.PLAIN, 21);
    private static Font menuitemfont = new Font(Font.SERIF, Font.PLAIN, 18);

    public static JSMenuBar createMenuBar() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1){e1.printStackTrace();}
        menuBar = new JSMenuBar();
        menuBar.setBorder(BorderFactory.createLineBorder(bgColor, 2, false));
        menuBar.add(Box.createHorizontalStrut(11));

        //File Begin
        JMenu mnFile = new JMenu("File");
        mnFile.setFont(font1);
        mnFile.setForeground(Color.white);
        menuBar.add(mnFile);
        menuBar.add(Box.createHorizontalStrut(11));
        //File End

        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(menuitemfont);
        exit.addActionListener(e -> System.exit(0));
        mnFile.add(exit);

        //Edit Begin
        JMenu mnEdit = new JMenu("Edit");
        mnEdit.setFont(font1);
        mnEdit.setForeground(Color.white);
        menuBar.add(mnEdit);

        JMenuItem fontchooser = new JMenuItem("Change Font");
        fontchooser.setFont(menuitemfont);
        fontchooser.addActionListener( e -> {
            JFontChooser fontChooser = new JFontChooser();
            int result = fontChooser.showDialog(JSCalc.frame);
            if (result == JFontChooser.OK_OPTION) {
                Font selected = fontChooser.getSelectedFont();
                JSCalc.textPane.setFont(selected);
                JSCalc.resultsPane.setFont(selected);
                for (CLabel button : JSCalc.buttons) {
                    button.setFont(selected);
                }
            }
        });
        mnEdit.add(fontchooser);

        menuBar.add(Box.createHorizontalStrut(11));

        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(font1);
        mnHelp.setForeground(Color.white);

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            JMenuItem accessibility = new JMenuItem("On-Screen Keyboard");
            accessibility.setFont(menuitemfont);
            accessibility.addActionListener(e -> {try {Runtime.getRuntime().exec("cmd /c osk");} catch (Exception f) {f.printStackTrace();}});
            mnHelp.add(accessibility);
        }

        menuBar.add(mnHelp);
        return menuBar;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
    }
}