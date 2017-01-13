package com.engine.GUIWindows;

import com.engine.EngineHelpers.GUIText;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static com.engine.EngineHelpers.EConstants.*;

public class EngineInstructions {
    private static EngineInstructions engineInstructions = null;
    private static JFrame frame;
    //public static void main(String[] args) {new EngineInstructions();}

    public static EngineInstructions getInstance() {
        if (engineInstructions == null) {engineInstructions = new EngineInstructions();}frame.toFront(); return engineInstructions;
    }

    private EngineInstructions() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame("Particle Engine Instructions");
        frame.setIconImage(Settings.getIcon());
        frame.setSize((int) (width * .41), (int) (height * .55));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(EFrame);
        frame.addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {close();}}});

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel label = new JLabel(GUIText.instructions());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private void close(){engineInstructions = null; frame.dispose();}
}
