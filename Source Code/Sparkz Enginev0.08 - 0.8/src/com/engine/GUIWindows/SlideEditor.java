package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import static com.engine.EngineHelpers.EConstants.*;

import com.engine.Interfaces_Extensions.WindowClosing;
import com.engine.JComponents.CSlider;
import com.engine.Utilities.Settings;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SlideEditor {
    private static SlideEditor sliderUI = null;
    public static JFrame frame;
    private final int MAX_TICK_LINES = 500;
    public JPanel ptoptionspanel,rfoptionspanel, ptssopanel, ptspeedspanel;

    public static JSlider ptsslider, ptdrslider, ptfrslider,
            ptlifeslider,ptwindslider, ptjitterslider,
            ptscminslider, ptscmaxslider, ptfrmaxslider,
            ptfrminslider, ptdragminslider,ptdragmaxslider,
            ptdrspeedslider, ptfrspeedminslider, ptscspeedminslider;

    private Label ptslabel, ptsamount,drlabel,dramount,frlabel,
            framount,ptlifelabel,ptlifeamount,ptwindlabel,ptwindamount,
            ptjitterlabel,ptjitteramount,ptscslabel,ptscminlabel,ptscmaxlabel,
            ptfrslabel,ptfrmaxlabel,ptfrminlabel,ptdragmaxlabel,ptdragminlabel,
            ptdraglabel, ptscspeedlabel, ptscspeedmin,ptfrspeedlabel,ptfrspeedminlabel,ptdrspeedlabel,
            ptdrminspeedlabel, lblMin, lblMax, label, label_1, label_2, label_3;

    //public static void main(String[] args) {new SlideEditor();}

    public static SlideEditor getInstance() {
        if (sliderUI == null) {sliderUI = new SlideEditor();}frame.toFront(); return sliderUI;
    }

    private SlideEditor() {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch (Exception e1){e1.printStackTrace();}
        frame = new JFrame("Particle Slide Editor");
        frame.setIconImage(Settings.getIcon());
        frame.setSize(489, 690);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(EFrame);

        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.setBounds(0, 0, 483, 653);
        frame.add(jTabbedPane);

        ptoptionspanel = new JPanel(null);
        jTabbedPane.addTab("Particle Options", null, ptoptionspanel, null);

        int MAX_TICKS = 49;
        ptsslider = new CSlider(0, 200, 1, new Rectangle(10, 60, 463, 43), 1, MAX_TICKS, true, true, true);
        ptsslider.addChangeListener(e -> {
            int particleSize = ptsslider.getValue(); ptsamount.setText(Double.toString(particleSize));
            EngineMethods.setParticleSize(particleSize); setPaintLabels(ptsslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptsslider);

        ptslabel = new Label("Particle Size:");
        ptslabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptslabel.setBackground(Color.lightGray);
        ptslabel.setBounds(10, 32, 106, 22);
        ptoptionspanel.add(ptslabel);

        ptsamount = new Label(Double.toString(ptsslider.getValue()));
        ptsamount.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptsamount.setAlignment(Label.CENTER);
        ptsamount.setBounds(149, 32, 187, 22);
        ptoptionspanel.add(ptsamount);

        drlabel = new Label("Drag Amount:");
        drlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        drlabel.setBackground(Color.lightGray);
        drlabel.setBounds(10, 245, 106, 22);
        ptoptionspanel.add(drlabel);

        ptdrslider = new CSlider(0, 200, dragAmount, new Rectangle(10, 273, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrslider.addChangeListener(e -> {
            int particleDrag = ptdrslider.getValue(); dramount.setText(Integer.toString(particleDrag));
            EngineMethods.setDragAmount(particleDrag); setPaintLabels(ptdrslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptdrslider);

        dramount = new Label(Double.toString(dragAmount));
        dramount.setFont(new Font("Dialog", Font.PLAIN, 17));
        dramount.setAlignment(Label.CENTER);
        dramount.setBounds(149, 245, 187, 22);
        ptoptionspanel.add(dramount);

        frlabel = new Label("Fireworks Amount:");
        frlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        frlabel.setBackground(Color.lightGray);
        frlabel.setBounds(10, 469, 142, 22);
        ptoptionspanel.add(frlabel);

        ptfrslider = new CSlider(0, 400, fireworksAmount, new Rectangle(10, 497, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrslider.addChangeListener(e -> {
            int fireworksAmount1 = ptfrslider.getValue(); framount.setText(Integer.toString(fireworksAmount1));
            EngineMethods.setFireworksAmount(fireworksAmount1); setPaintLabels(ptfrslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptfrslider);

        framount = new Label(Double.toString(fireworksAmount));
        framount.setFont(new Font("Dialog", Font.PLAIN, 17));
        framount.setAlignment(Label.CENTER);
        framount.setBounds(169, 469, 187, 22);
        ptoptionspanel.add(framount);

        JButton btnEditSize = new JButton("Edit Size");
        btnEditSize.addActionListener(e -> EngineMethods.setMaxParticleSizeSlider());
        btnEditSize.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditSize.setBounds(169, 147, 137, 39);
        ptoptionspanel.add(btnEditSize);

        JButton btnEditDrag = new JButton("Edit Drag");
        btnEditDrag.addActionListener(e -> EngineMethods.setMaxDragSlider());
        btnEditDrag.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditDrag.setBounds(172, 363, 134, 39);
        ptoptionspanel.add(btnEditDrag);

        JButton btnEditFireworks = new JButton("Edit Fireworks");
        btnEditFireworks.addActionListener(e -> EngineMethods.setMaxFireworksSlider());
        btnEditFireworks.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditFireworks.setBounds(169, 561, 144, 39);
        ptoptionspanel.add(btnEditFireworks);

        rfoptionspanel = new JPanel(null);
        jTabbedPane.addTab("Real Fireworks Options", null, rfoptionspanel, null);

        ptlifelabel = new Label("Particle Life:");
        ptlifelabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptlifelabel.setBackground(Color.lightGray);
        ptlifelabel.setBounds(10, 39, 106, 22);
        rfoptionspanel.add(ptlifelabel);

        ptlifeamount = new Label(Double.toString(baseLife));
        ptlifeamount.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptlifeamount.setAlignment(Label.CENTER);
        ptlifeamount.setBounds(149, 39, 187, 22);
        rfoptionspanel.add(ptlifeamount);

        ptlifeslider = new CSlider(0, 200, baseLife, new Rectangle(10, 67, 463, 43), 1, MAX_TICKS, true, true, true);
        ptlifeslider.addChangeListener(e -> {
            int lifeAmount = ptlifeslider.getValue(); ptlifeamount.setText(Integer.toString(lifeAmount));
            EngineMethods.setLifeAmount(lifeAmount); setPaintLabels(ptlifeslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptlifeslider);

        ptwindslider = new CSlider(0, 200, rfWind, new Rectangle(10, 257, 463, 43), 1, MAX_TICKS, true, true, true);
        ptwindslider.addChangeListener(e -> {
            int windAmount = ptwindslider.getValue(); ptwindamount.setText(Integer.toString(windAmount));
            EngineMethods.setWindAmount(windAmount); setPaintLabels(ptwindslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptwindslider);

        ptwindamount = new Label(Double.toString(rfWind));
        ptwindamount.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptwindamount.setAlignment(Label.CENTER);
        ptwindamount.setBounds(149, 229, 187, 22);
        rfoptionspanel.add(ptwindamount);

        ptwindlabel = new Label("Particle Wind:");
        ptwindlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptwindlabel.setBackground(Color.lightGray);
        ptwindlabel.setBounds(10, 229, 106, 22);
        rfoptionspanel.add(ptwindlabel);

        ptjitterslider = new CSlider(0, 200, rfJitter, new Rectangle(10, 468, 463, 43), 1, MAX_TICKS, true, true, true);
        ptjitterslider.addChangeListener(e -> {
            int jitterAmount = ptjitterslider.getValue(); ptjitteramount.setText(Integer.toString(jitterAmount));
            EngineMethods.setJitterAmount(jitterAmount); setPaintLabels(ptjitterslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptjitterslider);

        ptjitteramount = new Label(Double.toString(rfJitter));
        ptjitteramount.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptjitteramount.setAlignment(Label.CENTER);
        ptjitteramount.setBounds(149, 440, 187, 22);
        rfoptionspanel.add(ptjitteramount);

        ptjitterlabel = new Label("Particle Jitter:");
        ptjitterlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptjitterlabel.setBackground(Color.lightGray);
        ptjitterlabel.setBounds(10, 440, 106, 22);
        rfoptionspanel.add(ptjitterlabel);

        JButton btnEditBaseLife = new JButton("<html>Edit Base Life <br> <h4 style='text-align:center;color:blue'>(Single Particle)</h4></html>");
        btnEditBaseLife.addActionListener(e -> EngineMethods.baseLifeAmount());
        btnEditBaseLife.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditBaseLife.setBounds(27, 134, 186, 66);
        rfoptionspanel.add(btnEditBaseLife);

        JButton btnEditRfLife = new JButton("<html>Edit Real Fireworks Life <br> <h4 style='text-align:center;color:blue'>(Explosion Fireworks)</h4></html>");
        btnEditRfLife.addActionListener(e -> EngineMethods.rfLifeAmount());
        btnEditRfLife.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditRfLife.setBounds(227, 134, 226, 66);
        rfoptionspanel.add(btnEditRfLife);

        JButton btnEditWind = new JButton("Edit Wind");
        btnEditWind.addActionListener(e -> EngineMethods.windAmount());
        btnEditWind.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditWind.setBounds(177, 344, 139, 36);
        rfoptionspanel.add(btnEditWind);

        JButton btnEditJitter = new JButton("Edit Jitter");
        btnEditJitter.addActionListener(e -> EngineMethods.jitterAmount());
        btnEditJitter.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEditJitter.setBounds(177, 555, 137, 36);
        rfoptionspanel.add(btnEditJitter);

        //Particle Size Seed
        ptssopanel = new JPanel(null);
        jTabbedPane.addTab("Particle Size Options", null, ptssopanel, null);

        ptscslabel = new Label("Single Click Size:");
        ptscslabel.setForeground(Color.BLACK);
        ptscslabel.setBackground(Color.LIGHT_GRAY);
        ptscslabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptscslabel.setBounds(10, 25, 131, 22);
        ptssopanel.add(ptscslabel);

        ptscminlabel = new Label(Double.toString(singleClickSizeMinVal));
        ptscminlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptscminlabel.setAlignment(Label.CENTER);
        ptscminlabel.setBounds(256, 25, 146, 22);
        ptssopanel.add(ptscminlabel);

        ptscminslider = new CSlider(0, 200, (int)singleClickSizeMinVal, new Rectangle(10, 53, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscminslider.addChangeListener(e -> {
            int singleclickmin = ptscminslider.getValue(); ptscminlabel.setText(Integer.toString(singleclickmin));
            EngineMethods.setSingleClickSizeMin(singleclickmin); setPaintLabels(ptscminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptscminslider);

        ptscmaxslider = new CSlider(0, 200, (int)singleClickSizeMaxVal, new Rectangle(10, 152, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscmaxslider.addChangeListener(e -> {
            int singleclickmax = ptscmaxslider.getValue(); ptscmaxlabel.setText(Integer.toString(singleclickmax));
            EngineMethods.setSingleClickSizeMax(singleclickmax); setPaintLabels(ptscmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptscmaxslider);

        ptscmaxlabel = new Label(Double.toString(singleClickSizeMaxVal));
        ptscmaxlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptscmaxlabel.setAlignment(Label.CENTER);
        ptscmaxlabel.setBounds(256, 124, 146, 22);
        ptssopanel.add(ptscmaxlabel);

        //Fireworks
        ptfrmaxslider = new CSlider(0, 200, 1, new Rectangle(10, 354, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrmaxslider.addChangeListener(e -> {
            int fireworksmax = ptfrmaxslider.getValue(); ptfrmaxlabel.setText(Integer.toString(fireworksmax));
            EngineMethods.setFireworksSizeMax(fireworksmax); setPaintLabels(ptfrmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptfrmaxslider);

        ptfrmaxlabel = new Label(Double.toString(fireworksSizeMaxVal));
        ptfrmaxlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptfrmaxlabel.setAlignment(Label.CENTER);
        ptfrmaxlabel.setBounds(256, 326, 146, 22);
        ptssopanel.add(ptfrmaxlabel);

        ptfrminslider = new CSlider(0, 200, 1, new Rectangle(10, 255, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrminslider.addChangeListener(e -> {
            int fireworksmin = ptfrminslider.getValue(); ptfrminlabel.setText(Integer.toString(fireworksmin));
            EngineMethods.setFireworksSizeMin(fireworksmin); setPaintLabels(ptfrminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptfrminslider);

        ptfrminlabel = new Label(Double.toString(fireworksSizeMinVal));
        ptfrminlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptfrminlabel.setAlignment(Label.CENTER);
        ptfrminlabel.setBounds(256, 227, 146, 22);
        ptssopanel.add(ptfrminlabel);

        ptfrslabel = new Label("Fireworks Size:");
        ptfrslabel.setForeground(Color.BLACK);
        ptfrslabel.setBackground(Color.LIGHT_GRAY);
        ptfrslabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptfrslabel.setBounds(10, 227, 117, 22);
        ptssopanel.add(ptfrslabel);

        //Drag
        ptdragmaxslider = new CSlider(0, 200, (int)dragSizeMaxVal, new Rectangle(10, 555, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragmaxslider.addChangeListener(e -> {
            int dragmax = ptdragmaxslider.getValue(); ptdragmaxlabel.setText(Integer.toString(dragmax));
            EngineMethods.setDragSizeMax(dragmax); setPaintLabels(ptdragmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptdragmaxslider);

        ptdragmaxlabel = new Label(Double.toString(dragSizeMaxVal));
        ptdragmaxlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptdragmaxlabel.setAlignment(Label.CENTER);
        ptdragmaxlabel.setBounds(256, 527, 146, 22);
        ptssopanel.add(ptdragmaxlabel);

        ptdragminslider = new CSlider(0, 200, (int)dragSizeMinVal, new Rectangle(10, 456, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragminslider.addChangeListener(e -> {
            int dragmin = ptdragminslider.getValue(); ptdragminlabel.setText(Integer.toString(dragmin));
            EngineMethods.setDragSizeMin(dragmin); setPaintLabels(ptdragminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptdragminslider);

        ptdragminlabel = new Label(Double.toString(dragSizeMinVal));
        ptdragminlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptdragminlabel.setAlignment(Label.CENTER);
        ptdragminlabel.setBounds(256, 428, 146, 22);
        ptssopanel.add(ptdragminlabel);

        ptdraglabel = new Label("Drag Size:");
        ptdraglabel.setForeground(Color.BLACK);
        ptdraglabel.setBackground(Color.LIGHT_GRAY);
        ptdraglabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptdraglabel.setBounds(10, 428, 82, 22);
        ptssopanel.add(ptdraglabel);

        lblMin = new Label("Min:");
        lblMin.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblMin.setBounds(189, 25, 46, 14);
        ptssopanel.add(lblMin);

        lblMax = new Label("Max:");
        lblMax.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblMax.setBounds(189, 124, 46, 14);
        ptssopanel.add(lblMax);

        label = new Label("Min:");
        label.setFont(new Font("Tahoma", Font.PLAIN, 17));
        label.setBounds(189, 227, 46, 14);
        ptssopanel.add(label);

        label_1 = new Label("Max:");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        label_1.setBounds(189, 326, 46, 14);
        ptssopanel.add(label_1);

        label_2 = new Label("Min:");
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        label_2.setBounds(189, 428, 46, 14);
        ptssopanel.add(label_2);

        label_3 = new Label("Max:");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 17));
        label_3.setBounds(189, 527, 46, 14);
        ptssopanel.add(label_3);

        //Particle speed options
        ptspeedspanel = new JPanel(null);
        jTabbedPane.addTab("Particle Speed Options", null, ptspeedspanel, null);

        ptscspeedlabel = new Label("Single Click Speed:");
        ptscspeedlabel.setForeground(Color.BLACK);
        ptscspeedlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptscspeedlabel.setBackground(Color.LIGHT_GRAY);
        ptscspeedlabel.setBounds(10, 21, 146, 22);
        ptspeedspanel.add(ptscspeedlabel);

        ptscspeedmin = new Label(Double.toString(singleClickSpeedVal));
        ptscspeedmin.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptscspeedmin.setAlignment(Label.CENTER);
        ptscspeedmin.setBounds(190, 21, 146, 22);
        ptspeedspanel.add(ptscspeedmin);

        ptscspeedminslider = new CSlider(0, 200, (singleClickSpeedVal <= 1) ? 1 : (int)(singleClickSpeedVal), new Rectangle(10, 82, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscspeedminslider.addChangeListener(e -> {
            int singleclickspeed = ptscspeedminslider.getValue(); ptscspeedmin.setText(Integer.toString(singleclickspeed));
            EngineMethods.setSingleClickSpeed(singleclickspeed); setPaintLabels(ptscspeedminslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptscspeedminslider);

        //fireworks
        ptfrspeedminlabel = new Label(Double.toString(fireworksSpeedVal));
        ptfrspeedminlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptfrspeedminlabel.setAlignment(Label.CENTER);
        ptfrspeedminlabel.setBounds(190, 223, 146, 22);
        ptspeedspanel.add(ptfrspeedminlabel);

        ptfrspeedlabel = new Label("Fireworks Speed:");
        ptfrspeedlabel.setForeground(Color.BLACK);
        ptfrspeedlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptfrspeedlabel.setBackground(Color.LIGHT_GRAY);
        ptfrspeedlabel.setBounds(10, 223, 132, 22);
        ptspeedspanel.add(ptfrspeedlabel);

        ptfrspeedminslider = new CSlider(0, 200, (fireworksSpeedVal <= 1) ? 1 : (int)(fireworksSpeedVal), new Rectangle(10, 284, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrspeedminslider.addChangeListener(e -> {
            int fireworksSpeed = ptfrspeedminslider.getValue(); ptfrspeedminlabel.setText(Integer.toString(fireworksSpeed));
            EngineMethods.setFireworksSpeed(fireworksSpeed); setPaintLabels(ptfrspeedminslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptfrspeedminslider);

        //drag
        ptdrspeedlabel = new Label("Drag Speed:");
        ptdrspeedlabel.setForeground(Color.BLACK);
        ptdrspeedlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptdrspeedlabel.setBackground(Color.LIGHT_GRAY);
        ptdrspeedlabel.setBounds(10, 424, 97, 22);
        ptspeedspanel.add(ptdrspeedlabel);

        ptdrminspeedlabel = new Label(Double.toString(dragSpeedVal));
        ptdrminspeedlabel.setFont(new Font("Dialog", Font.PLAIN, 17));
        ptdrminspeedlabel.setAlignment(Label.CENTER);
        ptdrminspeedlabel.setBounds(190, 424, 146, 22);
        ptspeedspanel.add(ptdrminspeedlabel);

        ptdrspeedslider = new CSlider(0, 200, (dragSpeedVal <= 1) ? 1 : (int)(dragSpeedVal), new Rectangle(10, 485, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrspeedslider.addChangeListener(e -> {
            int dragSpeed = ptdrspeedslider.getValue(); ptdrminspeedlabel.setText(Integer.toString(dragSpeed));
            EngineMethods.setDragSpeed(dragSpeed); setPaintLabels(ptdrspeedslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptdrspeedslider);
        frame.setVisible(true);
    }

    private static void setPaintLabels(JSlider slider, int maxTickLines) {
        if (slider.getMaximum() > maxTickLines) {slider.setPaintLabels(false);}else {slider.setPaintLabels(true);}
    }
    public void close(){sliderUI = null; frame.dispose();}
}