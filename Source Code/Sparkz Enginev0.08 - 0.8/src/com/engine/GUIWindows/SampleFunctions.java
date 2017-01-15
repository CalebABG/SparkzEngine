package com.engine.GUIWindows;

import com.engine.MGrapher.ParticleGraph;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import static java.awt.Component.LEFT_ALIGNMENT;

public class SampleFunctions {
    private static SampleFunctions sampleFunctions = null;
    public static JFrame frame;

//    public static void main(String[] args) { getInstance(null);}

    public static SampleFunctions getInstance(JFrame parent){
        if (sampleFunctions == null) {sampleFunctions = new SampleFunctions(parent);} frame.toFront(); return sampleFunctions;
    }

    private SampleFunctions(JFrame parent) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame();
        frame.setIconImage(Settings.getIcon());
        frame.setSize(444, 322);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            FileInputStream fin = new FileInputStream(("." + Paths.get("/" + "src") + "/" + "Samples.ev"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = br.readLine()) != null) {listModel.addElement(line.trim());}
            br.close(); fin.close();
        } catch (Exception e){e.printStackTrace();}

        frame.setTitle("Sample Functions - " + listModel.size());

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Tahoma", Font.PLAIN, 18));
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (listModel.size() > 0) {list.setSelectedIndex((int) (Math.random() * listModel.size()));}
        panel.add(list, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(listScroller);

        JButton graph = new JButton("Graph Function :D");
        graph.setFont(new Font("Tahoma", Font.PLAIN, 18));
        graph.addActionListener(e -> {
            String selectedSampleFunction = list.getSelectedValue();
            ParticleGraph.textFields[0].setText(selectedSampleFunction);
            ParticleGraph.threadGraph(1, selectedSampleFunction);
        });
        panel.add(graph, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void close(){sampleFunctions = null; frame.dispose();}
}
