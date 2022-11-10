package com.cabg.gui;

import com.cabg.core.EngineThemes;
import com.cabg.core.EngineVariables;
import com.cabg.inputhandlers.ExtendedKeyAdapter;
import com.cabg.inputhandlers.ExtendedWindowAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.awt.Component.LEFT_ALIGNMENT;

public class SampleFunctions {
    private static SampleFunctions sampleFunctions = null;
    public static JFrame frame;

    public static void getInstance(JFrame parent) {
        if (sampleFunctions == null) sampleFunctions = new SampleFunctions(parent);
        frame.toFront();
    }

    private SampleFunctions(JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame();
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(444, 322);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            InputStream fin = getClass().getResourceAsStream("/ParticleGraphSamples.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = br.readLine()) != null) {
                listModel.addElement(line.trim());
            }
            br.close();
            fin.close();
        } catch (Exception e) {
            ExceptionLogger.append(e);
        }

        frame.setTitle("Sample Functions - " + listModel.size());

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) graphSelectedSample(list);
        }));
        if (listModel.size() > 0) {
            list.setSelectedIndex((int) (Math.random() * listModel.size()));
        }
        panel.add(list, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(listScroller);

        JButton graph = new JButton("Graph Selection");
        graph.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        graph.addActionListener(e -> graphSelectedSample(list));
        panel.add(graph, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void graphSelectedSample(JList<String> list) {
        String selectedSampleFunction = list.getSelectedValue();
        ParticleGrapher.textFields[0].setText(selectedSampleFunction);
        ParticleGrapher.threadGraph(selectedSampleFunction);
    }

    private void close() {
        frame.dispose();
        sampleFunctions = null;
    }
}
