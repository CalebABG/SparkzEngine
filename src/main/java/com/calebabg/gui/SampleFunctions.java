package com.calebabg.gui;

import com.calebabg.core.EngineThemes;
import com.calebabg.core.EngineVariables;
import com.calebabg.inputs.ExtendedKeyAdapter;
import com.calebabg.inputs.ExtendedWindowAdapter;
import com.calebabg.utilities.FontUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import static java.awt.Component.LEFT_ALIGNMENT;

public class SampleFunctions {
    private static SampleFunctions instance = null;

    private final JFrame frame;

    public static void getInstance(JFrame parent) {
        if (instance == null) instance = new SampleFunctions(parent);
        instance.frame.toFront();
    }

    private SampleFunctions(JFrame parent) {
        EngineThemes.setLookAndFeel();

        frame = new JFrame();
        frame.setIconImage(EngineVariables.iconImage);
        frame.setSize(444, 322);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new ExtendedWindowAdapter(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/text/GraphSamples.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                listModel.addElement(line.trim());
            }
        } catch (Exception e) {
            ExceptionWindow.append(e);
        }

        frame.setTitle("Sample Functions - " + listModel.size());

        JList<String> list = new JList<>(listModel);
        list.setFont(FontUtil.PLAIN_18);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addKeyListener(new ExtendedKeyAdapter.KeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) graphSelectedSample(list);
        }));

        if (!listModel.isEmpty()) list.setSelectedIndex((int) (Math.random() * listModel.size()));
        panel.add(list, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(listScroller);

        JButton graph = new JButton("Graph Selection");
        graph.setFont(FontUtil.PLAIN_18);
        graph.addActionListener(e -> graphSelectedSample(list));
        panel.add(graph, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void graphSelectedSample(JList<String> list) {
        String selectedSampleFunction = list.getSelectedValue();
        ParticleGraphEditor.setInputTextField(selectedSampleFunction);
        ParticleGraphEditor.threadGraph(selectedSampleFunction);
    }

    private void close() {
        frame.dispose();
        instance = null;
    }
}
