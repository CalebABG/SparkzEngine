package com.engine.MGrapher.JSCalc;

import com.engine.JComponents.CLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JSMenuBar extends JMenuBar {
    public  Color bgColor = JSCalc.keyColorbg;
    private Font font1 = new Font(Font.SERIF, Font.PLAIN, 21);
    public Font menuitemfont = new Font(Font.SERIF, Font.PLAIN, 18);
    public ArrayList<JMenu> menus = new ArrayList<>();
    public ArrayList<JMenuItem> menuItems = new ArrayList<>();

    public JSMenuBar() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e1){e1.printStackTrace();}
        setBorder(BorderFactory.createLineBorder(bgColor, 1, false));
        add(Box.createHorizontalStrut(11));

        //File
        JMenu file = new JMenu("File");
        add(file);
        menus.add(file);
        add(Box.createHorizontalStrut(8));

        //Exit
        JMenuItem exit = new JMenuItem("Close");
        exit.addActionListener(e -> JSCalc.close());
        file.add(exit);
        menuItems.add(exit);

        //Edit
        JMenu edit = new JMenu("Edit");
        add(edit);
        menus.add(edit);

        //Change Font
        JMenuItem chngfont = new JMenuItem("Change Font");
        chngfont.addActionListener( e -> {
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
        edit.add(chngfont);
        menuItems.add(chngfont);

        //Add themes
        JMenu theme_menu = new JMenu("Themes");
        theme_menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
        theme_menu.setOpaque(true); //Have to set opaque inside another menu
        edit.add(theme_menu);
        menuItems.add(theme_menu);

        JMenuItem theme1 = new JMenuItem("Default");
        theme1.addActionListener(e -> Themes.defaultTheme());
        theme1.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme1);
        menuItems.add(theme1);

        JMenuItem theme2 = new JMenuItem("Midnight Blues");
        theme2.addActionListener(e -> Themes.midnightBlues());
        theme2.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme2);
        menuItems.add(theme2);

        JMenuItem theme3 = new JMenuItem("Dark Mocha");
        theme3.addActionListener(e -> Themes.darkMocha());
        theme3.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme3);
        menuItems.add(theme3);

        JMenuItem theme4 = new JMenuItem("Mild Tangerine");
        theme4.addActionListener(e -> Themes.darkTangerine());
        theme4.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme4);
        menuItems.add(theme4);

        JMenuItem theme5 = new JMenuItem("Serene Sienna");
        theme5.addActionListener(e -> Themes.sienna());
        theme5.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme5);
        menuItems.add(theme5);

        JMenuItem theme6 = new JMenuItem("WinterGreen Dream");
        theme6.addActionListener(e -> Themes.winterGreenDream());
        theme6.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme6);
        menuItems.add(theme6);

        JMenuItem theme7 = new JMenuItem("Vegas Gold");
        theme7.addActionListener(e -> Themes.vegasGold());
        theme7.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme7);
        menuItems.add(theme7);

        JMenuItem theme8 = new JMenuItem("RoseWood");
        theme8.addActionListener(e -> Themes.roseWood());
        theme8.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme8);
        menuItems.add(theme8);

        JMenuItem theme9 = new JMenuItem("Antique Pink");
        theme9.addActionListener(e -> Themes.antiquePink());
        theme9.setBorder(BorderFactory.createLineBorder(bgColor));
        theme_menu.add(theme9);
        menuItems.add(theme9);


        add(Box.createHorizontalStrut(8));

        //Help
        JMenu help = new JMenu("Help");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            //On-Screen Keyboard
            JMenuItem access = new JMenuItem("On-Screen Keyboard");
            access.addActionListener(e -> {try {Runtime.getRuntime().exec("cmd /c osk");} catch (Exception f) {f.printStackTrace();}});
            help.add(access);
            menuItems.add(access);
        }

        add(help);
        menus.add(help);

        //Setup design
        for (JMenu menu : menus) {
            menu.setFont(font1);
            menu.getPopupMenu().setBorder(BorderFactory.createLineBorder(bgColor.darker()));
            menu.setForeground(Color.white);
        }

        for (JMenuItem menuItem : menuItems) {
            menuItem.setOpaque(true);
            menuItem.setBackground(bgColor);
            menuItem.setForeground(Color.white);
            menuItem.setFont(menuitemfont);
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() + 1, getHeight() + 1);
    }
}