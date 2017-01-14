package com.engine.GUIWindows;

import com.engine.MGrapher.ParticleGraph;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

import static java.awt.Component.LEFT_ALIGNMENT;

public class SampleFunctions {
    private static SampleFunctions songList = null;
    public static JFrame frame;
    public static DefaultListModel<String> listModel = new DefaultListModel<>();
    public static List<String> functions = null;
    public JList<String> list;
    public JButton graph;

    public static SampleFunctions getInstance(JFrame parent){
        if (songList == null) {songList = new SampleFunctions(parent);} frame.toFront(); return songList;
    }

    private SampleFunctions(JFrame parent) {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Sample Functions");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(444, 322);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent) {close();}});
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        try {
            InputStream fin = SampleFunctions.class.getResourceAsStream("/Samples.ev");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            functions = Collections.synchronizedList(new ArrayList<>());
            String line;
            while ((line = br.readLine()) != null) {functions.add(line.trim());}
            br.close();
            fin.close();
            for (String s : functions) {listModel.addElement(s);}
        } catch (Exception e){e.printStackTrace();}

        list = new JList<>(listModel);
        list.setFont(new Font("Tahoma", Font.PLAIN, 18));
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        panel.add(list, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(listScroller);

        graph = new JButton("Graph Function :D");
        graph.setFont(new Font("Tahoma", Font.PLAIN, 18));
        graph.addActionListener(e -> {
            String selectedSampleFunction = list.getSelectedValue();
            ParticleGraph.textFields[0].setText(selectedSampleFunction);
            ParticleGraph.threadGraph(1, selectedSampleFunction);
        });
        panel.add(graph, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void close(){
        frame.dispose(); songList = null;
    }
}
