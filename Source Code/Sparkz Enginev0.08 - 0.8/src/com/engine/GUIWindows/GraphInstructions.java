package com.engine.GUIWindows;

import static com.engine.EngineHelpers.EConstants.*;
import com.engine.EngineHelpers.GUIText;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GraphInstructions {
    private static JFrame frame;
    private static GraphInstructions graphInstructions = null;

    public static GraphInstructions getInstance(JFrame parent) {
        if (graphInstructions == null) {graphInstructions = new GraphInstructions(parent);}frame.toFront(); return graphInstructions;
    }

    private GraphInstructions(JFrame parent) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception x){x.printStackTrace();}
        frame = new JFrame("Particle Graph Instructions");
        frame.setIconImage(Settings.getIcon());
        frame.setSize((int) (width * .38), (int) (height * .55));
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(parent);
        frame.addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) {if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {close();}}});

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);

        JLabel label = new JLabel(GUIText.particleGraphInstructions());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(label);
        panel.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    private void close(){graphInstructions = null; frame.dispose();}
}
